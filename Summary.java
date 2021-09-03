import java.util.*;
import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.format.*;

public class Summary {
    // These list is to store data calculated base on user chosen metric and result
    // type
    ArrayList<Integer> result = new ArrayList<Integer>();
    // this groups list is to store a string of each groups
    ArrayList<String> groups = new ArrayList<String>();
    int groupsNum; // Store number of groups
    int daysPerGroup; // Store number of days per group
    String metric;

    static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/M/d");

    static boolean checkNumberInput(String input) {
        /* This method to check if user's input can be parse to integer. */
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    static boolean inputValidate(String selection, int numberOfSelection) {
        // This method is to validate the selection is valid or not
        for (int i = 0; i < numberOfSelection; i++) {
            if (selection.equals(String.valueOf(i))) {
                return true;
            }
        }
        return false;
    }

    static String groupingMethod(String selection, Scanner sc) {
        // This method help get user's method input and validate it. Call the method
        // recursively everytime error detected
        selection = sc.nextLine();
        Main.exitCheck(selection);

        if (!checkNumberInput(selection)) {
            System.out.println("==========================");
            System.out.println("""
                    Your input is not a number. Please try again:
                            \t1. No grouping: each day is a separate group.
                            \t2. Number of groups you want to divide.
                            \t3. Number of days you want each group to have.""");
            System.out.print(">>> ");
            selection = groupingMethod(selection, sc);
        }
        if (!inputValidate(selection, 3)) {
            System.out.println("==========================");
            System.out.println("""
                    Your input is invalid. Please try again:
                            \t1. No grouping: each day is a separate group.
                            \t2. Number of groups you want to divide.
                            \t3. Number of days you want each group to have.""");
            System.out.print(">>> ");
            selection = groupingMethod(selection, sc);
        }

        return selection;
    }

    public static Summary SummaryCalculator(Data dt, Scanner sc, Summary sum) {
        sum = new Summary();
        /* Get data object and start to summary data base on user's selected method */
        String selection = "";
        // Let user choose the grouping method
        System.out.println("==========================");
        System.out.println("""
                Please specify grouping types:
                        \t1. No grouping: each day is a separate group.
                        \t2. Number of groups you want to divide.
                        \t3. Number of days you want each group to have.""");
        System.out.print(">>> ");
        selection = groupingMethod(selection, sc);

        if (selection.equals("2")) {
            System.out.println("==========================");
            System.out.println("Enter a number of groups: ");
            System.out.print(">>>");
            sum.groupsNum = Integer.parseInt(groupInput(sc, dt));
            GroupNumMethod(dt, sum, sc);
        } else if (selection.equals("3")) {

        } else {

        }

        return sum;

    }

    static String groupInput(Scanner sc, Data dt) {
        String groupsNum;
        groupsNum = sc.nextLine();
        Main.exitCheck(groupsNum);

        if (!checkNumberInput(groupsNum)) {
            System.out.println("==========================");
            System.out.println("Your input is not a number. Please try again:");
            System.out.print(">>> ");
            groupsNum = groupInput(sc, dt);
        }

        if (Integer.parseInt(groupsNum) < 1) {
            System.out.println("==========================");
            System.out.println("Number of groups cannot be less than 1. Please try again:");
            System.out.print(">>> ");
            groupsNum = groupInput(sc, dt);
        }

        if (Integer.parseInt(groupsNum) > dt.range.size()) {
            System.out.println("==========================");
            System.out.println("Number of groups cannot exceed the days in time range. Please try again:");
            System.out.print(">>> ");
            groupsNum = groupInput(sc, dt);
        }

        return groupsNum;

    }

    static void GroupNumMethod(Data dt, Summary sum, Scanner sc) {
        LocalDate groupStart;
        LocalDate groupEnd;
        sum.groups.clear();
        if (dt.range.size() % sum.groupsNum == 0) {
            // if group is divided equally, assign a string made of start and end day of
            // that group
            groupStart = dt.range.get(0);
            groupEnd = dt.range.get((dt.range.size() / sum.groupsNum) - 1);
            for (int i = 0; i < sum.groupsNum; i++) {
                sum.groups.add(groupStart.format(df) + "," + groupEnd.format(df));
                groupStart = groupEnd.plusDays(1);
                groupEnd = groupStart.plusDays((dt.range.size() / sum.groupsNum) - 1);
            }
        } else {
            // if group cannot be divided equally, plus 1 day to a number of the groups
            // depend on how many days are left. Then assign to a string to list. The rest
            // groups are treated normally.
            groupStart = dt.range.get(0);
            groupEnd = dt.range.get((dt.range.size() / sum.groupsNum) - 1);
            for (int i = 0; i < (dt.range.size() % sum.groupsNum); i++) {
                groupEnd = groupEnd.plusDays(1);
                sum.groups.add(groupStart.format(df) + "," + groupEnd.format(df));
                groupStart = groupEnd.plusDays(1);
                groupEnd = groupStart.plusDays((dt.range.size() / sum.groupsNum) - 1);
            }
            for (int i = 0; i < sum.groupsNum - (dt.range.size() % sum.groupsNum); i++) {
                sum.groups.add(groupStart.format(df) + "," + groupEnd.format(df));
                groupStart = groupEnd.plusDays(1);
                groupEnd = groupStart.plusDays((dt.range.size() / sum.groupsNum) - 1);
            }
        }

        // Now we have the groups in a list. We will start calculate data base on what
        // user choose. First is metric, then, result type.
        metric(sc, sum);
    }

    static void metric(Scanner sc, Summary sum) {
        String selection = "";

        System.out.println("==========================");
        System.out.println("""
                Please specify a metric:
                        \t1. Positive cases.
                        \t2. Deaths
                        \t3. People Vaccinated""");
        System.out.print(">>> ");
        selection = metricInputCheck(sc, selection);

        switch (selection) {
            case "1":
                sum.metric = "cases";
                break;
            case "2":
                sum.metric = "deaths";
                break;
            case "3":
                sum.metric = "vaccinated";
                break;
            default:
                break;
        }
    }

    static void newCases(LocalDate groupStart, LocalDate groupEnd, Data dt, Summary sum) {

        // Add group new case to result
        int temp = 0;

        // loop through the data.date list, if the date is in group range, add new case
        // value of that index to temp
        for (int i = 0; i < dt.date.size(); i++) {
            if ((dt.date.get(i).isEqual(groupStart) || dt.date.get(i).isAfter(groupStart))
                    && (dt.date.get(i).isEqual(groupEnd) || dt.date.get(i).isBefore(groupStart))) {
                temp = temp + dt.newCase.get(i);
            }
        }
        sum.result.add(temp);
    }

    static void newDeath(LocalDate groupStart, LocalDate groupEnd, Data dt, Summary sum) {

        // Add group new deaths to result
        int temp = 0; // variable to store accumulative cases in a group

        // loop through the data.date list, if the date is in group range, add new death
        // value of that index to temp
        for (int i = 0; i < dt.date.size(); i++) {
            if ((dt.date.get(i).isEqual(groupStart) || dt.date.get(i).isAfter(groupStart))
                    && (dt.date.get(i).isEqual(groupEnd) || dt.date.get(i).isBefore(groupStart))) {
                temp = temp + dt.newDeath.get(i);
            }
            if (dt.date.get(i).isEqual(groupEnd)) {
                break;
            }
        }
        // add temp to result
        sum.result.add(temp);
    }

    static void newVac(LocalDate groupStart, LocalDate groupEnd, Data dt, Summary sum) {
        // Add group new people vaccinated to result
        int tempIndex = 0;
        int tempVac = 0;

        // Loop through data.date list, if date = group startDate, add the index of the
        // day before that to tempIndex, then continue. If the group startDate is the
        // first date of data.date list, add the date index to tempIndex
        for (int i = 0; i < dt.date.size(); i++) {
            if (dt.date.get(i).isEqual(groupStart)) {
                if (i == 0) {
                    tempIndex = i;
                } else {
                    tempIndex = i - 1;
                }
                continue;

            }
            // If the date is equal groupEnd date, we will calculate people vaccinated =
            // vaccinated of groupEnd minus vaccinated of the day before groupStart or
            // vaccinated of groupStart if groupStart is equal data.date[0]
            if (dt.date.get(i).isEqual(groupEnd)) {
                tempVac = dt.peopleVacinated.get(i) - dt.peopleVacinated.get(tempIndex);
                break;
            }
        }
        // add tempVac into result
        sum.result.add(tempVac);
    }

    static void upToCases(LocalDate groupEnd, Data dt, Summary sum) {
        // This method is to calculate cases from the first date to the end date of the
        // group
        int temp = 0;
        // Loop from first day to group end date, accumulate cases.
        for (int i = 0; dt.date.get(i).isBefore(groupEnd.plusDays(1)); i++) {
            temp = temp + dt.newCase.get(i);
        }
        sum.result.add(temp);
    }

    static void upToDeath(LocalDate groupEnd, Data dt, Summary sum) {
        // This method is to calculate deaths from the first date to the end date of the
        // group
        int temp = 0;
        // Loop from first day to group end date, accumulate death.
        for (int i = 0; dt.date.get(i).isBefore(groupEnd.plusDays(1)); i++) {
            temp = temp + dt.newDeath.get(i);
        }
        sum.result.add(temp);
    }

    static void upToVac(LocalDate groupEnd, Data dt, Summary sum) {
        // This method is to calculate people vaccinated from the first date to the end
        // date of the group
        int temp = 0;
        // Loop through data.date list. when the date equals group end, take the group
        // end date's people vaccinated
        for (int i = 0; i < dt.date.size(); i++) {
            if (dt.date.get(i).isEqual(groupEnd)) {
                temp = dt.peopleVacinated.get(i);
                break;
            }

        }
        sum.result.add(temp);
    }

    static String metricInputCheck(Scanner sc, String selection) {
        selection = sc.nextLine();
        Main.exitCheck(selection);

        if (!checkNumberInput(selection)) {
            System.out.println("==========================");
            System.out.println("Your input is invalid. Please try again:");
            System.out.print(">>> ");
            selection = metricInputCheck(sc, selection);
        }

        if (!inputValidate(selection, 3)) {
            System.out.println("==========================");
            System.out.println("Your input is invalid. Please try again:");
            System.out.print(">>> ");
            selection = metricInputCheck(sc, selection);
        }

        return selection;
    }

}