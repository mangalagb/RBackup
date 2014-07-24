package HelperFiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.plaf.TableUI;
class SortAccordingToCluster
{
	
	public void readClusterArray(String[] arr)
	{
		int clusterNumber[] = {2,6,2,5,5,3,7,6,1,5,7,2,7,4,1,5,2,7,2,7,2,1};
		int max = Integer.MIN_VALUE;
		
		for(int i = 0; i < clusterNumber.length; i++) {
		      if(clusterNumber[i] > max) {
		         max = clusterNumber[i];
		      }
		}
		
		String[][] clusters = new String[max][];
		for (int i = 0; i < max; i++)
		{
		    clusters[i] = new String[10];
		}
		
		
		for(int i=0; i< clusterNumber.length; i++)
		{
			int num = clusterNumber[i]; //6
			//clusters[num][]= arr[0];
			
			
			
		}
	}
	
	public void readCodeSnippetFile() throws IOException
	{
		//Scanner sc = new Scanner(new File("./src/window_based_analysis/functions/AxisCodeSnippets.txt"));
		
		Scanner sc = new Scanner(new File("./src/window_based_analysis/new_CodeSnippets/AxisCodeSnippets.txt"));
		List<String> lines = new ArrayList<String>();
		while (sc.hasNextLine()) {
		  lines.add(sc.nextLine());
		}

		String[] arr = lines.toArray(new String[0]);
		
		//readClusterArray(arr);
		sortIntoClusters(arr);
	}
	
	public void sortIntoClusters(String[] x)
	{
		//String[] x = {"a", "b", "c"};
		
		int[] y = {2,6,2,5,5,3,7,6,1,5,7,2,7,4,1,5,2,7,2,7,2,1};
		int max = Integer.MIN_VALUE;
		
		for(int i = 0; i < y.length; i++) {
		      if(y[i] > max) {
		         max = y[i];
		      }
		}
		
		System.out.println(y.length);
		System.out.println(x.length);
		
		ArrayList<ArrayList<String>> clusters = new ArrayList<ArrayList<String>>();
		
		for (int j=0; j<max; j++){
			clusters.add(new ArrayList<String>());
		}
		
		for(int i=0; i<x.length; i++)
		{
			clusters.get(y[i]-1).add(x[i]);
			
		}
		
		//System.out.println(clusters.get(0).get(0));
		//System.out.println(clusters.get(4).toString());
		
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
		s.readCodeSnippetFile();
	//	s.sortIntoClusters();
		
	}
}