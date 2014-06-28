package com.acmerocket.stratum.minisu;

import io.dropwizard.Bundle;
import io.dropwizard.configuration.ConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;

/**
 * Inspired by, derived from: https://github.com/minisu/Dropwizard-EnvVar-Interpolation
 * @author philion
 *
 */
public class EnvVarConfigBundle implements Bundle {
    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(), new StrSubstitutor(new EnvironmentVariableLookup())));
    }

    @Override
    public void run(Environment environment) { /* no-op */ }
    
    private static class EnvironmentVariableLookup extends StrLookup {
        @Override
        public String lookup(String key) {
            
            String value = System.getenv(key);
            if (value == null) {
                throw new EnvironmentException("The environment variable '" + key + "' is not defined; could not substitute the expression '${" + key + "}'.");
            }

            return value;
        }
    }
    
    private static class SubstitutingSourceProvider implements ConfigurationSourceProvider {
        private final ConfigurationSourceProvider baseProvider;
        private final StrSubstitutor strSubstitutor;

        public SubstitutingSourceProvider(ConfigurationSourceProvider baseProvider, StrSubstitutor strSubstitutor) {
            this.baseProvider = baseProvider;
            this.strSubstitutor = strSubstitutor;
        }

        @Override
        public InputStream open(String path) throws IOException {
            String config = convertStreamToString(baseProvider.open(path));
            StrSubstitutor environmentVariableSubstitutor = strSubstitutor;

            String substitutedConfig = environmentVariableSubstitutor.replace(config);
            return new ByteArrayInputStream(substitutedConfig.getBytes());
        }

        private static String convertStreamToString(InputStream is) {
            Scanner s = new Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
    }
    
    private static class EnvironmentException extends RuntimeException {
        public EnvironmentException(String errorMessage) {
            super(errorMessage);
        }
    }
}
