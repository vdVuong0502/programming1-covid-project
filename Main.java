import java.util.*;
import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.format.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String answer;
        Data prsData = new Data();
        Summary sumData = new Summary();
        System.out.println("Welcome to covid data processing and analytics tool.");
        System.out.println("==========================");
        System.out.println("You can type \"exit\" anytime you want to end the program.");
        System.out.println("==========================");
        while (true) {
            // main program
            prsData = Data.selectDataByArea(sc, prsData);

            prsData.sortByDate();
            prsData.timeFilter(sc);
            sumData = Summary.SummaryCalculator(prsData, sc, sumData);
            // test output
            System.out.println(sumData.groups);
            System.out.println(prsData.range);
            System.out.println(prsData.newCase);
            System.out.println(prsData.newDeath);
            // main program
            // leave this alone....
            while (true) {
                System.out.println("==========================");
                System.out.println("Do you want to continue(y/n)? ");
                System.out.print(">>> ");
                answer = sc.nextLine();
                if (answer.equals("y") || answer.equals("n")) {
                    break;
                }
                System.out.print("Invalid input.");

            }
            if (answer.equals("y")) {
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
            System.out.println("==========================");
            System.out.println("Thank you for using. The program is exiting.");
            System.out.println("==========================");
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
