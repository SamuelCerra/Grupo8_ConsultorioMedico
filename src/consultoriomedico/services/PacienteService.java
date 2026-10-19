
package consultoriomedico.services;

import consultoriomedico.models.Consulta;
import consultoriomedico.models.Paciente;
import consultoriomedico.models.SalaDeEspera;
import consultoriomedico.structures.Cola;
import consultoriomedico.structures.ListaSimple;
import consultoriomedico.structures.Nodo;
import consultoriomedico.structures.Pila;
import java.time.LocalDate;

public class PacienteService {

    private ListaSimple<Paciente> pacientes = new ListaSimple<>();
    private final SalaDeEspera salaDeEspera = new SalaDeEspera("Sala de espera 1");

    public Paciente crearPaciente(
            String identificacion,
            String nombre,
            String telefono,
            String direccion) {

        Paciente paciente =
                new Paciente(identificacion, nombre, telefono);

        paciente.setDireccion(direccion);
        pacientes.insertarFinal(paciente);

        return paciente;
    }

    public Paciente buscarPorIdentificacion(String identificacion) {
        Nodo<Paciente> pacienteActual = pacientes.getHead();

        while (pacienteActual != null) {
            if (pacienteActual.getDato()
                    .getIdentificacion().equals(identificacion)) {
                return pacienteActual.getDato();
            }

            pacienteActual = pacienteActual.getSiguiente();
        }

        return null;
    }

    public Paciente buscarPorIndice(int indice) {
        if (indice < 0 || indice >= pacientes.getTamano()) {
            return null;
        }

        return pacientes.buscarPorIndice(indice);
    }

    public void recorrerLista() {
        Nodo<Paciente> actual = pacientes.getHead();

        for (int i = 0; i < pacientes.getTamano(); i++) {
            Paciente pacienteActual = actual.getDato();

            System.out.println(
                    "Indice: " + i
                    + " Paciente: " + pacienteActual.getNombre()
            );

            actual = actual.getSiguiente();
        }
    }

