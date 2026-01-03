package Resources.Frames.Panels;

import Resources.Complex;
import Resources.FourierCircle;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
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
    int drawTime = 10;
    public DrawnContent (FourierCircle[] circles, int drawTime){

        this.circles = circles;
        this.drawTime = drawTime;

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);

        trail = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        trailG = trail.createGraphics();

        trailG.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        trailG.setColor(Color.YELLOW);

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
        g2D.setPaint(Color.YELLOW);
        g2D.setStroke(new BasicStroke(3));

        //Slight fade
        trailG.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.005f)
        );
        trailG.setColor(Color.BLACK);
        trailG.fillRect(0, 0, trail.getWidth(), trail.getHeight());

        trailG.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        trailG.setColor(Color.YELLOW);

        trailG.draw(new Line2D.Double(prevPosInUIScale.Re, prevPosInUIScale.Im, posInUIScale.Re, posInUIScale.Im));
        g2D.drawImage(trail, 0, 0, null);


        for (int index = 0; index < circles.length; index++) {
            Complex circleCenterPosUI = portToUIPos(circles[index].tailPos);
            Complex circleEdgePosUI = portToUIPos(circles[index].tipPos);
            g2D.setPaint(Color.GRAY);
            g2D.setStroke(new BasicStroke(1));
            g2D.draw(new Ellipse2D.Double(
                    circleCenterPosUI.Re-circles[index].amplitude,
                    circleCenterPosUI.Im-circles[index].amplitude,
                    2*circles[index].amplitude, 2*circles[index].amplitude));

            g2D.setPaint(Color.WHITE);
            g2D.setStroke(new BasicStroke(2));
            g2D.draw(new Line2D.Double(
                    circleCenterPosUI.Re, circleCenterPosUI.Im,
                        circleEdgePosUI.Re, circleEdgePosUI.Im));
        }




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
