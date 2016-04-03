package assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class Problem4 {

	public static void main(String[] args) {

		/*
		 * f(x)=2x^3-5x^2+3x-1
		 * df/dx = 6x^2-10x+3
		 */
		final DoubleUnaryOperator f = x -> 2 * x * x * x - 5 * x * x + 3 * x - 1;
		final DoubleUnaryOperator dfdx = x -> 6 * x * x - 10 * x + 3;
		final List<Double> fxs = new ArrayList<>();
		final double rf = ComputeUnit.newtonsMethod(0, f, dfdx, fxs);
		System.out.println("rootf: " + rf);


		
		/*
		 * g(x)=exp(0.3x)-exp(-0.6x)-1
		 * dg/dx=0.3exp(0.3x)+0.6exp(-0.6x)
		 */
		
		final DoubleUnaryOperator g = x -> Math.exp(0.3 * x) - Math.exp(-0.6 * x) - 1;
		final DoubleUnaryOperator dgdx = x -> 0.3 * Math.exp(0.3 * x) + 0.6 * Math.exp(-0.6 * x);
		final List<Double> gxs = new ArrayList<>();
		final double rg = ComputeUnit.newtonsMethod(0, g, dgdx, gxs);
		System.out.println("rootg: " + rg);


	}

}
