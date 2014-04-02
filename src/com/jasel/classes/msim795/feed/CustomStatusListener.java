/**
 * 
 */
package com.jasel.classes.msim795.feed;

import com.twitter.hbc.twitter4j.message.DisconnectMessage;
import com.twitter.hbc.twitter4j.message.StallWarningMessage;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

/**
 * @author jasel
 * 
 */
public class CustomStatusListener implements StatusListener {
	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStallWarning(StallWarning warning) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatus(Status status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onException(Exception arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onDisconnectMessage(DisconnectMessage arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStallWarningMessage(StallWarningMessage arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onUnknownMessageType(String arg0) {
		// TODO Auto-generated method stub
		
	}
}