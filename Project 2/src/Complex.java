// File modified from: http://introcs.cs.princeton.edu/java/32class/Complex.java.html  

public class Complex {
    private final double re;   // the real part -- cannot be changed once defined
    private final double im;   // the imaginary part-- cannot be changed once defined

    // create a new object with the given real and imaginary parts
    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    // return a string representation of the invoking Complex object
    public String toString() {
        if (im == 0) return re + "";
        if (re == 0) return im + "i";
        if (im < 0) return re + " - " + (-im) + "i";
        return re + " + " + im + "i";
    }

    // return abs/modulus/magnitude
    public double abs() {
        return Math.hypot(re, im);
    }

    // return angle/phase/argument, normalized to be between -pi and pi
    public double phase() {
        return Math.atan2(im, re);
    }

    // return a new Complex object whose value is (this + b)
    public Complex plus(Complex b) {
        Complex a = this;             // invoking object
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new Complex(real, imag);
    }

    // return a new Complex object whose value is (this - b)
    public Complex minus(Complex b) {
        Complex a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;
        return new Complex(real, imag);
    }

    // return a new Complex object whose value is (this * b)
    public Complex times(Complex b) {
        Complex a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new Complex(real, imag);
    }


    // return a new Complex object whose value is the conjugate of this
    public Complex conj() {
        return new Complex(re, -im);
    }


    // return the real or imaginary part
    public double re() {
        return re;
    }

    public double im() {
        return im;
    }

    // return a / b
    public Complex divides(Complex b) {
        Complex a = this;
        return a.times(b.reciprocal());
    }

    // return a new Complex object whose value is the reciprocal of this
    public Complex reciprocal() {
        double scale = re * re + im * im;
        return new Complex(re / scale, -im / scale);
    }

    // return a new Complex object whose value is the complex exponential of this
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }


    // return a new Complex object whose value is the complex sine of this
    public Complex sin() {
        return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex cosine of this
    public Complex cos() {
        return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex tangent of this
    public Complex tan() {
        return sin().divides(cos());
    }

    // return a new Complex object whose value is the complex square root of this
    public Complex sqrt() {
        return new Complex(Math.sqrt(mod()) * Math.cos(arg()/2), Math.sqrt(mod()) * Math.sin(arg()/2));
    }

    // return a new Complex object whose value is the complex natural log of this
    public Complex log() {
        return new Complex(Math.log(mod()), arg());
    }

    // returns a new Complex object whose value is this raised to the power of
    // another Complex object
    public Complex pow(Complex obj) {
        // if there's no imaginary parts just do normal pow
        if (im == 0 && obj.im() == 0) {
            return new Complex(Math.pow(re, obj.re()), 0);
        }
        return new Complex(Math.pow(Math.E, obj.re()) * Math.cos(obj.im()),
                Math.pow(Math.E, obj.re()) * Math.sin(obj.im()));
    }

    // returns the value of them modulus of this
    private double mod() {
        if (re == 0) {
            return Math.sqrt(im*im);
        } else if (im == 0) {
            return Math.sqrt(re*re);
        }
        return Math.sqrt(re*re + im*im);
    }

    // returns the value of the argument of this
    public double arg() {
        // cannot get argument if there's no real part
        if (re == 0) {
            return 0;
        }
        return Math.atan(im/re);
    }


    // Comparison operation
    public boolean equals(Object x) {
        if (x == null) return false;
        if (this.getClass() != x.getClass()) return false;
        Complex that = (Complex) x;
        return (this.re == that.re) && (this.im == that.im);
    }

    ///////////////////////////////////
    ///////////////// Static methods
    ///////////////////////////////////

    // String to Complex
    public static Complex valueOf(String exp) {
        // if there's no comma it's only a real part
        if (!exp.contains(","))
            return new Complex(Double.parseDouble(exp), 0);
        // otherwise, there's real and imaginary parts
        return new Complex(Double.parseDouble(exp.split(",")[0]),
                Double.parseDouble(exp.split(",")[1]));
    }


    // double to Complex
    public static Complex valueOf(double real) {
        return new Complex(real, 0);
    }



} 
    

