package med.voll.api.domain.pacientes;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;


@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    private  Boolean ativo;

    @Embedded
    private Endereco endereco;

    public Paciente(DadosCadastroPacientes dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());

    }


    public void atualizarInformacoes(@Valid DadosAtualizacaoPaciente dadosAtualizacaoPaciente) {

            if (dadosAtualizacaoPaciente.telefone() != null){
                this.telefone = dadosAtualizacaoPaciente.telefone();
            }
            if (dadosAtualizacaoPaciente.endereco() != null){
                this.endereco.atualizarInformacoes(dadosAtualizacaoPaciente.endereco());
            }
        }
    }

