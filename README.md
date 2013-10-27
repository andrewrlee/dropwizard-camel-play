dropwizard-camel-play
=====================

This is a very simple service with a managed camel component and a simple test route.

The managed service creates a camel context and implements the required dropwizard lifecyle hooks. It also has a helper method to allow the creation of producer templates. 

This is then added to the environment:

```java
	@Override
	public void run(Configuration config, Environment env) throws Exception {

    		ManagedCamel camel = new ManagedCamel(new DelayRoute());
		env.manage(camel);
		env.addResource(new SampleResource(camel.createProducer()));
	
 	}
```

For this simple example, we inject the producer template directly into the resource and then pass a message to the camel route on every request.

```java
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
```

Our DelayRoute RouteBuilder contains the following simple route:

```java	
		from("direct:start").delay(Duration.seconds(5).toMilliseconds()).asyncDelayed().to("log:hello");
```	

