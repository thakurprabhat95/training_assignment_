package com.rainfallreport;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RainfallReport {

	
	ArrayList<String> arrayList=new ArrayList<String>();
	
	
	public void readData() throws IOException
	{
		FileInputStream fileInputStream=new FileInputStream("src/com/rainfallreport/AllCityMonthlyReport.txt");
		
		DataInputStream dataInputStream=new DataInputStream(fileInputStream);
		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(dataInputStream));
		
		String stLine;
		String split = null;
		while ((stLine = bufferedReader.readLine()) != null)
		{
			split=stLine.replace(",", " ");
            // Print the string
			
            System.out.println(split);
            
            String[] splitStr=split.split("\\s+");
            int sum=0;
            String string = splitStr[0];   
            String string1=splitStr[1];
            
            int firstNum=Integer.parseInt(string);
            
            System.out.println("first number is ...."+firstNum);
            
           
           
            	
            	
				
			}
		     
		 
		
		}
//		  if(split.contains(" "))
//          {
//          	arrayList.add(split);
//          	String string = arrayList.get(1);
//          	System.out.println("value of first index is.."+string);
//          	
//          	
//          }
//          for (String as:arrayList) {
//          	
//          	System.out.println("printing the value...."+as);
//          	
//				
//			}
		
	
	
	
	
	public static void main(String[] args) throws IOException {
		
		File dir=new File("C:\\Users\\prabhat.thakur\\Desktop\\Agreeya_Project\\ReadingTxtFile\\src\\com\\rainfallreport");
		
		if(!dir.exists()){
		dir.mkdir();}
		
		String fileName="generate";
		File tagFile=new File(dir,fileName+".txt");
		if(!tagFile.exists()){
		tagFile.createNewFile();
		}
		
		RainfallReport rainfallReport=new RainfallReport();
		rainfallReport.readData();
	}
}
