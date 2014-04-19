/**
 * 
 */
package com.jasel.classes.msim795.feed;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Locale;

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
	
	
	public CustomStatusListener(BufferedWriter bw) {
		this.bw = bw;
	}
	
	

	@Override
	public void onStatus(Status status) {
		String editedText = status.getText()
				.replaceAll("@\\S+", " ")		// Remove all user references
				.replaceAll("#\\S+",  " ")		// Remove all hashtags
				.replaceAll("RT\\s", "")		// Remove all re-tweet notifications
				.replaceAll("http\\S+", " ")	// Remove all links
				.replaceAll("\\p{Punct}", " ")	// Remove all punctuation
				.replaceAll("[^ -~]", "")		// Strip non-printable and control characters
				.toUpperCase(Locale.US);		// Hopefully get rid of non-standard characters
												// and make searching easier in the C portion
												// of the code
		
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