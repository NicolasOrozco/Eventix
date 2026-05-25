package co.edu.uniquindio.poo.repository;

import co.edu.uniquindio.poo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para operaciones de persistencia sobre clientes.
 */
public interface ClienteRepository extends JpaRepository<Cliente, String> {}
