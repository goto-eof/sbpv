package com.andreidodu.sbpv;

import com.andreidodu.sbpv.constants.LabelConst;
import com.andreidodu.sbpv.util.CommandUtil;
import com.andreidodu.sbpv.util.TimUtil;
import com.andreidodu.sbpv.util.WindowUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

class Main {

    public static final String[] COMMAND = {"/bin/sh", "-c", "upower -d | grep percentage | grep -o -E '[^ ]+$'"};
    public static final String WINDOWS_TITLE = "Simple Battery Percentage Viewer";
    public static final int BATTERY_PROC_CALL_SLEEP_TIME = 1000;
    public static final String ONE_HUNDRED_PERC = "100%";

    public static void main(String[] args) throws InterruptedException, IOException {
        JFrame frame = new JFrame(WINDOWS_TITLE);
        Border border = BorderFactory.createLineBorder(Color.black);

        JLabel labelPercentage = WindowUtil.buildLabelPercentage(border);
        JLabel labelTime = WindowUtil.buildLableTime(border);

        Container contentPane = WindowUtil.getContentPane(frame);

        Box box = WindowUtil.createBox(border);

        box.add(labelPercentage);
        box.add(labelTime);

        contentPane.add(box);

        WindowUtil.applyFrameSettings(frame);

        Optional<LocalDateTime> lastRecordDateTime = Optional.empty();

        gatherBatteryInfo(lastRecordDateTime, labelPercentage, labelTime);

    }

    private static void gatherBatteryInfo(Optional<LocalDateTime> lastRecordDateTime, JLabel labelPercentage, JLabel labelTime) throws IOException, InterruptedException {
        while (true) {
            String pecentageString = CommandUtil.executeCommand(COMMAND);
            labelPercentage.setText(String.format(LabelConst.BATTERY_LABEL_PATTERN, pecentageString));

            updateLabelRecordDateTimeIfNecessary(lastRecordDateTime, labelTime, pecentageString);
            Thread.sleep(BATTERY_PROC_CALL_SLEEP_TIME);
        }
    }

    private static void updateLabelRecordDateTimeIfNecessary(Optional<LocalDateTime> lastRecordDateTime, JLabel labelTime, String pecentageString) {
        if (pecentageString.equalsIgnoreCase(ONE_HUNDRED_PERC)) {
            lastRecordDateTime = Optional.of(LocalDateTime.now());
        }
        lastRecordDateTime.ifPresent(dateTime -> {
            long seconds = TimUtil.calculateTimeElapsedInSeconds(dateTime);
            String timeElapsed = TimUtil.secondsToHumanableString(seconds);
            labelTime.setText(String.format(LabelConst.TIME_LABEL_PATTERN, timeElapsed));
        });
    }

}
