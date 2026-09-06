
package com.itm.consultoriomedico.models.domain;


import java.util.ArrayList;
import java.util.List;

public class Paciente extends Persona {

    private int edad;
    private String eps;

    private List<Consulta> consultas;

    public Paciente(String identificacion, String nombre, String telefono, int edad, String eps) {

        super(identificacion, nombre, telefono);
        this.edad = edad;
        this.eps = eps;
        this.consultas = new ArrayList<>();
    }

   @Override
public String rolEnConsulta() {
    return "Paciente - EPS: " + eps + ", Edad: " + edad;
}

    public void agregarConsulta(Consulta consulta) {
        consultas.add(consulta);
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }
}