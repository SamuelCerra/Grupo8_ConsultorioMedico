package consultoriomedico.models;

import java.time.LocalDate;

public class Consulta {
    private String motivo;
    private String diagnostico;
    private String tratamiento;
    private LocalDate fecha;

    public Consulta(String motivo, String diagnostico,
                    String tratamiento, LocalDate fecha) {
        setMotivo(motivo);
        setDiagnostico(diagnostico);
        setTratamiento(tratamiento);
        setFecha(fecha);
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("El motivo no puede ser nulo ni vacío");
        }
        this.motivo = motivo.trim();
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        if (diagnostico == null || diagnostico.isBlank()) {
            throw new IllegalArgumentException("El diagnóstico no puede ser nulo ni vacío");
        }
        this.diagnostico = diagnostico.trim();
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        if (tratamiento == null || tratamiento.isBlank()) {
            throw new IllegalArgumentException("El tratamiento no puede ser nulo ni vacío");
        }
        this.tratamiento = tratamiento.trim();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Consulta"
                + "\nMotivo: " + motivo
                + "\nDiagnostico: " + diagnostico
                + "\nTratamiento: " + tratamiento
                + "\nFecha: " + fecha;
    }
}
