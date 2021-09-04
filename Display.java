import java.util.*;

import javax.swing.text.Position;

import java.io.*;

public class Display {
    int maxValue;
    ArrayList<Integer> groupRowsPos = new ArrayList<Integer>();// store value group position
    ArrayList<Integer> groupColsPos = new ArrayList<Integer>();// store column group position
    int chartCol = 80;
    int chartRow = 24; // last row is to display the group number;
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
        selection = optionInput(sc, selection, sum);

        // tabular type display
        if (selection.equals("1")) {
            tableDisplay(sum, disp);
        }
        // chart type display
        else {
            chartDisplay(sum, disp);
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

    static void chartDisplay(Summary sum, Display disp) {
        boolean found; // to check if the position is found or not
        maxValueCalculate(sum, disp);
        groupPosition(sum, disp);
        for (int rows = 0; rows < disp.chartRow; rows++) {

            for (int cols = 0; cols < disp.chartCol; cols++) {
                found = false;
                if (cols == 0) { // if it is the first column
                    System.out.print("|");
                } else if (rows == (disp.chartRow - 1)) { // if it is not the first column, and it is bottom row
                    System.out.print("_");
                } else {
                    for (int element = 0; element < disp.groupColsPos.size(); element++) {
                        if ((cols == disp.groupColsPos.get(element)) && (rows == disp.groupRowsPos.get(element))) {
                            // if the row and the column are equal the assigned position
                            System.out.print("*");
                            found = true;
                            break;
                        }
                    }
                    if (!found) { // print empty space if nothing found
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
    }

    static void groupPosition(Summary sum, Display disp) {
        int colSpace = (disp.chartCol - 1) / sum.result.size(); // spacing between the columns
        int tempGroupCol = colSpace; // first column
        for (int i = 0; i < sum.result.size(); i++) {
            // calculate group rows. Since the system print the chart from top to bottom,
            // but we demonstrate value height from bottom to top. so we need to assign the
            // rows position reversal = (number of display rows) - (normally position)
            disp.groupRowsPos.add(
                    (int) ((disp.chartRow - 1) - (((float) sum.result.get(i) / disp.maxValue) * (disp.chartRow - 1))));
            disp.groupColsPos.add(tempGroupCol); // the first column position
            tempGroupCol = tempGroupCol + colSpace; // next column
        }
    }

    static void maxValueCalculate(Summary sum, Display disp) {
        int max = 0;
        for (int i = 0; i < sum.result.size(); i++) {
            if (sum.result.get(i) > max) {
                max = sum.result.get(i);
            }
        }
        disp.maxValue = max;
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

    static String optionInput(Scanner sc, String selection, Summary sum) {
        selection = sc.nextLine();
        Main.exitCheck(selection);

        if (!Summary.checkNumberInput(selection) || !Summary.inputValidate(selection, 2)) {
            System.out.println("Your input is invalid, please try again: ");
            System.out.print(">>> ");
            selection = sc.nextLine();
            Main.exitCheck(selection);
        }
        if ((sum.groups.size() > 79) && (selection.equals("2"))) {
            System.out.println("Number of groups > 79, cannot choose chart method: ");
            System.out.print(">>> ");
            selection = sc.nextLine();
            Main.exitCheck(selection);
        }
        return selection;
    };

}
