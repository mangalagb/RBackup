package window_based_analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

//This class reads the raw parameters file and orders them based on their frequency of occurrence
class WhichParameters {

	// Keep a list of all the parameters read from the csv in order
	final ArrayList<String> officialParametersCopy = new ArrayList<String>();
	ArrayList<String> partialParameters = new ArrayList<String>();

	public void functionsToAnalyze() throws IOException {
		// put this in a separate class as global variables will be replicated
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

		String[] temporary = { "grid" };

		for (String functionName : functionNames) {

			officialParametersCopy.clear();
			partialParameters.clear();

			readCSV(functionName);
			readFile(functionName);
		}
	}

	public void readCSV(String functionName) throws IOException {

		String csvFile = "./src/window_based_analysis/new_csvs/" + functionName
				+ ".csv";

		// String csvFile = "./src/window_based_analysis/testingFolder/" +
		// functionName
		// + ".csv";

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		br = new BufferedReader(new FileReader(csvFile));

		while ((line = br.readLine()) != null) {

			// use comma as separator
			String[] Parameters = line.split(cvsSplitBy);

			for (int i = 0; i < Parameters.length; i++) {

				// if it is unnamed and does not take a value, then denote it by
				// its default name.
				// But don't change the order of parameters

				// System.out.println(Parameters[i]);

				if (Parameters[i].contains("=")) {
					Pattern pattern = Pattern.compile("(.*)(=)(.*)");
					Matcher matcher = pattern.matcher(Parameters[i]);

					if (matcher.find()) {
						officialParametersCopy.add(matcher.group(1)
								.replace("\"", "").trim());
					}
				} else {
					officialParametersCopy.add(Parameters[i].trim());
				}
			}

		}

		br.close();

	}

