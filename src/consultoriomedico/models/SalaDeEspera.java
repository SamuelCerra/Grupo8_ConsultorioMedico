package consultoriomedico.models;

import consultoriomedico.structures.Cola;

public class SalaDeEspera {
    public String nombre;
    private Cola<Paciente> fila;

    public SalaDeEspera( String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException( " El nombre no puede ser nulo ni vacio");
        }
        this.nombre = nombre;
        this.fila = new Cola<>();
    }


    public Cola<Paciente> getFila() {
        return fila;
    }

    public String getNombre() {
        return nombre;
    }
}
