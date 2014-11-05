package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import models.*;


public class ReadFile {
	// A function that reads a file specified by path and returns an arraylist
	// of lines
	public static String a1copy="";
	public ArrayList<String> readFile(String functionName, String path) {
		BufferedReader br = null;

		ArrayList<String> lines = new ArrayList<String>();

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(path));

			while ((sCurrentLine = br.readLine()) != null) {
				lines.add(sCurrentLine);
			}
			lines.add("\n");
			br.close();

			return lines;

		} catch (IOException e) {

		}
		return null;

	}

	public String getSourceCode(String functionName, String parameters)
			throws IOException {

		if (parameters.contains(")") && parameters.contains("(")) {
			String temp = parameters.replace(")", "");
			String temp1 = functionName + "(";
			parameters = temp.replace(temp1, "");

		}

		BufferedReader br = null;

		try {

			String sCurrentLine;
			String buf = "";
			String sourceCode = "";

			br = new BufferedReader(new FileReader("./public/sourcecode/"
					+ functionName + "Code.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				buf = buf + sCurrentLine + "\n";
			}
			br.close();

			Iterable<String> itr1 = Splitter.on(",").split(parameters);

			// System.out.println(toMatch);
			Iterable<String> itr = Splitter
					.on("-------------------------------------------------------------------------------------------------------")
					.split(buf);

			Boolean contains = false;

			for (String snippet : itr) {
				for (String param : itr1) {// Check if all parameters are
											// present or not in the snippet
					if (snippet.contains(param.trim())) {
						contains = true;
					} else {
						contains = false;
						break;
					}

				}

				// Snippet contains all the parameters
				if (contains == true) {
					sourceCode = sourceCode.concat(snippet);
					sourceCode = sourceCode
							.concat("+++++++++++++++++++++++++++++++++\n");
				}

			}

			return sourceCode;

		} finally {

		}

	}

	public HashMap<SourceCode, List<SourceCode>> returnClusterMembers(
			String functionName, String path) throws IOException {
		ArrayList<String> lines = readFile(functionName, path);

		HashMap<SourceCode, List<SourceCode>> result = new HashMap<SourceCode, List<SourceCode>>();

		SourceCode clusterHead = new SourceCode();
		ArrayList<SourceCode> myList = new ArrayList<SourceCode>();

		for (String line : lines) {
			if (line.equals("") || line.equals("\n")) {
				ArrayList<SourceCode> tempmyList = new ArrayList<SourceCode>(
						myList);
				SourceCode tempclusterHead = new SourceCode(clusterHead);

				result.put(tempclusterHead, tempmyList);
				// System.out.println(clusterHead);
				clusterHead.setSnippet("");
				clusterHead.setSourceCode("");
				clusterHead.setStartHighlight("");
				clusterHead.setStopHighlight("");
				myList.clear();
			}

			else {

				if (line.startsWith("$")) {
					clusterHead.setSnippet(line.substring(1, line.length()));
					
					clusterHead.setStartHighlight("");
					clusterHead.setStopHighlight("");
					String param = line.substring(1, line.length());
					

					// From ShowSourceCode.java
					String a1 = getSourceCode(functionName, param);

					param = param.replace(")", "");

					String funcName = functionName + "\\(";
					String parameters = null;
					
					
					if(param.indexOf('(') != param.length()-1)
					{
					parameters = param.substring(param.indexOf("("));
					}
					else
						parameters="";
					
					System.out.println(parameters);
					
					if (!parameters.equals("") && parameters.contains(",")) {
						String t1 = parameters.substring(0, parameters.indexOf(","));
						
						t1 = t1.replace("(", "");
						String temp1 = funcName + t1;
						temp1 = temp1.replace("\\", "");
						String temp2 = parameters.substring(parameters.lastIndexOf(","),
								parameters.length()); // ...
						System.out.println("first parameter = "+temp1);
						System.out.println("last parameter = "+temp2);
						
						int[] positions = new int[15];
						int i = 0;
						// find the index of all temp1 and send them in a loop to replace
						
						for (int index = a1.indexOf(temp1); index >= 0; index = a1.indexOf(
								temp1, index + 1)) {
							 
							positions[i] = index;
							i++;

						}
						
							int previous = positions[0]; 
							for(int o=1;o<positions.length;o++)
							{
								int replaceEnds = temp1beforetemp2(temp1, temp2,a1,previous,positions[o]); //65 and 92
								if(replaceEnds !=0)
									{
										
										String temporary1 = a1.substring(0, previous);
										String temporary2 = a1.substring(previous, a1.length());
										
										String startHighlightPosition = "startHighlightPosition";
										temporary2 = startHighlightPosition+temporary2; 
										for(int y=o;y<positions.length;y++)
										{
											positions[y] += startHighlightPosition.length();
										}
										a1= temporary1 + temporary2;
										replaceEnds = replaceEnds+ startHighlightPosition.length()+temp2.length()+1;
										
										String temporary3 =  a1.substring(0, replaceEnds);
										String temporary4 = a1.substring(replaceEnds, a1.length());
										String stopHighlightPosition = "StopHighlightPosition";
										
										temporary4 = stopHighlightPosition+temporary4;
										
										for(int y=o;y<positions.length;y++)
										{
											positions[y] += stopHighlightPosition.length();
										}
							
										a1= temporary3 + temporary4;
									}
								previous = positions[o];
							
							}
						//System.out.println(a1);
						

					} else if(!parameters.equals("")) {
						parameters = parameters.replace("(", "");
						String temp1 = funcName+parameters;
						temp1 = temp1.replace("\\", "");
						
						String temp2 = parameters;
						temp2 = temp2.replace("(", "");
						System.out.println("first parameter = "+temp1);
						System.out.println("last parameter = "+temp2);
						int[] positions = new int[15];
						int i = 0;
						// find the index of all temp1 and send them in a loop to replace

						for (int index = a1.indexOf(temp1); index >= 0; index = a1.indexOf(
								temp1, index + 1)) {
							 //System.out.println(index);
							positions[i] = index;
							i++;

						}
						for(int t =0; t<positions.length;t++)
							System.out.println(positions[t]);
						
							int previous = positions[0]; 
							for(int o=1;o<positions.length;o++)
							{
								int replaceEnds = temp1beforetemp2(temp1, temp2,a1,previous,positions[o]); //65 and 92
								System.out.println("replace = "+replaceEnds);
								if(replaceEnds !=0)
									{
										
										String temporary1 = a1.substring(0, previous);
										String temporary2 = a1.substring(previous, a1.length());
										
										String startHighlightPosition = "startHighlightPosition";
										temporary2 = startHighlightPosition+temporary2; 
										for(int y=o;y<positions.length;y++)
										{
											positions[y] += startHighlightPosition.length();
										}
										a1= temporary1 + temporary2;
										replaceEnds = replaceEnds+ startHighlightPosition.length()+temp2.length()+1;
										//System.out.println(replaceEnds);
										
										String temporary3 =  a1.substring(0, replaceEnds);
										String temporary4 = a1.substring(replaceEnds, a1.length());
										String stopHighlightPosition = "StopHighlightPosition";
										
										temporary4 = stopHighlightPosition+temporary4;
										
										for(int y=o;y<positions.length;y++)
										{
											positions[y] += stopHighlightPosition.length();
										}
							
										a1= temporary3 + temporary4;
									}
								previous = positions[o];
							
							}
						//System.out.println(a1);
					}
					String a1Modified = removeUnmatchingCodeSegments(a1);
					//System.out.println(a1Modified);
					clusterHead.setSourceCode(a1Modified);
					
					//clusterHead.setSourceCode(a1);
					//a1copy= a1;
				}

				else {
					SourceCode clusterMember = new SourceCode();
					clusterMember.setSnippet(line);
					clusterMember.setStartHighlight("");
					clusterMember.setStopHighlight("");
					
					String param = line;
					
					// From ShowSourceCode.java
					String a1 = getSourceCode(functionName, param);

					param = param.replace(")", "");

					String funcName = functionName + "\\(";
					String parameters = null;
					
					
					if(param.indexOf('(') != param.length()-1)
					{
					parameters = param.substring(param.indexOf("("));
					}
					else
						parameters="";
					
					System.out.println(parameters);
					
					if (!parameters.equals("") && parameters.contains(",")) {
						String t1 = parameters.substring(0, parameters.indexOf(","));
						
						t1 = t1.replace("(", "");
						String temp1 = funcName + t1;
						temp1 = temp1.replace("\\", "");
						String temp2 = parameters.substring(parameters.lastIndexOf(","),
								parameters.length()); // ...
						System.out.println("first parameter = "+temp1);
						System.out.println("last parameter = "+temp2);
						
						int[] positions = new int[15];
						int i = 0;
						// find the index of all temp1 and send them in a loop to replace
						
						for (int index = a1.indexOf(temp1); index >= 0; index = a1.indexOf(
								temp1, index + 1)) {
							 
							positions[i] = index;
							i++;

						}
						
							int previous = positions[0]; 
							for(int o=1;o<positions.length;o++)
							{
								int replaceEnds = temp1beforetemp2(temp1, temp2,a1,previous,positions[o]); //65 and 92
								if(replaceEnds !=0)
									{
										
										String temporary1 = a1.substring(0, previous);
										String temporary2 = a1.substring(previous, a1.length());
										
										String startHighlightPosition = "startHighlightPosition";
										temporary2 = startHighlightPosition+temporary2; 
										for(int y=o;y<positions.length;y++)
										{
											positions[y] += startHighlightPosition.length();
										}
										a1= temporary1 + temporary2;
										replaceEnds = replaceEnds+ startHighlightPosition.length()+temp2.length()+1;
										
										String temporary3 =  a1.substring(0, replaceEnds);
										String temporary4 = a1.substring(replaceEnds, a1.length());
										String stopHighlightPosition = "StopHighlightPosition";
										
										temporary4 = stopHighlightPosition+temporary4;
										
										for(int y=o;y<positions.length;y++)
										{
											positions[y] += stopHighlightPosition.length();
										}
							
										a1= temporary3 + temporary4;
									}
								previous = positions[o];
							
							}
						//System.out.println(a1);
						

					} else if(!parameters.equals("")) {
						parameters = parameters.replace("(", "");
						String temp1 = funcName+parameters;
						temp1 = temp1.replace("\\", "");
						
						String temp2 = parameters;
						temp2 = temp2.replace("(", "");
						System.out.println("first parameter = "+temp1);
						System.out.println("last parameter = "+temp2);
						int[] positions = new int[15];
						int i = 0;
						// find the index of all temp1 and send them in a loop to replace

						for (int index = a1.indexOf(temp1); index >= 0; index = a1.indexOf(
								temp1, index + 1)) {
							 //System.out.println(index);
							positions[i] = index;
							i++;

						}
						for(int t =0; t<positions.length;t++)
							System.out.println(positions[t]);
						
							int previous = positions[0]; 
							for(int o=1;o<positions.length;o++)
							{
								int replaceEnds = temp1beforetemp2(temp1, temp2,a1,previous,positions[o]); //65 and 92
								System.out.println("replace = "+replaceEnds);
								if(replaceEnds !=0)
									{
										
										String temporary1 = a1.substring(0, previous);
										String temporary2 = a1.substring(previous, a1.length());
										
										String startHighlightPosition = "startHighlightPosition";
										temporary2 = startHighlightPosition+temporary2; 
										for(int y=o;y<positions.length;y++)
										{
											positions[y] += startHighlightPosition.length();
										}
										a1= temporary1 + temporary2;
										replaceEnds = replaceEnds+ startHighlightPosition.length()+temp2.length()+1;
										//System.out.println(replaceEnds);
										
										String temporary3 =  a1.substring(0, replaceEnds);
										String temporary4 = a1.substring(replaceEnds, a1.length());
										String stopHighlightPosition = "StopHighlightPosition";
										
										temporary4 = stopHighlightPosition+temporary4;
										
										for(int y=o;y<positions.length;y++)
										{
											positions[y] += stopHighlightPosition.length();
										}
							
										a1= temporary3 + temporary4;
									}
								previous = positions[o];
							
							}
						//System.out.println(a1);
					}
					String a1Modified = removeUnmatchingCodeSegments(a1);
					//System.out.println(a1Modified);
					clusterMember.setSourceCode(a1Modified);

					//ToDo;
					myList.add(clusterMember);
				}
			}
		}

		return result;

	}

	public String removeUnmatchingCodeSegments(String a1)
	{
		String startHighlightPosition = "startHighlightPosition";
		String stopHighlightPosition = "stopHighlightPosition";
		Iterable<String> itr = Splitter
				.on("+++++++++++++++++++++++++++++++++")
				.split(a1);
		
		ArrayList<String> myList = Lists.newArrayList(itr);
		
		for (Iterator<String> iterator = myList.iterator(); iterator
				.hasNext();) {
			String currentString = iterator.next(); 
			if (!currentString.contains(startHighlightPosition) && !currentString.contains(stopHighlightPosition))
				iterator.remove();

		}
		
		
		String a1Modified = "";
		
		for (String currentString : myList) 
		{
			a1Modified = a1Modified + currentString;
			a1Modified = a1Modified +"\n+++++++++++++++++++++++++++++++++";
		}
		
		return a1Modified;
		
		
	}
	public int temp1beforetemp2(String temp1, String temp2,String a1, int current,int next) {
		
				int ans = a1.indexOf(temp2, current);
				if(next !=0)
				{
					if (ans > current && ans < next) {
						return ans;
						}
				}
				
				else if(next ==0)
					return ans;

			return 0;

	}

	public static void main(String[] args) throws IOException {
		ReadFile r = new ReadFile();
		
		
		String result = r.removeUnmatchingCodeSegments(a1copy);
		System.out.println(result);
		
	}
}