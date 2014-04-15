/**
 * 
 */
package com.jasel.classes.msim795.feed;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

/**
 * @author Jasel
 *
 */
public class CustomStatusListener implements StatusListener {

	@Override
	public void onStatus(Status status) {
		System.out.println(status);
	}
	
	
	
	@Override
	public void onException(Exception exception) {
		System.out.println("onException() called");
		exception.printStackTrace();
	}

	
	
	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		System.out.println("onDeletionNotice() called");
	}

	
	
	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		System.out.println("onScrubGeo() called");
	}

	
	
	@Override
	public void onStallWarning(StallWarning warning) {
		System.out.println("onStallWarning() called");
	}

	
	
	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		System.out.println("onTrackLimitationNotice() called");
	}
}