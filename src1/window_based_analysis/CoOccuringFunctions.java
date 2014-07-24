package window_based_analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/*
 * This class examines the code file of a target function
 * and for each of its occurence, extracts the co-occuring functions
 * based on the window method
 */
class CoOccuringFunctions {

	public void readFile() throws IOException {

		BufferedReader br = null;

		String sCurrentLine;
		String buf = null;

		br = new BufferedReader(new FileReader(
				"./src/window_based_analysis/ablineCode.txt"));
		System.out.println("rrrrrreading file");

		while ((sCurrentLine = br.readLine()) != null) {

			buf = buf + sCurrentLine + "\n";
		}

		Iterable<String> itr = Splitter
				.on("-------------------------------------------------------------------------------------------------------")
				.split(buf);
	
		for (String s : itr) {
			getPlotCode(s);
			// System.out.println(s);
		}
		
		br.close();
	}

	public void getPlotCode(String code) throws IOException {

		String[] parts = code.split("\n");
		Pattern pattern = Pattern.compile("(\\babline\\(\\b)(.*)");

		for (int i = 0; i < parts.length; i++) {
			Matcher matcher = pattern.matcher(parts[i]);
			// Window length is 3 (not-including the target function)
			while (matcher.find() && i >= 4) {

				String[] codeToAnalyze = new String[] { parts[i - 3],
						parts[i - 2], parts[i - 1], parts[i] };
				extractCoOccuringFunctions(codeToAnalyze);

			}
		}
	}

	public void extractCoOccuringFunctions(String[] codeToAnalyze)
			throws IOException {

		ArrayList<String> temporary = new ArrayList<String>();

		for (String s : codeToAnalyze) {

			ArrayList<String> a = Lists.newArrayList(Splitter.on('(')
					.trimResults().omitEmptyStrings().split(s));

			for (int i = 0; i < a.size() - 1; i++) {

				String temp = a.get(i).toString();

				if (temp.contains(" ")) {
					String p = temp.substring(temp.lastIndexOf(" "),
							temp.length());

					if (!(p.length() > 20) && !p.contains("=")
							&& !p.contains("[") && !p.contains("<-")
							&& !p.contains("$") && !p.contains("\"")
							&& !p.contains("(") && !p.contains(")")
							&& !p.matches("plot") && !(p.length() <= 1)
							&& !p.contains("-") && !p.contains("+")
							&& !p.contains("*") && !p.contains("/")
							&& !p.contains(".") && !p.contains("#")
							&& !p.contains(":") && !p.contains("{")
							&& !p.contains("}") && !p.isEmpty()
							&& !p.equals("c") && !p.equals("0")) {

						temporary.add(p.trim());

					}
				}

				else if (!(temp.length() > 20) && !temp.contains("=")
						&& !temp.contains("[") && !temp.contains("<-")
						&& !temp.contains("$") && !temp.contains("\"")
						&& !temp.contains("(") && !temp.contains(")")
						&& !temp.matches("plot") && !(temp.length() <= 1)
						&& !temp.contains("-") && !temp.contains("+")
						&& !temp.contains("*") && !temp.contains("/")
						&& !temp.contains(".") && !temp.contains("#")
						&& !temp.contains(":") && !temp.contains("{")
						&& !temp.contains("}") && !temp.isEmpty()
						&& !temp.equals("c") && !temp.equals("0")) {

					temporary.add(temp.trim());

				}

			}

			

		}
		writeCsv(temporary);
	}

	public void writeCsv(ArrayList<String> arr) throws IOException {

		if (!(arr.size() <= 1)) {
			FileWriter file = new FileWriter("./src/window_based_analysis/3_items.txt", true);

			int d = arr.size() - 1;
			for (int y = 0; y < arr.size(); y++) {
				if (y == d)
					file.write(arr.get(y));

				else
					file.write(arr.get(y) + ",");

			}

			file.write("\n");

			file.close();
		}
	}

	public static void main(String[] args) throws IOException {

		CoOccuringFunctions co = new CoOccuringFunctions();
		co.readFile();
		System.out.println("done");
	}

}