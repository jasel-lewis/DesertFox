/**
 * 
 */
package com.jasel.classes.msim795.feed;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

/**
 * @author jasel
 *
 */
public class CustomStatusListener implements StatusListener {
	
	/* (non-Javadoc)
	 * @see twitter4j.StatusListener#onStatus(twitter4j.Status)
	 */
	@Override
	public void onStatus(Status status) {
		System.out.println("STATUS:  User: " + status.getUser());
		System.out.println("         Text: " + status.getText());
	}
	
	
	
	/* (non-Javadoc)
	 * @see twitter4j.StreamListener#onException(java.lang.Exception)
	 */
	@Override
	public void onException(Exception arg0) {}

	
	
	/* (non-Javadoc)
	 * @see twitter4j.StatusListener#onDeletionNotice(twitter4j.StatusDeletionNotice)
	 */
	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}

	
	
	/* (non-Javadoc)
	 * @see twitter4j.StatusListener#onScrubGeo(long, long)
	 */
	@Override
	public void onScrubGeo(long userId, long upToStatusId) {}

	
	
	/* (non-Javadoc)
	 * @see twitter4j.StatusListener#onStallWarning(twitter4j.StallWarning)
	 */
	@Override
	public void onStallWarning(StallWarning warning) {}

	

	/* (non-Javadoc)
	 * @see twitter4j.StatusListener#onTrackLimitationNotice(int)
	 */
	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
}