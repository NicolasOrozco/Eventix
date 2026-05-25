package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.dto.CompraRequestDTO;
import co.edu.uniquindio.poo.model.*;
import co.edu.uniquindio.poo.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EventixService {

    private final EventoRepository eventoRepository;
    private final ClienteRepository clienteRepository;
    private final BoletoRepository boletoRepository;

    public EventixService(EventoRepository eventoRepository, ClienteRepository clienteRepository, BoletoRepository boletoRepository) {
        this.eventoRepository = eventoRepository;
        this.clienteRepository = clienteRepository;
        this.boletoRepository = boletoRepository;
    }

    // --- CRUD DE EVENTOS (Módulo 1) ---

    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }

    public Evento guardarEvento(Evento evento) {
        // RN-02: La fecha del evento debe ser futura [cite: 414]
        if (evento.getFecha() == null || evento.getFecha().isBefore(LocalDate.now().plusDays(1))) {
            throw new IllegalArgumentException("Invariante violada: La fecha del evento debe ser futura.");
        }
        // Invariantes del modelo
        if (evento.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a cero.");
        }
        if (evento.getPrecioGeneral() <= 0) {
            throw new IllegalArgumentException("Invariante violada: El precio general debe ser mayor a cero ($>0$).");
        }
        if (evento.getPrecioVIP() <= evento.getPrecioGeneral()) {
            throw new IllegalArgumentException("Invariante violada: El precio VIP debe ser estrictamente mayor al precio general.");
        }

        // Al crear un evento nuevo, las boletas disponibles equivalen a su capacidad total
        if (evento.getId() == null) {
            evento.setBoletasDisponibles(evento.getCapacidad());
        }
        return eventoRepository.save(evento);
    }

    public void eliminarEvento(Long id) {
        Evento ev = eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado."));
        // Flujo alterno A3: No se puede eliminar si ya tiene ventas registradas [cite: 523]
        if (ev.getBoletasDisponibles() < ev.getCapacidad()) {
            throw new IllegalArgumentException("No se puede eliminar un evento que ya cuenta con boletas vendidas.");
        }
        eventoRepository.deleteById(id);
    }

    // --- TRANSACCIÓN PRINCIPAL: COMPRAR BOLETO (Módulo 2) ---

    @Transactional
    public List<Boleto> procesarCompra(CompraRequestDTO request) {
        // Validar límite máximo por cliente (Caso de aceptación 4: Máximo 5 boletas)
        if (request.getCantidad() > 5) {
            throw new IllegalArgumentException("Validación fallida: No se permite comprar más de 5 boletas en una única transacción.");
        }

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("El cliente con ID ingresado no existe."));

        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new IllegalArgumentException("El evento seleccionado no existe."));

        // RF-09: Impedir compras para eventos inactivos/cancelados [cite: 405]
        if (!evento.isEstaActivo()) {
            throw new IllegalArgumentException("Operación denegada: El evento seleccionado está cancelado o inactivo.");
        }

        // RN-01: Validar capacidad [cite: 413]
        if (evento.getBoletasDisponibles() < request.getCantidad()) {
            throw new IllegalArgumentException("Sin cupos: No hay suficientes boletas disponibles para cumplir con la solicitud.");
        }

        // Modificar stock (Invariante boletasVendidas <= capacidad) [cite: 467]
        evento.setBoletasDisponibles(evento.getBoletasDisponibles() - request.getCantidad());
        eventoRepository.save(evento);

        double precioFinal = request.isEsVIP() ? evento.getPrecioVIP() : evento.getPrecioGeneral(); // RN-06
        List<Boleto> boletosGenerados = new ArrayList<>();

        for (int i = 0; i < request.getCantidad(); i++) {
            Boleto boleto = new Boleto();
            // RN-03 / RF-07: Generar código único por cada boleto [cite: 403, 415]
            boleto.setCodigo("EVX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            boleto.setEsVIP(request.isEsVIP());
            boleto.setPrecio(precioFinal);
            boleto.setFechaCompra(LocalDate.now()); // RN-05 / RF-08 [cite: 404, 417]
            boleto.setCliente(cliente);
            boleto.setEvento(evento);

            boletosGenerados.add(boletoRepository.save(boleto));
        }

        return boletosGenerados;
    }
}
