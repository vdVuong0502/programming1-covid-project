import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.*;

public class Data {
    String name;
    ArrayList<LocalDate> date = new ArrayList<LocalDate>();
    ArrayList<LocalDate> range = new ArrayList<LocalDate>();
    ArrayList<Integer> newCase = new ArrayList<Integer>();
    ArrayList<Integer> newDeath = new ArrayList<Integer>();
    ArrayList<Integer> peopleVacinated = new ArrayList<Integer>();
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/M/d");
    LocalDate startDate;
    LocalDate endDate;

    public static Data selectDataByArea(Scanner userInput, Data processedData) throws IOException {
        // Select area
        processedData = new Data();
        String selection;
        System.out.println("""
                Please specify a geographic area whose data you want to view:
                \t1. By continent
                \t2. By country""");
        System.out.print(">>> ");
        selection = userInput.nextLine();
        Main.exitCheck(selection);

        while (!(selection.equals("1")) && !(selection.equals("2"))) {
            System.out.println("==========================");
            System.out.println("""
                    Invalid selection. Please check again:
                    \t1. By continent
                    \t2. By country""");
            System.out.print(">>> ");
            selection = userInput.nextLine();
            Main.exitCheck(selection);
        }

        // Specify by continent
        if (selection.equals("1")) {
            System.out.println("Enter a continent: ");
            System.out.print(">>> ");
            String continent = Main.textHandle(userInput.nextLine());
            Main.exitCheck(continent);

            while (!findContinent(continent)) {
                System.out.println("==========================");
                System.out.println("Continent not found. Please enter continent again or enter \"exit\" to leave: ");
                System.out.print(">>> ");
                continent = Main.textHandle(userInput.nextLine());
                Main.exitCheck(continent);
            }
            System.out.println("Loading...");
            readByContinent(continent, processedData);

        } else {
            System.out.println("Enter a country: ");
            System.out.print(">>> ");
            String country = Main.textHandle(userInput.nextLine());
            Main.exitCheck(country);

            while (!findCountry(country)) {
                System.out.println("==========================");
                System.out.println("Country not found. Please enter country again or enter \"exit\" to leave: ");
                System.out.print(">>> ");
                country = Main.textHandle(userInput.nextLine());
                Main.exitCheck(country);
            }
            System.out.println("Loading...");
            readByCountry(country, processedData);
        }

        return processedData;

    }

    void sortByDate() {

        /* this method is used for sorting all arraylists by ascending date */
        int tempInt;
        LocalDate tempDate;

        for (int i = 0; i < date.size(); i++) {
            for (int j = i + 1; j < date.size(); j++) {
                if (date.get(i).isAfter(date.get(j))) { // swap elements if not in order
                    tempDate = date.get(i);
                    date.set(i, date.get(j));
                    date.set(j, tempDate);

                    tempInt = newCase.get(i);
                    newCase.set(i, newCase.get(j));
                    newCase.set(j, tempInt);

                    tempInt = newDeath.get(i);
                    newDeath.set(i, newDeath.get(j));
                    newDeath.set(j, tempInt);

                    tempInt = peopleVacinated.get(i);
                    peopleVacinated.set(i, peopleVacinated.get(j));
                    peopleVacinated.set(j, tempInt);
                }
            }
        }
    }

