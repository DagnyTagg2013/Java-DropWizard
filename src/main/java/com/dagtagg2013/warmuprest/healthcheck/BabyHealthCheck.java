package com.dagtagg2013.warmuprest.healthcheck;

import com.dagtagg2013.warmuprest.resource.BabyResource;
import com.yammer.metrics.core.HealthCheck;

/**
 * Created with IntelliJ IDEA.
 * User: daphneeng
 * Date: 10/29/13
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class BabyHealthCheck extends HealthCheck {

    BabyResource newBabyResource;
    private static final String EXPECTED_INITIAL_BUDDY = "Mama";

    public BabyHealthCheck(BabyResource newBabyResource) {
        // DENG TODO:  find out what this associates with in HealthCheck base class
        super(newBabyResource.getClass().getSimpleName());
        this.newBabyResource = newBabyResource;
    }


    @Override
    protected Result check() throws Exception {

        // DENG PROBLEM:  Resource class IS the REST-annotated endpoint class; and cannot be tested as a standalone POJO with OPTIONAL input params,
        //                so then OPTIONAL params must be supplied!
        if (!this.newBabyResource.talk(this.newBabyResource.getDefaultBuddy(), this.newBabyResource.getDefaultSaying()).getContent().contains(this.EXPECTED_INITIAL_BUDDY)) {
            return Result.unhealthy("YML environment configuration *.YML template is not initialized correctly for Service BabyResource resource.");
        }

        return Result.healthy();
    }
}
