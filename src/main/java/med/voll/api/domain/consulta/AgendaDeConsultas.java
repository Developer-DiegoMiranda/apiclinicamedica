package med.voll.api.domain.consulta;


import jakarta.validation.ValidationException;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.pacientes.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())){
           throw new ValidationException("Id do paciente informado não existe!");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do medico informado não existe!");
        }

        validadores.forEach(v -> v.validar(dados));


        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var medico = escolherMedico(dados);
        if (medico == null){

            throw new ValidacaoException("Não existe médico disponivel nessa data!");

        }

        var consulta = new Consulta(medico, paciente, dados.data());
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }



    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
       if (dados.idMedico() != null){
           return medicoRepository.getReferenceById(dados.idMedico());
       }

       if (dados.especialidade() == null){
           throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido!");
       }

       return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());

    }

public void cancelar(DadosCancelamentoConsulta dadosCancelamentoConsulta) {
        var consulta = consultaRepository.findById(dadosCancelamentoConsulta.idConsulta())
                .orElseThrow(() -> new ValidacaoException("Consulta não encontrada!"));

        if (consulta.getData().isBefore(LocalDateTime.now().plusHours(24))){
            throw new ValidacaoException("Consultas só podem ser canceladas com antecedência de 24 horas.");
        }

        consulta.setMotivoCancelamento(dadosCancelamentoConsulta.motivo());
        consultaRepository.save(consulta);
    }

}
