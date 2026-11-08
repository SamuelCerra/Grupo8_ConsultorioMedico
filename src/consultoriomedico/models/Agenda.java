
package consultoriomedico.models;

import consultoriomedico.structures.ListaDoble;

public class Agenda {

    private final String fecha;
    private final ListaDoble<Cita> citas;

    public Agenda(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha no puede estar vacía");
        }

        this.fecha = fecha;
        this.citas = new ListaDoble<>();
    }

    public String getFecha() {
        return fecha;
    }

    public ListaDoble<Cita> getCitas() {
        return citas;
    }
}
