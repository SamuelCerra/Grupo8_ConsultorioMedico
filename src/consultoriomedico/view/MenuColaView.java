package consultoriomedico.view;

import consultoriomedico.models.Paciente;
import consultoriomedico.services.PacienteService;
import consultoriomedico.utils.ConsoleUtils;

public class MenuColaView {
    private final PacienteService pacienteService;

    public MenuColaView(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = ConsoleUtils.leerEntero("Elija una opción: ");
            switch (opcion) {
                case 1 -> registrarLlegada();
                case 2 -> atender();
                case 3 -> consultarSiguiente();
                case 4 -> verSiHayEnEspera();
                case 5 -> contar();
                case 6 -> listar();
                case 0 -> System.out.println("Saliendo del menú de la sala de espera...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void mostrarOpciones() {
        System.out.println("\n--- GESTIÓN DE SALA DE ESPERA ---");
        System.out.println("1. Registrar llegada de paciente ");
        System.out.println("2. Atender al siguiente paciente ");
        System.out.println("3. Ver quién sigue en la fila ");
        System.out.println("4. ¿Hay pacientes en espera? ");
        System.out.println("5. Cantidad de pacientes en espera ");
        System.out.println("6. Listar la fila completa ");
        System.out.println("0. Volver al menú principal");
    }

    private void registrarLlegada() {
    String identificacion = ConsoleUtils.leerTexto("Identificación del paciente: ");
    
    Paciente paciente = pacienteService.buscarPorIdentificacion(identificacion);
    
    if (paciente == null) {
        System.out.println("Error: No existe un paciente registrado con esa identificación.");
        return;
    }
    
    pacienteService.registrarLLegada(identificacion);
    System.out.println("¡Paciente registrado exitosamente en la sala de espera!");
}

    private void atender() {
        if (pacienteService.sinClientesEnEspera()) {
            System.out.println("No hay pacientes en espera.");
            return;
        }
        Paciente pacienteAtendido = pacienteService.atenderSiguiente();
        System.out.println("Atendiendo al paciente: " + pacienteAtendido.getNombre() + " (ID: " + pacienteAtendido.getIdentificacion() + ")");
    }

    private void consultarSiguiente() {
        if (pacienteService.sinClientesEnEspera()) {
            System.out.println("No hay pacientes en espera.");
            return;
        }
        Paciente siguiente = pacienteService.consultarSiguiente();
        System.out.println("Próximo paciente: " + siguiente.getNombre() + " (ID: " + siguiente.getIdentificacion() + ")");
    }

    private void verSiHayEnEspera() {
        if (pacienteService.sinClientesEnEspera()) {
            System.out.println("No hay pacientes en espera.");
        } else {
            System.out.println("Sí hay pacientes en espera.");
        }
    }

    private void contar() {
        int cantidad = pacienteService.contarEnEspera();
        System.out.println("Cantidad de pacientes esperando: " + cantidad);
    }

    private void listar() {
    String identificacion = ConsoleUtils.leerTexto("Identificación del paciente: ");
    String resultado = pacienteService.listarOperaciones(identificacion);
    System.out.println(resultado);
    }
}
