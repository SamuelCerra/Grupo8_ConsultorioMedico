package consultoriomedico.models;

public abstract class Persona implements RolClinico {
    private String identificacion;
    private String nombre;
    private String telefono;

    public Persona(String identificacion, String nombre, String telefono) {
        setIdentificacion(identificacion);
        setNombre(nombre);
        setTelefono(telefono);
    }

    @Override
    public String datosResumen() {
        return "Identificacion: " + identificacion
                + "\nNombre: " + nombre
                + "\nTelefono: " + telefono;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalArgumentException("La identificación no puede ser nula ni vacía");
        }
        this.identificacion = identificacion.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío");
        }
        this.nombre = nombre.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("El teléfono no puede ser nulo ni vacío");
        }
        this.telefono = telefono.trim();
    }
}