package com.racingsimulator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.LinkedList;
import java.util.List;

import java.util.Properties;
import java.util.StringTokenizer;


public class ReaderPropertiesFile {
	private Properties properties;
	
	public ReaderPropertiesFile(String filePropertiesName){
		properties = new Properties();
		load(filePropertiesName);
	}
	
	public String getProperty(String propertyName){
		return properties.getProperty(propertyName);
	}
	
	public int getPropertyInt(String propertyName){
		return Integer.valueOf(getProperty(propertyName));
	}
	
	public double getPropertyDouble(String propertyName){
		return Double.valueOf(getProperty(propertyName));
	}
	
	public List<String> getPropertyList(String propertyName){
		StringTokenizer tokens = new StringTokenizer(
				getProperty(propertyName), ","
		);
		List<String> list = new LinkedList<String>();
		while(tokens.hasMoreElements()){
			list.add(tokens.nextElement().toString());
		}
		return list;
	}
	
	public boolean isEmptyFileProperties(){
		return properties.isEmpty();
	}
	
	public void load(String filePropertiesName){
		try {
			properties.load(new BufferedInputStream(
						new FileInputStream(new File(filePropertiesName))
					)
			);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
