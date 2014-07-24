package HelperFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class CountNumberOfCodeSnippetsAvailable {
	public void doThis(String functionName) throws IOException {
		BufferedReader br = null;
		String currentLine;
		br = new BufferedReader(new FileReader(
				"./src/window_based_analysis/new_csvs/" + functionName
				+ ".csv"));
		int i = 0;

		while ((currentLine = br.readLine()) != null) {
			++i;
		}

		br.close();
		i = i - 1;

		System.out.println(i);
	}

	public static void main(String[] args) throws IOException {
		String[] functionNames = { "abline", "arrows", "Axis", "axis",
				"axis.POSIXct", "barplot", "box", "boxplot", "bxp", "cdplot",
				"co.intervals", "contour", "coplot", "curve", "dotchart",
				"filled.contour", "grconvertX", "grid", "hist", "identify",
				"image", "layout", "legend", "lines", "locator", "matplot",
				"mosaicplot", "mtext", "pairs", "persp", "pie", "points",
				"polygon", "polypath", "rasterImage", "rect", "rug",
				"segments", "smoothScatter", "spineplot", "stars", "stem",
				"strheight", "stripchart", "sunflowerplot", "symbols", "text",
				"title" };

		CountNumberOfCodeSnippetsAvailable t = new CountNumberOfCodeSnippetsAvailable();

		for (String func : functionNames) {
			t.doThis(func);
		}

	}
}