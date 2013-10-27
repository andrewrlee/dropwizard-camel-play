package uk.co.optimisticpanda.camel.delay;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.camel.ProducerTemplate;

import com.yammer.metrics.annotation.Timed;

@Path("/sample")
@Produces(MediaType.TEXT_HTML)
public class SampleResource {

	private ProducerTemplate producer;

	public SampleResource(ProducerTemplate producer) {
		this.producer = producer;
	}

	@GET
	@Timed
	public String respond() {
		String message = "Hello at " + new Date();
		producer.asyncSendBody("direct:start", message);
		return message;
	}

}
