package game.utils;

import game.input.MenuKeyInputHandler;
import game.ui.TerminalDisplayRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import java.util.logging.Logger;


public class LogFileSelectManager {
    private static final Logger logger = Logger.getLogger(LogFileSelectManager.class.getName());

    private final File[] logFilesOptions;

    public int selectedLogFileOption = 0;

    public LogFileSelectManager() {
        logFilesOptions = LogReader.getLogFiles();
    }

    public void switchOption(boolean next) {
        selectedLogFileOption = (selectedLogFileOption + (next ? 1 : logFilesOptions.length - 1)) % logFilesOptions.length;

        printLogFilesOptions();
    }

    public void selectOption() throws FileNotFoundException {
        File selectedFile = logFilesOptions[selectedLogFileOption];

        logger.info("File " + selectedFile.getName() + " has been opened.");

        try (Scanner fileScanner = new Scanner(selectedFile)) {
            TerminalDisplayRenderer.clearScreen();

            // Define two possible date formats, one with AM/PM and one without
            SimpleDateFormat dateFormatWithAmPm = new SimpleDateFormat("MMM dd, yyyy h:mm:ss a");
            SimpleDateFormat dateFormatWithoutAmPm = new SimpleDateFormat("MMM dd, yyyy H:mm:ss");
            Date previousTimeStamp = null;

            while (true) {
                String line1 = "", line2 = "";

                if (fileScanner.hasNextLine()) {
                    line1 = fileScanner.nextLine();
                }
                if (fileScanner.hasNextLine()) {
                    line2 = fileScanner.nextLine();
                }

                if (line1.isBlank() || line2.isBlank()) {
                    break;
                }

                // Adjust regex to match the new timestamp format
                if (line1.matches("^[A-Za-z]{3} \\d{1,2}, \\d{4} \\d{1,2}:\\d{2}:\\d{2}.*")) {
                    // Extract timestamp portion
                    String timeString = line1.split(" ", 5)[0] + " " + line1.split(" ", 5)[1] + " " + line1.split(" ", 5)[2] + " " + line1.split(" ", 5)[3];

                    try {
                        Date currentTimeStamp;

                        // Try parsing with both formats
                        try {
                            currentTimeStamp = dateFormatWithAmPm.parse(timeString);
                        } catch (ParseException e) {
                            currentTimeStamp = dateFormatWithoutAmPm.parse(timeString);
                        }

                        if (previousTimeStamp != null) {
                            long timeDifference = currentTimeStamp.getTime() - previousTimeStamp.getTime();

                            if (timeDifference > 0) {
                                Thread.sleep(timeDifference);
                            }
                        }

                        // Print the second line
                        System.out.println(line2);

                        previousTimeStamp = currentTimeStamp;
                    } catch (ParseException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public void printLogFilesOptions(){
        TerminalDisplayRenderer.clearScreen();

        for (int i=0; i < logFilesOptions.length; i++) {
            if (i == selectedLogFileOption) {
                System.out.print("--> ");
            }
            System.out.println(logFilesOptions[i].getName());
        }
    }
}
