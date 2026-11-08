
package consultoriomedico.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

import consultoriomedico.models.Agenda;
import consultoriomedico.models.Cita;
import consultoriomedico.models.Medico;
import consultoriomedico.models.Paciente;
import consultoriomedico.structures.ListaDoble;
import consultoriomedico.structures.ListaSimple;
import consultoriomedico.structures.Nodo;
import consultoriomedico.structures.NodoDoble;

public class AgendaService {

    private static final String RUTA_ARCHIVO = "data/agenda.txt";

    private final ListaSimple<Agenda> agendas;

    private final PacienteService pacienteService;
    private final MedicoService medicoService;
    private final CitaService citaService;

    private boolean cargaRealizada;

    private Agenda agendaSeleccionada;
    private Cita citaSeleccionada;

    public AgendaService(
            PacienteService pacienteService,
            MedicoService medicoService,
            CitaService citaService) {

        if (pacienteService == null
                || medicoService == null
                || citaService == null) {
            throw new IllegalArgumentException(
                    "Los servicios de pacientes, médicos y citas son obligatorios.");
        }

        this.pacienteService = pacienteService;
        this.medicoService = medicoService;
        this.citaService = citaService;

        this.agendas = new ListaSimple<>();
        this.cargaRealizada = false;
        this.agendaSeleccionada = null;
        this.citaSeleccionada = null;
    }

    public String registrarCita(Cita cita) {

        if (cita == null
            || cita.getFecha() == null
            || cita.getPaciente() == null
            || cita.getMedico() == null) {

            return "No se puede registrar una cita inválida.";
        }

        String fecha = cita.getFecha().toString();

        Agenda agenda = buscarAgenda(fecha);

        if (agenda == null) {
            agenda = new Agenda(fecha);
            agendas.insertarFinal(agenda);
        }

        if (agenda.getCitas().buscar(cita) != null) {
            return "La cita ya está registrada en la agenda.";
        }

        agenda.getCitas().insertarAlFinal(cita);

        String resultadoGuardado = guardarAgendas();

        if (!"Agenda guardada correctamente.".equals(resultadoGuardado)) {
            return "La cita se registró en memoria, pero no se pudo guardar "
                    + "en el archivo.\n" + resultadoGuardado;
        }

        return "Cita registrada exitosamente en la agenda.";
    }

    private Agenda buscarAgenda(String fecha) {

        if (fecha == null || fecha.isBlank()) {
            return null;
        }

        Nodo<Agenda> actual = agendas.getHead();

        while (actual != null) {

            Agenda agenda = actual.getDato();

            if (agenda.getFecha().equals(fecha)) {
                return agenda;
            }

            actual = actual.getSiguiente();
        }

        return null;
    }

    public String listarAdelante(String fecha) {

        Agenda agenda = buscarAgenda(fecha);

        if (agenda == null || agenda.getCitas().isEmpty()) {
            return "No hay citas registradas para esa fecha.";
        }

        return agenda.getCitas().recorrerAdelante();
    }

    public String listarAtras(String fecha) {

        Agenda agenda = buscarAgenda(fecha);

        if (agenda == null || agenda.getCitas().isEmpty()) {
            return "No hay citas registradas para esa fecha.";
        }

        return agenda.getCitas().recorrerAtras();
    }

    public String irAlInicio(String fecha) {

        Agenda agenda = buscarAgenda(fecha);

        if (agenda == null) {
            return "No hay citas registradas para esa fecha.";
        }

        try {

            Cita cita = agenda.getCitas().irAlInicio();

            agendaSeleccionada = agenda;
            citaSeleccionada = cita;

            return formatearCita(cita);

        } catch (NoSuchElementException e) {
            return "No hay citas para seleccionar.";
        }
    }

    public String irAlFinal(String fecha) {

        Agenda agenda = buscarAgenda(fecha);

        if (agenda == null) {
            return "No hay citas registradas para esa fecha.";
        }

        try {

            Cita cita = agenda.getCitas().irAlFinal();

            agendaSeleccionada = agenda;
            citaSeleccionada = cita;

            return formatearCita(cita);

        } catch (NoSuchElementException e) {
            return "No hay citas para seleccionar.";
        }
    }

    public String siguiente(String fecha) {

        Agenda agenda = buscarAgenda(fecha);

        if (agenda == null) {
            return "No hay citas registradas para esa fecha.";
        }

        try {

            Cita cita = agenda.getCitas().siguiente();

            agendaSeleccionada = agenda;
            citaSeleccionada = cita;

            return formatearCita(cita);

        } catch (NoSuchElementException e) {
            return "No existe una cita siguiente.";
        }
    }

    public String anterior(String fecha) {

        Agenda agenda = buscarAgenda(fecha);

        if (agenda == null) {
            return "No hay citas registradas para esa fecha.";
        }

        try {

            Cita cita = agenda.getCitas().anterior();

            agendaSeleccionada = agenda;
            citaSeleccionada = cita;

            return formatearCita(cita);

        } catch (NoSuchElementException e) {
            return "No existe una cita anterior.";
        }
    }

    public boolean hayCitaSiguiente(String fecha) {

        Agenda agenda = buscarAgenda(fecha);

        return agenda != null
                && agenda.getCitas().haySiguiente();
    }

    public boolean hayCitaAnterior(String fecha) {

        Agenda agenda = buscarAgenda(fecha);

        return agenda != null
                && agenda.getCitas().hayAnterior();
    }

    public boolean hayCitaSeleccionada(String fecha) {

        return agendaSeleccionada != null
                && citaSeleccionada != null
                && agendaSeleccionada.getFecha().equals(fecha);
    }

