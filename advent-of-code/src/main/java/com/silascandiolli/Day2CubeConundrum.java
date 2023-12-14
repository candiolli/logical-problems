package com.silascandiolli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2CubeConundrum {
    
    static final String regexGameId = "(?:Game)\\s\\d+";

    static final String regexGames = "\\d+\\s(?:blue|red|green)";

    static final Map<String, Integer> cubeBag = new HashMap<>(Map.of("red", 12, "green", 13, "blue", 14));

    public static void main(String[] args) {
        try {
            InputStream is = Day2CubeConundrum.class.getClassLoader().getResourceAsStream("day2input");

            String content = readFromInputStream(is);

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

            int sum2 = games.stream()
                .mapToInt(game -> {
                    int gameId = Integer.parseInt(game.replaceAll(".*Game (\\d+).*", "$1"));
                    List<String> squareGrabs = Arrays.asList(game.substring(("Game " + gameId + ": ").length()).split("; "));

                    Stream<String[]> stream = squareGrabs.stream()
                            .flatMap(squareGrab -> Arrays.stream(squareGrab.split("; ")))
                            .map(cube -> cube.split(" "));

                    Map<String, Integer> minCubesType = stream
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

    private static Integer getGameId(List<String> value) {
        return Integer.valueOf(value.get(0).split(" ")[1]);
    }

    private static boolean comparePossibleGames(Map<String, Integer> sumsColors, Map<String, Integer> possiblegames) {
        for (String key : sumsColors.keySet()) {
            Integer value = possiblegames.get(key);
            if (sumsColors.get(key) > value) {
                return false;
            }
        }
        return true;
    }

    private static Map<String, Integer> sumColorsGames(List<String> games) {
        Map<String, Integer> sumsColors = new HashMap();

        for (String game : games) {
            String[] infos = game.split(" ");
            String quantity = infos[0];
            String color = infos[1];

            Integer sum = sumsColors.get(color);
            if (sum == null) {
                sumsColors.put(color, Integer.valueOf(quantity));
            } else {
                sumsColors.replace(color, sum + Integer.valueOf(quantity));
            }
        }

        return sumsColors;
    }

    private static List<String> extractValueFromText(String regex, String input) {
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(input);

        return extractGroups(matcher);
    }

    private static List<String> extractGroups(Matcher matcher) {
        List<String> groupsFound = new ArrayList<>();
        while (matcher.find()) {
            String groupValue0 = matcher.group(0);
            if (groupValue0 != null && !groupValue0.isBlank())
                groupsFound.add(groupValue0);

            for (int i = 1; i <= matcher.groupCount(); i++) {
                String groupValue = matcher.group(i);
                if (groupValue != null && !groupValue.isBlank())
                    groupsFound.add(groupValue);
            }
        }
        return groupsFound;
    }
    
    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
