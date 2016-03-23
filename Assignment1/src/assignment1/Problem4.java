package assignment1;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class Problem4 {




	/**
	 * Creates a table documenting the steps of Newton's method.
	 * 
	 * @param x
	 *            x_i from iterations of the method
	 * @param f
	 *            the function
	 * @param dfdx
	 *            the derivative of the function with respect to x
	 * @param name
	 *            the name of the function
	 * @return table of steps
	 */
	private static Table createTable(final List<Double> x, final DoubleUnaryOperator f, final DoubleUnaryOperator dfdx,
			final String name) {
		final Table table = new Table(x.size() - 1, 5);
		int c = 0;

		for (int i = 0; i < 5; ++i)
			table.setAlignment(i, Alignment.RIGHT);
		table.setHeader(c++, "$x_{i+1}$");
		for (int i = 0; i < x.size() - 1; ++i) {
			c = 0;
			table.setContent(i, c++, Integer.toString(i));
			final double x_i = x.get(i);
			table.setContent(i, c++, "$" + nf.format(x.get(i)) + "$");
			table.setContent(i, c++, "$" + nf.format(f.applyAsDouble(x_i)) + "$");
			table.setContent(i, c++, "$" + nf.format(dfdx.applyAsDouble(x_i)) + "$");
			table.setContent(i, c++, "$" + nf.format(x.get(i + 1)) + "$");
		}
		return table;
	}

	public static void main(String[] args) {
		final Variables roots = new Variables();
		/*
		 * f(x)=2x^3-5x^2+3x-1
		 * df/dx = 6x^2-10x+3
		 */
		final DoubleUnaryOperator f = x -> 2 * x * x * x - 5 * x * x + 3 * x - 1;
		final DoubleUnaryOperator dfdx = x -> 6 * x * x - 10 * x + 3;
		final List<Double> fxs = new ArrayList<>();
		final double rf = ComputeUnit.newtonsMethod(0, f, dfdx, fxs);
		roots.put("rootf", nf.format(rf));
		final Table tableF = createTable(fxs, f, dfdx, "f");

		
		/*
		 * g(x)=exp(0.3x)-exp(-0.6x)-1
		 * dg/dx=0.3exp(0.3x)+0.6exp(-0.6x)
		 */
		
		final DoubleUnaryOperator g = x -> Math.exp(0.3 * x) - Math.exp(-0.6 * x) - 1;
		final DoubleUnaryOperator dgdx = x -> 0.3 * Math.exp(0.3 * x) + 0.6 * Math.exp(-0.6 * x);
		final List<Double> gxs = new ArrayList<>();
		final double rg = ComputeUnit.newtonsMethod(0, g, dgdx, gxs);
		roots.put("rootg", nf.format(rg));
		final Table tableG = createTable(gxs, g, dgdx, "g");

		final File newtonDir = new File("newton");
		newtonDir.mkdir();
		roots.write(new File(newtonDir, "roots.tex"));
		tableF.write(new File(newtonDir, "f.tex"));
		tableG.write(new File(newtonDir, "g.tex"));

	}

}
