
package consultoriomedico.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import consultoriomedico.models.Consulta;
import consultoriomedico.models.Paciente;
import consultoriomedico.models.SalaDeEspera;
import consultoriomedico.structures.Cola;
import consultoriomedico.structures.ListaSimple;
import consultoriomedico.structures.Nodo;
import consultoriomedico.structures.Pila;

public class PacienteService {

    private static final String RUTA_ARCHIVO = "data/pacientes.txt";

    private ListaSimple<Paciente> pacientes = new ListaSimple<>();

    private final SalaDeEspera salaDeEspera =
            new SalaDeEspera("Sala de espera 1");

    public PacienteService() {
        cargarPacientes();
    }

    public Paciente crearPaciente(
            String identificacion,
            String nombre,
            String telefono,
            String direccion) {

        if (buscarPorIdentificacion(identificacion) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un paciente con esa identificación.");
        }

        Paciente paciente =
                new Paciente(identificacion, nombre, telefono);

        paciente.setDireccion(direccion);
        pacientes.insertarFinal(paciente);

        guardarPacientes();

        return paciente;
    }

    public Paciente buscarPorIdentificacion(String identificacion) {

        if (identificacion == null) {
            return null;
        }

        Nodo<Paciente> pacienteActual = pacientes.getHead();

        while (pacienteActual != null) {

            if (pacienteActual.getDato()
                    .getIdentificacion().equals(identificacion)) {

                return pacienteActual.getDato();
            }

            pacienteActual = pacienteActual.getSiguiente();
        }

        return null;
    }

    public Paciente buscarPorIndice(int indice) {

        if (indice < 0 || indice >= pacientes.getTamano()) {
            return null;
        }

        return pacientes.buscarPorIndice(indice);
    }

    public void recorrerLista() {

        Nodo<Paciente> actual = pacientes.getHead();

        for (int i = 0; i < pacientes.getTamano(); i++) {

            Paciente pacienteActual = actual.getDato();

            System.out.println(
                    "Indice: " + i
                    + " Paciente: " + pacienteActual.getNombre()
            );

            actual = actual.getSiguiente();
        }
    }

    public void guardarPacientes() {

        File carpeta = new File("data");

        if (!carpeta.exists() && !carpeta.mkdirs()) {
            System.out.println(
                    "No fue posible crear la carpeta data.");
            return;
        }

        try (BufferedWriter escritor = new BufferedWriter(
                new FileWriter(RUTA_ARCHIVO))) {

            Nodo<Paciente> actual = pacientes.getHead();

            while (actual != null) {

                Paciente paciente = actual.getDato();

                String linea =
                        paciente.getIdentificacion() + ";"
                        + paciente.getNombre() + ";"
                        + paciente.getTelefono() + ";"
                        + paciente.getDireccion();

                escritor.write(linea);
                escritor.newLine();

                actual = actual.getSiguiente();
            }

        } catch (FileNotFoundException e) {

            System.out.println(
                    "No se encontró el archivo de pacientes.");

        } catch (IOException e) {

            System.out.println(
                    "Error al guardar los pacientes: "
                    + e.getMessage());
        }
    }

    public void cargarPacientes() {

        pacientes = new ListaSimple<>();

        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader lector = new BufferedReader(
                new FileReader(archivo))) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split(";", -1);

                if (datos.length != 4) {
                    System.out.println(
                            "Se omitió un registro de paciente incorrecto.");
                    continue;
                }

