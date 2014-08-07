package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
	
	// A function to return the code snippets ordered by group
	
	public HashMap<String, String> returnClusterMembers(String functionName, String path)
	{
		
		ArrayList<String> lines = readFile(functionName, path);
		
		
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		String clusterHead = "";
		String clusterMembers = "";
		
		//mark cluster head with special character $
		
		for(String line : lines)
		{	//System.out.println(line);
			if(line.equals("") || line.equals("\n"))
			{
				result.put(clusterHead, clusterMembers);
				//System.out.println(clusterHead);
				clusterHead = "";
				clusterMembers = "";
			}
			
			else
			{
				if(line.startsWith("$"))
				{
					clusterHead = line.substring(1, line.length());
				}
				
				else
				{
					clusterMembers += line;
					clusterMembers += "\n";
				}
			}
		}
		
		return result;
	}
	
//	public static void main(String[] args)
//	{
//		ReadFile r = new ReadFile();
//		HashMap<String, String> result = r.returnClusterMembers("Axis", "./public/functions/allSnippets/AxisCodeSnippets.txt");
//		
//		for (Map.Entry<String, String> entry : result.entrySet()) {
//			
//			System.out.println(entry.getKey());
//			System.out.println(entry.getValue());
//			System.out.println("___________________________________________----");
//		}
//	}
	
}