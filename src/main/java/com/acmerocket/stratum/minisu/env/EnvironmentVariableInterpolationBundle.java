package com.acmerocket.stratum.minisu.env;

import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.apache.commons.lang.text.StrSubstitutor;

import com.acmerocket.stratum.minisu.env.EnvironmentVariableLookup;
import com.acmerocket.stratum.minisu.env.SubstitutingSourceProvider;

public class EnvironmentVariableInterpolationBundle implements Bundle {
    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(), new StrSubstitutor(new EnvironmentVariableLookup())));
    }

    @Override
    public void run(Environment environment) { /* no-op */ }
}
