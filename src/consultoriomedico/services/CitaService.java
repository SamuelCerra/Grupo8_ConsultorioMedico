package consultoriomedico.services;

import java.time.LocalDate;

import consultoriomedico.models.Cita;
import consultoriomedico.models.Medico;
import consultoriomedico.models.Paciente;
import consultoriomedico.structures.ListaSimple;
import consultoriomedico.structures.Nodo;

public class CitaService {
    private ListaSimple<Cita> citas = new ListaSimple<>();

    public Cita crearCita(LocalDate fecha, String hora, String motivo, Paciente paciente, Medico medico) {
        if (paciente == null || medico == null) {
            throw new IllegalArgumentException("La cita requiere paciente y médico válidos.");
        }

        Cita cita = new Cita(fecha, hora, motivo, paciente, medico);
        citas.insertarFinal(cita);
        paciente.agregarCita(cita);
        medico.agregarCita(cita);
        return cita;
    }

    public Cita buscarPorPacienteYFecha(Paciente paciente, LocalDate fecha) {
        Nodo<Cita> actual = citas.getHead();
        while (actual != null) {
            if (actual.getDato().getPaciente().equals(paciente) && actual.getDato().getFecha().equals(fecha)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public void recorrerLista() {
        Nodo<Cita> actual = citas.getHead();
        for (int i = 0; i < citas.getTamano(); i++) {
            Cita citaActual = actual.getDato();
            System.out.println("Indice: " + i + " Cita: " + citaActual.getFecha() + " - " + citaActual.getPaciente().getNombre());
            actual = actual.getSiguiente();
        }
    }
}
