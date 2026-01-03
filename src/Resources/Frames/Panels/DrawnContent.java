package Resources.Frames.Panels;

import Resources.Complex;
import Resources.FourierCircle;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class DrawnContent extends JPanel implements ActionListener{

    final int SCREEN_WIDTH = 960;
    final int SCREEN_HEIGHT = 720;

    final double TARGET_FPS = 60;

    Timer timer;
    BufferedImage trail;
    Graphics2D trailG;

    FourierCircle[] circles;
    Complex brushPos;
    Complex prevBrushPos;
    public DrawnContent (FourierCircle[] circles){

        this.circles = circles;

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);

        trail = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        trailG = trail.createGraphics();

        trailG.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        trailG.setColor(Color.WHITE);

        calculateDrawings();
        calculateDrawings();

        timer = new Timer((int) Math.round(1/TARGET_FPS), this);
        timer.start();

    }

    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Complex prevPosInUIScale = portToUIPos(prevBrushPos);
        Complex posInUIScale = portToUIPos(brushPos);
        g2D.setPaint(Color.WHITE);
        g2D.setStroke(new BasicStroke(2));

        trailG.draw(new Line2D.Double(prevPosInUIScale.Re, prevPosInUIScale.Im, posInUIScale.Re, posInUIScale.Im));
        g2D.drawImage(trail, 0, 0, null);

    }

    public void calculateDrawings() {
        for (int index = 0; index < circles.length; index++) {
            circles[index].phase += circles[index].angularVelocity * (1/TARGET_FPS);

            circles[index].tailPos = (index == 0) ? circles[index].tailPos : circles[index-1].tipPos;
            circles[index].tipPos = circles[index].tailPos.sum(
                    Complex.fromPolar(circles[index].amplitude, circles[index].phase));
        }

        prevBrushPos = brushPos;
        brushPos = circles[circles.length-1].tipPos;
    }

    public Complex portToUIPos(Complex number){
        return new Complex(number.Re+480, 360-number.Im);
    }

    public void cleanup() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        calculateDrawings();
        repaint();
    }
}
