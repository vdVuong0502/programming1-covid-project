import java.util.*;

import java.io.*;
import java.time.format.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LocalDate dt;
        // Convert String to LocalDate type
        String date = sc.nextLine();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
        dt = LocalDate.parse(date, dateFormat);
        System.out.println(dt.format(dateFormat));

    }

}