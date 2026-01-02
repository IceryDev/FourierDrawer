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

public class Title extends JFrame implements ActionListener, ChangeListener {

    final int SCREEN_WIDTH = 480;
    final int SCREEN_HEIGHT = 360;
    final int INITIAL_SAMPLE_COUNT = 10;

    JButton fileChoose;
    JButton confirmButton;
    JSlider sampleCountSlider;
    JLabel sampleCountText;
    JLabel fileText;

    File chosenFile = null;
    int sampleCount = INITIAL_SAMPLE_COUNT;
    public Title () {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Fourier Drawer");
        this.setIconImage(new ImageIcon("./src/Resources/Assets/Icon/Icon.png").getImage());
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


        fileText = new JLabel();
        fileText.setText("File: ");
        fileText.setFont(new Font("Rockwell", Font.PLAIN, 15));
        fileText.setForeground(Color.WHITE);
        fileText.setBounds(150, 65, 200, 40);
        container.add(fileText);

        fileChoose = new JButton();
        fileChoose.setBounds(165, 100, 150, 40);
        fileChoose.setText("Choose File");
        fileChoose.addActionListener(this);
        fileChoose.setFont(new Font("Rockwell", Font.PLAIN, 15));
        fileChoose.setBackground(Color.BLACK);
        fileChoose.setForeground(Color.WHITE);
        fileChoose.setFocusable(false);
        fileChoose.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        container.add(fileChoose);

        sampleCountSlider = new JSlider(1, 101, INITIAL_SAMPLE_COUNT);
        sampleCountSlider.setBounds(140, 180, 200, 40);
        sampleCountSlider.setBackground(Color.BLACK);
        sampleCountSlider.setPaintTicks(true);
        sampleCountSlider.setPaintTrack(true);
        sampleCountSlider.setMinorTickSpacing(5);
        sampleCountSlider.setMajorTickSpacing(100);
        sampleCountSlider.setPaintLabels(true);
        sampleCountSlider.setForeground(Color.WHITE);
        sampleCountSlider.addChangeListener(this);
        container.add(sampleCountSlider);

        sampleCountText = new JLabel();
        sampleCountText.setText("Sample Count: " + sampleCountSlider.getValue());
        sampleCountText.setFont(new Font("Rockwell", Font.PLAIN, 15));
        sampleCountText.setForeground(Color.WHITE);
        sampleCountText.setBounds(150, 145, 200, 40);
        container.add(sampleCountText);

        confirmButton = new JButton();
        confirmButton.setBounds(165, 250, 150, 40);
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
            DrawingFrame newWindow = new DrawingFrame(this.chosenFile, this.sampleCount);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == sampleCountSlider){
            this.sampleCount = sampleCountSlider.getValue();
            sampleCountText.setText("Sample Count: " + this.sampleCount);
        }
    }
}
