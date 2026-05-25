package co.edu.uniquindio.poo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad que representa un evento ofrecido por Eventix.
 *
 * <p>Conserva la capacidad total, las boletas disponibles, los precios por tipo
 * de entrada y el estado activo o cancelado del evento.</p>
 */
@Entity
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private LocalDate fecha;
    private String lugar;
    private double capacidad;
    private double boletasDisponibles;
    private double precioGeneral;
    private double precioVIP;
    private boolean estaActivo;

    /**
     * Constructor requerido por JPA.
     */
    public Evento() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public double getCapacidad() { return capacidad; }
    public void setCapacidad(double capacidad) { this.capacidad = capacidad; }
    public double getBoletasDisponibles() { return boletasDisponibles; }
    public void setBoletasDisponibles(double boletasDisponibles) { this.boletasDisponibles = boletasDisponibles; }
    public double getPrecioGeneral() { return precioGeneral; }
    public void setPrecioGeneral(double precioGeneral) { this.precioGeneral = precioGeneral; }
    public double getPrecioVIP() { return precioVIP; }
    public void setPrecioVIP(double precioVIP) { this.precioVIP = precioVIP; }
    public boolean isEstaActivo() { return estaActivo; }
    public void setEstaActivo(boolean estaActivo) { this.estaActivo = estaActivo; }
}
