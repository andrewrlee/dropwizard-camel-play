package uk.co.optimisticpanda.camel.delay;

import org.apache.camel.builder.RouteBuilder;

import com.yammer.dropwizard.util.Duration;

public class Route extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		from("direct:start").delay(Duration.seconds(5).toMilliseconds()).asyncDelayed().to("log:hello");
		
	}

}
