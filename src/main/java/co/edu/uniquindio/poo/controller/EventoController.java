package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.Evento;
import co.edu.uniquindio.poo.service.EventixService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para administrar eventos.
 *
 * <p>Expone operaciones CRUD y traduce errores de validacion del servicio a
 * respuestas HTTP 400.</p>
 */
@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*") // Permite la interacción directa del frontend
public class EventoController {

    private final EventixService service;

    /**
     * Crea el controlador con el servicio de negocio de Eventix.
     *
     * @param service servicio que ejecuta las reglas de eventos.
     */
    public EventoController(EventixService service) {
        this.service = service;
    }

    /**
     * Lista todos los eventos registrados.
     *
     * @return eventos disponibles en el sistema.
     */
    @GetMapping
    public List<Evento> listar() {
        return service.listarEventos();
    }

    /**
     * Crea un evento a partir del cuerpo de la peticion.
     *
     * @param evento datos del evento nuevo.
     * @return respuesta con el evento creado o el mensaje de error de validacion.
     */
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Evento evento) {
        try {
            return ResponseEntity.ok(service.crearEvento(evento));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Actualiza los datos de un evento existente.
     *
     * @param id identificador del evento a modificar.
     * @param evento datos nuevos del evento.
     * @return respuesta con el evento actualizado o el mensaje de error de validacion.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Evento evento) {
        try {
            return ResponseEntity.ok(service.actualizarEvento(id, evento));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Elimina un evento si no tiene ventas asociadas.
     *
     * @param id identificador del evento a eliminar.
     * @return respuesta vacia en caso exitoso o mensaje de error de validacion.
     */
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
