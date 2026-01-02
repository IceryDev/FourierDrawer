import Resources.Complex;
import Resources.FourierCircle;

import java.util.Arrays;
import java.util.Comparator;


public class FourierDrawer {
    public static void main(String[] args) {

        Complex[] points = { new Complex(1, 0), new Complex(0, 1),
                            new Complex(-1, 0), new Complex(0, -1)};
        FourierCircle[] circles = performDFT(points, 10);

        for (FourierCircle circle : circles){
            System.out.println(circle.amplitude + ":" + circle.phase + ":" + circle.angularVelocity);
        }



    }

    public static FourierCircle[] performDFT(Complex[] points, double drawTime){

        final int FREQ_BIN_COUNT = points.length;
        final double DFT_EXPONENT_CONSTANT = -Math.PI * 2 / FREQ_BIN_COUNT;

        FourierCircle[] result = new FourierCircle[FREQ_BIN_COUNT];

        for (int freqBin = 0; freqBin < FREQ_BIN_COUNT; freqBin++){
            Complex total = new Complex(0, 0);
            for (int pointIndex = 0; pointIndex < points.length; pointIndex++){
                Complex exponentialPart =
                        Complex.fromPolar(1, DFT_EXPONENT_CONSTANT * freqBin * pointIndex);
                total = total.sum(points[pointIndex].product(exponentialPart));
            }

            total = total.quotient(new Complex(FREQ_BIN_COUNT, 0)); //Normalise

            double amplitude = total.modulo();
            double phase = ((Math.abs(amplitude) < Complex.DECIMAL_PRECISION) ? 0 : total.argRad());
            result[freqBin] = new FourierCircle(phase, amplitude,
                    FourierCircle.getAngularVelocity(freqBin, points.length, drawTime));

        }
        Arrays.sort(result, Comparator.comparingDouble(a -> a.amplitude));
        return result;
    }


}
