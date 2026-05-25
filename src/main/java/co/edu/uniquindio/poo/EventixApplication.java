package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Evento;
import co.edu.uniquindio.poo.repository.ClienteRepository;
import co.edu.uniquindio.poo.repository.EventoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

/**
 * Punto de entrada de la aplicacion Spring Boot de Eventix.
 */
@SpringBootApplication
public class EventixApplication {

    /**
     * Inicia la aplicacion.
     *
     * @param args argumentos de linea de comandos recibidos por Spring Boot.
     */
    public static void main(String[] args) {
        SpringApplication.run(EventixApplication.class, args);
    }

    /**
     * Carga datos iniciales para facilitar pruebas manuales del MVP.
     *
     * @param clienteRepo repositorio usado para crear clientes de prueba.
     * @param eventoRepo repositorio usado para crear eventos de prueba.
     * @return tarea que Spring ejecuta al arrancar la aplicacion.
     */
    @Bean
    CommandLineRunner cargarDatosDePrueba(ClienteRepository clienteRepo, EventoRepository eventoRepo) {
        return args -> {
            // Cargar Clientes obligatorios del documento
            clienteRepo.save(new Cliente("1090274067", "Ana Torres", "ana@email.com", "3001234567"));
            clienteRepo.save(new Cliente("1092852380", "Juan Pérez", "juan@email.com", "3007654321"));
            clienteRepo.save(new Cliente("1111111111", "Laura Gómez", "laura@email.com", "3114567890"));
            clienteRepo.save(new Cliente("2222222222", "Daniel Rojas", "daniel@email.com", "3129876543"));
            clienteRepo.save(new Cliente("3333333333", "Camila Ruiz", "camila@email.com", "3150001122"));
            clienteRepo.save(new Cliente("4444444444", "Felipe Castro", "felipe@email.com", "3173334455"));

            // Cargar Eventos obligatorios del documento (Asegurando fechas futuras para evitar infringir RN-02) [cite: 414, 593]
            crearEventoDemo(eventoRepo, "Concierto Rock", LocalDate.now().plusMonths(2), "Estadio Centenario", 100, 50000, 120000);
            crearEventoDemo(eventoRepo, "Festival Café", LocalDate.now().plusMonths(1), "Parque del Café", 200, 20000, 45000);
            crearEventoDemo(eventoRepo, "Concierto Vallenato", LocalDate.now().plusWeeks(3), "Plaza de Bolívar", 150, 40000, 90000);
            crearEventoDemo(eventoRepo, "Festival Food", LocalDate.now().plusMonths(3), "Centro de Convenciones", 300, 15000, 30000);

            // Evento inactivo de prueba para validar el flujo alterno A1 (Evento Cancelado) [cite: 498]
            Evento cineBajoEstrellas = new Evento();
            cineBajoEstrellas.setNombre("Cine Bajo Las Estrellas");
            cineBajoEstrellas.setFecha(LocalDate.now().plusMonths(4));
            cineBajoEstrellas.setLugar("Uniquindío Campus");
            cineBajoEstrellas.setCapacidad(50);
            cineBajoEstrellas.setBoletasDisponibles(50);
            cineBajoEstrellas.setPrecioGeneral(10000);
            cineBajoEstrellas.setPrecioVIP(25000);
            cineBajoEstrellas.setEstaActivo(false); // Inactivo / Cancelado
            eventoRepo.save(cineBajoEstrellas);
        };
    }

    /**
     * Crea y persiste un evento activo de demostracion.
     *
     * @param repo repositorio de eventos.
     * @param nombre nombre visible del evento.
     * @param fecha fecha programada del evento.
     * @param lugar ubicacion del evento.
     * @param cap capacidad total y boletas iniciales disponibles.
     * @param pGen precio de boleto general.
     * @param pVip precio de boleto VIP.
     */
    private void crearEventoDemo(EventoRepository repo, String nombre, LocalDate fecha, String lugar, double cap, double pGen, double pVip) {
        Evento e = new Evento();
        e.setNombre(nombre);
        e.setFecha(fecha);
        e.setLugar(lugar);
        e.setCapacidad(cap);
        e.setBoletasDisponibles(cap);
        e.setPrecioGeneral(pGen);
        e.setPrecioVIP(pVip);
        e.setEstaActivo(true);
        repo.save(e);
    }
}
