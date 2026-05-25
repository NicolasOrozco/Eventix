package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.dto.CompraRequestDTO;
import co.edu.uniquindio.poo.service.EventixService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boletos")
@CrossOrigin(origins = "*")
public class BoletoController {

    private final EventixService service;

    public BoletoController(EventixService service) {
        this.service = service;
    }

    @PostMapping("/comprar")
    public ResponseEntity<?> comprar(@RequestBody CompraRequestDTO request) {
        try {
            return ResponseEntity.ok(service.procesarCompra(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
