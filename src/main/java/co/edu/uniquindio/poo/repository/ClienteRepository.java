package co.edu.uniquindio.poo.repository;

import co.edu.uniquindio.poo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, String> {}
