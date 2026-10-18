
package consultoriomedico.view;

import consultoriomedico.models.Cita;
import consultoriomedico.models.Consulta;
import consultoriomedico.models.Medico;
import consultoriomedico.models.Paciente;
import consultoriomedico.services.CitaService;
import consultoriomedico.services.PacienteService;
import consultoriomedico.services.MedicoService;
import consultoriomedico.utils.ConsoleUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MenuView {

    
        private final PacienteService pacienteService =
                new PacienteService();

        private final CitaService citaService =
                new CitaService();

        private final MedicoService medicoService =
                new MedicoService(citaService);

 
    public void iniciar() {
        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = ConsoleUtils.leerEntero(
                    "Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> menuPaciente();
                case 2 -> menuMedico();
                case 3 -> menuCita();
                case 4 -> menuConsulta();
                case 5 -> menuPilaConsultas();
                case 0 -> System.out.println(
                        "Saliendo del sistema...");
                default -> System.out.println(
                        "Opcion invalida.");
            }

        } while (opcion != 0);

        ConsoleUtils.cerrar();
    }

    public void mostrarMenuPrincipal() {
        System.out.println("\n===== CONSULTORIO MEDICO =====");
        System.out.println("1. Pacientes");
        System.out.println("2. Medicos");
        System.out.println("3. Citas");
        System.out.println("4. Consultas");
        System.out.println("5. Historial de consultas");
        System.out.println("0. Salir");
        }

    // =========================
    // MENU PACIENTES
    // =========================

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

            opcion = ConsoleUtils.leerEntero(
                    "Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> crearPaciente();
                case 2 -> buscarPacientePorIndice();
                case 3 -> buscarPacientePorIdentificacion();
                case 4 -> actualizarPacientePorIdentificacion();
                case 5 -> eliminarPacientePorIdentificacion();
                case 6 -> pacienteService.recorrerLista();
                case 0 -> System.out.println(
                        "Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    // =========================
    // MENU MEDICOS
    // =========================

    private void menuMedico() {
        int opcion;

        do {
            System.out.println("\n--- MEDICOS ---");
            System.out.println("1. Registrar medico");
            System.out.println("2. Buscar medico por identificacion");
            System.out.println("3. Listar medicos");
            System.out.println("4. Eliminar medico");
            System.out.println("0. Volver");

            opcion = ConsoleUtils.leerEntero(
                    "Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> crearMedico();
                case 2 -> buscarMedicoPorIdentificacion();
                case 3 -> medicoService.recorrerLista();
                case 4 -> eliminarMedico();
                case 0 -> System.out.println(
                        "Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private void crearMedico() {
        try {
            String identificacion =
                    ConsoleUtils.leerTexto("Identificacion: ");

            String nombre =
                    ConsoleUtils.leerTexto("Nombre: ");

            String telefono =
                    ConsoleUtils.leerTexto("Telefono: ");

            String especialidad =
                    ConsoleUtils.leerTexto("Especialidad: ");

            String registro =
                    ConsoleUtils.leerTexto("Numero de registro: ");

            medicoService.crearMedico(
                    identificacion,
                    nombre,
                    telefono,
                    especialidad,
                    registro
            );

            System.out.println("Medico registrado exitosamente.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscarMedicoPorIdentificacion() {
        String identificacion =
                ConsoleUtils.leerTexto(
                        "Identificacion del medico: ");

        Medico medico =
                medicoService.buscarPorIdentificacion(
                        identificacion);

        if (medico == null) {
            System.out.println("Medico no encontrado.");
            return;
        }

        System.out.println("Identificacion: "
                + medico.getIdentificacion());
        System.out.println("Nombre: " + medico.getNombre());
        System.out.println("Telefono: " + medico.getTelefono());
        System.out.println("Especialidad: "
                + medico.getEspecialidad());
        System.out.println("Numero de registro: "
                + medico.getNumeroRegistro());
    }

    private void eliminarMedico() {
        String identificacion =
                ConsoleUtils.leerTexto(
                        "Identificacion del medico a eliminar: ");

        Medico medico =
                medicoService.buscarPorIdentificacion(
                        identificacion);

        if (medico == null) {
            System.out.println("Medico no encontrado.");
            return;
        }

        System.out.println(
                "Importante: antes de eliminar un medico, "
                + "verifica que no tenga citas asociadas.");

        medicoService.eliminarMedico(medico);
    }

    // =========================
    // MENU CITAS
    // =========================

    private void menuCita() {
        int opcion;

        do {
            System.out.println("\n--- CITAS ---");
            System.out.println("1. Crear cita");
            System.out.println("2. Listar citas");
            System.out.println("3. Eliminar cita");
            System.out.println("0. Volver");

            opcion = ConsoleUtils.leerEntero(
                    "Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> crearCita();
                case 2 -> citaService.recorrerLista();
                case 3 -> eliminarCita();
                case 0 -> System.out.println(
                        "Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    // =========================
    // MENU CONSULTAS
    // =========================

    private void menuConsulta() {
        int opcion;

        do {
            System.out.println("\n--- CONSULTAS ---");
            System.out.println("1. Registrar consulta");
            System.out.println("2. Ver historial clinico");
            System.out.println("3. Eliminar consulta");
            System.out.println("0. Volver");

            opcion = ConsoleUtils.leerEntero(
                    "Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> registrarConsulta();
                case 2 -> mostrarHistorialClinico();
                case 3 -> eliminarConsulta();
                case 0 -> System.out.println(
                        "Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    // =========================
    // CREAR PACIENTE
    // =========================

    private void crearPaciente() {
        try {
            String identificacion =
                    ConsoleUtils.leerTexto("Identificacion: ");

            String nombre =
                    ConsoleUtils.leerTexto("Nombre: ");

            String telefono =
                    ConsoleUtils.leerTexto("Telefono: ");

            String direccion =
                    ConsoleUtils.leerTexto("Direccion: ");

            if (identificacion == null
                    || identificacion.isBlank()) {
                throw new IllegalArgumentException(
                        "La identificacion es obligatoria.");
            }

            if (pacienteService.buscarPorIdentificacion(
                    identificacion) != null) {
                throw new IllegalArgumentException(
                        "Ya existe un paciente con esa identificacion.");
            }

            pacienteService.crearPaciente(
                    identificacion,
                    nombre,
                    telefono,
                    direccion
            );

            System.out.println("Paciente creado exitosamente.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =========================
    // BUSCAR PACIENTE
    // =========================

    private void buscarPacientePorIdentificacion() {
        String identificacion =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente: ");

        Paciente paciente =
                pacienteService.buscarPorIdentificacion(
                        identificacion);

        if (paciente != null) {
            System.out.println("Paciente encontrado: "
                    + paciente.getNombre());
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    private void buscarPacientePorIndice() {
        int indice =
                ConsoleUtils.leerEntero(
                        "Indice del paciente: ");

        Paciente paciente =
                pacienteService.buscarPorIndice(indice);

        if (paciente != null) {
            System.out.println("Paciente encontrado: "
                    + paciente.getNombre());
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    // =========================
    // ACTUALIZAR PACIENTE
    // =========================

    private void actualizarPacientePorIdentificacion() {
        String identificacion =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente a actualizar: ");

        Paciente paciente =
                pacienteService.buscarPorIdentificacion(
                        identificacion);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        System.out.println("1. Actualizar nombre");
        System.out.println("2. Actualizar telefono");
        System.out.println("3. Actualizar direccion");

        int opcion =
                ConsoleUtils.leerEntero(
                        "Seleccione una opcion: ");

        switch (opcion) {
            case 1 -> pacienteService.actualizarNombrePaciente(
                    paciente,
                    ConsoleUtils.leerTexto("Nuevo nombre: "));
            case 2 -> pacienteService.actualizarTelefonoPaciente(
                    paciente,
                    ConsoleUtils.leerTexto("Nuevo telefono: "));
            case 3 -> pacienteService.actualizarDireccionPaciente(
                    paciente,
                    ConsoleUtils.leerTexto("Nueva direccion: "));
            default -> System.out.println("Opcion invalida.");
        }
    }

    // =========================
    // ELIMINAR PACIENTE
    // =========================

    private void eliminarPacientePorIdentificacion() {
        String identificacion =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente a eliminar: ");

        Paciente paciente =
                pacienteService.buscarPorIdentificacion(
                        identificacion);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        pacienteService.eliminarPaciente(paciente);
    }

    // =========================
    // CREAR CITA
    // =========================

    private void crearCita() {
        try {
            String identificacionPaciente =
                    ConsoleUtils.leerTexto(
                            "Identificacion del paciente: ");

            Paciente paciente =
                    pacienteService.buscarPorIdentificacion(
                            identificacionPaciente);

            if (paciente == null) {
                System.out.println(
                        "Paciente no encontrado. "
                        + "Registre primero al paciente.");
                return;
            }

            String identificacionMedico =
                    ConsoleUtils.leerTexto(
                            "Identificacion del medico: ");

            Medico medico =
                    medicoService.buscarPorIdentificacion(
                            identificacionMedico);

            if (medico == null) {
                System.out.println(
                        "Medico no encontrado. "
                        + "Registre primero al medico.");
                return;
            }

            LocalDate fecha =
                    LocalDate.parse(
                            ConsoleUtils.leerTexto(
                                    "Fecha (AAAA-MM-DD): "));

            String hora =
                    ConsoleUtils.leerTexto("Hora de la cita: ");

            String motivo =
                    ConsoleUtils.leerTexto("Motivo de la cita: ");

            Cita cita = citaService.crearCita(
                    fecha,
                    hora,
                    motivo,
                    paciente,
                    medico
            );

            System.out.println(
                    "Cita creada exitosamente para "
                    + cita.getPaciente().getNombre());

        } catch (DateTimeParseException e) {
            System.out.println(
                    "Error: la fecha debe tener el formato AAAA-MM-DD.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =========================
    // ELIMINAR CITA
    // =========================

    private void eliminarCita() {
        String identificacionPaciente =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente: ");

        Paciente paciente =
                pacienteService.buscarPorIdentificacion(
                        identificacionPaciente);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        try {
            LocalDate fecha =
                    LocalDate.parse(
                            ConsoleUtils.leerTexto(
                                    "Fecha de la cita (AAAA-MM-DD): "));

            Cita cita =
                    citaService.buscarPorPacienteYFecha(
                            paciente,
                            fecha);

            if (cita == null) {
                System.out.println("Cita no encontrada.");
                return;
            }

            citaService.eliminarCita(cita);

        } catch (DateTimeParseException e) {
            System.out.println(
                    "Error: la fecha debe tener el formato AAAA-MM-DD.");
        }
    }

    // =========================
    // REGISTRAR CONSULTA
    // =========================

    private void registrarConsulta() {
        try {
            String identificacionPaciente =
                    ConsoleUtils.leerTexto(
                            "Identificacion del paciente: ");

            String motivo =
                    ConsoleUtils.leerTexto(
                            "Motivo de la consulta: ");

            String diagnostico =
                    ConsoleUtils.leerTexto("Diagnostico: ");

            String tratamiento =
                    ConsoleUtils.leerTexto("Tratamiento: ");

            LocalDate fecha =
                    LocalDate.parse(
                            ConsoleUtils.leerTexto(
                                    "Fecha (AAAA-MM-DD): "));

            pacienteService.crearConsulta(
                    identificacionPaciente,
                    motivo,
                    diagnostico,
                    tratamiento,
                    fecha
            );

        } catch (DateTimeParseException e) {
            System.out.println(
                    "Error: la fecha debe tener el formato AAAA-MM-DD.");
        }
    }

    // =========================
    // MOSTRAR HISTORIAL
    // =========================

    private void mostrarHistorialClinico() {
        String identificacionPaciente =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente: ");

        pacienteService.mostrarHistorialClinico(
                identificacionPaciente);
    }

    // =========================
    // ELIMINAR CONSULTA
    // =========================

    private void eliminarConsulta() {
        String identificacionPaciente =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente: ");

        try {
            LocalDate fecha =
                    LocalDate.parse(
                            ConsoleUtils.leerTexto(
                                    "Fecha de la consulta (AAAA-MM-DD): "));

            pacienteService.eliminarConsulta(
                    identificacionPaciente,
                    fecha);

        } catch (DateTimeParseException e) {
            System.out.println(
                    "Error: la fecha debe tener el formato AAAA-MM-DD.");
        }
    }
    
    // =========================
    // MENU HISTORIAL DE CONSULTAS
    // =========================

    private void menuPilaConsultas() {
        int opcion;

        do {
            System.out.println("\n--- HISTORIAL DE CONSULTAS ---");
            System.out.println("1. Registrar consulta");
            System.out.println("2. Deshacer ultima consulta");
            System.out.println("3. Ver ultima consulta");
            System.out.println("4. Verificar si hay consultas por eliminar");
            System.out.println("5. Contar consultas por eliminar");
            System.out.println("6. Listar consultas por eliminar");
            System.out.println("0. Volver");

            opcion = ConsoleUtils.leerEntero(
                    "Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> registrarConsulta();
                case 2 -> deshacerUltimaConsulta();
                case 3 -> verUltimaConsulta();
                case 4 -> verificarPilaConsultas();
                case 5 -> contarConsultasPila();
                case 6 -> listarConsultasPila();
                case 0 -> System.out.println(
                        "Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    // =========================
    // ELIMINAR ULTIMA CONSULTA
    // =========================

    private void deshacerUltimaConsulta() {
        String identificacion =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente: ");

        if (pacienteService.sinOperacionesPorDeshacer(
                identificacion)) {
            System.out.println("No hay operaciones por deshacer");
            return;
        }

        Consulta consulta =
                pacienteService.deshacerUltimaConsulta(
                        identificacion);

        if (consulta != null) {
            System.out.println(
                    "Se deshizo la ultima consulta:");
            System.out.println(consulta);
        }
    }

    // =========================
    // VER ULTIMA CONSULTA
    // =========================

    private void verUltimaConsulta() {
        String identificacion =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente: ");

        if (pacienteService.sinOperacionesPorDeshacer(
                identificacion)) {
            System.out.println("No hay operaciones por deshacer");
            return;
        }

        Consulta consulta =
                pacienteService.consultarUltimaConsulta(
                        identificacion);

        if (consulta != null) {
            System.out.println("Ultima consulta registrada:");
            System.out.println(consulta);
        }
    }

    // =========================
    // VERIFICAR PILA
    // =========================

    private void verificarPilaConsultas() {
        String identificacion =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente: ");

        if (pacienteService.sinOperacionesPorDeshacer(
                identificacion)) {
            System.out.println("No hay operaciones por deshacer");
        } else {
            System.out.println(
                    "Hay consultas por deshacer.");
        }
    }

    // =========================
    // CONTAR CONSULTAS
    // =========================

    private void contarConsultasPila() {
        String identificacion =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente: ");

        int cantidad =
                pacienteService.contarOperaciones(
                        identificacion);

        System.out.println(
                "Consultas en la pila: " + cantidad);
    }

    // =========================
    // LISTAR CONSULTAS
    // =========================

    private void listarConsultasPila() {
        String identificacion =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente: ");

        String resultado =
                pacienteService.listarOperaciones(
                        identificacion);

        System.out.println(resultado);
    }
}
