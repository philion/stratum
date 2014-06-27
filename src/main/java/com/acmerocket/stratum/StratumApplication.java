package com.acmerocket.stratum;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.hubspot.dropwizard.guice.GuiceBundle;

public class StratumApplication extends Application<StratumConfiguration> {

	public static void main(String[] args) throws Exception {
		new StratumApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<StratumConfiguration> bootstrap) {

		GuiceBundle<StratumConfiguration> guiceBundle = GuiceBundle.<StratumConfiguration>newBuilder()
				.addModule(new StratumModule())
				.enableAutoConfig(getClass().getPackage().getName())
				.setConfigClass(StratumConfiguration.class)
				.build();

		bootstrap.addBundle(guiceBundle);
	}

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void run(StratumConfiguration helloWorldConfiguration, Environment environment) throws Exception {
    }
}
