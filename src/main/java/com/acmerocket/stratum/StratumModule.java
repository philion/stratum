package com.acmerocket.stratum;

import javax.inject.Named;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class StratumModule extends AbstractModule {
	
	@Override
	protected void configure() {

	}
	
	@Provides
	@Named("template")
	public String provideTemplate(StratumConfiguration configuration) {
		return configuration.getTemplate();
	}

	@Provides
	@Named("defaultName")
	public String provideDefaultName(StratumConfiguration configuration) {
		return configuration.getDefaultName();
	}

}
