/**
 * 
 */
package com.jasel.classes.msim795.feed;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

/**
 * @author Jasel
 *
 */
public class CustomStatusListener implements StatusListener  {
	private BufferedWriter bw;
	
	
	public CustomStatusListener(String filename) {
		try {
			bw = new BufferedWriter(new FileWriter(new File(filename)));
		} catch (IOException ioe) {
			System.err.println("Cannot open " + filename + " for writing");
			ioe.printStackTrace();
			System.exit(10);
		} catch (NullPointerException npe) {
			System.err.println("Filename passed for output file is NULL");
			System.exit(11);
		}
	}
	
	

	@Override
	public void onStatus(Status status) {
		String editedText = status.getText()
				.replaceAll("@\\S+", " ")		// Remove all user references
				.replaceAll("#\\S+",  " ")		// Remove all hashtags
				.replaceAll("RT\\s", "")		// Remove all re-tweet notifications
				.replaceAll("http\\S+", " ")	// Remove all links
				.replaceAll("\\p{Punct}", " "); // Remove all punctuation
		
		writeLine(editedText);
	}
	
	
	
	@Override
	public void onException(Exception exception) {
		System.err.println("onException() called");
		exception.printStackTrace();
	}

	
	
	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		// Do nothing
	}

	
	
	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		// Do nothing
	}

	
	
	@Override
	public void onStallWarning(StallWarning warning) {
		System.err.println("Stall warning received: " + warning);
	}

	
	
	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		// Do nothing
	}
	
	
	
	private void writeLine(String string) {
		write(string, true);
	}
	
	
	
	private void write(String string, boolean appendNewline) {
		try {
			bw.write(string);
			
			if (appendNewline) {
				bw.newLine();
			}
		} catch (IOException e) {
			System.err.println("Can't write to the BufferedWriter");
			e.printStackTrace();
		}
	}
}