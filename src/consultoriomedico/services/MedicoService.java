
package consultoriomedico.services;

import consultoriomedico.models.Cita;
import consultoriomedico.models.Medico;
import consultoriomedico.structures.ListaSimple;
import consultoriomedico.structures.Nodo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MedicoService {

    private static final String RUTA_ARCHIVO = "data/medicos.txt";

    private final ListaSimple<Medico> medicos =
            new ListaSimple<>();

    private final CitaService citaService;

    public MedicoService(CitaService citaService) {
        if (citaService == null) {
            throw new IllegalArgumentException(
                    "El servicio de citas es obligatorio.");
        }

        this.citaService = citaService;
        cargarMedicos();
    }

    public Medico crearMedico(
            String identificacion,
            String nombre,
            String telefono,
            String especialidad,
            String numeroRegistro) {

        if (identificacion == null || identificacion.isBlank()
                || nombre == null || nombre.isBlank()
                || telefono == null || telefono.isBlank()
                || especialidad == null || especialidad.isBlank()
                || numeroRegistro == null || numeroRegistro.isBlank()) {
            throw new IllegalArgumentException(
                    "Todos los datos del medico son obligatorios.");
        }

        if (buscarPorIdentificacion(identificacion) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un medico con esa identificacion.");
        }

        Medico medico = new Medico(
                identificacion,
                nombre,
                telefono,
                especialidad,
                numeroRegistro
        );

        medicos.insertarFinal(medico);
        guardarMedicos();

        return medico;
    }

    public Medico buscarPorIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.isBlank()) {
            return null;
        }

        Nodo<Medico> actual = medicos.getHead();

        while (actual != null) {
            Medico medico = actual.getDato();

            if (medico.getIdentificacion()
                    .equals(identificacion)) {
                return medico;
            }

            actual = actual.getSiguiente();
        }

        return null;
    }

    public void recorrerLista() {
        if (medicos.estaVacia()) {
            System.out.println("No hay medicos registrados.");
            return;
        }

        Nodo<Medico> actual = medicos.getHead();
        int indice = 0;

        while (actual != null) {
            Medico medico = actual.getDato();

            System.out.println("\nIndice: " + indice);
            System.out.println(
                    "Identificacion: " + medico.getIdentificacion());
            System.out.println("Nombre: " + medico.getNombre());
            System.out.println("Telefono: " + medico.getTelefono());
            System.out.println(
                    "Especialidad: " + medico.getEspecialidad());
            System.out.println(
                    "Numero de registro: "
                    + medico.getNumeroRegistro());

            actual = actual.getSiguiente();
            indice++;
        }
    }

    public boolean eliminarMedico(Medico medico) {
        if (medico == null) {
            System.out.println("Medico no encontrado.");
            return false;
        }

        if (citaService.tieneCitasMedico(medico)) {
            System.out.println(
                    "No se puede eliminar el medico porque tiene citas registradas.");
            return false;
        }

        boolean eliminado = medicos.eliminarPorValor(medico);

        if (eliminado) {
            guardarMedicos();
            System.out.println("Medico eliminado exitosamente.");
            return true;
        }

        System.out.println("No fue posible eliminar el medico.");
        return false;
    }

    private void guardarMedicos() {
        File carpeta = new File("data");

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        try (BufferedWriter escritor =
                new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {

            Nodo<Medico> actual = medicos.getHead();

            while (actual != null) {
                Medico medico = actual.getDato();

                escritor.write(
                        medico.getIdentificacion() + ";"
                        + medico.getNombre() + ";"
                        + medico.getTelefono() + ";"
                        + medico.getEspecialidad() + ";"
                        + medico.getNumeroRegistro()
                );

                escritor.newLine();
                actual = actual.getSiguiente();
            }

        } catch (IOException e) {
            System.out.println(
                    "Error al guardar los medicos: " + e.getMessage());
        }
    }

    private void cargarMedicos() {
        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader lector =
                new BufferedReader(new FileReader(archivo))) {

            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(";", -1);

                if (datos.length == 5
                        && buscarPorIdentificacion(datos[0]) == null) {

                    Medico medico = new Medico(
                            datos[0],
                            datos[1],
                            datos[2],
                            datos[3],
                            datos[4]
                    );

                    medicos.insertarFinal(medico);
                }
            }

        } catch (IOException e) {
            System.out.println(
                    "Error al cargar los medicos: " + e.getMessage());
        }
    }
}
