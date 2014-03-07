/**
 * 
 */
package com.jasel.classes.msim795.feed;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author jasel
 *
 */
public class Streamer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String defaultConfigFilename = "default.properties";
		String customConfigFilename = "";
		Properties props = new Properties();
		InputStream iStream = null;
		
		try {
			iStream = Streamer.class.getResourceAsStream(defaultConfigFilename);
			
			if (iStream == null) {
				System.err.println("Unable to load the default configuration properties");
			} else {
				props.load(iStream);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (iStream != null) {
				try {
					iStream.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		
		customConfigFilename = System.getProperty("config");
		
		if (customConfigFilename != null) {
			try {
				props.load(new FileInputStream(customConfigFilename));
			} catch (FileNotFoundException fnfe) {
				System.err.println("Could not locate the user-defined configuration file: " + customConfigFilename);
				fnfe.printStackTrace();
			} catch (IOException ioe) {
				System.err.println("Could not read from the user-defined configuration file: " + customConfigFilename);
				ioe.printStackTrace();
			}
		}
		
		TwitterDriver example = new TwitterDriver();
		
		try {
			example.connect(
					props.getProperty("consumerKey"),
					props.getProperty("consumerSecret"),
					props.getProperty("accessToken"),
					props.getProperty("accessTokenSecret")
			);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
}