import java.util.*;
import java.io.*;

public class covidAnalyse {
    public static void main(String[] args) throws IOException {
        String selection;
        Scanner userInput = new Scanner(System.in);

        System.out.println("Welcome to covid data processing and analytics tool.");
        System.out.println("==============");
        System.out.println("You can type \"exit\" anytime you want to end the program.");
        System.out.println("==============");

        // Select area
        System.out.println("Please specify a geographic area whose data you want to view:");
        System.out.println("1. By continent");
        System.out.println("2. By country");
        selection = userInput.nextLine();
        exitCheck(selection);

        switch (key) {
            case value:

                break;

            default:
                break;
        }

    }

    static void exitCheck(String selection) {// This method check whether user want to quit or not.
        selection = selection.replace("\n", "").replace("\r", "");
        selection = selection.toLowerCase();
        if (selection.equals("exit")) {
            System.out.println("Thank you for using. The program is exiting.");
            System.exit(0);
        }
    }

}

class DataFilter {
    String name;
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<Integer> newCase = new ArrayList<Integer>();
    ArrayList<Integer> newDeath = new ArrayList<Integer>();
    ArrayList<Integer> peopleVacinated = new ArrayList<Integer>();
    ArrayList<Integer> population = new ArrayList<Integer>();

}