    void timeFilter(Scanner sc) {
        /* This method is used for filter data based on user's chosen time range */

        // choose time range
        System.out.println("==========================");
        System.out.println("""
                Specify a time range:
                    \t1. A pair of start date and end date (inclusive).
                    \t2. A number of days or weeks from a particular date.
                    \t3. A number of days or weeks to a particular date.""");
        System.out.print(">>> ");
        String selection = sc.nextLine();
        Main.exitCheck(selection);
        // selection validate
        while (!(selection.equals("1")) && !(selection.equals("2")) && !(selection.equals("3"))) {
            System.out.println("==========================");
            System.out.println("""
                    Invalid input. Specify time range again or enter "exit" to leave:
                        \t1. A pair of start date and end date (inclusive).
                        \t2. A number of days or weeks from a particular date.
                        \t3. A number of days or weeks to a particular date.""");
            System.out.print(">>> ");
            selection = sc.nextLine();
            Main.exitCheck(selection);
        }

        // Condition to execute methods
        if (selection.equals("1")) { // method if user choose to enter a pair of start date and end date
            pairOfDates(sc);
        } else if (selection.equals("2")) {
            // If user choose this time range format, they have to choose to enter number of
            // days or weeks
            System.out.println("==========================");
            System.out.println("""
                    Choose days or weeks from a date:
                        \t1. Days from a date.
                        \t2. Weeks from a date.""");
            System.out.print(">>> ");
            selection = sc.nextLine();
            Main.exitCheck(selection);
            // Selection validate
            while (!(selection.equals("1")) && !(selection.equals("2"))) {
                System.out.println("==========================");
                System.out.println("""
                        Invalid input. Choose days or weeks or "exit" to leave:
                        \t1. Days from a date.
                        \t2. Weeks from a date.""");
                System.out.print(">>> ");
                selection = sc.nextLine();
                Main.exitCheck(selection);
            }
            // Execute these 2 methods base on user's input
            if (selection.equals("1")) {
                daysFromDate(sc);
            } else {
                weeksFromDate(sc);
            }
        } else {
            // If user choose this time range format, they have to choose to enter number of
            // days or weeks
            System.out.println("==========================");
            System.out.println("""
                    Choose days or weeks to a date:
                        \t1. Days to a date.
                        \t2. Weeks to a date.""");
            System.out.print(">>> ");
            selection = sc.nextLine();
            Main.exitCheck(selection);
            // Selection validates
            while (!(selection.equals("1")) && !(selection.equals("2"))) {
                System.out.println("==========================");
                System.out.println("""
                        Invalid input. Choose days or weeks or "exit" to leave:
                        \t1. Days to a date.
                        \t2. Weeks to a date.""");
                System.out.print(">>> ");
                selection = sc.nextLine();
                Main.exitCheck(selection);
            }
            // Execute these 2 methods base on user's input
            if (selection.equals("1")) {
                daysToDate(sc);
            } else {
                weeksToDate(sc);
            }
        }

        // add all the dates in time range into range list

        for (int i = 0; i < date.size(); i++) {
            if ((date.get(i).isEqual(startDate) || date.get(i).isAfter(startDate))
                    && (date.get(i).isEqual(endDate) || date.get(i).isBefore(endDate))) {
                range.add(date.get(i));
            }
        }
    }

