import java.util.*;
import java.io.*;

public class Display {

    public ArrayList<String> group = new ArrayList<String>();
    public ArrayList<Integer> result = new ArrayList<Integer>();

    // choose display method
    void ChartOption(Scanner sc) {
        int arrLength;
        String selection;
        System.out.println("""
                Please choose a display method:
                \t1. Tabular display:
                \t2. Chart display: """);
        System.out.println(">>>");
        selection = sc.nextLine();
        Main.exitCheck(selection);
        // selection invalid
        while (!(selection.equals(1)) && !(selection.equals(2))) {
            System.out.println("==================");
            System.out.println("""
                    Invalid input, please choose again:
                    \t1. Tabular display:
                    \t2. Chart display:""");
            System.out.print(">>> ");
            selection = sc.nextLine();
            Main.exitCheck(selection);
        }
        // execute user option
        arrLength = group.size();
        // tabular type display
        if (selection.equals(1)) {
            System.out.printf("%-6s%25s", "Range", "Value");
            for (int i = 0; i < arrLength; i++) {
                System.out.printf("%-6s%25d\n", group.get(i), result.get(i));
            }
        }
        // chart type display
        else {

        }

    }

}