                try {

                    String identificacion = datos[0].trim();
                    String nombre = datos[1].trim();
                    String telefono = datos[2].trim();
                    String direccion = datos[3].trim();

                    if (identificacion.isEmpty()
                            || nombre.isEmpty()
                            || telefono.isEmpty()) {

                        System.out.println(
                                "Se omitió un paciente con datos incompletos.");
                        continue;
                    }

                    if (buscarPorIdentificacion(identificacion) != null) {
                        continue;
                    }

                    Paciente paciente = new Paciente(
                            identificacion,
                            nombre,
                            telefono
                    );

                    paciente.setDireccion(direccion);

                    pacientes.insertarFinal(paciente);

                } catch (IllegalArgumentException e) {

                    System.out.println(
                            "Se omitió un paciente con datos inválidos.");
                }
            }

        } catch (FileNotFoundException e) {

            System.out.println(
                    "No se encontró el archivo de pacientes.");

        } catch (IOException e) {

            System.out.println(
                    "Error al cargar los pacientes: "
                    + e.getMessage());
        }
    }

    public Consulta crearConsulta(
            String identificacionPaciente,
            String motivo,
            String diagnostico,
            String tratamiento,
            LocalDate fecha) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return null;
        }

        Consulta consulta = new Consulta(
                motivo,
                diagnostico,
                tratamiento,
                fecha
        );

        paciente.agregarConsulta(consulta);
        paciente.getPilaConsultas().push(consulta);

        System.out.println(
                "Consulta registrada exitosamente para "
                + paciente.getNombre()
        );

        return consulta;
    }

    public Consulta deshacerUltimaConsulta(
            String identificacionPaciente) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            return null;
        }

        Pila<Consulta> pila = paciente.getPilaConsultas();

        if (pila.isEmpty()) {
            return null;
        }

        Consulta consulta = pila.pop();

        paciente.getHistorialClinico()
                .eliminarPorValor(consulta);

        return consulta;
    }

    public Consulta consultarUltimaConsulta(
            String identificacionPaciente) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null
                || paciente.getPilaConsultas().isEmpty()) {
            return null;
        }

        return paciente.getPilaConsultas().peek();
    }

    public boolean sinOperacionesPorDeshacer(
            String identificacionPaciente) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        return paciente == null
                || paciente.getPilaConsultas().isEmpty();
    }

    public int contarOperaciones(
            String identificacionPaciente) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            return 0;
        }

        return paciente.getPilaConsultas().size();
    }

    public String listarOperaciones(
            String identificacionPaciente) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            return "Paciente no encontrado.";
        }

        Pila<Consulta> pila = paciente.getPilaConsultas();

        if (pila.isEmpty()) {
            return "No hay operaciones por deshacer";
        }

        Pila<Consulta> auxiliar = new Pila<>();
        StringBuilder resultado = new StringBuilder();
        int numero = 1;

        while (!pila.isEmpty()) {

            Consulta consulta = pila.pop();

            resultado.append("Consulta ")
                    .append(numero++)
                    .append("\n")
                    .append(consulta)
                    .append("\n\n");

            auxiliar.push(consulta);
        }

        while (!auxiliar.isEmpty()) {
            pila.push(auxiliar.pop());
        }

        return resultado.toString().trim();
    }

    public void mostrarHistorialClinico(
            String identificacionPaciente) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        Nodo<Consulta> actual =
                paciente.getHistorialClinico().getHead();

        if (actual == null) {
            System.out.println(
                    "El paciente no tiene historial clinico."
            );
            return;
        }

        System.out.println(
                "Historial clinico de " + paciente.getNombre()
        );

        int i = 1;

        while (actual != null) {

            System.out.println("\nConsulta " + i);
            System.out.println(actual.getDato());

            actual = actual.getSiguiente();
            i++;
        }
    }

    public Consulta buscarConsulta(
            String identificacionPaciente,
            LocalDate fecha) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            return null;
        }

        Nodo<Consulta> actual =
                paciente.getHistorialClinico().getHead();

        while (actual != null) {

            Consulta consulta = actual.getDato();

            if (consulta.getFecha().equals(fecha)) {
                return consulta;
            }

            actual = actual.getSiguiente();
        }

        return null;
    }

    public boolean eliminarConsulta(
            String identificacionPaciente,
            LocalDate fecha) {

        Paciente paciente =
                buscarPorIdentificacion(identificacionPaciente);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return false;
        }

        Consulta consulta =
                buscarConsulta(identificacionPaciente, fecha);

        if (consulta == null) {
            System.out.println("Consulta no encontrada.");
            return false;
        }

        boolean eliminado =
                paciente.getHistorialClinico()
                        .eliminarPorValor(consulta);

        if (eliminado) {

            Pila<Consulta> pila =
                    paciente.getPilaConsultas();

            Pila<Consulta> auxiliar = new Pila<>();

            while (!pila.isEmpty()) {

                Consulta actual = pila.pop();

                if (actual != consulta) {
                    auxiliar.push(actual);
                }
            }

            while (!auxiliar.isEmpty()) {
                pila.push(auxiliar.pop());
            }

            System.out.println(
                    "Consulta eliminada exitosamente."
            );

            return true;
        }

        System.out.println("Error al eliminar la consulta.");
        return false;
    }

    public String listarEnEspera() {

        Cola<Paciente> fila = salaDeEspera.getFila();

        if (fila.isEmpty()) {
            return "No hay pacientes en espera.";
        }

        StringBuilder sb =
                new StringBuilder("--- PACIENTES EN ESPERA ---");

        int total = fila.size();

        for (int i = 1; i <= total; i++) {

            Paciente paciente = fila.dequeue();

            sb.append("\n")
                    .append(i)
                    .append(". ")
                    .append(paciente.getNombre())
                    .append(" (ID: ")
                    .append(paciente.getIdentificacion())
                    .append(")");

            fila.enqueue(paciente);
        }

        return sb.toString();
    }

    public void registrarLLegada(String identificacion) {

        Paciente paciente =
                buscarPorIdentificacion(identificacion);

        if (paciente != null) {
            salaDeEspera.getFila().enqueue(paciente);
        }
    }

    public Paciente atenderSiguiente() {

        Paciente paciente =
                salaDeEspera.getFila().dequeue();

        return paciente;
    }

    public Paciente consultarSiguiente() {

        Paciente paciente =
                salaDeEspera.getFila().front();

        return paciente;
    }

    public boolean sinClientesEnEspera() {
        return salaDeEspera.getFila().isEmpty();
    }

    public int contarEnEspera() {
        return salaDeEspera.getFila().size();
    }

    private Paciente buscarPaciente(String identificacion) {

        Paciente paciente =
                buscarPorIdentificacion(identificacion);

        if (paciente == null) {
            throw new IllegalArgumentException(
                    "Paciente no encontrado.");
        }

        return paciente;
    }

    public void actualizarNombrePaciente(
            Paciente paciente,
            String nuevoNombre) {

        if (paciente != null) {

            paciente.setNombre(nuevoNombre);

            guardarPacientes();

            System.out.println(
                    "Nombre del paciente actualizado exitosamente."
            );

        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    public void actualizarTelefonoPaciente(
            Paciente paciente,
            String nuevoTelefono) {

        if (paciente != null) {

            paciente.setTelefono(nuevoTelefono);

            guardarPacientes();

            System.out.println(
                    "Telefono del paciente actualizado exitosamente."
            );

        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    public void actualizarDireccionPaciente(
            Paciente paciente,
            String nuevaDireccion) {

        if (paciente != null) {

            paciente.setDireccion(nuevaDireccion);

            guardarPacientes();

            System.out.println(
                    "Direccion del paciente actualizada exitosamente."
            );

        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    public boolean eliminarPaciente(Paciente paciente) {

        if (paciente != null) {

            boolean eliminado = pacientes.eliminar(paciente);

            if (eliminado) {

                guardarPacientes();

                System.out.println(
                        "Paciente eliminado exitosamente."
                );

                return true;
            }

            System.out.println("Error al eliminar el paciente.");
            return false;
        }

        System.out.println("Paciente no encontrado.");
        return false;
    }
}
