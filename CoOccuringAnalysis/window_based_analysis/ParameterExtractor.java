package window_based_analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;

/*
 * This class extracts the function parameters as specified in an array and stores them in 
 * ./src/window_based_analysis/functions/"+functionName+"Parameters.txt
 * This file is later used by FinalParameters in the same package to calculate frequent parameters
 */
class ParameterExtractor {

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
		
		String[] func = { "bxp"};

		//all done

		for (String functionName : functionNames)
			readFile(functionName);
	}

	public void readFile(String functionName) throws IOException {
		BufferedReader br = null;

		try {

			String sCurrentLine;

			 br = new BufferedReader(new FileReader(
			 "./src/window_based_analysis/functions/"+functionName+"Code.txt"));


			while ((sCurrentLine = br.readLine()) != null) {
				extractParameter(sCurrentLine, functionName);
				//System.out.println(sCurrentLine);
			}
		} finally {

		}
		br.close();

	}

	public void extractParameter(String line, String functionName)
			throws IOException {

		 
		 
		Pattern pattern = Pattern
				.compile("(\\b" + functionName + "\\(\\b)(.*)");

		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			// System.out.println(matcher.group(2));
			extractData(matcher.group(2), functionName);
			
			
		}
		
		

	}

	public void extractData(String oldData, String functionName)
			throws IOException {

		String data = oldData.substring(0, oldData.lastIndexOf(")"));
		// System.out.println(data);
		data = data + ',';

		ArrayList<String> parameterArray = new ArrayList<String>();

		char[] ch = new char[data.length()];
		for (int i = 0; i < data.length(); i++) {
			ch[i] = data.charAt(i);
		}

		int counter = 0;
		String temp = "";

		for (int i = 0; i < ch.length; i++) {

			if (ch[i] == '(')
				++counter;
			if (ch[i] == ')')
				--counter;

			temp = temp + ch[i];

			if ((ch[i] == ',' && counter == 0)) {

				if (temp.contains("(")) {
					String temp2 = temp.substring(0, temp.indexOf("("));
					parameterArray.add(temp2.trim());
					temp = "";
				} else {
					parameterArray.add(temp.substring(0, temp.length() - 1)
							.trim());
					temp = "";
				}
			}

		}

		cleanUpData(parameterArray, functionName);

	}

	public void cleanUpData(ArrayList<String> parameterArray, String functionName) throws IOException {

		// Add parsing for special cases here

		for (int i = 0; i < parameterArray.size(); i++) {

			String str = parameterArray.get(i);

			if (str.contains("$")) {

				Iterable<String> Iter = Splitter.on('$').split(str);

				parameterArray.set(i, Iter.iterator().next());

			}

			if (str.contains(")")) {

				Iterable<String> Iter = Splitter.on(')').split(str);

				parameterArray.set(i, Iter.iterator().next());

			}
		}
		
//		for(String s : parameterArray)
//			System.out.println(s);

		writeToFile(parameterArray, functionName);

	}

	public void writeToFile(ArrayList<String> parameterArray, String functionName)
			throws IOException {
		// Write the parameters to file

		 FileWriter file = new
		 FileWriter("./src/window_based_analysis/new_output/"+functionName+"Parameters.txt",true);
		 
		 ListIterator iter = parameterArray.listIterator();
			
		while (iter.hasNext()) {
	        int index = iter.nextIndex();
	        String str = (String) iter.next();
	        file.write(str);
			file.write("*0*");
	    }
		//System.out.println(toWrite);
		 
		file.write("\n");
		file.flush();
		file.close();

	}

	public static void main(String[] args) throws IOException {

		ParameterExtractor p = new ParameterExtractor();
		p.functionsToAnalyze();
		// p.extractData();
		System.out.println("done");

	}

}