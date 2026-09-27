package consultoriomedico.view;

import consultoriomedico.models.Cita;
import consultoriomedico.models.Medico;
import consultoriomedico.models.Paciente;
import consultoriomedico.services.CitaService;
import consultoriomedico.services.PacienteService;
import consultoriomedico.utils.ConsoleUtils;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MenuView {
    private final PacienteService pacienteService = new PacienteService();
    private final CitaService citaService = new CitaService();

    public void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = ConsoleUtils.leerEntero("Seleccione una opcion: ");
            switch (opcion) {
                case 1 -> menuPaciente();
                case 2 -> menuCita();
                case 3 -> menuConsulta();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);

        ConsoleUtils.cerrar();
    }

    public void mostrarMenuPrincipal() {
        System.out.println("===== CONSULTORIO MÉDICO =====");
        System.out.println("1. Pacientes");
        System.out.println("2. Citas");
        System.out.println("3. Consultas");
        System.out.println("0. Salir");
    }

    private void menuPaciente() {
        int opcion;
        do {
            System.out.println("\n--- PACIENTES ---");
            System.out.println("1. Crear paciente");
            System.out.println("2. Buscar paciente por indice");
            System.out.println("3. Buscar paciente por identificacion");
            System.out.println("4. Actualizar paciente por identificacion");
            System.out.println("5. Eliminar paciente por identificacion");
            System.out.println("6. Listar pacientes");
            System.out.println("0. Volver");
            opcion = ConsoleUtils.leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> crearPaciente();
                case 2 -> buscarPacientePorIndice();
                case 3 -> buscarPacientePorIdentificacion();
                case 4 -> actualizarPacientePorIdentificacion();
                case 5 -> eliminarPacientePorIdentificacion();
                case 6 -> pacienteService.recorrerLista();
                case 0 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void menuCita() {
        int opcion;
        do {
            System.out.println("\n--- CITAS ---");
            System.out.println("1. Crear cita");
            System.out.println("2. Listar citas");
            System.out.println("0. Volver");
            opcion = ConsoleUtils.leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> crearCita();
                case 2 -> citaService.recorrerLista();
                case 0 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void menuConsulta() {
        int opcion;
        do {
            mostrarMenuConsulta();
            opcion = ConsoleUtils.leerEntero("Seleccione una opcion: ");
            switch (opcion) {
                case 1 -> registrarConsulta();
                case 2 -> mostrarHistorialClinico();
                case 0 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuConsulta() {
        System.out.println("\n--- CONSULTAS ---");
        System.out.println("1. Registrar consulta");
        System.out.println("2. Ver historial clinico");
        System.out.println("0. Volver");
    }

    private void mostrarMenuActualizarPaciente() {
        System.out.println("1. Actualizar nombre");
        System.out.println("2. Actualizar telefono");
        System.out.println("3. Actualizar direccion");
    }

    private void crearPaciente() {
        try {
            String identificacion = ConsoleUtils.leerTexto("Identificacion: ");
            String nombre = ConsoleUtils.leerTexto("Nombre: ");
            String telefono = ConsoleUtils.leerTexto("Telefono: ");
            String direccion = ConsoleUtils.leerTexto("Direccion: ");

            if (identificacion == null || identificacion.isBlank()) {
                throw new IllegalArgumentException("La identificacion no puede estar vacia.");
            }

            pacienteService.crearPaciente(identificacion, nombre, telefono, direccion);
            System.out.println("Paciente creado exitosamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscarPacientePorIdentificacion() {
        String identificacion = ConsoleUtils.leerTexto("Identificacion del paciente: ");
        Paciente paciente = pacienteService.buscarPorIdentificacion(identificacion);
        if (paciente != null) {
            System.out.println("Paciente encontrado: " + paciente.getNombre());
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    private void buscarPacientePorIndice() {
        int indice = ConsoleUtils.leerEntero("Indice del paciente: ");
        Paciente paciente = pacienteService.buscarPorIndice(indice);
        if (paciente != null) {
            System.out.println("Paciente encontrado: " + paciente.getNombre());
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    private void actualizarPacientePorIdentificacion() {
        String identificacion = ConsoleUtils.leerTexto("Identificacion del paciente a actualizar: ");
        Paciente paciente = pacienteService.buscarPorIdentificacion(identificacion);
        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        mostrarMenuActualizarPaciente();
        int opcion = ConsoleUtils.leerEntero("Seleccione una opcion: ");
        switch (opcion) {
            case 1 -> pacienteService.actualizarNombrePaciente(
                    paciente, ConsoleUtils.leerTexto("Nuevo nombre: "));
            case 2 -> pacienteService.actualizarTelefonoPaciente(
                    paciente, ConsoleUtils.leerTexto("Nuevo telefono: "));
            case 3 -> pacienteService.actualizarDireccionPaciente(
                    paciente, ConsoleUtils.leerTexto("Nueva direccion: "));
            default -> System.out.println("Opcion invalida.");
        }
    }

    private void eliminarPacientePorIdentificacion() {
        String identificacion = ConsoleUtils.leerTexto("Identificacion del paciente a eliminar: ");
        Paciente paciente = pacienteService.buscarPorIdentificacion(identificacion);
        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        pacienteService.eliminarPaciente(paciente);
    }

    private void crearCita() {
        try {
            String identificacionPaciente = ConsoleUtils.leerTexto("Identificacion del paciente: ");
            Paciente paciente = pacienteService.buscarPorIdentificacion(identificacionPaciente);
            if (paciente == null) {
                System.out.println("Paciente no encontrado.");
                return;
            }

            String identificacionMedico = ConsoleUtils.leerTexto("Identificacion del medico: ");
            Medico medico = new Medico(
                    identificacionMedico,
                    ConsoleUtils.leerTexto("Nombre del medico: "),
                    ConsoleUtils.leerTexto("Telefono del medico: "),
                    ConsoleUtils.leerTexto("Especialidad del medico: "),
                    ConsoleUtils.leerTexto("Numero de registro: ")
            );

            LocalDate fecha = LocalDate.parse(ConsoleUtils.leerTexto("Fecha (AAAA-MM-DD): "));
            String hora = ConsoleUtils.leerTexto("Hora de la cita: ");
            String motivo = ConsoleUtils.leerTexto("Motivo de la cita: ");

            Cita cita = citaService.crearCita(fecha, hora, motivo, paciente, medico);
            System.out.println("Cita creada exitosamente para " + cita.getPaciente().getNombre());
        } catch (DateTimeParseException e) {
            System.out.println("Error: la fecha debe tener el formato AAAA-MM-DD.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarConsulta() {
        try {
            String identificacionPaciente = ConsoleUtils.leerTexto("Identificacion del paciente: ");
            String motivo = ConsoleUtils.leerTexto("Motivo de la consulta: ");
            String diagnostico = ConsoleUtils.leerTexto("Diagnostico: ");
            String tratamiento = ConsoleUtils.leerTexto("Tratamiento: ");
            LocalDate fecha = LocalDate.parse(ConsoleUtils.leerTexto("Fecha (AAAA-MM-DD): "));

            pacienteService.crearConsulta(identificacionPaciente, motivo, diagnostico, tratamiento, fecha);
        } catch (DateTimeParseException e) {
            System.out.println("Error: la fecha debe tener el formato AAAA-MM-DD.");
        }
    }

    private void mostrarHistorialClinico() {
        String identificacionPaciente = ConsoleUtils.leerTexto("Identificacion del paciente: ");
        pacienteService.mostrarHistorialClinico(identificacionPaciente);
    }
}
