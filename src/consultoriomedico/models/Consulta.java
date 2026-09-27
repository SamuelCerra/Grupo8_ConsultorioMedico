package consultoriomedico.models;

import java.time.LocalDate;

public class Consulta {
    private String motivo;
    private String diagnostico;
    private LocalDate fecha;

    public Consulta(String motivo, String diagnostico, LocalDate fecha) {
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Consulta"
                + "\nMotivo: " + motivo
                + "\nDiagnostico: " + diagnostico
                + "\nFecha: " + fecha;
    }
}
