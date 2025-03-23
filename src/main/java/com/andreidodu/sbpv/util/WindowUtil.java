package com.andreidodu.sbpv.util;

import com.andreidodu.sbpv.constants.LabelConst;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class WindowUtil {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 110;
    public static final String TIME_ZERO_ZERO = "00:00:00";

    public static Container getContentPane(JFrame frame) {
        Container contentPane = frame.getContentPane();
        contentPane.setBackground(Color.BLACK);
        contentPane.setLayout(new BorderLayout(10, 10));
        return contentPane;
    }

    public static Box createBox(Border border) {
        Box box = Box.createVerticalBox();
        box.setBorder(border);
        box.setSize(new Dimension(WIDTH, HEIGHT));
        return box;
    }

    public static void applyFrameSettings(JFrame frame) {
        frame.add(Box.createRigidArea(new Dimension(WIDTH, HEIGHT)));
        frame.setAlwaysOnTop(true);
        frame.setLocationByPlatform(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public static JLabel buildLableTime(Border border) {
        JLabel labelTime = new JLabel(String.format(LabelConst.TIME_LABEL_PATTERN, TIME_ZERO_ZERO));
        labelTime.setForeground(Color.GREEN);
        labelTime.setBorder(border);
        labelTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        return labelTime;
    }

    public static JLabel buildLabelPercentage(Border border) {
        JLabel labelPercentage = new JLabel(String.format(LabelConst.BATTERY_LABEL_PATTERN, "loading..."));
        labelPercentage.setForeground(Color.GREEN);
        labelPercentage.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
        labelPercentage.setBorder(border);
        labelPercentage.setAlignmentX(Component.CENTER_ALIGNMENT);
        return labelPercentage;
    }

}
