import java.util.*;
import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.format.*;

public class covidAnalyse {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String answer;
        Data prsData = new Data();

        System.out.println("Welcome to covid data processing and analytics tool.");
        System.out.println("==============");
        System.out.println("You can type \"exit\" anytime you want to end the program.");
        System.out.println("==============");
        while (true) {
            // main program
            prsData = Data.selectDataByArea(sc, prsData);
            System.out.println(prsData.date);
            System.out.println(prsData.newCase);
            System.out.println(prsData.date.size());
            System.out.println(prsData.newCase.size());

            // main program
            // leave this alone....
            while (true) {
                System.out.println("Do you want to continue(y/n)? ");
                System.out.print(">>>");
                answer = sc.nextLine();
                if (answer.equals("y") || answer.equals("n")) {
                    break;
                }
                System.out.print("invalid input.");

            }
            if (answer.equals("y")) {
                prsData = null;
                continue;
            } else {
                System.out.print("Goodbye");
                break;
            }
        }

    }

    static void exitCheck(String selection) {// This method check whether user want to quit or not.
        selection = textHandle(selection);
        if (selection.equals("exit")) {
            System.out.println("Thank you for using. The program is exiting.");
            System.exit(0);
        }
    }

    static String textHandle(String text) {
        /*
         * This method is used incase the user input Scanner read \n character.
         * Basically, it's is used for easier comparison statements.
         */
        text = text.replace("\n", "").replace("\r", "");
        text = text.toLowerCase();
        return text;
    }

}

class Data {
    String name;
    ArrayList<LocalDate> date = new ArrayList<LocalDate>();
    ArrayList<Integer> newCase = new ArrayList<Integer>();
    ArrayList<Integer> newDeath = new ArrayList<Integer>();
    ArrayList<Integer> peopleVacinated = new ArrayList<Integer>();
    DateTimeFormatter df = DateTimeFormatter.ofPattern("M/d/yyyy");
    LocalDate startDate;
    LocalDate endDate;

    public static Data selectDataByArea(Scanner userInput, Data processedData) throws IOException {
        // Select area
        String selection;
        System.out.println("""
                Please specify a geographic area whose data you want to view:
                \t1. By continent
                \t2. By country""");
        System.out.print(">>>");
        selection = userInput.nextLine();
        covidAnalyse.exitCheck(selection);

        while (!(selection.equals("1")) && !(selection.equals("2"))) {
            System.out.println("=======================================");
            System.out.println("""
                    Invalid selection. Please check again:
                    \t1. By continent
                    \t2. By country""");
            System.out.print(">>>");
            selection = userInput.nextLine();
            covidAnalyse.exitCheck(selection);
        }

        // Specify by continent
        if (selection.equals("1")) {
            System.out.println("Enter a continent: ");
            System.out.print(">>>");
            String continent = covidAnalyse.textHandle(userInput.nextLine());
            covidAnalyse.exitCheck(continent);

            while (!findContinent(continent)) {
                System.out.println("Continent not found. Please enter continent again or enter \"exit\" to leave: ");
                System.out.print(">>>");
                continent = covidAnalyse.textHandle(userInput.nextLine());
                covidAnalyse.exitCheck(continent);
            }

            readByContinent(continent, processedData);

        } else {
            System.out.println("Enter a country: ");
            System.out.print(">>>");
            String country = covidAnalyse.textHandle(userInput.nextLine());
            covidAnalyse.exitCheck(country);

            while (!findCountry(country)) {
                System.out.println("Country not found. Please enter country again or enter \"exit\" to leave: ");
                System.out.print(">>>");
                country = covidAnalyse.textHandle(userInput.nextLine());
                covidAnalyse.exitCheck(country);
            }
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
        System.out.println("""
                Specify a time range:
                    \t1. A pair of start date and end date (inclusive).
                    \t2. A number of days or weeks from a particular date.
                    \t3. A number of days or weeks to a particular date.""");
        System.out.print(">>>");
        String selection = sc.nextLine();
        covidAnalyse.exitCheck(selection);

        while (!(selection.equals("1")) && !(selection.equals("2")) && !(selection.equals("3"))) {
            System.out.println("=======================================");
            System.out.println("""
                    Invalid input. Specify time range again or enter "exit" to leave:
                        \t1. A pair of start date and end date (inclusive).
                        \t2. A number of days or weeks from a particular date.
                        \t3. A number of days or weeks to a particular date.""");
            System.out.print(">>>");
            selection = sc.nextLine();
            covidAnalyse.exitCheck(selection);
        }
        if (selection.equals("1")) {

        } else if (selection.equals("2")) {

        } else {

        }
    }

    void pairOfDates() {

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
            if (covidAnalyse.textHandle(tokens[1]).equals(continent)) {
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
            if (covidAnalyse.textHandle(tokens[2]).equals(country)) {
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
            if (covidAnalyse.textHandle(tokens[1]).equals(continent)) {
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
            if (covidAnalyse.textHandle(tokens[2]).equals(country)) {
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
