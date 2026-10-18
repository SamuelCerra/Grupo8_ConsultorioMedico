
package consultoriomedico.services;

import java.time.LocalDate;

import consultoriomedico.models.Cita;
import consultoriomedico.models.Medico;
import consultoriomedico.models.Paciente;
import consultoriomedico.structures.ListaSimple;
import consultoriomedico.structures.Nodo;

public class CitaService {

    private final ListaSimple<Cita> citas = new ListaSimple<>();

    public Cita crearCita(
            LocalDate fecha,
            String hora,
            String motivo,
            Paciente paciente,
            Medico medico) {

        if (fecha == null) {
            throw new IllegalArgumentException(
                    "La fecha de la cita es obligatoria.");
        }

        if (hora == null || hora.isBlank()) {
            throw new IllegalArgumentException(
                    "La hora de la cita es obligatoria.");
        }

        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException(
                    "El motivo de la cita es obligatorio.");
        }

        if (paciente == null || medico == null) {
            throw new IllegalArgumentException(
                    "La cita requiere un paciente y un médico válidos.");
        }

        Cita cita = new Cita(
                fecha,
                hora,
                motivo,
                paciente,
                medico
        );

        citas.insertarFinal(cita);

        return cita;
    }

    public Cita buscarPorPacienteYFecha(
            Paciente paciente,
            LocalDate fecha) {

        if (paciente == null || fecha == null) {
            return null;
        }

        Nodo<Cita> actual = citas.getHead();

        while (actual != null) {
            Cita cita = actual.getDato();

            if (cita.getPaciente() == paciente
                    && fecha.equals(cita.getFecha())) {
                return cita;
            }

            actual = actual.getSiguiente();
        }

        return null;
    }

    public void recorrerLista() {
        Nodo<Cita> actual = citas.getHead();

        if (actual == null) {
            System.out.println("No hay citas registradas.");
            return;
        }

        int indice = 0;

        while (actual != null) {
            Cita cita = actual.getDato();

            System.out.println("\nÍndice: " + indice);
            System.out.println("Fecha: " + cita.getFecha());
            System.out.println("Hora: " + cita.getHora());
            System.out.println("Motivo: " + cita.getMotivo());
            System.out.println(
                    "Paciente: " + cita.getPaciente().getNombre());
            System.out.println(
                    "Médico: " + cita.getMedico().getNombre());

            actual = actual.getSiguiente();
            indice++;
        }
    }

    public boolean eliminarCita(Cita cita) {
        if (cita == null) {
            System.out.println("Cita no encontrada.");
            return false;
        }

        Cita registrada = buscarPorPacienteYFecha(
                cita.getPaciente(),
                cita.getFecha()
        );

        if (registrada != cita) {
            System.out.println(
                    "La cita no está registrada en el sistema.");
            return false;
        }

        boolean eliminada = citas.eliminarPorValor(cita);

        if (!eliminada) {
            System.out.println("Error al eliminar la cita.");
            return false;
        }

        System.out.println("Cita eliminada exitosamente.");
        return true;
    }
    
    public boolean tieneCitasMedico(Medico medico) {
        if (medico == null) {
            return false;
        }

        Nodo<Cita> actual = citas.getHead();

        while (actual != null) {
            if (actual.getDato().getMedico() == medico) {
                return true;
            }

            actual = actual.getSiguiente();
        }

        return false;
    }
}
