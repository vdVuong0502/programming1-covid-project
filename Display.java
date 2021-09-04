import java.util.*;
import java.io.*;

public class Display {
    int chartCol = 80;
    int chartRow = 25; // last row is to display the group number;
    ArrayList<String> rangeList = new ArrayList<String>();

    // choose display method
    static Display displayMethod(Scanner sc, Summary sum, Display disp) {
        disp = new Display();
        rangeListFormat(sum, disp);
        String selection = "";
        System.out.println("""
                Please choose a display method:
                \t1. Tabular display:
                \t2. Chart display: """);
        System.out.println(">>>");
        selection = optionInput(sc, selection);

        // tabular type display
        if (selection.equals("1")) {
            tableDisplay(sum, disp);
        }
        // chart type display
        else {

        }
        return disp;
    }

    static void rangeListFormat(Summary sum, Display disp) {
        /* this method is to format groups in summary object to display */
        String[] tokens;
        for (int i = 0; i < sum.groups.size(); i++) {
            tokens = sum.groups.get(i).split(",");
            if (tokens.length == 1) {
                // if each date is a group, we will use this format
                disp.rangeList.add(tokens[0]);
            } else {
                // if group contain group start date and group end date we will use this format
                disp.rangeList.add(tokens[0] + " - " + tokens[1]);

            }
        }
    }

    static void tableDisplay(Summary sum, Display disp) {
        tableHeadDisplay();

        for (int i = 0; i < disp.rangeList.size(); i++) {
            System.out.println(String.format("%35s %25s %10d", disp.rangeList.get(i), "|", sum.result.get(i)));
        }
        System.out.printf("%s", "-------------------------------------------------------------------------------");
        System.out.println();
        System.out.printf("Your chosen metric is %s", sum.metric);
        System.out.println();
        System.out.printf("Your chosen result type is %s", sum.resultType);
        System.out.println();
    }

    static void tableHeadDisplay() {
        System.out.printf("%35s %25s %10s", "Range", "|", "Value");
        System.out.println();
        System.out.printf("%s", "-------------------------------------------------------------------------------");
        System.out.println();
    }

    static String optionInput(Scanner sc, String selection) {
        selection = sc.nextLine();
        Main.exitCheck(selection);

        if (!Summary.checkNumberInput(selection) || !Summary.inputValidate(selection, 2)) {
            System.out.println("Your input is invalid, please try again: ");
            System.out.print(">>> ");
            selection = sc.nextLine();
            Main.exitCheck(selection);
        }
        return selection;
    };

}
