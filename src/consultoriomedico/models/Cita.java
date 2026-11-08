package consultoriomedico.models;

import java.time.LocalDate;
import java.util.Objects;

public class Cita {
    private LocalDate fecha;
    private String hora;
    private String motivo;
    private Paciente paciente;
    private Medico medico;
    private Consulta consulta;

    public Cita(LocalDate fecha, String hora, String motivo, Paciente paciente, Medico medico) {
        setFecha(fecha);
        setHora(hora);
        setMotivo(motivo);
        setPaciente(paciente);
        setMedico(medico);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha de la cita no puede ser nula");
        }
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        if (hora == null || hora.isBlank()) {
            throw new IllegalArgumentException("La hora de la cita no puede ser nula ni vacía");
        }
        this.hora = hora.trim();
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("El motivo de la cita no puede ser nulo ni vacío");
        }
        this.motivo = motivo.trim();
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("La cita requiere un paciente válido");
        }
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("La cita requiere un médico válido");
        }
        this.medico = medico;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Cita otra = (Cita) obj;
        return Objects.equals(fecha, otra.fecha)
                && Objects.equals(hora, otra.hora)
                && Objects.equals(motivo, otra.motivo)
                && Objects.equals(paciente != null ? paciente.getIdentificacion() : null,
                        otra.paciente != null ? otra.paciente.getIdentificacion() : null)
                && Objects.equals(medico != null ? medico.getIdentificacion() : null,
                        otra.medico != null ? otra.medico.getIdentificacion() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, hora, motivo,
                paciente != null ? paciente.getIdentificacion() : null,
                medico != null ? medico.getIdentificacion() : null);
    }
}
