package consultoriomedico.utils;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleUtils {
    private static final Scanner scanner = new Scanner(System.in);

    private ConsoleUtils() {
    }

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        try {
            if (!scanner.hasNextLine()) {
                return "";
            }
            return scanner.nextLine();
        } catch (NoSuchElementException | IllegalStateException e) {
            return "";
        }
    }

    public static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            if (!scanner.hasNextInt()) {
                return 0;
            }
            int valor = scanner.nextInt();
            scanner.nextLine();
            return valor;
        } catch (NoSuchElementException | IllegalStateException e) {
            return 0;
        }
    }

    public static double leerDecimal(String mensaje) {
        System.out.print(mensaje);
        try {
            if (!scanner.hasNextDouble()) {
                return 0;
            }
            double valor = scanner.nextDouble();
            scanner.nextLine();
            return valor;
        } catch (NoSuchElementException | IllegalStateException e) {
            return 0;
        }
    }

    public static void cerrar() {
        scanner.close();
    }
}