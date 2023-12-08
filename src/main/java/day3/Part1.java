package day3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {

    private static BufferedReader reader;
    private static Pattern numberRegex = Pattern.compile("([0-9][0-9][0-9])|([0-9][0-9])|([0-9])");
    private static Pattern symbolRegex = Pattern.compile("[^0-9.]");
    private static int sumOfParts = 0;

    // read line.
    // find number.
    // find full size of number in the line as a range
    // check if there is any symbol around that number
    // if yes, add number to sum
    // return sum

    public static void main(String[] args) {
        setupReaderAndRegex();


        String[] lines = reader.lines().toArray(String[]::new);

        for (int i = 0; i < lines.length; i++) {
            Matcher matcher = numberRegex.matcher(lines[i]);

            while (matcher.find()) {
                String numberAsString = matcher.group();

                if (hasAdjacentSymbol(matcher.start(), matcher.end(), lines, i)) {
                    System.out.println("VALID: ");
                    System.out.println("Line number: " + i + "|" + numberAsString);
                    sumOfParts += Integer.parseInt(numberAsString);
                }

            }
        }

        System.out.println(sumOfParts);

    }

    private static boolean hasAdjacentSymbol(int start, int end, String[] lines, int currentLineIndex) {
        start = start == 0 ? start : start - 1;
        end = end == lines.length ? end : end + 1;


        if (currentLineIndex != 0) {
            String previousLine = lines[currentLineIndex-1];

            String substringToSearchForSymbols = previousLine.substring(start, end);

            Matcher matcher = symbolRegex.matcher(substringToSearchForSymbols);
            if (matcher.find())
                return true;
        }

        if (currentLineIndex != lines.length - 1) {
            String nextLine = lines[currentLineIndex+1];

            String substringToSearchForSymbols = nextLine.substring(start, end);

            Matcher matcher = symbolRegex.matcher(substringToSearchForSymbols);
            if (matcher.find())
                return true;
        }

        String sameLine = lines[currentLineIndex];

        String substringToSearchForSymbols = sameLine.substring(start, end);

        Matcher matcher = symbolRegex.matcher(substringToSearchForSymbols);
        return matcher.find();
    }

    private static void setupReaderAndRegex() {
        try {
            reader = new BufferedReader(new FileReader("src\\main\\java\\day3\\day3input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
