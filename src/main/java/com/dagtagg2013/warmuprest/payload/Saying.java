package com.dagtagg2013.warmuprest.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: daphneeng
 * Date: 10/29/13
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */

public class Saying {

    @JsonProperty
    private final long id;

    @JsonProperty
    private final String content;

    public Saying(){
        id = 0;
        content = "DEFAULT";
    }

    public Saying(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}

