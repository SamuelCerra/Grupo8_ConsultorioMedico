import com.itm.consultoriomedico.models.domain.Paciente;
import com.itm.consultoriomedico.models.domain.Medico;
import com.itm.consultoriomedico.models.domain.Consulta;
import com.itm.consultoriomedico.models.domain.Cita;
import com.itm.consultoriomedico.models.domain.Persona;

import java.time.LocalDate;

public class PruebaCreacionObjetos {

    public static void main(String[] args) {

        Paciente paciente = new Paciente(
                "123456",
                "Juan Pérez",
                "3001234567",
                25,
                "Sura"
        );

        Medico medico = new Medico(
                "987654",
                "Dra. Yessy Mauricio",
                "3009876543",
                "Cardiología",
                "12345"
        );

        Consulta consulta = new Consulta(
                "Dolor de cabeza",
                "Migraña",
                "Reposo y medicamentos",
                LocalDate.now()
        );

        paciente.agregarConsulta(consulta);

        Cita cita = new Cita(
                LocalDate.now(),
                "10:00",
                "Consulta de seguimiento",
                paciente,
                medico
        );

        Persona personaPaciente = paciente;
        Persona personaMedico = medico;

        System.out.println("=== DATOS DEL PACIENTE ===");
        System.out.println(personaPaciente.datosResumen());
        System.out.println(personaPaciente.rolEnConsulta());

        System.out.println();

        System.out.println("=== DATOS DEL MÉDICO ===");
        System.out.println(personaMedico.datosResumen());
        System.out.println(personaMedico.rolEnConsulta());

        System.out.println();

        System.out.println("=== CONSULTA ===");
        System.out.println(consulta);

        System.out.println();

        System.out.println("=== CITA ===");
        System.out.println("Fecha: " + cita.getFecha());
        System.out.println("Hora: " + cita.getHora());
        System.out.println("Motivo: " + cita.getMotivo());
        System.out.println("Paciente: " + cita.getPaciente().getNombre());
        System.out.println("Médico: " + cita.getMedico().getNombre());

    }
}