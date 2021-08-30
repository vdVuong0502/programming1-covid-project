import java.util.*;
import java.io.*;

public class covidAnalyse {
    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to covid data processing and analytics tool.");
        System.out.println("==============");
        System.out.println("You can type \"exit\" anytime you want to end the program.");
        System.out.println("==============");

        Data.createDataObject();

    }

    static void exitCheck(String selection) {// This method check whether user want to quit or not.
        selection = textHandle(selection);
        if (selection.equals("exit")) {
            System.out.println("Thank you for using. The program is exiting.");
            System.exit(0);
        }
    }

    static String textHandle(String text) {
        text = text.replace("\n", "").replace("\r", "");
        text = text.toLowerCase();
        return text;
    }

}

class Data {
    String name;
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<Integer> newCase = new ArrayList<Integer>();
    ArrayList<Integer> newDeath = new ArrayList<Integer>();
    ArrayList<Integer> peopleVacinated = new ArrayList<Integer>();

    public static void createDataObject() throws IOException {
        // variable to check if exist.
        // Create an empty object
        Data processedData = new Data();
        // Select area
        String selection;
        Scanner userInput = new Scanner(System.in);
        System.out.println("Please specify a geographic area whose data you want to view:");
        System.out.println("1. By continent");
        System.out.println("2. By country");
        selection = userInput.nextLine();
        covidAnalyse.exitCheck(selection);

        // Specify by continent
        if (selection.equals("1")) {
            System.out.print("Enter a continent: ");
            String continent = covidAnalyse.textHandle(userInput.nextLine());
            covidAnalyse.exitCheck(continent);

            while (!findContinent(continent)) {
                System.out.print("Continent not found. Please enter continent again or enter \"exit\" to leave: ");
                continent = covidAnalyse.textHandle(userInput.nextLine());
                covidAnalyse.exitCheck(continent);
            }

            readByContinent(continent, processedData);
        }
    }

    static boolean findContinent(String continent) throws IOException {
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

    static void readByContinent(String continent, Data dt) throws IOException {
        int repeat;
        BufferedReader contRd = new BufferedReader(new FileReader("covid-data.csv"));
        String line;
        String[] tokens;
        while ((line = contRd.readLine()) != null) {
            repeat = 0;

            line = line + "0";// this line of code is to deal with empty cases, death, vaccinated, population
            // line of data

            tokens = line.split(",");

            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].equals("")) {
                    tokens[i] = "0";
                }
            }
            if (covidAnalyse.textHandle(tokens[1]).equals(continent)) {
                if (!dt.date.isEmpty()) {
                    for (int i = 0; i < dt.date.size(); i++) {
                        if (tokens[3].equals(dt.date.get(i))) {
                            dt.newCase.set(i, (Integer.parseInt(tokens[4]) + dt.newCase.get(i)));
                            dt.newDeath.set(i, (Integer.parseInt(tokens[5]) + dt.newDeath.get(i)));
                            dt.peopleVacinated.set(i, (Integer.parseInt(tokens[6]) + dt.peopleVacinated.get(i)));
                            repeat++;
                        }
                    }
                }

                if (repeat == 0) {
                    dt.date.add(tokens[3]);
                    dt.newCase.add(Integer.parseInt(tokens[4]));
                    dt.newDeath.add(Integer.parseInt(tokens[5]));
                    dt.peopleVacinated.add(Integer.parseInt(tokens[6]));
                }

                dt.name = tokens[1];
            }
        }

    }

}
