
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Application {

    public static void main(String[] args) {

        if (args.length == 0 || args[0] == null || args[0].trim().isEmpty()) {
            System.out.println("You need to specify a path to CSV input file!");
            return;
        } else {

            String filename = args[0];
            String keyGiven = args.length == 2 ? args[1] : "";

            try {
                List<String[]> allData = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader(filename));
                String line;

                // Parse all lines
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(";");
                    allData.add(values);
                }

                // Init values
                Integer value, min, max;
                List<Integer> minMax;
                Map<String, List<Integer>> entries = new HashMap<>(); // Hashmap for interactive lookup
                Boolean found = false;

                // Print by given key
                for (String[] row : allData) {

                    minMax = new ArrayList<>();
                    min = Integer.MAX_VALUE;
                    max = Integer.MIN_VALUE;

                    // Parse String value and assign min, max
                    for (int i = 1; i < row.length; i++) {
                        value = Integer.parseInt(row[i]);
                        min = value < min ? value : min;
                        max = value > max ? value : max;
                    }

                    // Check if key was provided
                    if (!keyGiven.isEmpty() && keyGiven.equals(row[0])) {
                        System.out.println(min + " " + max);
                        found = true;
                        break;
                    }

                    minMax.add(min);
                    minMax.add(max);
                    entries.put(row[0], minMax);
                }

                // If key was provided as second parameter exit program
                if (!keyGiven.isEmpty()) {

                    if (!found) {
                        System.out.println("Entry with key '" + "'" + keyGiven + " has not been found!");
                    }
                    System.exit(0);

                } else {

                    // Init scanner for interactive lookups
                    Scanner scanner = new Scanner(System.in);
                    String inputKey = "...";

                    // Loop until only enter is pressed
                    while(!inputKey.isEmpty()) {

                        System.out.println();
                        System.out.println("Enter key:");
                        inputKey = scanner.nextLine();

                        // Entry lookup
                        minMax = entries.get(inputKey);

                        if (minMax != null) { // Entry was found
                            min = minMax.get(0);
                            max = minMax.get(1);
                            System.out.println(min + " " + max);
                        } else if (inputKey.isEmpty()) { // Only enter was pressed
                            System.out.println("Program was closed!");
                        } else { // Entry was not found
                            System.out.println("Entry with such key has not been found!");
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error occured while opening the file: " + filename);
            }
        }
    }
}
