package Resources.Frames;

import Resources.Complex;
import Resources.FourierCircle;
import Resources.FourierDrawer;
import Resources.Frames.Panels.DrawnContent;
import Resources.SVGHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;

public class DrawingFrame extends JFrame {

    File chosenFile;
    int sampleCount;
    int drawTime;
    int scale;

    public DrawingFrame(File chosenFile, int sampleCount, int drawTime, int scale){

        this.chosenFile = chosenFile;
        this.sampleCount = sampleCount;
        this.drawTime = drawTime;
        this.scale = scale;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        //this.setLocationRelativeTo(null);
        this.setTitle("Fourier Drawer");
        this.setIconImage(new ImageIcon("./src/Resources/Assets/Icon/Icon.png").getImage());
        this.getContentPane().setBackground(Color.BLACK);

        Complex[] points = SVGHandler.SVGToPoints(chosenFile.getAbsolutePath(), sampleCount, scale);

        FourierCircle[] circles =
                FourierDrawer.extractZeroFrequency(
                        FourierDrawer.filterZeroAmplitudes(
                                FourierDrawer.performDFT(points, this.drawTime)));

        /*for (FourierCircle circle : circles){
            System.out.println(circle.amplitude + ":" + circle.phase + ":" + circle.angularVelocity);
        }*/ //For Debug

        DrawnContent mainPanel = new DrawnContent(circles, drawTime);

        Border outerGap = BorderFactory.createEmptyBorder(5, 5, 5, 5);

        Border titleScreenBorder = BorderFactory.createLineBorder(Color.WHITE);

        mainPanel.setBorder(BorderFactory.createCompoundBorder(outerGap, titleScreenBorder));

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                mainPanel.cleanup();
            }
        });

        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }
}
