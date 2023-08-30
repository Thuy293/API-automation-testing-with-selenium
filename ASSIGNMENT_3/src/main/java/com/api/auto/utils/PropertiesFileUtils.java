package com.api.auto.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.io.FileOutputStream;

public class PropertiesFileUtils {
	
	public static String getProperty(String key) throws IOException {
		FileInputStream file = new FileInputStream("./configuration/configs.properties");
		Properties properties = new Properties();
		properties.load(file);
		return (String) properties.get(key);
		
	}

	public void saveToken(String token, String value) throws IOException {
		FileOutputStream file = new FileOutputStream("./configuration/token.properties");
		Properties properties= new Properties();
		properties.setProperty(token, value);
		properties.store(file, "Set new value in properties");
		System.out.println("Set new value in file properties success.");
		
	}
	
	
	public static String getToken(String token) throws IOException {
		
		FileInputStream file = new FileInputStream("./configuration/token.properties");
		Properties properties = new Properties();
		properties.load(file);
		return (String) properties.get(token);
		
		
	}
}

