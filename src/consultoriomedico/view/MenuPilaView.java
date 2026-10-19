package consultoriomedico.view;

import consultoriomedico.models.Consulta;
import consultoriomedico.services.PacienteService;
import consultoriomedico.utils.ConsoleUtils;

import java.time.LocalDate;

public class MenuPilaView {

    private final PacienteService pacienteService;

    public MenuPilaView(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    public void iniciar() {

        int opcion;

        do {

            System.out.println(
                    "\n--- HISTORIAL DE CONSULTAS ---");

            System.out.println(
                    "1. Registrar consulta");

            System.out.println(
                    "2. Deshacer ultima consulta");

            System.out.println(
                    "3. Ver ultima consulta");

            System.out.println(
                    "4. Verificar si hay consultas por eliminar");

            System.out.println(
                    "5. Contar consultas por eliminar");

            System.out.println(
                    "6. Listar consultas por eliminar");

            System.out.println(
                    "0. Volver");

            opcion = ConsoleUtils.leerEntero(
                    "Seleccione una opcion: ");

            switch (opcion) {

                case 1 -> registrar();

                case 2 -> deshacerUltimaConsulta();

                case 3 -> verUltimaConsulta();

                case 4 -> verificarPilaConsultas();

                case 5 -> contarConsultasPila();

                case 6 -> listarConsultasPila();

                case 0 -> System.out.println(
                        "Volviendo al menu principal...");

                default -> System.out.println(
                        "Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private void registrar() {

        String identificacionPaciente =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente: ");

        String motivo =
                ConsoleUtils.leerTexto(
                        "Motivo de la consulta: ");

        String diagnostico =
                ConsoleUtils.leerTexto(
                        "Diagnostico: ");

        String tratamiento =
                ConsoleUtils.leerTexto(
                        "Tratamiento: ");

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
    }

    private void deshacerUltimaConsulta() {

        String identificacion =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente: ");

        if (pacienteService.sinOperacionesPorDeshacer(
                identificacion)) {

            System.out.println(
                    "No hay operaciones por deshacer");

            return;
        }

        Consulta consulta =
                pacienteService.deshacerUltimaConsulta(
                        identificacion);

        System.out.println(
                "Se deshizo la ultima consulta:");

        System.out.println(consulta);
    }

    private void verUltimaConsulta() {

        String identificacion =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente: ");

        if (pacienteService.sinOperacionesPorDeshacer(
                identificacion)) {

            System.out.println(
                    "No hay operaciones por deshacer");

            return;
        }

        Consulta consulta =
                pacienteService.consultarUltimaConsulta(
                        identificacion);

        System.out.println(
                "Ultima consulta registrada:");

        System.out.println(consulta);
    }

    private void verificarPilaConsultas() {

        String identificacion =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente: ");

        if (pacienteService.sinOperacionesPorDeshacer(
                identificacion)) {

            System.out.println(
                    "No hay operaciones por deshacer");

        } else {

            System.out.println(
                    "Hay consultas por deshacer.");
        }
    }

    private void contarConsultasPila() {

        String identificacion =
                ConsoleUtils.leerTexto(
                        "Identificacion del paciente: ");

        int cantidad =
                pacienteService.contarOperaciones(
                        identificacion);

        System.out.println(
                "Consultas en la pila: "
                        + cantidad);
    }

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