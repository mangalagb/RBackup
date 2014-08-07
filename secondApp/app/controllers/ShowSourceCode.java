package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.common.base.Splitter;

class ShowSourceCode{
	
	
	
	public HashMap<String, String> getSourceCode(ArrayList<String> lines, String functionName) throws IOException
	{
		HashMap<String, String> result = new HashMap<String, String>();
		
		for(String line : lines)
		{
			readFile(line, functionName, result);
		}
		
		return result;
		
		
	}
	
	
	public void readFile(String parameters, String functionName, HashMap<String, String> result) throws IOException {
		
		String param = parameters;
		
		if(parameters.contains(")") && parameters.contains("("))
		{
			String temp = parameters.replace(")", "");
			String temp1 = functionName + "(";
			parameters = temp.replace(temp1, "");
			
		}
		
		BufferedReader br = null;
		//System.out.println(parameters);

		try {

			String sCurrentLine;
			String buf = "";
			String sourceCode = "";

			 br = new BufferedReader(new FileReader(
					 "./public/sourcecode/" + functionName + "Code.txt"));


			while ((sCurrentLine = br.readLine()) != null) {
				buf = buf + sCurrentLine + "\n";
			}
			
			Iterable<String> itr = Splitter
					.on("-------------------------------------------------------------------------------------------------------")
					.split(buf);
			// For each snippet search for the occurence of one type of function use
			for (String snippet : itr) {
				
				Pattern pattern = Pattern.compile("(.*)"+ parameters + "(.*)");
				Matcher matcher = pattern.matcher(snippet);
				
				if(matcher.find())
				{
					
					sourceCode = sourceCode + snippet;
					sourceCode = sourceCode + "+++++++++++++++++++++++++++++++++++++++++++++++++";
					
				}
				
				
				
			}
			result.put(param, sourceCode);
			br.close();
			
		} finally {

		}
		

	}
	
//	public static void main(String[] args) throws IOException
//	{
//		ShowSourceCode s = new ShowSourceCode();
//		String functionName = "Axis";
//		
//		ReadFile r = new ReadFile();
//    	ArrayList<String> lines = r.readFile(functionName);
//    	
//    	
//		s.getSourceCode(lines, functionName);
//		
//		for (Map.Entry<String, String> entry : result.entrySet()) {
//
//			System.out.println(entry.getKey());
//			System.out.print(entry.getValue());
//			System.out.print("+++++++++++++++++++++++++++++++++++++++++++=");
//
//		}
//		
//		
//		
//	}
	
}