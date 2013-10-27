package uk.co.optimisticpanda.camel.delay;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;

public class DelayService extends Service<Configuration> {

	public static void main(String[] args) throws Exception {
		new DelayService().run(new String[] { "server", "service.yml" });
	}

	@Override
	public void initialize(Bootstrap<Configuration> arg0) {
	}

	@Override
	public void run(Configuration config, Environment env) throws Exception {

		ManagedCamel camel = new ManagedCamel(new Route());
		env.manage(camel);
		env.addResource(new SampleResource(camel.createProducer()));

	}

}
