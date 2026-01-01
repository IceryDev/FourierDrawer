import javax.swing.JFrame;
import Resources.Complex;

public class FourierDrawer {
    public static void main(String[] args) {
        Complex num = new Complex(0, 3);

        System.out.println(num.argDeg());
        System.out.println(num.power(-1));
    }
}
