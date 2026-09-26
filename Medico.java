package consultoriomedico.models;

import consultoriomedico.structures.ListaSimple;

public class Medico extends Persona {
    private String especialidad;
    private String numeroRegistro;
    private ListaSimple<Cita> citas;

    public Medico(String identificacion, String nombre, String telefono, String especialidad, String numeroRegistro) {
        super(identificacion, nombre, telefono);
        this.especialidad = especialidad;
        this.numeroRegistro = numeroRegistro;
        this.citas = new ListaSimple<>();
    }

    @Override
    public String rolEnConsulta() {
        return "Medico - Especialidad: " + especialidad + ", Registro: " + numeroRegistro;
    }

    public void agregarCita(Cita cita) {
        if (cita != null) {
            citas.insertarFinal(cita);
        }
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public ListaSimple<Cita> getCitas() {
        return citas;
    }
}
