package com.silascandiolli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PuzzleDay2 {

    public static void main(String[] args) {
        // if (args.length < 1) {
        //     System.out.println("Please provide the file path.");
        //     return;
        // }

        // String filePath = args[0];
        try {
            InputStream resource = PuzzleDay2.class.getClassLoader().getResourceAsStream("day2input");
            String content = readFromInputStream(resource);

            // only 12 red cubes, 13 green cubes, and 14 blue cubes
            Map<String, Integer> partOneCubesConfig = Map.of(
                "red", 12,
                "green", 13,
                "blue", 14
            );

            List<String> games = Arrays.asList(content.split("\n"));

            int sum = games.stream()
                .mapToInt(game -> {
                    int gameId = Integer.parseInt(game.replaceAll(".*Game (\\d+).*", "$1"));
                    List<String> squareGrabs = Arrays.asList(game.substring(("Game " + gameId + ": ").length()).split("; "));

                    boolean allGrabsValid = squareGrabs.stream().allMatch(squareGrab -> {
                        List<String> cubes = Arrays.asList(squareGrab.split(", "));
                        return cubes.stream().allMatch(cube -> {
                            String[] parts = cube.split(" ");
                            int count = Integer.parseInt(parts[0]);
                            String type = parts[1];
                            return partOneCubesConfig.getOrDefault(type, 0) >= count;
                        });
                    });

                    return allGrabsValid ? gameId : 0;
                })
                .sum();

            System.out.println("Sum: " + sum);

            InputStream resource2 = PuzzleDay2.class.getClassLoader().getResourceAsStream("day2input2");
            String content2 = readFromInputStream(resource2);
            List<String> games2 = Arrays.asList(content2.split("\n"));

            int sum2 = games2.stream()
                .mapToInt(game -> {
                    int gameId = Integer.parseInt(game.replaceAll(".*Game (\\d+).*", "$1"));
                    List<String> squareGrabs = Arrays.asList(game.substring(("Game " + gameId + ": ").length()).split("; "));

                    Map<String, Integer> minCubesType = squareGrabs.stream()
                        .flatMap(squareGrab -> Arrays.stream(squareGrab.split("; ")))
                        .map(cube -> cube.split(" "))
                        .collect(Collectors.toMap(
                            cube -> cube[1],
                            cube -> Integer.parseInt(cube[0]),
                            Integer::max
                        ));

                    int cubesPower = minCubesType.values().stream().reduce(1, (acc, count) -> acc * count);

                    return cubesPower;
                })
                .sum();

            System.out.println("Sum2: " + sum2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFromInputStream(InputStream inputStream)
    throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
        = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
    return resultStringBuilder.toString();
    }
}