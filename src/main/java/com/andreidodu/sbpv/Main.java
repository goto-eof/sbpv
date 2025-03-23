package com.andreidodu.sbpv;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;

class Main {

    public static final String[] COMMAND = {"/bin/sh", "-c", "upower -d | grep percentage | grep -o -E '[^ ]+$'"};
    public static final int HEIGHT = 110;
    public static final int WIDTH = 400;
    public static final String END_OF_LINE = "\\n";

    public static void main(String[] args) throws InterruptedException, IOException {
        JFrame frame = new JFrame("Simple Battery Percentage Viewer");
        Border border = BorderFactory.createLineBorder(Color.black);

        JLabel labelPercentage = buildLabelPercentage(border);
        JLabel labelTime = buildLableTime(border);

        Container contentPane = getContentPane(frame);

        Box box = createBox(border);

        box.add(labelPercentage);
        box.add(labelTime);

        contentPane.add(box);

        applyFrameSettings(frame);

        Optional<LocalDateTime> lastRecordDateTime = Optional.empty();

        gatherBatteryInfo(lastRecordDateTime, labelPercentage, labelTime);

    }

    private static void gatherBatteryInfo(Optional<LocalDateTime> lastRecordDateTime, JLabel labelPercentage, JLabel labelTime) throws IOException, InterruptedException {
        while (true) {
            String pecentageString = executeCommand(COMMAND);

            if (pecentageString.equalsIgnoreCase("100%")) {
                lastRecordDateTime = Optional.of(LocalDateTime.now());
            }

            labelPercentage.setText("Battery: " + pecentageString);

            lastRecordDateTime.ifPresent(dateTime -> {
                long seconds = calculateTimeElapsedInSeconds(dateTime);
                String timeElapsed = secondsToHumanableString(seconds);
                labelTime.setText("Elapsed time since full charge: " + timeElapsed);
            });
            Thread.sleep(1000);
        }
    }

    private static String secondsToHumanableString(long seconds) {
        return String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
    }

    private static long calculateTimeElapsedInSeconds(LocalDateTime dateTime) {
        Duration duration = Duration.between(dateTime, LocalDateTime.now());
        return duration.getSeconds();
    }

    private static Container getContentPane(JFrame frame) {
        Container contentPane = frame.getContentPane();
        contentPane.setBackground(Color.BLACK);
        contentPane.setLayout(new BorderLayout(10, 10));
        return contentPane;
    }

    private static Box createBox(Border border) {
        Box box = Box.createVerticalBox();
        box.setBorder(border);
        box.setSize(new Dimension(WIDTH, HEIGHT));
        return box;
    }

    private static void applyFrameSettings(JFrame frame) {
        frame.add(Box.createRigidArea(new Dimension(WIDTH, HEIGHT)));
        frame.setAlwaysOnTop(true);
        frame.setLocationByPlatform(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    private static JLabel buildLableTime(Border border) {
        JLabel labelTime = new JLabel("Elapsed time since full charge: 00:00:00");
        labelTime.setForeground(Color.GREEN);
        labelTime.setBorder(border);
        labelTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        return labelTime;
    }

    private static JLabel buildLabelPercentage(Border border) {
        JLabel labelPercentage = new JLabel("Battery: loading...");
        labelPercentage.setForeground(Color.GREEN);
        labelPercentage.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
        labelPercentage.setBorder(border);
        labelPercentage.setAlignmentX(Component.CENTER_ALIGNMENT);
        return labelPercentage;
    }

    public static String executeCommand(String[] cmd) throws java.io.IOException, InterruptedException {
        Process proc = Runtime.getRuntime().exec(cmd);
        proc.waitFor();

        InputStream is = proc.getInputStream();
        Scanner s = new Scanner(is).useDelimiter(END_OF_LINE);

        InputStream es = proc.getErrorStream();
        Scanner e = new Scanner(es).useDelimiter(END_OF_LINE);
        if (!s.hasNext()) {
            s = e;
        }
        String val = "";
        if (s.hasNext()) {
            val = s.next();
        } else {
            val = "";
        }
        return val;
    }

}
