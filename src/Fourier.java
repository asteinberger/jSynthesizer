/*File ForwardRealToComplex01.java
Copyright 2004, R.G.Baldwin
Rev 5/14/04

The static method named transform performs a real
to complex Fourier transform.

Does not implement the FFT algorithm. Implements
a straight-forward sampled-data version of the
continuous Fourier transform defined using
integral calculus.  See ForwardRealToComplexFFT01
for an FFT algorithm.

Returns real, imag, magnitude, and phase angle in
degrees.

Incoming parameters are:
  double[] data - incoming real data
  double[] realOut - outgoing real data
  double[] imagOut - outgoing imaginary data
  double[] angleOut - outgoing phase angle in
    degrees
  double[] magnitude - outgoing amplitude
    spectrum
  int zero - the index of the incoming data
    sample that represents zero time
  double lowF - Low freq limit as fraction of
    sampling frequency
  double highF - High freq limit as fraction of
    sampling frequency

The frequency increment is the difference between
high and low limits divided by the length of
the magnitude array

The magnitude is computed as the square root of
the sum of the squares of the real and imaginary
parts.  This value is divided by the incoming
data length, which is given by data.length.

Returns a number of points in the frequency
domain equal to the incoming data length
regardless of the high and low frequency
limits.
************************************************/

public class Fourier {
	
	private double[] dataLeft;
	private double[] dataRight;
	private double[][] spectrum;
	
	public Fourier(double[] dL, double[] dR) {
		this.dataLeft = dL;
		this.dataRight = dR;
		this.spectrum = new double [dL.length][2];
	} // end Fourier constructor
	
	public double[][] transform() {
		
		double pi = Math.PI; // for convenience
		int dataLen = this.dataLeft.length;
		
		// Outer loop iterates on frequency values.
		for (double k = 0.0; k < dataLen; k++) {
			
			this.spectrum[(int) k][0] = 0.0;
			this.spectrum[(int) k][1] = 0.0;
			
			for (double n = 0; n < dataLen; n++) {
				this.spectrum[(int) k][0] += this.dataLeft[(int) n]*Math.cos(-2.0*pi/dataLen*k*n);
				this.spectrum[(int) k][1] += this.dataRight[(int) n]*Math.sin(-2.0*pi/dataLen*k*n);
			} // end for
		
		} // end outer loop
		
		return this.spectrum;
		
	} // end transform method

	public double[] getDataLeft() {
		return this.dataLeft;
	}

	public void setDataLeft(double[] d) {
		this.dataLeft = d;
	}
	
	public double[] getDataRight() {
		return this.dataRight;
	}

	public void setDataRight(double[] d) {
		this.dataRight = d;
	}

	public double[][] getSpectrum() {
		return this.spectrum;
	}

	public void setSpectrum(double[][] s) {
		this.spectrum = s;
	}
	
} // end class Fourier