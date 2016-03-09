package com.dagtagg2013.warmuprest.configuration;

/**
 * Created with IntelliJ IDEA.
 * User: daphneeng
 * Date: 10/22/13
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */

/*
    DENG NOTE: This class must align elements with
    - *.yml config properties
    - resource BabyResource constructor
    - BabyResource properties
    - service bootstrap invocation

 */
import com.yammer.dropwizard.config.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;


public class BabyConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String defaultBuddy = "Some Stranger";

    @NotEmpty
    @JsonProperty
    private String defaultSaying = "Some Saying";

    public String getDefaultBuddy() {
        return defaultBuddy;
    }

    public String getDefaultSaying() {
        return defaultSaying;
    }

    public void setDefaultBuddy(String defaultBuddy) {
        this.defaultBuddy = defaultBuddy;
    }

    public void setDefaultSaying(String defaultSaying) {
        this.defaultSaying = defaultSaying;
    }

}