	public void readFile(String functionName) throws IOException {
		BufferedReader br = null;

		try {

			String sCurrentLine;
			int lineNumber = 0;

			br = new BufferedReader(new FileReader(
					"./src/window_based_analysis/new_output/" + functionName
							+ "Parameters.txt"));

			// br = new BufferedReader(new FileReader(
			// "./src/window_based_analysis/testingFolder/" + functionName
			// + "Parameters.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				++lineNumber;
				determineParameters(sCurrentLine, functionName, lineNumber);
			}
		} finally {

		}
		br.close();

	}

	public void determineParameters(String line, String functionName,
			int lineNumber) throws IOException {
		
		System.out.println(functionName);
		 System.out.println(lineNumber);

		HashMap<String, String> usedParameters = new HashMap<String, String>();
		ArrayList<String> officialParameters = new ArrayList<String>(
				officialParametersCopy);

		line = line.replaceAll(" = ", "~");
		line = line.replaceAll(" =", "~");
		line = line.replaceAll("= ", "~");
		line = line.replaceAll("=", "~");

		// Trim whitespace at ends, and replace/collapse whitespace into single
		// spaces
		String modifiedLine = CharMatcher.WHITESPACE.trimAndCollapseFrom(line,
				' ');
		

		Iterable<String> itr = Splitter.on("*0*").split(modifiedLine);
		List<String> temporary = Lists.newArrayList(itr);
		ArrayList<String> parametersToBeResolved = new ArrayList<String>();
		
		for (Iterator<String> iterator = temporary.iterator(); iterator
				.hasNext();) {
			String g = iterator.next();
			g=g.trim();
			parametersToBeResolved.add(g);
		
		}
		
		
		// ///////////////////////////////////////////////////////////////////////////////////////

		// 1 st PASS = Full Parameter Name Matching //

		for (String detectedParam : parametersToBeResolved) {
			for (String officialParam : officialParameters) {
				if (detectedParam.matches(officialParam + "(~)(.*)")) {

					// Add it to usedParameters

					Pattern pattern = Pattern
							.compile(officialParam + "(~)(.*)");
					Matcher matcher = pattern.matcher(detectedParam);
					if (matcher.find()) {
						String value = matcher.group(2).replace("\"", "")
								.replace(")", "").replace("(", "").trim();
						usedParameters.put(officialParam, value);

					}

				}
			}
		}

		ArrayList<String> temp = new ArrayList<String>();
		for (Map.Entry<String, String> entry : usedParameters.entrySet()) {

			temp.add(entry.getKey());

		}

		// Remove elements that have been detected from parametersToBeResolved

		for (String j : temp) {
			for (Iterator<String> iterator = parametersToBeResolved.iterator(); iterator
					.hasNext();) {
				if (iterator.next().contains(j))
					iterator.remove();

			}
		}

		// Remove detected parameters from list of Official Parameters

		for (String j : temp) {
			for (Iterator<String> iterator = officialParameters.iterator(); iterator
					.hasNext();) {

				if (iterator.next().contains(j))
					iterator.remove();

			}
		}

		temp.clear();
		
		

		// ///////////////////////////////////////////////////////////////////////////////////////////////

		// // 2 nd Pass = Partial Parameter Name matching//

		if (!parametersToBeResolved.isEmpty()) {
			for (String detectedParam : parametersToBeResolved) {
				// System.out.println(detectedParam);
				for (String officialParam : officialParameters) {
					String partialParam;
					if (officialParam.length() >= 3) {
						partialParam = officialParam.substring(0,
								officialParam.length() / 2);
					} else
						partialParam = officialParam;

					if (detectedParam.startsWith(partialParam)) {

						Pattern pattern = Pattern.compile(partialParam
								+ "(~)(.*)");
						Matcher matcher = pattern.matcher(detectedParam);
						if (matcher.find()) {
							String value = matcher.group(2).replace("\"", "")
									.replace(")", "").replace("(", "").trim();
							usedParameters.put(officialParam, value);
							partialParameters.add(partialParam);
						}

					}
				}
			}
		}

		for (Map.Entry<String, String> entry : usedParameters.entrySet()) {

			temp.add(entry.getKey());
		}

		// Remove elements that have been detected from
		// parametersToBeResolved
		for (String j : temp) {
			for (Iterator<String> iterator = parametersToBeResolved.iterator(); iterator
					.hasNext();) {
				if (iterator.next().contains(j))
					iterator.remove();

			}
		}
		// Remove detected parameters from list of Official Parameters

		for (String j : temp) {
			for (Iterator<String> iterator = officialParameters.iterator(); iterator
					.hasNext();) {

				if (iterator.next().contains(j))
					iterator.remove();

			}
		}

		temp.clear();
		
		

		// /////////////////////////////////////////////////////////////////////////////////////////

		// 3 rd Pass = Remaining Parameters resolution //

		// Remove used parameters from list of officialParametersDuplicate

		ArrayList<String> officialParametersDuplicate = new ArrayList<String>(
				officialParametersCopy);

		for (Map.Entry<String, String> entry : usedParameters.entrySet()) {

			temp.add(entry.getKey());
		}

		for (String j : temp) {
			for (Iterator<String> iterator = officialParametersDuplicate
					.iterator(); iterator.hasNext();) {

				if (iterator.next().equals(j))
					iterator.remove();

			}
		}

		temp.clear();

		
			
		// Remove all parameters not belonging to the function being examined
		// ie.
		// those imported from other functions such as par, list etc.
		
		

		if (!parametersToBeResolved.isEmpty()) {

			for (Iterator<String> iterator = parametersToBeResolved.iterator(); iterator
					.hasNext();) {
				String detectedParam = iterator.next();

				if (detectedParam.contains("~")) {

					Pattern pattern = Pattern.compile("(.*)(~)(.*)");
					Matcher matcher = pattern.matcher(detectedParam);
					if (matcher.find()) {
						String str = matcher.group(1).trim();
						Boolean present = false;

						for (String officialParam : officialParametersDuplicate) {
							if (officialParam.equals(str))
								present = true;
						}

						if (present == false)
							{	iterator.remove();
							
							}
					}
				}
				
				else if(detectedParam.contains("]"))
				{
					iterator.remove();
				}
			}
			
			
			// Now, all the parameters in parametersToBeResolved can be safely
			// mapped to
			// OfficialParametersCopy to complete the 3rd iteration

			if (!parametersToBeResolved.isEmpty()) {
				for (int i = 0; i < parametersToBeResolved.size(); i++) {
					usedParameters.put(officialParametersDuplicate.get(i),
							parametersToBeResolved.get(i));
				}
			}

		}
		
		 csvWriteUsedParameters(usedParameters, functionName);

	}

	public void csvWriteUsedParameters(HashMap<String, String> usedParameters,
			String functionName) throws IOException {

		// Indicates which parameters have been used in a particular occurrence
		// of a function

		String csvFile = "./src/window_based_analysis/new_csvs/" + functionName
				+ ".csv";
		// String csvFile = "./src/window_based_analysis/testingFolder/" +
		// functionName + ".csv";

		BufferedWriter br = null;

		String[] parameterNames = new String[officialParametersCopy.size()];

		for (int i = 0; i < officialParametersCopy.size(); i++) {
			if (usedParameters.containsKey(officialParametersCopy.get(i))) {
				parameterNames[i] = "1";
			} else {
				parameterNames[i] = "0";
			}

		}

		br = new BufferedWriter(new FileWriter(csvFile, true));

		StringBuilder builder = new StringBuilder();

		for (String s : parameterNames)
			builder.append(s + ",");

		String temp1 = builder.toString();
		temp1 = temp1.substring(0, temp1.length() - 1);
		String toWrite = temp1.concat("\n");
		//System.out.println(temp1);

		br.write(toWrite);

		br.close();

	}

	public static void main(String[] args) throws IOException {
		WhichParameters fp = new WhichParameters();
		fp.functionsToAnalyze();
		System.out.println("Finished everything");
	}

}