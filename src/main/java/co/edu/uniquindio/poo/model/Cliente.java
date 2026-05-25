package co.edu.uniquindio.poo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Entidad que representa a un cliente registrado en Eventix.
 */
@Entity
public class Cliente {
    @Id
    private String id; // CC [cite: 373]
    private String nombre;
    private String email;
    private String telefono;

    /**
     * Constructor requerido por JPA.
     */
    public Cliente() {}

    /**
     * Crea un cliente con los datos basicos usados por el sistema.
     *
     * @param id identificacion del cliente.
     * @param nombre nombre completo.
     * @param email correo electronico.
     * @param telefono numero telefonico.
     */
    public Cliente(String id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
