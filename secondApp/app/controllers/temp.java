package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;

import models.*;

public class temp
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
				
				Iterable<String> itr1 = Splitter
						.on(",")
						.split(parameters);
				
				
				
				
				//System.out.println(toMatch);
				Iterable<String> itr = Splitter
						.on("-------------------------------------------------------------------------------------------------------")
						.split(buf);
				
				Boolean contains = false;
				
				for (String snippet : itr) {
					
					for (String param : itr1) {
						
						if(snippet.contains(param.trim()))
						{
							contains = true;
						}
						else
							contains = false;
					}
					
					if(contains == true)
					{
						sourceCode = sourceCode + snippet;
					}
					
					
				}
				
				br.close();
				return sourceCode;
				
			}
			finally
			{
				
			}
			
		}
		
		public HashMap<SourceCode, ArrayList<SourceCode>> returnClusterMembers(String functionName, String path) throws IOException
		{
			ArrayList<String> lines = readFile(functionName, path);
			
			HashMap<SourceCode, ArrayList<SourceCode>> result = new HashMap<SourceCode, ArrayList<SourceCode>>();
			
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
					myList.clear();
				}
				
				else
				{	
					
					if(line.startsWith("$"))
					{
						clusterHead.setSnippet(line.substring(1, line.length()));
						clusterHead.setSourceCode(getSourceCode(functionName, line.substring(1, line.length())));
						
					}
					
					else
					{	
						SourceCode clusterMember = new SourceCode();
						clusterMember.setSnippet(line);
						clusterMember.setSourceCode(getSourceCode(functionName, line));
						myList.add(clusterMember);
					}
				}
			}
			
			return result;
			
		}
		
		public static void main(String[] args) throws IOException
		{
			
			temp t = new temp();
			HashMap<SourceCode, ArrayList<SourceCode>> result = t.returnClusterMembers("Axis", "./public/functions/allSnippets/AxisCodeSnippets.txt");
			
			for (Map.Entry<SourceCode, ArrayList<SourceCode>> entry : result.entrySet()) {
				
				System.out.println(entry.getKey().getSnippet());
				if(! entry.getKey().getSourceCode().equals(""))
					System.out.println("yes");
				System.out.println(entry.getValue().size());
				
				ArrayList<SourceCode> myList = entry.getValue();
				
				for(SourceCode s : myList)
				{
					if(!s.getSourceCode().equals(""))
					System.out.println("yes");
					
					else
					System.out.println("no");
				}
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
			}
		}
}