package co.edu.uniquindio.poo.dto;

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
