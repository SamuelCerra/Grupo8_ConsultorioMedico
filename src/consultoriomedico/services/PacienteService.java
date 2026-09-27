package consultoriomedico.services;

import consultoriomedico.models.Consulta;
import consultoriomedico.models.Paciente;
import consultoriomedico.structures.ListaSimple;
import consultoriomedico.structures.Nodo;
import java.time.LocalDate;

public class PacienteService {
    private ListaSimple<Paciente> pacientes = new ListaSimple<>();

    public Paciente crearPaciente(String identificacion, String nombre, String telefono, String direccion) {
        Paciente paciente = new Paciente(identificacion, nombre, telefono);
        paciente.setDireccion(direccion);
        pacientes.insertarFinal(paciente);
        return paciente;
    }

    public Paciente buscarPorIdentificacion(String identificacion) {
        Nodo<Paciente> pacienteActual = pacientes.getHead();

        while (pacienteActual != null) {
            if (pacienteActual.getDato().getIdentificacion().equals(identificacion)) {
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

        Paciente paciente = buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return null;
        }

        Consulta consulta = new Consulta(
                motivo,
                diagnostico,
                fecha
        );

        paciente.agregarConsulta(consulta);

        System.out.println(
                "Consulta registrada exitosamente para "
                + paciente.getNombre()
        );

        return consulta;
    }

    public void mostrarHistorialClinico(String identificacionPaciente) {
        Paciente paciente = buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        Nodo<Consulta> actual =
                paciente.getHistorialClinico().getHead();

        if (actual == null) {
            System.out.println(
                    "El paciente no tiene historial clínico."
            );
            return;
        }

        System.out.println(
                "Historial clínico de " + paciente.getNombre()
        );

        for (int i = 0;
             i < paciente.getHistorialClinico().getTamano();
             i++) {

            System.out.println("\nConsulta " + (i + 1));
            System.out.println(actual.getDato());

            actual = actual.getSiguiente();
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
                buscarConsulta(
                        identificacionPaciente,
                        fecha
                );

        if (consulta == null) {
            System.out.println("Consulta no encontrada.");
            return false;
        }

        boolean eliminado =
                paciente.getHistorialClinico()
                        .eliminarPorValor(consulta);

        if (eliminado) {
            System.out.println(
                    "Consulta eliminada exitosamente."
            );
            return true;
        }

        System.out.println(
                "Error al eliminar la consulta."
        );

        return false;
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
            boolean eliminado =
                    pacientes.eliminar(paciente);

            if (eliminado) {
                System.out.println(
                        "Paciente eliminado exitosamente."
                );
                return true;
            }

            System.out.println(
                    "Error al eliminar el paciente."
            );

            return false;
        }

        System.out.println("Paciente no encontrado.");
        return false;
    }
}
