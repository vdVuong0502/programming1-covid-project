import java.util.*;
import java.io.*;
import java.time.format.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Convert String to LocalDate type
        String date = sc.nextLine();
        System.out.print(isDateFormat(date));

    }

    static boolean isDateFormat(String date) {
        try {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
            LocalDate.parse(date, dateFormat);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}