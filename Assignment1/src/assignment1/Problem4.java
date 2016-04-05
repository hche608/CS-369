package assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

/**
 * @author hche608
 *
 */
public class Problem4 {
	
	private static void printSteps(final List<Double> x, final DoubleUnaryOperator f, final DoubleUnaryOperator dfdx, final double root){
		for (int i = 0; i < x.size() - 1; ++i) {
            final double x_i = x.get(i);
            System.out.print("Step " + i);
            System.out.print(", x_i: " + x.get(i));
            System.out.print(", f(x_i): " + f.applyAsDouble(x_i));
            System.out.print(", df(x_i)/dx: " + dfdx.applyAsDouble(x_i));
            System.out.println(", x_i+1: " + x.get(i+1));
		}
		System.out.println("root: " + root + "\n");
	}

	public static void main(String[] args) {

		/*
		 * f(x)=2x^3-5x^2+3x-1
		 * df/dx = 6x^2-10x+3
		 */
        
		final DoubleUnaryOperator f = x -> 2 * x * x * x - 5 * x * x + 3 * x - 1;
		final DoubleUnaryOperator dfdx = x -> 6 * x * x - 10 * x + 3;
		final List<Double> fxs = new ArrayList<>();
		final double rf = ComputeUnit.newtonsMethod(0, f, dfdx, fxs);
		printSteps(fxs, f, dfdx, rf);


		
		/*
		 * g(x)=exp(0.3x)-exp(-0.6x)-1
		 * dg/dx=0.3exp(0.3x)+0.6exp(-0.6x)
		 */
		
		final DoubleUnaryOperator g = x -> Math.exp(0.3 * x) - Math.exp(-0.6 * x) - 1;
		final DoubleUnaryOperator dgdx = x -> 0.3 * Math.exp(0.3 * x) + 0.6 * Math.exp(-0.6 * x);
		final List<Double> gxs = new ArrayList<>();
		final double rg = ComputeUnit.newtonsMethod(0, g, dgdx, gxs);
		printSteps(gxs, g, dgdx, rg);
		
		
		/*
		 * h(x)=log(x) − 1 + exp(−x)
		 * hg/dx=0.3exp(0.3x)+0.6exp(-0.6x)
		 */
		
		final DoubleUnaryOperator h = x -> Math.log(x) - 1 + Math.exp(-x);
		final DoubleUnaryOperator dhdx = x -> 1/x - Math.exp(-x);
		final List<Double> hxs = new ArrayList<>();
		final double rh = ComputeUnit.newtonsMethod(1, h, dhdx, hxs);
		printSteps(hxs, h, dhdx, rh);


	}

}
