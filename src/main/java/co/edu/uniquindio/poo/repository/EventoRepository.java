package co.edu.uniquindio.poo.repository;

import co.edu.uniquindio.poo.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio JPA para operaciones de persistencia y consulta sobre eventos.
 */
public interface EventoRepository extends JpaRepository<Evento, Long> {
    /**
     * Busca eventos cuyo nombre contenga el texto indicado sin distinguir mayusculas.
     *
     * @param nombre fragmento de nombre a buscar.
     * @return eventos que coinciden con el criterio.
     */
    List<Evento> findByNombreContainingIgnoreCase(String nombre);
}
