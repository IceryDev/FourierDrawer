package Resources.Frames;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DrawingFrame extends JFrame {

    final int SCREEN_WIDTH = 960;
    final int SCREEN_HEIGHT = 720;

    File chosenFile;
    int sampleCount;

    public DrawingFrame(File chosenFile, int sampleCount){

        this.chosenFile = chosenFile;
        this.sampleCount = sampleCount;

        System.out.println("I have the values File: " + chosenFile.getAbsolutePath() + ", and sample count of " + sampleCount);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Fourier Drawer");
        this.setIconImage(new ImageIcon("./src/Resources/Assets/Icon/Icon.png").getImage());
        this.getContentPane().setBackground(Color.BLACK);
        this.setVisible(true);
    }
}
