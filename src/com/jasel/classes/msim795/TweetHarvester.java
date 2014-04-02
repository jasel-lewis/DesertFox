/**
 * https://dev.twitter.com/docs/twitter-libraries
 * https://github.com/twitter/hbc
 */
package com.jasel.classes.msim795;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jasel.classes.msim795.exception.MissingAuthParameterException;
import com.jasel.classes.msim795.feed.TwitterConnector;

/**
 * @author jasel
 * 
 */
public class TweetHarvester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String packageName = TweetHarvester.class.getPackage().getName();
		String defaultConfigFilename = "default.properties";
		String customConfigFilename = "";
		Properties props = new Properties();
		InputStream iStream = null;

		// Convert dots to slashes
		packageName = packageName.replaceAll("\\.", "/");

		try {
			iStream = TweetHarvester.class.getClassLoader().getResourceAsStream(
					new String(packageName + "/resources/" + defaultConfigFilename)
			);

			if (iStream == null) {
				System.err
						.println("Unable to load the default configuration properties.  Cannot continue.");
				System.exit(1);
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

		// Allow command line config parameter to override the default
		// properties file
		customConfigFilename = System.getProperty("config");

		if (customConfigFilename != null) {
			try {
				props.load(new FileInputStream(customConfigFilename));
			} catch (FileNotFoundException fnfe) {
				System.err
						.println("Could not locate the user-defined configuration file: "
								+ customConfigFilename);
				fnfe.printStackTrace();
			} catch (IOException ioe) {
				System.err
						.println("Could not read from the user-defined configuration file: "
								+ customConfigFilename);
				ioe.printStackTrace();
			}
		}

		TwitterConnector connector = new TwitterConnector();

		try {
			connector.connect(props);

			Thread.sleep(1000);

			connector.close();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (MissingAuthParameterException mape) {
			mape.printStackTrace();
		}
	}
}