    public String cancelarCitaSeleccionada(String fecha) {

        if (!hayCitaSeleccionada(fecha)) {
            return "Primero debe seleccionar una cita.";
        }

        Agenda agenda = agendaSeleccionada;
        Cita cita = citaSeleccionada;

        boolean eliminada = citaService.eliminarCita(cita);

        if (!eliminada) {
            return "No fue posible cancelar la cita.";
        }

        NodoDoble<Cita> nodo = agenda.getCitas().buscar(cita);

        if (nodo != null) {
            agenda.getCitas().eliminar(nodo);
        }

        agendaSeleccionada = null;
        citaSeleccionada = null;

        if (agenda.getCitas().isEmpty()) {
            agendas.eliminarPorValor(agenda);
        }

        String resultadoGuardado = guardarAgendas();

        if (!"Agenda guardada correctamente.".equals(resultadoGuardado)) {
            return "La cita fue cancelada en memoria, pero no se pudo "
                    + "actualizar el archivo.\n" + resultadoGuardado;
        }

        return "Cita cancelada exitosamente.";
    }

    public int contarCitas(String fecha) {

        Agenda agenda = buscarAgenda(fecha);

        if (agenda == null) {
            return 0;
        }

        return agenda.getCitas().size();
    }

    private String formatearCita(Cita cita) {

        StringBuilder sb = new StringBuilder();

        sb.append("\n--- CITA SELECCIONADA ---\n");
        sb.append("Fecha: ").append(cita.getFecha()).append("\n");
        sb.append("Hora: ").append(cita.getHora()).append("\n");
        sb.append("Motivo: ").append(cita.getMotivo()).append("\n");
        sb.append("Paciente: ")
                .append(cita.getPaciente().getNombre()).append("\n");
        sb.append("Identificación del paciente: ")
                .append(cita.getPaciente().getIdentificacion()).append("\n");
        sb.append("Médico: ")
                .append(cita.getMedico().getNombre()).append("\n");
        sb.append("Identificación del médico: ")
                .append(cita.getMedico().getIdentificacion()).append("\n");

        return sb.toString();
    }

    public String guardarAgendas() {

        File directorio = new File("data");

        if (!directorio.exists() && !directorio.mkdirs()) {
            return "No fue posible crear el directorio data.";
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {

            Nodo<Agenda> actualAgenda = agendas.getHead();

            while (actualAgenda != null) {

                Agenda agenda = actualAgenda.getDato();

                NodoDoble<Cita> actualCita = agenda.getCitas().getHead();

                while (actualCita != null) {

                    Cita cita = actualCita.getDato();

                    escritor.write(
                            cita.getPaciente().getIdentificacion()
                                    + ";"
                                    + cita.getMedico().getIdentificacion()
                                    + ";"
                                    + cita.getFecha()
                                    + ";"
                                    + cita.getHora()
                                    + ";"
                                    + cita.getMotivo());

                    escritor.newLine();

                    actualCita = actualCita.getSiguiente();
                }

                actualAgenda = actualAgenda.getSiguiente();
            }

            return "Agenda guardada correctamente.";

        } catch (FileNotFoundException e) {

            return "No se encontró el archivo de agenda.";

        } catch (IOException e) {

            return "Error al guardar la agenda: " + e.getMessage();
        }
    }   

    public String cargarAgendas() {

        if (cargaRealizada) {
            return "Las agendas ya fueron cargadas correctamente.";
        }

        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            return "Todavía no existe un archivo de agenda.";
        }

        while (!agendas.estaVacia()) {
            agendas.eliminarInicio();
        }

        citaService.limpiarCitas();

        int cargadas = 0;
        int omitidas = 0;
        boolean faltanReferencias = false;

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                if (linea.isBlank()) {
                    continue;
                }

                try {

                    String[] datos = linea.split(";", -1);

                    if (datos.length != 5) {
                        omitidas++;
                        continue;
                    }

                    String identificacionPaciente = datos[0].trim();
                    String identificacionMedico = datos[1].trim();
                    String fechaTexto = datos[2].trim();
                    String hora = datos[3].trim();
                    String motivo = datos[4].trim();

                    if (identificacionPaciente.isEmpty()
                            || identificacionMedico.isEmpty()
                            || fechaTexto.isEmpty()
                            || hora.isEmpty()
                            || motivo.isEmpty()) {

                        omitidas++;
                        continue;
                    }

                    LocalDate fecha = LocalDate.parse(fechaTexto);

                    Paciente paciente = pacienteService.buscarPorIdentificacion(
                            identificacionPaciente);

                    Medico medico = medicoService.buscarPorIdentificacion(
                            identificacionMedico);

                    if (paciente == null || medico == null) {
                        omitidas++;
                        faltanReferencias = true;
                        continue;
                    }

                    Cita cita = citaService.crearCita(
                            fecha,
                            hora,
                            motivo,
                            paciente,
                            medico);

                    Agenda agenda = buscarAgenda(fechaTexto);

                    if (agenda == null) {
                        agenda = new Agenda(fechaTexto);
                        agendas.insertarFinal(agenda);
                    }

                    agenda.getCitas().insertarAlFinal(cita);

                    cargadas++;

                } catch (DateTimeParseException
                        | IllegalArgumentException e) {

                    omitidas++;
                }
            }

            cargaRealizada = !faltanReferencias;

            return "Carga finalizada.\n"
                    + "Citas recuperadas: " + cargadas + "\n"
                    + "Registros omitidos: " + omitidas
                    + (faltanReferencias
                            ? "\nRegistre los pacientes y médicos faltantes "
                                    + "y vuelva a intentar la carga."
                            : "");

        } catch (FileNotFoundException e) {

            return "No se encontró el archivo de agenda.";

        } catch (IOException e) {

            cargaRealizada = false;

            return "Error al cargar las agendas: " + e.getMessage();
        }
    }
}
