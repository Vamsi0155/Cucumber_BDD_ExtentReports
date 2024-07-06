package com.Orange.Factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;


public class ReadConfigFiles {

	
	private static FileInputStream fis;
	
	public static Properties config = new Properties();
	
	private static final Logger logger = LogManager.getLogger(ReadConfigFiles.class);
	
	
	static {
		try {
			fis = new FileInputStream(new File (System.getProperty("user.dir") + "//configure.properties"));
			config.load(fis);
			
		} catch (Exception e) {
			logger.error("Error while loading of confiure properties ", e);
		}
		finally {
			try {
				if(fis !=null) {
					fis.close();
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

}
