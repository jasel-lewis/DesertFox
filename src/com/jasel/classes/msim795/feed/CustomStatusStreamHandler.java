/**
 * 
 */
package com.jasel.classes.msim795.feed;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;

import com.twitter.hbc.twitter4j.handler.StatusStreamHandler;
import com.twitter.hbc.twitter4j.message.DisconnectMessage;
import com.twitter.hbc.twitter4j.message.StallWarningMessage;

/**
 * @author Jasel
 *
 */
public class CustomStatusStreamHandler implements StatusStreamHandler {

	@Override
	public void onStatus(Status status) {
		System.out.println("onStatus() called");
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

	
	
	@Override
	public void onException(Exception exception) {
		System.out.println("onException() called");
		exception.printStackTrace();
	}

	
	
	@Override
	public void onDisconnectMessage(DisconnectMessage message) {
		System.out.println("onDisconnectMessage() called");
	}

	
	
	@Override
	public void onStallWarningMessage(StallWarningMessage message) {
		System.out.println("onStallWarningMessage() called");
	}

	
	
	@Override
	public void onUnknownMessageType(String type) {
		System.out.println("onUnknownMessageType() called");
	}
}