package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import models.*;

import com.google.common.base.Splitter;

class ShowSourceCode{
	
	
	
	public String getSourceCode(String functionName, String parameters) throws IOException
	{
		
		if(parameters.contains(")") && parameters.contains("("))
		{
			String temp = parameters.replace(")", "");
			String temp1 = functionName + "(";
			parameters = temp.replace(temp1, "");
			
		}
		
		BufferedReader br = null;
		
		try{
			
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
			
			for (String snippet : itr) {
				
				Pattern pattern = Pattern.compile("(.*)"+ parameters + "(.*)");
				Matcher matcher = pattern.matcher(snippet);
				
				if(matcher.find())
				{
					
					sourceCode = sourceCode + snippet;
					sourceCode = sourceCode + "+++++++++++++++++++++++++++++++++++++++++++++++++";
					
				}
				
			}
			
			br.close();
			return sourceCode;
			
		}
		finally
		{
			
		}
		
		
		
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
	
	public static void main(String[] args) throws IOException
	{
		ShowSourceCode s = new ShowSourceCode();
		String functionName = "Axis";
		String line = "Axis(x = X, side = 1)";
		
		//ReadFile r = new ReadFile();
    	//ArrayList<String> lines = r.readFile(functionName);
    	
    	
		String h = s.getSourceCode(functionName, line);
		
		
			System.out.println(h);

		
		
		
		
	}
	
}