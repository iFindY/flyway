
package de.hamburg.arkadi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Main {

    public static void main(String... args) {

        Path path = Path.of(args[0]);

        if (path.isAbsolute()) {

            try {
                Stream<String> myFile = Files.lines(path);

                double result = myFile
                        .map(s -> {
                            Integer hoursStart = Integer.valueOf(s.substring(0, 2)) * 60;
                            Integer minutesStart = Integer.valueOf(s.substring(3, 5));

                            Integer hoursEnd = Integer.valueOf(s.substring(6, 8)) * 60;
                            Integer minutesEnd = Integer.valueOf(s.substring(9, 11));
                            return ((hoursEnd + minutesEnd) - (hoursStart + minutesStart)) / 60.0;
                        })
                        .mapToDouble(Double::doubleValue)
                        .sum();

                System.out.println("Arbeitsstunden :" + result);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            int monthHours = Integer.valueOf(args[0]);
            int myHours = Integer.valueOf(args[1]);

            System.out.println("Am Ende bleibt : " + (monthHours - monthHours / 40 * myHours));
        }

    }
}