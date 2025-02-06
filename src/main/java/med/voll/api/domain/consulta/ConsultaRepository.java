package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;
import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

    boolean existsByPacienteIdAndDataBetween(Long pacienteId, LocalDateTime inicio, LocalDateTime fim);
}
