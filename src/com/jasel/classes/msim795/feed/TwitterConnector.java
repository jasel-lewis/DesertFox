package com.jasel.classes.msim795.feed;

import com.google.common.collect.Lists;
import com.jasel.classes.msim795.exception.MissingAuthParameterException;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFirehoseEndpoint;
import com.twitter.hbc.core.endpoint.StreamingEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.twitter.hbc.twitter4j.Twitter4jStatusClient;
import com.twitter.hbc.twitter4j.handler.StatusStreamHandler;
import com.twitter.hbc.twitter4j.message.DisconnectMessage;
import com.twitter.hbc.twitter4j.message.StallWarningMessage;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class TwitterConnector {
	private CustomStatusListener csl = new CustomStatusListener();
	private CustomStatusStreamHandler cssh = new CustomStatusStreamHandler();
	Twitter4jStatusClient t4jClient;
	private int numProcessingThreads = 4;

	public void connect(Properties props) throws InterruptedException,
			MissingAuthParameterException {
		
		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
		BlockingQueue<Event> evtQueue = new LinkedBlockingQueue<Event>(1000);
		//List<? extends StatusListener> listeners = (List<? extends StatusListener>) Lists.newArrayList(csl, cssh);
		//List<? extends StatusListener> listeners = (List<? extends StatusListener>) Lists.newArrayList(csl);
		List<? extends StatusListener> listeners = Lists.newArrayList();
		ExecutorService executorService;
		Hosts hosts;
		StreamingEndpoint endpoint;
		Authentication auth;
		ClientBuilder builder;
		String apiKey = props.getProperty("apiKey");
		String apiSecret = props.getProperty("apiSecret");
		String token = props.getProperty("accessToken");
		String secret = props.getProperty("accessTokenSecret");

		if (apiKey.isEmpty() || apiSecret.isEmpty()	|| token.isEmpty() || secret.isEmpty()) {
			throw new MissingAuthParameterException("One or more authentication parameters is NULL");
		}

		// Define the host to connect to, the endpoint and the authentication
		hosts = new HttpHosts(Constants.STREAM_HOST);
		endpoint = new StatusesFirehoseEndpoint();
		auth = new OAuth1(apiKey, apiSecret, token, secret);
		
		// Build a client
		builder = new ClientBuilder()
				.name("Client-01")
				.hosts(hosts)
				.endpoint(endpoint)
				.authentication(auth)
				.processor(new StringDelimitedProcessor(msgQueue))
				.eventMessageQueue(evtQueue);

		// Create an ExecutorService to spawn threads which will do the actual work of
		// parsing the incoming messages and calling the listeners on each message
		executorService = Executors.newFixedThreadPool(numProcessingThreads);
		
((List<? extends StatusListener>)listeners).append(csl);

		// Build the Client and wrap it within a Twitter4jClient
		t4jClient = new Twitter4jStatusClient(
				builder.build(),
				msgQueue,
				listeners,
				executorService
		);

		// Establish a connection
		t4jClient.connect();

		// t4jClient.process() must be called once per processing thread
		for (int threads = 0; threads < numProcessingThreads; threads++) {
			t4jClient.process();
		}
	}

	
	
	/*
	 * Permanently stop the connection and perform any necessary cleanup
	 */
	public void close() {
		t4jClient.stop();
		System.err.println("t4jClient has been stopped");
	}
}