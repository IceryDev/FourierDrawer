package Resources.Frames;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Title extends JFrame implements ActionListener {

    final int SCREEN_WIDTH = 480;
    final int SCREEN_HEIGHT = 360;

    JButton fileChoose;
    public Title () {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Fourier Drawer");
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

        fileChoose = new JButton();
        fileChoose.setBounds(165, 130, 150, 40);
        fileChoose.setText("Choose File");
        fileChoose.addActionListener(this);
        fileChoose.setFont(new Font("Rockwell", Font.PLAIN, 15));
        fileChoose.setBackground(Color.BLACK);
        fileChoose.setForeground(Color.WHITE);
        fileChoose.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        container.add(fileChoose);



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
                System.out.println(file.getAbsolutePath());
            }
        }
    }
}
