package med.voll.api.domain.consulta;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.pacientes.Paciente;

import java.time.LocalDateTime;

    @Table(name = "consultas")
    @Entity(name = "Consulta")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(of = "id")

    public class Consulta {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "medico_id")
        private Medico medico;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "paciente_id")
        private Paciente paciente;

//        @Column(name = "motivo_cancelamento")
//        @Enumerated(EnumType.STRING)
//        private MotivoCancelamento motivoCancelamento;
//
        private boolean cancelada = false;

        @Enumerated(EnumType.STRING)
        private MotivoCancelamento motivoCancelamento;


        private LocalDateTime data;


        public Consulta(Medico medico, Paciente paciente, LocalDateTime data) {
            this.medico = medico;
            this.paciente = paciente;
            this.data = data;
        }


        public void cancelar(MotivoCancelamento motivo) {
            this.motivoCancelamento = motivo;
            this.cancelada = true;
        }

        public void setMotivoCancelamento(@NotNull MotivoCancelamento motivo) {
            this.motivoCancelamento = motivo;
            this.cancelada = true;
        }
    }
