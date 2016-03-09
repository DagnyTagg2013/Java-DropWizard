package com.dagtagg2013.warmuprest.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: daphneeng
 * Date: 11/15/13
 * Time: 5:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Baby {

    @JsonProperty
    private long id;

    @JsonProperty
    private AtomicLong countSayings;

    @JsonProperty
    private Saying latestSaying;

    @JsonProperty
    // private BigDecimal weightInPounds;
    private String weightInPounds;

    @JsonProperty
    private Boolean isDiaperDirty;

    public Baby(){
    }

    public Baby(long id, AtomicLong countSayings, String latestSaying, BigDecimal poundsWeight, Boolean isDiaperDirty) {

        this.id = id;
        this.countSayings = countSayings;
        this.latestSaying = new Saying(1L, latestSaying);
        this.weightInPounds = poundsWeight.toPlainString();
        this.isDiaperDirty = isDiaperDirty;

    }

    public long getId() {
        return id;
    }

    public AtomicLong getCountSayings() {
        return countSayings;
    }

    public Saying getLatestSaying() {
        return latestSaying;
    }

    public String getWeightInPounds() {
        return weightInPounds;
    }

    public Boolean getIsDiaperDirty() {
        return isDiaperDirty;
    }

}
