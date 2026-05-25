package co.edu.uniquindio.poo.dto;

/**
 * DTO de entrada para solicitar la compra de boletos.
 *
 * <p>Agrupa la identificacion del cliente, el evento, la cantidad requerida y
 * si la compra corresponde a boletos VIP.</p>
 */
public class CompraRequestDTO {
    private String clienteId;
    private Long eventoId;
    private int cantidad;
    private boolean esVIP;

    // Getters y Setters
    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }
    public Long getEventoId() { return eventoId; }
    public void setEventoId(Long eventoId) { this.eventoId = eventoId; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public boolean isEsVIP() { return esVIP; }
    public void setEsVIP(boolean esVIP) { this.esVIP = esVIP; }
}
