
package consultoriomedico.models;

public class Medico extends Persona {

    private String especialidad;
    private String numeroRegistro;

    public Medico(
            String identificacion,
            String nombre,
            String telefono,
            String especialidad,
            String numeroRegistro) {

        super(identificacion, nombre, telefono);
        setEspecialidad(especialidad);
        setNumeroRegistro(numeroRegistro);
    }

    @Override
    public String rolEnConsulta() {
        return "Medico - Especialidad: "
                + especialidad
                + ", Registro: "
                + numeroRegistro;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.isBlank()) {
            throw new IllegalArgumentException("La especialidad no puede ser nula ni vacía");
        }
        this.especialidad = especialidad.trim();
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        if (numeroRegistro == null || numeroRegistro.isBlank()) {
            throw new IllegalArgumentException("El número de registro no puede ser nulo ni vacío");
        }
        this.numeroRegistro = numeroRegistro.trim();
    }
}
