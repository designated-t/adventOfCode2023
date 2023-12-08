package day2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private enum MAX_OCCURENCES {
        RED(12),
        GREEN(13),
        BLUE(14);

        public final int NUMBER;

        MAX_OCCURENCES(int number) {
            this.NUMBER = number;
        }
    }


    private static int redOccurrences = 0;
    private static int blueOccurrences = 0;
    private static int greenOccurrences = 0;
    private static int gameIdSum = 0;


    private static BufferedReader reader;

    // for each input line, a game, determine whether it is possible to run that game with a total of
    // 12 red cubes, 13 green cubes, and 14 blue cubes
    // return the sum of the IDs of those games
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

            // 88
            int gameId = Integer.parseInt(lineToFormat[0].split("[ ,:]")[1]);

            lineToFormat[0] = lineToFormat[0].replaceAll("[a-zA-Z0-9 ]+:", "");

            List<String[]> game = new ArrayList<>();

            for (String s : lineToFormat) {
                game.add(s.split("[,:]+"));
            }

            if (solveGame(game)) {
                gameIdSum += gameId;
            }
        }

        System.out.println(gameIdSum);
    }

    private static boolean solveGame(List<String[]> gameOfSets) {

        for (String[] set : gameOfSets) {

            redOccurrences = 0;
            blueOccurrences = 0;
            greenOccurrences = 0;

            List<String[]> setOfPlays = new ArrayList<>();

            for (String s : set) {
                String str = s.substring(1);
                setOfPlays.add(str.split(" "));
            }

            if (!solveGameSet(setOfPlays))
                return false;
        }

        return true;
    }

    private static boolean solveGameSet(List<String[]> setOfPlays) {


        for (String[] play : setOfPlays) {

            int amountOfCubes = Integer.parseInt(play[0]);

            switch (play[1]) {
                case "red" -> redOccurrences += amountOfCubes;
                case "blue" -> blueOccurrences += amountOfCubes;
                case "green" -> greenOccurrences += amountOfCubes;
            }
        }

        return areOccurrencesUnderMax();
    }

    private static void setupReader() {
        try {
            reader = new BufferedReader(new FileReader("src\\main\\java\\day2\\day2input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private static boolean areOccurrencesUnderMax() {
        return redOccurrences <= MAX_OCCURENCES.RED.NUMBER &&
                greenOccurrences <= MAX_OCCURENCES.GREEN.NUMBER &&
                blueOccurrences <= MAX_OCCURENCES.BLUE.NUMBER;
    }
}
