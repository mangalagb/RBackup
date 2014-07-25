package HelperFiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.plaf.TableUI;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import au.com.bytecode.opencsv.CSVReader;
class SortAccordingToCluster
{
	
	
	public String[] readCodeSnippetFile() throws IOException
	{
		//Scanner sc = new Scanner(new File("./src/window_based_analysis/functions/AxisCodeSnippets.txt"));
		
		Scanner sc = new Scanner(new File("./src/window_based_analysis/new_CodeSnippets/AxisCodeSnippets.txt"));
		List<String> lines = new ArrayList<String>();
		while (sc.hasNextLine()) {
		  lines.add(sc.nextLine());
		}
		// Array containing code snippets
		String[] arr = lines.toArray(new String[0]);
		
		return arr;
	}
	
	public int[] readClusterNumbers() throws IOException
	{
		BufferedReader br = null;
		String sCurrentLine;
		br = new BufferedReader(new FileReader("/home/gowri/MATLAB SCRIPTS/NEW/try.csv"));
		int[] clusterNumbers = null;
		
		while ((sCurrentLine = br.readLine()) != null) {
			clusterNumbers = extractNumbers(sCurrentLine);
		}
		br.close();
		return clusterNumbers;
	}
	
	public int[] extractNumbers(String line)
	{
		Iterable<String> itr = Splitter.on(",").split(line);
		List<String> temporary = Lists.newArrayList(itr);
		
		int[] clusterNumbers = new int[temporary.size()];
		
		for(int i=0; i<temporary.size();i++)
		{
			clusterNumbers[i] = Integer.parseInt(temporary.get(i));
		}
		
		return clusterNumbers;
	}
	public void sortIntoClusters(String[] codeSnippet, int[] clusterNumbers)
	{
		
		int max = Integer.MIN_VALUE;
		
		for(int i = 0; i < clusterNumbers.length; i++) {
		      if(clusterNumbers[i] > max) {
		         max = clusterNumbers[i];
		      }
		}
		
		ArrayList<ArrayList<String>> clusters = new ArrayList<ArrayList<String>>();
		
		for (int j=0; j<max; j++){
			clusters.add(new ArrayList<String>());
		}
		
		for(int i=0; i<codeSnippet.length; i++)
		{
			clusters.get(clusterNumbers[i]-1).add(codeSnippet[i]);
			
		}
		
		for(int i=0; i<max; i++)
		{
			for(String s : clusters.get(i))
			{
				System.out.println(s);
			}
			
			System.out.println("\n");
		}
		
		
		
	}
	
	public static void main(String[] args) throws IOException
	{
		SortAccordingToCluster s = new SortAccordingToCluster();
		
		String [] codeSnippets = s.readCodeSnippetFile();
		int[] clusterNumbers = s.readClusterNumbers();
		
		s.sortIntoClusters(codeSnippets, clusterNumbers);
		
	}
}