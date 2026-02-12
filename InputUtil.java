import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputUtil {

    static Scanner sc = new Scanner(System.in);

    public static int lerInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Número inválido!");
            }
        }
    }

    public static String lerString(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    public static LocalDateTime lerData() {

        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        while (true) {
            try {
                System.out.print("Data (dd/MM/yyyy HH:mm): ");
                return LocalDateTime.parse(sc.nextLine(), fmt);
            } catch (Exception e) {
                System.out.println("Formato inválido!");
            }
        }
    }
}
