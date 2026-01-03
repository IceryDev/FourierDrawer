package Resources.Frames;

import Resources.Complex;
import Resources.FourierCircle;
import Resources.FourierDrawer;
import Resources.Frames.Panels.DrawnContent;

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

        //Stuff to calculate points here. For now, manually entering points.
        /*Complex[] points = { new Complex(6 * this.scale, 0), new Complex(0, 4 * this.scale),
                    new Complex((7 * this.scale), 0), new Complex(0, -(4 * this.scale))};*/
        Complex[] points = Temp.points;
        for (int index = 0; index < points.length; index++){
            points[index].Im = -points[index].Im + 100;
        }

        FourierCircle[] circles =
                FourierDrawer.filterZeroAmplitudes(FourierDrawer.performDFT(points, this.drawTime));

        for (FourierCircle circle : circles){
            System.out.println(circle.amplitude + ":" + circle.phase + ":" + circle.angularVelocity);
        }

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
