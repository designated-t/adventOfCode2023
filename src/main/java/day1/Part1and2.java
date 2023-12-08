package day1;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1and2 {

    // Change SOLVE_FOR_PART_2 to false to solve for part 1
    private static final boolean SOLVE_FOR_PART_2 = true;


    private static StringBuilder lineVerboseRegex;
    private static BufferedReader reader = null;
    private static final Map<String, String> numbersVerboseToNumeric = Map.of(
            "one", "1",
            "two", "2",
            "three", "3",
            "four", "4",
            "five", "5",
            "six", "6",
            "seven", "7",
            "eight", "8",
            "nine", "9"
    );

    public static void main(String[] args) {

        setupReaderAndRegex();

        //body
        try {
            String currentLine;
            List<String> list = new ArrayList<>();
            while ((currentLine = reader.readLine()) != null) {
                solveLine(currentLine, list);
            }

            int finalNum = list.stream()
                    .map(Integer::parseInt)
                    .reduce(0, Integer::sum);

            System.out.println("Final answer for chosen part: " + finalNum);


        } catch (IOException e) {
            System.out.println("IO Exception occurred when reading line. " +
                    "This could be because the read file has unreadable characters, " +
                    "file was not found, " +
                    "or due to IO Stream edge cases");
        }
    }

    private static void setupReaderAndRegex() {
        try {
            reader = new BufferedReader(new FileReader("src\\main\\java\\day1\\day1input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        lineVerboseRegex = new StringBuilder("([0-9])");

        for (Map.Entry<String, String> entry : numbersVerboseToNumeric.entrySet()) {
            lineVerboseRegex.append(String.format("|(%s)", entry.getKey()));
        }
    }

    private static void solveLine(String line, List<String> listPart1) {

        if (!SOLVE_FOR_PART_2) {
            Matcher matcher = Pattern.compile("([0-9])").matcher(line);

            line = "";

            while (matcher.find(0))
                line += matcher.group();
        }

        if (SOLVE_FOR_PART_2) {

            Matcher matcher = Pattern.compile(lineVerboseRegex.toString()).matcher(line);

            line = "";
            int index = 0;

            while (matcher.find(index)) {
                String occurrence = matcher.group();
                line += occurrence;

                // required to advance once from start due to there being edge cases such as "oneight"
                // this allows for the matcher to find a match after "n", instead of "i" like by default
                // so it'll find as follows:
                // sdfoneightsadf -> |sdf[one]ightsadf -> sdfo|n[eight]sadf
                // "|" represents the start of match finder
                index = matcher.start() + 1;
            }

            for (Map.Entry<String, String> entry : numbersVerboseToNumeric.entrySet()) {
                line = line.replaceAll("(" + entry.getKey() + ")", entry.getValue());
            }
        }

        String first = line.substring(0, 1);
        String last = line.substring(line.length() - 1);

        //System.out.println("Line: " + line + " | Final: " + first + last);
        listPart1.add(first + last);
    }
}