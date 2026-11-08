package consultoriomedico.view;

import consultoriomedico.utils.ConsoleUtils;
import consultoriomedico.services.AgendaService;

public class MenuAgendaView {

    private final AgendaService agendaService;

    public MenuAgendaView(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    public void iniciar() {
        int opcion;

        do {
            mostrarOpciones();

            opcion = ConsoleUtils.leerEntero(
                    "Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> cargarAgendas();
                case 2 -> recorrerAdelante();
                case 3 -> recorrerAtras();
                case 4 -> irAlInicio();
                case 5 -> irAlFinal();
                case 6 -> siguiente();
                case 7 -> anterior();
                case 8 -> cancelarCita();
                case 9 -> contarCitas();
                case 0 -> System.out.println(
                        "Volviendo al menu principal...");
                default -> System.out.println(
                        "Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private void mostrarOpciones() {
        System.out.println("\n--- GESTIÓN DE AGENDA ---");
        System.out.println("1. Cargar citas guardadas");
        System.out.println("2. Recorrer agenda hacia adelante");
        System.out.println("3. Recorrer agenda hacia atrás");
        System.out.println("4. Ir a la primera cita");
        System.out.println("5. Ir a la última cita");
        System.out.println("6. Siguiente cita");
        System.out.println("7. Cita anterior");
        System.out.println("8. Cancelar cita seleccionada");
        System.out.println("9. Contar citas de una fecha");
        System.out.println("0. Volver");
    }

    private String leerFecha() {
        return ConsoleUtils.leerTexto(
                "Fecha de la agenda (AAAA-MM-DD): ");
    }

    private void cargarAgendas() {
        System.out.println(agendaService.cargarAgendas());
    }

    private void recorrerAdelante() {
        String fecha = leerFecha();
        System.out.println(
                agendaService.listarAdelante(fecha));
    }

    private void recorrerAtras() {
        String fecha = leerFecha();
        System.out.println(
                agendaService.listarAtras(fecha));
    }

    private void irAlInicio() {
        String fecha = leerFecha();
        System.out.println(
                agendaService.irAlInicio(fecha));
    }

    private void irAlFinal() {
        String fecha = leerFecha();
        System.out.println(
                agendaService.irAlFinal(fecha));
    }

    private void siguiente() {
        String fecha = leerFecha();
        System.out.println(
                agendaService.siguiente(fecha));
    }

    private void anterior() {
        String fecha = leerFecha();
        System.out.println(
                agendaService.anterior(fecha));
    }

    private void cancelarCita() {
    String fecha = leerFecha();

    if (!agendaService.hayCitaSeleccionada(fecha)) {
        System.out.println(
                "No hay una cita seleccionada para esa fecha.");
        return;
    }

    System.out.println(
            "¿Desea cancelar la cita seleccionada?");
    System.out.println("1. Si");
    System.out.println("2. No");

    int opcion = ConsoleUtils.leerEntero(
            "Seleccione una opcion: ");

    if (opcion == 1) {
        System.out.println(
                agendaService.cancelarCitaSeleccionada(fecha));
    } else {
        System.out.println("Operacion cancelada.");
    }
}

    private void contarCitas() {
        String fecha = leerFecha();

        int cantidad = agendaService.contarCitas(fecha);

        System.out.println(
                "Cantidad de citas para "
                                + fecha + ": " + cantidad);
    }
}