package day2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part2 {
    private static BufferedReader reader;
    private static int minRedOccurrences = 0;
    private static int minBlueOccurrences = 0;
    private static int minGreenOccurrences = 0;

    // for each input line, a game, determine the least amount of cubes of each colour that make it possible
    // for Game X: 27 green, 29 blue; 1 red, 3 green, 9 blue; 3 red;
    // the least amount of cubes of each colour required is: 3 red, 27 green, 29 blue
    // the power of the set above is 3 * 27 * 29 = 2349.

    // return the sum of all sets of similar rule from above from each set.
    private static int sumOfPowerOfAllSets = 0;

    public static void main(String[] args) {

        setupReader();

        try {
            solve();
        } catch (IOException e) {
            System.out.println("IO Exception occurred when reading line. " +
                    "This could be because the read file has unreadable characters, " +
                    "file was not found, " +
                    "or due to IO Stream edge cases");
        }
    }

    private static void solve() throws IOException {

        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            // Line example:
            // Game 88: 9 blue, 11 red; 5 green, 7 blue, 12 red; 10 red, 2 green, 1 blue;

            // ["Game 88: 9 blue, 11 red;", " 5 green, 7 blue, 12 red;", " 10 red, 2 green, 1 blue;"]
            String[] lineToFormat = currentLine.split(";");

            lineToFormat[0] = lineToFormat[0].replaceAll("[a-zA-Z0-9 ]+:", "");

            List<String[]> game = new ArrayList<>();

            for (String s : lineToFormat) {
                game.add(s.split("[,:]+"));
            }

            solveGame(game);

        }

        System.out.println(sumOfPowerOfAllSets);
    }

    private static void solveGame(List<String[]> gameOfSets) {

        minRedOccurrences = 0;
        minBlueOccurrences = 0;
        minGreenOccurrences = 0;

        for (String[] set : gameOfSets) {

            List<String[]> setOfPlays = new ArrayList<>();

            for (String s : set) {
                String str = s.substring(1);
                setOfPlays.add(str.split(" "));
            }

            solveGameSet(setOfPlays);
        }

        sumOfPowerOfAllSets += (minRedOccurrences * minGreenOccurrences * minBlueOccurrences);
    }

    private static void solveGameSet(List<String[]> setOfPlays) {
        for (String[] play : setOfPlays) {

            int amountOfCubes = Integer.parseInt(play[0]);

            switch (play[1]) {
                case "red" -> minRedOccurrences = Math.max(minRedOccurrences, amountOfCubes);
                case "blue" -> minBlueOccurrences = Math.max(minBlueOccurrences, amountOfCubes);
                case "green" -> minGreenOccurrences = Math.max(minGreenOccurrences, amountOfCubes);
            }
        }
    }

    private static void setupReader() {
        try {
            reader = new BufferedReader(new FileReader("src\\main\\java\\day2\\day2input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
