package consultoriomedico.models;

import consultoriomedico.structures.ListaSimple;

public class Paciente extends Persona {
    private ListaSimple<Cita> citas;
    private ListaSimple<Consulta> historialClinico;
    private String direccion = "";

    public Paciente(String identificacion, String nombre, String telefono) {
        super(identificacion, nombre, telefono);
        this.citas = new ListaSimple<>();
        this.historialClinico = new ListaSimple<>();
    }

    @Override
    public String rolEnConsulta() {
        return "Paciente ";
    }

    public void agregarCita(Cita cita) {
        if (cita != null) {
            citas.insertarFinal(cita);
        }
    }

    public void agregarConsulta(Consulta consulta) {
        if (consulta != null) {
            historialClinico.insertarFinal(consulta);
        }
    }

    public ListaSimple<Cita> getCitas() {
        return citas;
    }

    public ListaSimple<Consulta> getHistorialClinico() {
        return historialClinico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
