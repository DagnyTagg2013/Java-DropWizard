package com.dagtagg2013.warmuprest.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.QueryParam;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: daphneeng
 * Date: 11/7/13
 * Time: 5:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Food {

    @JsonProperty
    String description;

    @JsonProperty
    BigDecimal weight;

    public Food(String description, BigDecimal weight) {
        this.description = description;
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getWeight() {
        return weight;
    }
}
