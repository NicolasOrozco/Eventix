package co.edu.uniquindio.poo.repository;

import co.edu.uniquindio.poo.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByNombreContainingIgnoreCase(String nombre);
}
