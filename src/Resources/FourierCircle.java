package Resources;

public class FourierCircle {
    public double phase;
    public double amplitude;
    public double angularVelocity;

    public FourierCircle(double phase, double amplitude, double angularVelocity) {
        this.phase = phase;
        this.amplitude = amplitude;
        this.angularVelocity = angularVelocity;
    }

    public static double getAngularVelocity(
            int freqBinIndex, int sampleCount, double drawTime){
        if (drawTime == 0) { throw new ArithmeticException("Can't draw the image in 0 seconds!"); }
        double samplingFrequency = sampleCount / drawTime;

        double circleFrequency = 0;
        if (freqBinIndex <= (sampleCount / 2)) {
            circleFrequency = ((double) freqBinIndex / (double) sampleCount) * samplingFrequency;
        }
        else{ //Negative side of the frequency spectrum
            circleFrequency = (((double) freqBinIndex / (double) sampleCount) - 1) * samplingFrequency;
        }

        return 2 * Math.PI * circleFrequency;
    }
}
