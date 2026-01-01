package Resources;

public class Complex {
    final double PI2_IN_DEG = 360;
    final double DECIMAL_PRECISION = 1e-10;

    double Im = 0;
    double Re = 0;

    public Complex(double Re, double Im) {
        this.Im = truncate(Im);
        this.Re = truncate(Re);
    }

    // To prevent unnecessary arrows in the visualisation
    public double truncate(double value){
        return (Math.abs(value) < DECIMAL_PRECISION) ? 0 : value;
    }

    @Override
    public String toString() {
        return (this.Re + " " + this.Im + "i");
    }

    public double modulo(){
        return Math.hypot(this.Re, this.Im);
    }

    public boolean equals(Complex number){
        return (this.Re == number.Re && this.Im == number.Im);
    }

    public Complex sum(Complex number){
        return new Complex(this.Re + number.Re, this.Im + number.Im);
    }

    public Complex subtract(Complex number){
        return new Complex(this.Re - number.Re, this.Im - number.Im);
    }

    public Complex product(Complex number){
        return new Complex(
                ((this.Re * number.Re) - (this.Im * number.Im)),
                ((this.Re * number.Im) + (this.Im * number.Re))
        );
    }

    public Complex quotient(Complex number){
        if (number.Im == number.Re && number.Re == 0) { throw new ArithmeticException(); }
        Complex denominator = number.product(number.conjugate());
        Complex numerator = this.product(number.conjugate());
        return new Complex(
                (numerator.Re / denominator.Re),
                (numerator.Im / denominator.Im)
        );
    }

    public Complex power(int exponent){
        double argument = exponent * this.argRad();
        double radius = Math.pow(this.modulo(), exponent);
        return Complex.fromPolar(
                radius,
                argument
        );
    }

    public Complex conjugate(){
        return new Complex(
                this.Re,
                -this.Im
        );
    }

    public double argRad(){
        if (this.Im == this.Re && this.Re == 0) { throw new ArithmeticException(); }
        return Math.atan2(this.Im, this.Re);
    }

    public double argDeg(){
        if (this.Im == this.Re && this.Re == 0) { throw new ArithmeticException(); }
        return (PI2_IN_DEG * Math.atan2(this.Im, this.Re) / (2 * Math.PI));
    }

    public static Complex fromPolar(double radius, double theta){
        return new Complex(
                (radius * Math.cos(theta)),
                (radius * Math.sin(theta))
        );
    }
}
