package uk.co.optimisticpanda.camel.delay;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.lifecycle.Managed;

public class ManagedCamel implements Managed {

	private static final Logger log = LoggerFactory.getLogger(ManagedCamel.class);
	private final DefaultCamelContext camelContext;

	public ManagedCamel(RouteBuilder... routes) throws Exception{
		camelContext = new DefaultCamelContext();
		for (RouteBuilder routeBuilder : routes) {
			camelContext.addRoutes(routeBuilder);
		}
			
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run() {
				log.info("Received hang up - stopping camel.");
				try {
					if(!camelContext.isStopped()){
						camelContext.stop();
					}
				} catch (Exception ex) {
					log.warn("Error during stopping camel.", ex);
				}
			};
		});
	}

	public void start() throws Exception {
		camelContext.start();
	}

	public void stop() throws Exception {
		camelContext.stop();
	}

	public ProducerTemplate createProducer() {
		return camelContext.createProducerTemplate();
	}

}
