package com.doc.maven.readRwite;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Ex3_ReadWriteTxtFileAndBindWithObject {  
	  public static void main(String[] args)throws Exception {
	        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/employeeInputData.txt"));

	        ArrayList<Person> list = new ArrayList<Person>();
	        Person p = null;
	        String readLine = br.readLine();

	        while(readLine != null){
	        	
	        	String [] person  = readLine.split("\\|");
	             
	            
	            p = new Person();
                p.setAge(Integer.parseInt(person[0].trim()));
                p.setFname(person[1].trim());
                p.setLname(person[2].trim());
                p.setCity(person[3].trim());
                list.add(p);
                readLine = br.readLine();
	        }


	       try( FileOutputStream fout = new FileOutputStream("src/main/resources/employeeOutputData.txt"))
	       {

	        for(Person prsn : list){
	            fout.write(prsn.toString().getBytes());
	            fout.write('\n');
	            
	            System.out.println("Person :"+prsn.toString());
	        }
	       }
	       catch (IOException e) {
	    	   System.out.println(e.getStackTrace());
		}

	    }

	}

