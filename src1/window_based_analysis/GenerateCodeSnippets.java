package window_based_analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

class GenerateCodeSnippets
{
	public void functionsToAnalyze() throws IOException {
		String[] functionNames = { "abline", "arrows", "Axis", "axis",
				"axis.POSIXct", "barplot", "box", "boxplot", "bxp", "cdplot",
				"co.intervals", "contour", "coplot", "curve", "dotchart",
				"filled.contour", "grconvertX", "grid", "hist", "identify",
				"image", "layout", "legend", "lines", "locator", "matplot", "mosaicplot",
				"mtext", "pairs",  "persp", "pie", "points", "polygon", "polypath",
				"rasterImage", "rect", "rug", "segments", "smoothScatter", "spineplot", "stars",
				"stem", "strheight", "stripchart", "sunflowerplot", "symbols",
				"text", "title"};
		
		String[] func = { "Axis"};

		//all done

		for (String functionName : functionNames)
			readParameterFiles(functionName);
	}
	
	public void readParameterFiles(String functionName) throws IOException
	{
		BufferedReader br1 = null;
		
		
		String sCurrentLine;
		
		br1 = new BufferedReader(new FileReader(
				"./src/window_based_analysis/new_output/" + functionName
						+ "Parameters.txt"));
		
		while ((sCurrentLine = br1.readLine()) != null) {
			
			
			writeCodeSnippetFiles(sCurrentLine, functionName);
		}
		
		br1.close();
	}
	
	public void writeCodeSnippetFiles(String line, String functionName) throws IOException
	{
		
		Iterable<String> itr = Splitter.on("*0*").split(line);
		List<String> temporary = Lists.newArrayList(itr);
		
		
		
		String toWrite = functionName+"(";
		
		for(String s : temporary)
		{
			toWrite = toWrite.concat(s);
			toWrite = toWrite.concat(", ");
		}
		
		toWrite = toWrite.substring(0, toWrite.length()-2);
		
		toWrite = toWrite.concat(")\n");
		
		//System.out.print(toWrite);
		
		
		BufferedWriter br2 = null;
		
		br2 = new BufferedWriter(new FileWriter("./src/window_based_analysis/new_CodeSnippets/" + functionName
				+ "CodeSnippets.txt", true));
		
		br2.write(toWrite);
		br2.close();
	}
	
	
	public static void main(String[] args) throws IOException
	{
		GenerateCodeSnippets g = new GenerateCodeSnippets();
		g.functionsToAnalyze();
		System.out.println("Finished Everything");
	}
}