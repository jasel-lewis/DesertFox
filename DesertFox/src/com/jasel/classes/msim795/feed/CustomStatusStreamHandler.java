/**
 * 
 */
package com.jasel.classes.msim795.feed;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;

import com.twitter.hbc.twitter4j.v3.handler.StatusStreamHandler;
import com.twitter.hbc.twitter4j.v3.message.DisconnectMessage;
import com.twitter.hbc.twitter4j.v3.message.StallWarningMessage;

/**
 * @author jasel
 *
 */
public class CustomStatusStreamHandler implements StatusStreamHandler {

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
	 * @see twitter4j.StatusListener#onStatus(twitter4j.Status)
	 */
	@Override
	public void onStatus(Status status) {}

	
	
	/* (non-Javadoc)
	 * @see twitter4j.StatusListener#onTrackLimitationNotice(int)
	 */
	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

	
	
	/* (non-Javadoc)
	 * @see twitter4j.StreamListener#onException(java.lang.Exception)
	 */
	@Override
	public void onException(Exception arg0) {}

	
	
	/* (non-Javadoc)
	 * @see com.twitter.hbc.twitter4j.v3.handler.StatusStreamHandler#onDisconnectMessage(com.twitter.hbc.twitter4j.message.DisconnectMessage)
	 */
	@Override
	public void onDisconnectMessage(DisconnectMessage message) {}

	
	
	/* (non-Javadoc)
	 * @see com.twitter.hbc.twitter4j.v3.handler.StatusStreamHandler#onUnknownMessageType(java.lang.String)
	 */
	@Override
	public void onUnknownMessageType(String msg) {}



	/* (non-Javadoc)
	 * @see com.twitter.hbc.twitter4j.v3.handler.StatusStreamHandler#onUnknownMessageType(java.lang.String)
	 */
	@Override
	public void onStallWarningMessage(StallWarningMessage warning) {}
}