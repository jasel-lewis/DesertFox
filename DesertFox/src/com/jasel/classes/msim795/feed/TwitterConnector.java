package com.jasel.classes.msim795.feed;

import com.google.common.collect.Lists;
import com.jasel.classes.msim795.exception.MissingAuthParameterException;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.twitter.hbc.twitter4j.v3.Twitter4jStatusClient;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class TwitterConnector {
	private CustomStatusListener csl = new CustomStatusListener();
	private CustomStatusStreamHandler cssh = new CustomStatusStreamHandler();
	private BasicClient basicClient;
	private int numProcessingThreads = 4;
	
	
	public void connect(String consumerKey, String consumerSecret, String token, String secret)
			throws InterruptedException, MissingAuthParameterException {
		
		if (consumerKey.isEmpty() || consumerSecret.isEmpty() || token.isEmpty() || secret.isEmpty()) {
			throw new MissingAuthParameterException("One or more authentication parameters is NULL");
		}
		
		// Create an appropriately sized blocking queue
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

		// Define our end-point: By default, delimited=length is set (we need this for our processor)
		// and stall warnings are on.
		StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();

		Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);

		// Initialize a new BasicClient. By default gzip is enabled.
		basicClient = new ClientBuilder()
		.hosts(Constants.STREAM_HOST)
		.endpoint(endpoint)
		.authentication(auth)
		.processor(new StringDelimitedProcessor(queue))
		.build();

		// Create an executor service which will spawn threads to do the actual work of parsing the incoming messages and
		// calling the listeners on each message
		ExecutorService execService = Executors.newFixedThreadPool(numProcessingThreads);

		// Wrap our BasicClient with the twitter4j client
		Twitter4jStatusClient t4jClient = new Twitter4jStatusClient(
				basicClient,
				queue,
				Lists.newArrayList(csl, cssh),
				execService
		);

		// Establish a connection
		t4jClient.connect();
		
		for (int threads = 0; threads < numProcessingThreads; threads++) {
			// This must be called once per processing thread
			t4jClient.process();
		}
	}
	
	
	
	public void close() {
		basicClient.stop();
	}
}