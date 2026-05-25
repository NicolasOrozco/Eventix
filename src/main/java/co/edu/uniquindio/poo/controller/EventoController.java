package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.Evento;
import co.edu.uniquindio.poo.service.EventixService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*") // Permite la interacción directa del frontend
public class EventoController {

    private final EventixService service;

    public EventoController(EventixService service) {
        this.service = service;
    }

    @GetMapping
    public List<Evento> listar() {
        return service.listarEventos();
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Evento evento) {
        try {
            return ResponseEntity.ok(service.guardarEvento(evento));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Evento evento) {
        try {
            evento.setId(id);
            return ResponseEntity.ok(service.guardarEvento(evento));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminarEvento(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}