    public Consulta crearConsulta(
            String identificacionPaciente,
            String motivo,
            String diagnostico,
            String tratamiento,
            LocalDate fecha) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return null;
        }

        Consulta consulta = new Consulta(
                motivo,
                diagnostico,
                tratamiento,
                fecha
        );

        paciente.agregarConsulta(consulta);
        paciente.getPilaConsultas().push(consulta);

        System.out.println(
                "Consulta registrada exitosamente para "
                + paciente.getNombre()
        );

        return consulta;
    }
    
    public Consulta deshacerUltimaConsulta(
            String identificacionPaciente) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            return null;
        }

        Pila<Consulta> pila = paciente.getPilaConsultas();

        if (pila.isEmpty()) {
            return null;
        }

        Consulta consulta = pila.pop();

        paciente.getHistorialClinico()
                .eliminarPorValor(consulta);

        return consulta;
    }

    public Consulta consultarUltimaConsulta(
            String identificacionPaciente) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null
                || paciente.getPilaConsultas().isEmpty()) {
            return null;
        }

        return paciente.getPilaConsultas().peek();
    }

    public boolean sinOperacionesPorDeshacer(
            String identificacionPaciente) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        return paciente == null
                || paciente.getPilaConsultas().isEmpty();
    }

    public int contarOperaciones(
            String identificacionPaciente) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            return 0;
        }

        return paciente.getPilaConsultas().size();
    }

    public String listarOperaciones(
            String identificacionPaciente) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            return "Paciente no encontrado.";
        }

        Pila<Consulta> pila = paciente.getPilaConsultas();

        if (pila.isEmpty()) {
            return "No hay operaciones por deshacer";
        }

        Pila<Consulta> auxiliar = new Pila<>();
        StringBuilder resultado = new StringBuilder();
        int numero = 1;

        while (!pila.isEmpty()) {
            Consulta consulta = pila.pop();

            resultado.append("Consulta ")
                    .append(numero++)
                    .append("\n")
                    .append(consulta)
                    .append("\n\n");

            auxiliar.push(consulta);
        }

        while (!auxiliar.isEmpty()) {
            pila.push(auxiliar.pop());
        }

        return resultado.toString().trim();
    }

    public void mostrarHistorialClinico(
            String identificacionPaciente) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        Nodo<Consulta> actual =
                paciente.getHistorialClinico().getHead();

        if (actual == null) {
            System.out.println(
                    "El paciente no tiene historial clinico."
            );
            return;
        }

        System.out.println(
                "Historial clinico de " + paciente.getNombre()
        );

        int i = 1;

        while (actual != null) {
            System.out.println("\nConsulta " + i);
            System.out.println(actual.getDato());

            actual = actual.getSiguiente();
            i++;
        }
    }

    public Consulta buscarConsulta(
            String identificacionPaciente,
            LocalDate fecha) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            return null;
        }

        Nodo<Consulta> actual =
                paciente.getHistorialClinico().getHead();

        while (actual != null) {
            Consulta consulta = actual.getDato();

            if (consulta.getFecha().equals(fecha)) {
                return consulta;
            }

            actual = actual.getSiguiente();
        }

        return null;
        }

    
    public boolean eliminarConsulta(
            String identificacionPaciente,
            LocalDate fecha) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return false;
        }

        Consulta consulta =
                buscarConsulta(identificacionPaciente, fecha);

        if (consulta == null) {
            System.out.println("Consulta no encontrada.");
            return false;
        }

        boolean eliminado =
                paciente.getHistorialClinico()
                        .eliminarPorValor(consulta);

        if (eliminado) {

            Pila<Consulta> pila =
                    paciente.getPilaConsultas();

            Pila<Consulta> auxiliar = new Pila<>();

            while (!pila.isEmpty()) {
                Consulta actual = pila.pop();

                if (actual != consulta) {
                    auxiliar.push(actual);
                }
            }

            while (!auxiliar.isEmpty()) {
                pila.push(auxiliar.pop());
            }

            System.out.println(
                    "Consulta eliminada exitosamente."
            );
            return true;
        }

        System.out.println("Error al eliminar la consulta.");
        return false;
    }

    public String listarEnEspera() {
        Cola<Paciente> fila = salaDeEspera.getFila();


        if (fila.isEmpty()) {
            return "No hay pacientes en espera.";
        }

        StringBuilder sb = new StringBuilder("--- PACIENTES EN ESPERA ---");
        int total = fila.size();

        for (int i = 1; i <= total; i++) {
            Paciente paciente = fila.dequeue();
            sb.append("\n").append(i).append(". ")
              .append(paciente.getNombre())
              .append(" (ID: ").append(paciente.getIdentificacion()).append(")");
            fila.enqueue(paciente); 
        }
        return sb.toString();
    }

    public void registrarLLegada(String identificacion){
        Paciente paciente = buscarPacienteOFallar(identificacion);
        salaDeEspera.getFila().enqueue(paciente);
    }

    public Paciente atenderSiguiente(){
        Paciente paciente =  salaDeEspera.getFila().dequeue();
        return paciente;
    }

    public Paciente consultarSiguiente(){
        Paciente paciente = salaDeEspera.getFila().front();
        return paciente;
    }

    public boolean sinClientesEnEspera(){
        return salaDeEspera.getFila().isEmpty();
    }

    public int contarEnEspera(){
        return salaDeEspera.getFila().size();
    }

    private Paciente buscarPacienteOFallar(String identificacion){
        Paciente paciente = buscarPorIdentificacion(identificacion);
        if (paciente == null) {
            throw new IllegalArgumentException( "Paciente no encontrado.");
        }
        return paciente;
    }

    public void actualizarNombrePaciente(
            Paciente paciente,
            String nuevoNombre) {

        if (paciente != null) {
            paciente.setNombre(nuevoNombre);
            System.out.println(
                    "Nombre del paciente actualizado exitosamente."
            );
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    public void actualizarTelefonoPaciente(
            Paciente paciente,
            String nuevoTelefono) {

        if (paciente != null) {
            paciente.setTelefono(nuevoTelefono);
            System.out.println(
                    "Telefono del paciente actualizado exitosamente."
            );
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    public void actualizarDireccionPaciente(
            Paciente paciente,
            String nuevaDireccion) {

        if (paciente != null) {
            paciente.setDireccion(nuevaDireccion);
            System.out.println(
                    "Direccion del paciente actualizada exitosamente."
            );
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    public boolean eliminarPaciente(Paciente paciente) {

        if (paciente != null) {
            boolean eliminado = pacientes.eliminar(paciente);

            if (eliminado) {
                System.out.println(
                        "Paciente eliminado exitosamente."
                );
                return true;
            }

            System.out.println("Error al eliminar el paciente.");
            return false;
        }

        System.out.println("Paciente no encontrado.");
        return false;
    }
}
