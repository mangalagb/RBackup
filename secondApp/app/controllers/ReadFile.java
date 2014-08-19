package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import com.google.common.base.Splitter;

import models.*;

public class ReadFile
{
	// A function that reads a file specified by path and returns an arraylist of lines
		public ArrayList<String> readFile(String functionName, String path)
		{
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
				br.close();
				
				Iterable<String> itr1 = Splitter
						.on(",")
						.split(parameters);
				
				
				
				
				//System.out.println(toMatch);
				Iterable<String> itr = Splitter
						.on("-------------------------------------------------------------------------------------------------------")
						.split(buf);
				
				Boolean contains = false;
				
				for (String snippet : itr) {
					for(String param : itr1)
					{//Check if all parameters are present or not in the snippet
						if(snippet.contains(param.trim()))
						{
							contains = true;
						}
						else
							{contains = false;
							break;
							}
							
					}
					
					//Snippet contains all the parameters
					if(contains == true)
					{
						sourceCode = sourceCode.concat(snippet);
						sourceCode = sourceCode.concat("+++++++++++++++++++++++++++++++++\n");
					}
					
					
				}
				
				
				return sourceCode;
				
			}
			finally
			{
				
			}
			
		}
		
		
		public HashMap<SourceCode, List<SourceCode>> returnClusterMembers(String functionName, String path) throws IOException
		{
			ArrayList<String> lines = readFile(functionName, path);
			
			HashMap<SourceCode, List<SourceCode>> result = new HashMap<SourceCode, List<SourceCode>>();
			
			SourceCode clusterHead = new SourceCode();
			ArrayList<SourceCode> myList = new ArrayList<SourceCode>();

			for(String line : lines)
			{
				if(line.equals("") || line.equals("\n"))
				{
					ArrayList<SourceCode> tempmyList = new ArrayList<SourceCode>(myList);
					SourceCode tempclusterHead = new SourceCode(clusterHead);

					result.put(tempclusterHead, tempmyList);
					//System.out.println(clusterHead);
					clusterHead.setSnippet("");
					clusterHead.setSourceCode("");
					clusterHead.setToHighlight("");
					myList.clear();
				}
				
				else
				{	
					
					if(line.startsWith("$"))
					{
						clusterHead.setSnippet(line.substring(1, line.length()));
						String param = line.substring(1, line.length());
						clusterHead.setSourceCode(getSourceCode(functionName, param));
						
						param = param.replace(")", "");
						clusterHead.setToHighlight(param);
						
						
					}
					
					else
					{	
						SourceCode clusterMember = new SourceCode();
						clusterMember.setSnippet(line);
						clusterMember.setSourceCode(getSourceCode(functionName, line));
						
						String param = line.replace(")", "");
						clusterMember.setToHighlight(param);
						
						myList.add(clusterMember);
					}
				}
			}
			
			return result;
			
		}
		
		public static void main(String[] args) throws IOException
		{
			
			ReadFile t = new ReadFile();
			String param ="x = y, side = 2, ...";
			
			//System.out.println(h);
			
			//String path = "./public/functions/allSnippets/myAxisCodeSnippets.txt";
			String path = "./public/functions/allSnippets/AxisCodeSnippets.txt";
			
			HashMap<SourceCode, List<SourceCode>> result = t.returnClusterMembers("Axis", path);
			
			for (Map.Entry<SourceCode, List<SourceCode>> entry : result.entrySet()) {
				
				SourceCode s = entry.getKey();
				System.out.println("KEY = "+s.getSnippet());
				
				System.out.println(s.getToHighlight());
				
				
				for(SourceCode d : entry.getValue())
				{
					System.out.println("VALUE =  "+d.getSnippet());
					System.out.println(d.getToHighlight());
					
				}
				
			}

		}
}