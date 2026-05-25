package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.dto.CompraRequestDTO;
import co.edu.uniquindio.poo.service.EventixService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para operaciones relacionadas con boletos.
 */
@RestController
@RequestMapping("/api/boletos")
@CrossOrigin(origins = "*")
public class BoletoController {

    private final EventixService service;

    /**
     * Crea el controlador con el servicio que procesa las compras.
     *
     * @param service servicio central de Eventix.
     */
    public BoletoController(EventixService service) {
        this.service = service;
    }

    /**
     * Procesa una solicitud de compra de boletos.
     *
     * @param request datos del cliente, evento, cantidad y tipo de boleto.
     * @return boletos generados o mensaje de error de validacion.
     */
    @PostMapping("/comprar")
    public ResponseEntity<?> comprar(@RequestBody CompraRequestDTO request) {
        try {
            return ResponseEntity.ok(service.procesarCompra(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
