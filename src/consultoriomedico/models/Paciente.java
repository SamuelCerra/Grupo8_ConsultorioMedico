
package consultoriomedico.models;

import consultoriomedico.structures.ListaSimple;
import consultoriomedico.structures.Pila;

public class Paciente extends Persona {

    private final ListaSimple<Consulta> historialClinico;
    private final Pila<Consulta> pilaConsultas;
    private String direccion = "";

    public Paciente(
            String identificacion,
            String nombre,
            String telefono) {

        super(identificacion, nombre, telefono);
        this.historialClinico = new ListaSimple<>();
        this.pilaConsultas = new Pila<>();
    }

    @Override
    public String rolEnConsulta() {
        return "Paciente";
    }

    public void agregarConsulta(Consulta consulta) {
        if (consulta != null) {
            historialClinico.insertarFinal(consulta);
        }
    }

    public ListaSimple<Consulta> getHistorialClinico() {
        return historialClinico;
    }

    public Pila<Consulta> getPilaConsultas() {
        return pilaConsultas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
