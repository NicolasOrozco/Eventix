package co.edu.uniquindio.poo.repository;

import co.edu.uniquindio.poo.model.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para operaciones de persistencia sobre boletos.
 */
public interface BoletoRepository extends JpaRepository<Boleto, Long> {}
