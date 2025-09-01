package com.main.SatellitePathPredictionSpringBoot;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class TLEDownloader {

    public static void main(String[] args) {
//        String tleUrl = "https://celestrak.org/NORAD/elements/active.txt";
    	String tleUrl = "https://celestrak.com/NORAD/elements/gp.php?GROUP=active&FORMAT=tle";

        String outputFile = "active.tle";

        try {
            // Step 1: Download TLE lines
            List<String> tleLines = downloadTleFromCelestrak(tleUrl);
            System.out.println("Downloaded " + tleLines.size() + " lines of TLE data.");

            // Step 2: Save to file
            saveToFile(tleLines, outputFile);
            System.out.println("Saved TLE data to " + outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> downloadTleFromCelestrak(String url) throws IOException {
        System.out.println("Downloading from: " + url);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            return reader.lines().collect(Collectors.toList());
        }
    }

    public static void saveToFile(List<String> lines, String fileName) throws IOException {
        Files.write(Path.of(fileName), lines, StandardCharsets.UTF_8);
    }
}
