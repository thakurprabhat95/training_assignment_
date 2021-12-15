package com.rainfallreport;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jdbc.ConfigFile;
import com.jdbc.ConnectionClass;

public class TxtFileDatabaseConversion
{
    public  void saveTextFileDataIntoDatabase()
    {
        try {
            
            BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\prabhat.thakur\\Desktop\\Agreeya_Project\\ReadingTxtFile\\src\\com\\rainfallreport\\AllCityMonthlyReport.txt")));
            String line;
            while ((line = br.readLine()) != null) 
            {
            	Scanner sc = new Scanner(line);     
                sc.useDelimiter(",");
                List<String> ar = new ArrayList<>();
                while (sc.hasNext()) 
                {
                    ar.add(sc.next());
                }
                System.out.println("Reading data form file");
                System.out.println(ar);
                calculateAverageRainFall(ar);          	
            }          
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    //calculate average of rain fall
    public static void calculateAverageRainFall(List<String> ar)
    {
        String zipcode = ar.get(0);
        String city = ar.get(1);
        ar.remove(0);
        ar.remove(0); 
        float avgrfall = (ar.stream().mapToInt(Integer::parseInt).sum())/12; 
        try {
            Connection c1 = ConnectionClass.getConnection(ConfigFile.URLACCESS,ConfigFile.USERNAME,ConfigFile.PASSWORD);
              PreparedStatement ps = c1.prepareStatement("insert into cityrainfall(zipcode,city,avgrainfall) values(?,?,?)");
            ps.setString(1,zipcode);
            ps.setString(2,city);
            ps.setFloat(3,avgrfall);
            int x=ps.executeUpdate();
            if(x>0) 
            {
            System.out.println("Data Inserted Successfully ");
                     
            }
            
            System.out.println("The Average Rainfall of "+city+" having zipcode "+zipcode+" is "+avgrfall);
            System.out.println("-------------------------------------------------"); 
           } 
        catch (SQLException e) 	
        {
            e.printStackTrace();
        }
    }
    
    //this method for connection and query
    public  void dataFromDatabaseIntoTextFile()
    {
    	List<String> data = new ArrayList<String>();
    	try {
    		 Connection c1 = ConnectionClass.getConnection(ConfigFile.URLACCESS,ConfigFile.USERNAME,ConfigFile.PASSWORD);
    	       PreparedStatement ps = c1.prepareStatement("select *from cityrainfall");
    	       ResultSet executeSelectQuery = ps.executeQuery();
    	       
    	       while (executeSelectQuery.next()) {
   				String zipcode = executeSelectQuery.getString("zipcode");
   				
   				String city = executeSelectQuery.getString("city");
   				float averRainFall = executeSelectQuery.getFloat("avgrainfall");
   				data.add(zipcode + " " + city + " " + averRainFall);
   				
   				

   			}
    	       
    	       writeToTextFile(data,"C:\\Users\\prabhat.thakur\\Desktop\\Agreeya_Project\\ReadingTxtFile\\src\\com\\rainfallreport\\DatabaseToTextFile.txt.txt");
    		
    		
    	}
    	catch (Exception e) {
    		e.printStackTrace();
		}
    }

    //this method to write into text file from database
    private static void writeToTextFile(List<String> data, String filelocation) throws SQLException {
    	
    	BufferedWriter bufferedWriterOutput = null;
		try {
			File file = new File(filelocation);
			bufferedWriterOutput = new BufferedWriter(new FileWriter(file, true));
			for (String s : data) {
				bufferedWriterOutput.write(s);
				bufferedWriterOutput.newLine();

			}
			
			//open notepad file consisting database file using jwt
			try {
				
				
			File fileLocation = new File("C:\\Users\\prabhat.thakur\\Desktop\\Agreeya_Project\\ReadingTxtFile\\src\\com\\rainfallreport\\DatabaseToTextFile.txt.txt");
			
				if(!Desktop.isDesktopSupported())
			{
				System.out.println("Not supported..");
				return;
			}
			
			Desktop desktop = Desktop.getDesktop();  
			if(fileLocation.exists())
				desktop.open(fileLocation);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			
			bufferedWriterOutput.close();
		} 
		
		
		catch (IOException e) {
		}
		
	}
    
    //this method to validate the length of zipcode
   private  void validationForZipcode()
   {
	   try {
  		 Connection connection = ConnectionClass.getConnection(ConfigFile.URLACCESS,ConfigFile.USERNAME,ConfigFile.PASSWORD);
  	       PreparedStatement ps = connection.prepareStatement("select *from cityrainfall");
  	       ResultSet executeSelectQuery = ps.executeQuery();
  	       
  	       while (executeSelectQuery.next()) {
 				String zipcode = executeSelectQuery.getString("zipcode");
 	   			
 				if(zipcode.length()==5)
 				{
 					System.out.println("Status:Ok..");
 				}
 				else if(zipcode.length()<5 || zipcode.length()>5)
 				{
 					
 					//customize exception
 					throw new ZipcodeException("Length should be 5!!!");
 				}
 				
 		
 			}
 		
  	}
  	catch (Exception e) {
  		e.printStackTrace();
		}
	   
   }
   
   public void chooseAnyOption()
   {
	   
	   int choice;
	   Scanner scanner=new Scanner(System.in);
	  
	   
	   //loop until user wants to exit
	 
		   //print option
		   System.out.println("Choose Option:-\n"
                   + "1. Insert Into Database from Text File\n"
                   + "2. Insert into Text File from Database\n"
                   + "3. Length Validation of Zipcode\n"
                   + "4.Exit\n"
                   + "Enter your Option : ");
		   choice=scanner.nextInt();
		   
		   switch(choice)
		   {
		   case 1:
			   this.saveTextFileDataIntoDatabase();
			   this.chooseAnyOption();
			   break;
		   case 2:
			   this.dataFromDatabaseIntoTextFile();
			   break;
		   case 3:
			   this.validationForZipcode();
			   break;
		   case 4:
			   System.exit(0);
		   default:
			   System.out.println("Invalid option !!, try again");
		   }
	   }
	   
   
	public static void main(String[] args) {
		TxtFileDatabaseConversion conversion=new TxtFileDatabaseConversion();
		conversion.chooseAnyOption();
    	
    }
}


