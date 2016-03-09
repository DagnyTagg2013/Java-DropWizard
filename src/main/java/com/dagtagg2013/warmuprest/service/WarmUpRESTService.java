package com.dagtagg2013.warmuprest.service;

import com.dagtagg2013.warmuprest.configuration.BabyConfiguration;

import com.dagtagg2013.warmuprest.resource.BabyResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.dagtagg2013.warmuprest.healthcheck.BabyHealthCheck;


/**
 * Created with IntelliJ IDEA.
 * User: daphneeng
 * Date: 10/22/13
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */

public class WarmUpRESTService extends Service<BabyConfiguration> {

    private static final String BABY_RESOURCE_CONFIG_NAME = "BabyConfiguration";

    public static void main(String[] args) throws Exception {
        new WarmUpRESTService().run(args);
    }

    @Override
    public void initialize(Bootstrap<BabyConfiguration> bootstrap) {
        // DENG NOTE:  this associates the BabyConfiguration from unique-matching properties in *.YML environment configuration 1:1 for THIS Service
        bootstrap.setName(this.BABY_RESOURCE_CONFIG_NAME);
    }

    @Override
    public void run(BabyConfiguration configuration,
                    Environment environment)
    {
        // DENG NOTE:  DEFAULT params from loaded configuration file are here associated with DEFAULT resource instance
        BabyResource newBabyResource = new BabyResource(configuration.getDefaultBuddy(), configuration.getDefaultSaying());
        environment.addResource(newBabyResource);
        environment.addHealthCheck(new BabyHealthCheck(newBabyResource));
    }

}