    boolean checkNumberInput(String input) {
        /* This method to check if user's input can be parse to integer. */
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    String durationInput(Scanner sc, String duration) {
        /*
         * This method to let user enter duration and check if the duration can be parse
         * to integer.
         */
        duration = sc.nextLine();
        Main.exitCheck(duration);
        while (!checkNumberInput(duration)) {
            System.out.println("==========================");
            System.out.println("Input must be an integer: ");
            System.out.print(">>> ");
            duration = sc.nextLine();
            Main.exitCheck(duration);
        }
        return duration;
    }

    void daysFromDate(Scanner sc) {
        /*
         * This method is to let user choose a time range base on a date and a number of
         * days to be plus to it.
         */
        String dateStr = ""; // to store the date
        String days = ""; // to store number of days
        // get user date input and validate. If it's fine, store it to object's start
        // date
        System.out.println("==========================");
        System.out.println("Enter base date following the format \"YYYY/MM/DD\": ");
        System.out.print(">>> ");
        dateStr = checkDateInput(sc, dateStr);
        startDate = LocalDate.parse(dateStr, df);

        System.out.println("==========================");
        System.out.println("Enter the number of days from base date: ");
        System.out.print(">>> ");

        // get user number of days input and validate. If it's fine, plus the
        // original start date and store it to object's end
        // date
        days = durationInput(sc, days);
        endDate = startDate.plusDays(Integer.parseInt(days));
        // Input validate
        while (!checkInTimeRange(endDate.format(df))) {
            System.out.println("==========================");
            System.out.println("""
                    Time range you chose out of possible range. Please choose days again.
                    \sPossible time range: """);
            System.out.println("\t" + date.get(0) + " -- " + date.get(date.size() - 1));
            days = durationInput(sc, days);
            endDate = startDate.plusDays(Integer.parseInt(days));
        }
    }

    void daysToDate(Scanner sc) {
        /*
         * This method is to let user choose a time range base on a date and a number of
         * days to be minus.
         */
        String dateStr = ""; // store date
        String days = ""; // store number of days to minus

        // get user date input and validate. If it's fine, store it to object's end
        // date
        System.out.println("==========================");
        System.out.println("Enter base date: ");
        System.out.print(">>> ");
        dateStr = checkDateInput(sc, dateStr);
        endDate = LocalDate.parse(dateStr, df);

        System.out.println("==========================");
        System.out.println("Enter the number of days to base date: ");
        System.out.print(">>> ");
        // get user number of days input and validate. If it's fine, minus the
        // original end date and store it to object's start
        // date
        days = durationInput(sc, days);
        startDate = endDate.minusDays(Integer.parseInt(days));
        // Input validates
        while (!checkInTimeRange(startDate.format(df))) {
            System.out.println("==========================");
            System.out.println("""
                    Time range you chose out of possible range. Please choose days again.
                    \sPossible time range: """);
            System.out.println("\t" + date.get(0) + " -- " + date.get(date.size() - 1));
            days = durationInput(sc, days);
            startDate = endDate.minusDays(Integer.parseInt(days));
        }
    }

    void weeksFromDate(Scanner sc) {

        /*
         * This method is dealt with the same as days methods. just need to convert
         * weeks -> days
         */
        String dateStr = "";
        String weeks = "";
        System.out.println("==========================");
        System.out.println("Enter base date following the format \"YYYY/MM/DD\": ");
        System.out.print(">>> ");
        dateStr = checkDateInput(sc, dateStr);
        startDate = LocalDate.parse(dateStr, df);

        System.out.println("==========================");
        System.out.println("Enter the number of weeks from base date: ");
        System.out.print(">>> ");

        weeks = durationInput(sc, weeks);
        endDate = startDate.plusDays(Long.parseLong(weeks) * 7);

        while (!checkInTimeRange(endDate.format(df))) {
            System.out.println("==========================");
            System.out.println("""
                    Time range you chose out of possible range. Please choose weeks again.
                    \sPossible time range: """);
            System.out.println("\t" + date.get(0) + " -- " + date.get(date.size() - 1));
            weeks = durationInput(sc, weeks);
            endDate = startDate.plusDays(Long.parseLong(weeks) * 7);
        }
    }

    void weeksToDate(Scanner sc) {
        /*
         * This method is dealt with the same as days methods. just need to convert
         * weeks -> days
         */
        String dateStr = "";
        String weeks = "";
        System.out.println("==========================");
        System.out.println("Enter base date following the format \"YYYY/MM/DD\": ");
        System.out.print(">>> ");
        dateStr = checkDateInput(sc, dateStr);
        endDate = LocalDate.parse(dateStr, df);

        System.out.println("==========================");
        System.out.println("Enter the number of weeks to base date: ");
        System.out.print(">>> ");

        weeks = durationInput(sc, weeks);
        startDate = endDate.minusDays(Long.parseLong(weeks) * 7);

        while (!checkInTimeRange(startDate.format(df))) {
            System.out.println("==========================");
            System.out.println("""
                    Time range you chose out of possible range. Please choose weeks again.
                    \sPossible time range: """);
            System.out.println("\t" + date.get(0) + " -- " + date.get(date.size() - 1));
            weeks = durationInput(sc, weeks);
            startDate = endDate.minusDays(Long.parseLong(weeks) * 7);
        }
    }

    boolean isDateFormat(String date) {
        /* This method is used to check if the input date is correctly format or not */
        try {
            LocalDate.parse(date, df);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    boolean isLeapYear(int year) {
        boolean leap = false;

        // if the year is divided by 4
        if (year % 4 == 0) {

            // if the year is century
            if (year % 100 == 0) {

                // if year is divided by 400
                // then it is a leap year
                if (year % 400 == 0)
                    leap = true;
                else
                    leap = false;
            }

            // if the year is not century
            else
                leap = true;
        }

        else {
            leap = false;
        }

        return leap;
    }

    boolean checkNotLeapYearInput(String dateStr) {

        /*
         * In a non leap year, even if users enter February 29th, the library will
         * automatically convert it to February 28th. The checkDateFormat method can not
         * check this either. So this method will do that mission.
         */
        String[] tokens = dateStr.split("/");
        // If it's not a leap year
        if (!isLeapYear(Integer.parseInt(tokens[0]))) {
            // and the month is February
            if (Integer.parseInt(tokens[1]) == 2) {
                // If user's input is day 29th
                if (Integer.parseInt(tokens[2]) > 28) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean checkInTimeRange(String dateStr) {
        // Check if the dates users enter and the dates calculated is in the database
        // time range or not
        if (LocalDate.parse(dateStr, df).isBefore(date.get(0))
                || LocalDate.parse(dateStr, df).isAfter(date.get(date.size() - 1))) {
            return false;
        }
        return true;
    }

    String checkDateInput(Scanner sc, String dateStr) {
        // deal with possible exceptions with user's date input. If input error occurs,
        // error message is displayed and the method is called recursively until users
        // enter a correct date input.
        dateStr = sc.nextLine();
        Main.exitCheck(dateStr);
        if (!isDateFormat(dateStr)) {
            System.out.println("==========================");
            System.out.println("Your input is not following the format, please try again: ");
            System.out.print(">>> ");
            dateStr = checkDateInput(sc, dateStr);
        }

        if (!checkNotLeapYearInput(dateStr)) {
            System.out.println("==========================");
            System.out.println("This is not leap year, February days cannot exceed 28.\nPlease enter date again");
            System.out.print(">>> ");
            dateStr = checkDateInput(sc, dateStr);
        }
        if (!checkInTimeRange(dateStr)) {
            System.out.println("==========================");
            System.out.println("Your input exceed possible time range. Please enter again:");
            System.out.print(">>> ");
            dateStr = checkDateInput(sc, dateStr);
        }
        return dateStr;
    }

    void pairOfDates(Scanner sc) {
        // Let users enter a pair of date and validate. if correct, store to startDate
        // and endDate

        String dateStr = "";
        System.out.println("==========================");
        System.out.print("""
                Enter start date and end start base on the format "YYYY/MM/DD"
                \sNOTE: You can only choose start date and end date between """);
        System.out.println(" " + date.get(0) + " and " + date.get(date.size() - 1));
        System.out.println("Enter start date: ");
        System.out.print(">>> ");
        dateStr = checkDateInput(sc, dateStr);
        startDate = LocalDate.parse(dateStr, df);

        System.out.println("==========================");
        System.out.println("Enter end date: ");
        System.out.print(">>> ");
        dateStr = checkDateInput(sc, dateStr);
        endDate = LocalDate.parse(dateStr, df);

        if (endDate.isBefore(startDate)) {
            System.out.println("==========================");
            System.out.println("Your end date is before start date. Please check and enter again.");
            pairOfDates(sc);
        }

    }

    static boolean findContinent(String continent) throws IOException {
        /*
         * This method is quite similar to readByContinent. I use it to check if the
         * continent they input is not exist in the csv file
         */
        BufferedReader contRd = new BufferedReader(new FileReader("covid-data.csv"));
        String line;
        String[] tokens;
        boolean found = false;

        while (((line = contRd.readLine()) != null) && !found) {
            tokens = line.split(",");
            if (Main.textHandle(tokens[1]).equals(continent)) {
                found = true;
                break;
            }
        }
        contRd.close();
        return found;
    }

    static boolean findCountry(String country) throws IOException {
        /*
         * This method is quite similar to find continent. I use it to check if the
         * country they input is not exist in the csv file
         */
        BufferedReader countryRd = new BufferedReader(new FileReader("covid-data.csv"));
        String line;
        String[] tokens;
        boolean found = false;

        while (((line = countryRd.readLine()) != null) && !found) {
            tokens = line.split(",");
            if (Main.textHandle(tokens[2]).equals(country)) {
                found = true;
                break;
            }
        }
        countryRd.close();
        return found;
    }

    static void readByContinent(String continent, Data dt) throws IOException {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("M/d/yyyy");
        int repeat; // this variable is used to help add data to object's instance

        // Buffered Reader to read line by line. I don't use Scanner because I'm not
        // parsing anything
        BufferedReader contRd = new BufferedReader(new FileReader("covid-data.csv"));
        String line;
        String[] tokens;
        while ((line = contRd.readLine()) != null) {
            repeat = 0;
            line = line + "0";// this line of code is to deal with empty cases, death, vaccinated, population
            tokens = line.split(",");

            // empty column will be assign = 0
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].equals("")) {
                    tokens[i] = "0";
                }
            }
            // This for loop is to handle negative data of new case and new death
            if (!tokens[0].equals("iso_code")) {
                for (int i = 4; i < 7; i++) {
                    if (Integer.parseInt(tokens[i]) < 0) {
                        tokens[i] = "0";
                    }
                }
            }

            /*
             * If the selected continent rows are found, new dates will be added, if existed
             * dates appear, their statistics will be add to previous ones. The date is not
             * listed in order, however, the index of the date and the index of its other
             * columns will be the same, so it should be fine.
             */
            if (Main.textHandle(tokens[1]).equals(continent)) {
                if (!dt.date.isEmpty()) {
                    // existed dates will be added. only works if the arraylist is not empty
                    for (int i = 0; i < dt.date.size(); i++) {
                        if (LocalDate.parse(tokens[3], df).isEqual(dt.date.get(i))) {
                            dt.newCase.set(i, (Integer.parseInt(tokens[4]) + dt.newCase.get(i)));
                            dt.newDeath.set(i, (Integer.parseInt(tokens[5]) + dt.newDeath.get(i)));
                            dt.peopleVacinated.set(i, (Integer.parseInt(tokens[6]) + dt.peopleVacinated.get(i)));
                            repeat++;
                        }
                    }
                }
                // New dates will be appended
                if (repeat == 0) {
                    dt.date.add(LocalDate.parse(tokens[3], df));
                    dt.newCase.add(Integer.parseInt(tokens[4]));
                    dt.newDeath.add(Integer.parseInt(tokens[5]));
                    dt.peopleVacinated.add(Integer.parseInt(tokens[6]));
                }
                // assign name of continent
                dt.name = tokens[1];
            }
        }
        contRd.close();

    }

    static void readByCountry(String country, Data dt) throws IOException {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("M/d/yyyy");
        // Buffered Reader to read line by line. I don't use Scanner because I'm not
        // parsing anything
        BufferedReader countryRd = new BufferedReader(new FileReader("covid-data.csv"));
        String line;
        String[] tokens;
        while ((line = countryRd.readLine()) != null) {
            line = line + "0";// this line of code is to deal with empty cases, death, vaccinated, population
            tokens = line.split(",");

            // empty column will be assign = 0
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].equals("")) {
                    tokens[i] = "0";
                }
            }

            // This for loop is to handle negative data of new case and new death
            if (!tokens[0].equals("iso_code")) {
                for (int i = 4; i < 7; i++) {
                    if (Integer.parseInt(tokens[i]) < 0) {
                        tokens[i] = "0";
                    }
                }
            }

            /*
             * If the selected country rows are found,that rows statistics will be added.
             * The date is not listed in order, however, the index of the date and the index
             * of its other columns will be the same, so it should be fine.
             */
            if (Main.textHandle(tokens[2]).equals(country)) {
                // New dates will be appended
                dt.date.add(LocalDate.parse(tokens[3], df));
                dt.newCase.add(Integer.parseInt(tokens[4]));
                dt.newDeath.add(Integer.parseInt(tokens[5]));
                dt.peopleVacinated.add(Integer.parseInt(tokens[6]));
                // assign name of country
                dt.name = tokens[2];
            }
        }
        countryRd.close();

    }
}