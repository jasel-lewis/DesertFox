package com.jasel.classes.msim795.feed;

import com.google.common.collect.Lists;
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

public class TwitterDriver {
	private CustomStatusListener csl1 = new CustomStatusListener();
	private CustomStatusStreamHandler cssh = new CustomStatusStreamHandler();

	
	public void connect(String consumerKey, String consumerSecret, String token, String secret) throws InterruptedException {
		// Create an appropriately sized blocking queue
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

		// Define our endpoint: By default, delimited=length is set (we need this for our processor)
		// and stall warnings are on.
		StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();

		Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);

		// Create a new BasicClient. By default gzip is enabled.
		BasicClient client = new ClientBuilder()
		.hosts(Constants.STREAM_HOST)
		.endpoint(endpoint)
		.authentication(auth)
		.processor(new StringDelimitedProcessor(queue))
		.build();

		// Create an executor service which will spawn threads to do the actual work of parsing the incoming messages and
		// calling the listeners on each message
		int numProcessingThreads = 4;
		ExecutorService service = Executors.newFixedThreadPool(numProcessingThreads);

		// Wrap our BasicClient with the twitter4j client
		Twitter4jStatusClient t4jClient = new Twitter4jStatusClient(
				client,
				queue,
				Lists.newArrayList(csl1, cssh),
				service
		);

		// Establish a connection
		t4jClient.connect();
		
		for (int threads = 0; threads < numProcessingThreads; threads++) {
			// This must be called once per processing thread
			t4jClient.process();
		}

		Thread.sleep(5000);

		client.stop();
	}
}