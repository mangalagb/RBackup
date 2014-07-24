package window_based_analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;

// NOT USED
//This class reads the raw parameters file and orders them based on their frequency of occurrence
class FinalParameters {

	ArrayList<String> officialParameters = new ArrayList<String>();

	public void functionsToAnalyze() throws IOException {
		String[] functionNames = { "abline", "arrows", "Axis", "axis",
				"axis.POSIXct", "barplot", "box", "boxplot", "bxp", "cdplot",
				"co.intervals", "contour", "coplot", "curve", "dotchart",
				"filled.contour", "grconvertX", "grid", "hist", "identify" };

		String[] temp = { "arrows" };

		for (String functionName : temp) {
			readCSV(functionName);
			// readFile(functionName);
		}
	}

	public void readCSV(String functionName) throws IOException {

		String csvFile = "./src/window_based_analysis/output/" + functionName
				+ ".csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {

			// use comma as separator
			String[] Parameters = line.split(cvsSplitBy);

			for (int i = 0; i < Parameters.length; i++) {

				// if it is unnamed and does not take a value, then denot eit by
				// its default name.
				// But dont change the order of parameters
				
				//System.out.println(Parameters[i]);

				if (Parameters[i].contains("=")) {
					Pattern pattern = Pattern.compile("(.*)(=)(.*)");
					Matcher matcher = pattern.matcher(Parameters[i]);

					if (matcher.find()) {
						officialParameters.add(matcher.group(1)
								.replace("\"", "").trim());
					}
				}
				else
				{
					officialParameters.add(Parameters[i].trim());
				}
			}

		}

		br.close();
//		 for(String h : officialParameters)
//		 System.out.println(h);
	}

	public void readFile(String functionName) throws IOException {
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(
					"./src/window_based_analysis/functions/" + functionName
							+ "Parameters.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				determineParameters(sCurrentLine, functionName);
			}
		} finally {
			// System.out.println("Done");
		}
		br.close();

	}

	public void determineParameters(String line, String functionName)
			throws IOException {

		line = line.replaceAll(" = ", "~");
		line = line.replaceAll(" =", "~");
		line = line.replaceAll("= ", "~");
		line = line.replaceAll("=", "~");

		HashMap<String, String> usedParameters = new HashMap<String, String>();

		Iterable<String> itr = Splitter.on(" ").split(line);

		for (String f : itr) {

			// System.out.println(f);

			for (String param : officialParameters) {

				Pattern pattern = Pattern.compile(param + "(~)(.*)");
				//How to encode location info here. How to detect if x0  or y0 is being
				//used or not ?

				Matcher matcher = pattern.matcher(f);

				if (matcher.find()) {
					String value = matcher.group(2).replace("\"", "");
					if (value.contains(",")) {
						Iterable<String> temp = Splitter.on("(").split(value);
						value = temp.iterator().next();
					}
					usedParameters.put(param, value);
					// System.out.println(param);
				}

			}

		}

		csvWrite(usedParameters, functionName);
	}

	public void csvWrite(HashMap<String, String> usedParameters,
			String functionName) throws IOException {

		String csvFile = "./src/window_based_analysis/output/" + functionName
				+ ".csv";
		BufferedWriter br = null;

		String[] parameterValues = new String[officialParameters.size()];

		br = new BufferedWriter(new FileWriter(csvFile, true));

		for (int i = 0; i < officialParameters.size(); i++) {

			if (usedParameters.containsKey(officialParameters.get(i))) {
				parameterValues[i] = usedParameters.get(
						officialParameters.get(i)).trim()
						+ ",";
			} else {
				parameterValues[i] = ",";
			}
		}

		StringBuilder builder = new StringBuilder();
		for (String s : parameterValues) {
			builder.append(s);
		}

		String toWrite = builder.toString().concat("\n");
		// System.out.println(toWrite);

		br.write(toWrite);
		br.close();

	}

	public static void main(String[] args) throws IOException {
		FinalParameters fp = new FinalParameters();
		fp.functionsToAnalyze();
		System.out.println("Finished everything");
	}

}