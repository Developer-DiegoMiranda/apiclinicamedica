package med.voll.api.domain.consulta;

import java.time.LocalDate;

public record DadosDetalhamentoConsulta(Long id, Long idMedico, Long idPaciente, LocalDate data) {

    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), LocalDate.from(consulta.getData()));
    }
}
