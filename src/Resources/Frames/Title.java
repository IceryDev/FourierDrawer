package Resources.Frames;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public class Title extends JFrame implements ActionListener, ChangeListener {

    final int SCREEN_WIDTH = 480;
    final int SCREEN_HEIGHT = 360;
    final int INITIAL_SAMPLE_COUNT = 10;
    final int INITIAL_DRAW_TIME = 10;
    final int INITIAL_SCALE = 5;

    JButton fileChoose;
    JButton confirmButton;
    JSlider sampleCountSlider;
    JSlider drawTimeSlider;
    JSlider scaleSlider;
    JLabel sampleCountText;
    JLabel drawTimeText;
    JLabel scaleText;
    JLabel fileText;

    File chosenFile = null;
    int sampleCount = INITIAL_SAMPLE_COUNT;
    int drawTime = INITIAL_DRAW_TIME;
    int scale = INITIAL_SCALE;

    boolean fileSelected = false;
    public Title () {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Fourier Drawer");
        this.setIconImage(new ImageIcon(  //Remove the double dot in build
                Objects.requireNonNull(getClass().getResource("../Assets/Icon/Icon.png"))).getImage());
        this.getContentPane().setBackground(Color.BLACK);

        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBackground(Color.BLACK);
        setContentPane(container);

        Border outerGap = BorderFactory.createEmptyBorder(0, 5, 5, 5);

        Border titleScreenBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE), "Fourier Drawer",
                0, 0, new Font("Rockwell", Font.ITALIC, 25), Color.WHITE);

        container.setBorder(BorderFactory.createCompoundBorder(outerGap, titleScreenBorder));

        JLabel byTitle = new JLabel();
        byTitle.setText("by Icery");
        byTitle.setFont(new Font("Rockwell", Font.ITALIC, 15));
        byTitle.setForeground(Color.WHITE);
        byTitle.setBounds(135, 15, 200, 40);
        container.add(byTitle);

        ImageIcon imageIcon = new ImageIcon(   //Remove the double dot in build
                Objects.requireNonNull(getClass().getResource("../Assets/Illustration.png")));
        JLabel illustration = new JLabel();
        illustration.setIcon(imageIcon);
        illustration.setBounds(270, 25, 165, 138);
        container.add(illustration);


        fileText = new JLabel();
        fileText.setText("File: ");
        fileText.setFont(new Font("Rockwell", Font.PLAIN, 15));
        fileText.setForeground(Color.WHITE);
        fileText.setBounds(25, 65, 200, 40);
        container.add(fileText);

        fileChoose = new JButton();
        fileChoose.setBounds(40, 100, 150, 40);
        fileChoose.setText("Choose File");
        fileChoose.addActionListener(this);
        fileChoose.setFont(new Font("Rockwell", Font.PLAIN, 15));
        fileChoose.setBackground(Color.BLACK);
        fileChoose.setForeground(Color.WHITE);
        fileChoose.setFocusable(false);
        fileChoose.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        container.add(fileChoose);

        sampleCountSlider = new JSlider(1, 1001, INITIAL_SAMPLE_COUNT);
        sampleCountSlider.setBounds(15, 180, 200, 40);
        sampleCountSlider.setBackground(Color.BLACK);
        sampleCountSlider.setPaintTicks(true);
        sampleCountSlider.setPaintTrack(true);
        sampleCountSlider.setMinorTickSpacing(50);
        sampleCountSlider.setMajorTickSpacing(200);
        sampleCountSlider.setPaintLabels(true);
        sampleCountSlider.setForeground(Color.WHITE);
        sampleCountSlider.addChangeListener(this);
        container.add(sampleCountSlider);

        sampleCountText = new JLabel();
        sampleCountText.setText("Sample Count: " + sampleCountSlider.getValue());
        sampleCountText.setFont(new Font("Rockwell", Font.PLAIN, 15));
        sampleCountText.setForeground(Color.WHITE);
        sampleCountText.setBounds(25, 145, 200, 40);
        container.add(sampleCountText);

        drawTimeSlider = new JSlider(5, 25, INITIAL_SAMPLE_COUNT);
        drawTimeSlider.setBounds(15, 260, 200, 40);
        drawTimeSlider.setBackground(Color.BLACK);
        drawTimeSlider.setPaintTicks(true);
        drawTimeSlider.setPaintTrack(true);
        drawTimeSlider.setMinorTickSpacing(1);
        drawTimeSlider.setMajorTickSpacing(5);
        drawTimeSlider.setPaintLabels(true);
        drawTimeSlider.setForeground(Color.WHITE);
        drawTimeSlider.addChangeListener(this);
        container.add(drawTimeSlider);

        drawTimeText = new JLabel();
        drawTimeText.setText("Draw Time: " + drawTimeSlider.getValue() + " seconds");
        drawTimeText.setFont(new Font("Rockwell", Font.PLAIN, 15));
        drawTimeText.setForeground(Color.WHITE);
        drawTimeText.setBounds(25, 225, 200, 40);
        container.add(drawTimeText);

        scaleSlider = new JSlider(1, 51, INITIAL_SCALE);
        scaleSlider.setBounds(290, 200, 150, 40);
        scaleSlider.setBackground(Color.BLACK);
        scaleSlider.setPaintTicks(true);
        scaleSlider.setPaintTrack(true);
        scaleSlider.setMinorTickSpacing(5);
        scaleSlider.setMajorTickSpacing(10);
        scaleSlider.setPaintLabels(true);
        scaleSlider.setForeground(Color.WHITE);
        scaleSlider.addChangeListener(this);
        container.add(scaleSlider);

        scaleText = new JLabel();
        scaleText.setText("Scale: x" + scaleSlider.getValue());
        scaleText.setFont(new Font("Rockwell", Font.PLAIN, 15));
        scaleText.setForeground(Color.WHITE);
        scaleText.setBounds(280, 165, 150, 40);
        container.add(scaleText);

        confirmButton = new JButton();
        confirmButton.setBounds(290, 250, 150, 40);
        confirmButton.setText("Start Drawing!");
        confirmButton.addActionListener(this);
        confirmButton.setFont(new Font("Rockwell", Font.PLAIN, 15));
        confirmButton.setBackground(Color.BLACK);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setEnabled(false);
        confirmButton.setFocusable(false);
        confirmButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        container.add(confirmButton);



        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==fileChoose){
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    } else {
                        return f.getName().toLowerCase().endsWith(".svg");
                    }
                }

                @Override
                public String getDescription() {
                    return "SVG Documents (*.svg)";
                }
            });
            int response = chooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION){
                File file = new File(chooser.getSelectedFile().getAbsolutePath());
                this.chosenFile = file;
                fileChoose.setText(file.getName());
                confirmButton.setEnabled(true);
            }
        }
        else if (e.getSource()==confirmButton){
            DrawingFrame newWindow = new DrawingFrame(
                    this.chosenFile, this.sampleCount, this.drawTime, this.scale);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == sampleCountSlider){
            this.sampleCount = sampleCountSlider.getValue();
            sampleCountText.setText("Sample Count: " + this.sampleCount);
        }
        else if(e.getSource() == drawTimeSlider){
            this.drawTime = drawTimeSlider.getValue();
            drawTimeText.setText("Draw Time: " + this.drawTime + " seconds");
        }
        else if(e.getSource() == scaleSlider) {
            this.scale = scaleSlider.getValue();
            scaleText.setText("Scale: x" + scaleSlider.getValue());
        }
    }
}
