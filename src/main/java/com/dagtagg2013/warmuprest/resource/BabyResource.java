package com.dagtagg2013.warmuprest.resource;

/**
 * Created with IntelliJ IDEA.
 * User: daphneeng
 * Date: 10/29/13
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */

import com.dagtagg2013.warmuprest.payload.Baby;
import com.dagtagg2013.warmuprest.payload.Food;
import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import com.dagtagg2013.warmuprest.payload.Saying;

@Path("/baby")
@Produces(MediaType.APPLICATION_JSON)
public class BabyResource {

    // TOREVIEW:  start with ONE Singleton Baby; LATER handle MULTIPLEs
    private AtomicLong latestId;
    private AtomicLong countSayings;
    private Saying latestSaying;
    // TOREVIEW:  thread-safe modification of this
    private BigDecimal weightInPounds;
    private boolean isDiaperDirty;

    private final String defaultBuddy;
    private final String defaultSaying;

    private static final BigDecimal DEFAULT_BABY_WEIGHT_IN_POUNDS = new BigDecimal(18);
    private static final BigDecimal DEFAULT_MEAL_WEIGHT_IN_POUNDS = new BigDecimal(0.25);
    private static final String HAPPY_EAT_SAYING = "Yummy, Mama!";
    private static final String UNHAPPY_EAT_SAYING = "Biyau!";

    private static final String HAPPY_CHANGED_SAYING = "Diaper, off; Thank you, mama!";

    public BabyResource(String configDefaultBuddy, String configDefaultSaying) {

        this.latestId = new AtomicLong(1L);
        this.countSayings = new AtomicLong(0L);
        this.weightInPounds = BabyResource.DEFAULT_BABY_WEIGHT_IN_POUNDS;
        this.isDiaperDirty = false;

        this.defaultBuddy = configDefaultBuddy;
        this.defaultSaying = configDefaultSaying;

        this.latestSaying = new Saying(1L, this.defaultSaying);

    }

    @GET
    @Timed
    @Path("/talk")
    public Saying talk(@QueryParam("person") Optional<String> person, @QueryParam("saying") Optional<String> saying) {

        String phrase = saying.or(defaultSaying);
        String buddy = person.or(defaultBuddy);
        StringBuilder finalSaying = new StringBuilder(phrase);
        finalSaying.append(", ");
        finalSaying.append(buddy);
        finalSaying.append('!');

        this.latestSaying = new Saying(countSayings.incrementAndGet(),
                finalSaying.toString());

        return this.latestSaying;

    }

    @GET
    @Timed
    @Path("/view")
    public Baby getCurrentBaby() {

        Baby currentBaby = new Baby(this.getId(), this.getCountSayings(), this.getLatestSaying().getContent(), this.getWeightInPounds(), this.isDiaperDirty());

        return currentBaby;
    }

    public long getId() {

        return this.latestId.get();

    }

    public BigDecimal getWeightInPounds() {
        return weightInPounds;
    }

    public Saying getLatestSaying() {
        return latestSaying;
    }



    public String getDefaultBuddy() {
        return defaultBuddy;
    }

    public String getDefaultSaying() {
        return defaultSaying;
    }

    public AtomicLong getCountSayings() {
        return countSayings;
    }

    public boolean isDiaperDirty() {
        return isDiaperDirty;
    }

    // DENG NOTE:  issue here is that REST annotations for Optional query parameters are not applied to a regular POJO method like this which is
    //             otherwise invoked on Resource Healthcheck; so this code replication needs to get fixed from the overloaded talk(...) method with GET
    public Saying talk(String person, String saying) {

        // DENG TODO:  check with Guava APIs for similar check
        if ( (person == null) || person.isEmpty()) { person = defaultBuddy; }
        if ( (saying == null) || saying.isEmpty()) { saying = defaultSaying; }

        return new Saying(countSayings.incrementAndGet(),
                          defaultSaying.format(defaultBuddy));
    }

    // DENG NOTE:  this is an idempotent REPLACE PART semantic, NOT WHOLE
    @PUT
    @Timed
    @Path("/change-diaper")
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized Saying change(final Saying changedSaying) {

        this.isDiaperDirty = false;

        this.latestSaying =  new Saying(countSayings.incrementAndGet(),
                                        changedSaying.getContent());

        return this.latestSaying;

    }

    // DENG NOTE: using incremental update semantic; in THREAD-SAFE manner
    // ALSO assume Food will be JSON content or file payload into POST command!
    // TODO:  review READs on contended data also for serialized non-stale views
    @POST
    @Timed
    @Path("/feed")
    public synchronized Saying feed(@QueryParam("food") Food food) {

        Saying eatingResponse = null;

        // tests if food weight is BELOW minimum permissible value, but no CAP!
        // if ((food.getWeight().compareTo(new BigDecimal(0.00)) == 1) && (food.getWeight().compareTo(BabyResource.DEFAULT_MEAL_WEIGHT_IN_POUNDS) == -1)) {
        if ((food.getWeight().compareTo(BabyResource.DEFAULT_MEAL_WEIGHT_IN_POUNDS) == -1)) {
            this.weightInPounds = this.weightInPounds.add(food.getWeight());
            this.latestSaying =  new Saying(countSayings.incrementAndGet(), BabyResource.HAPPY_EAT_SAYING);
        }  else {
            this.latestSaying =  new Saying(countSayings.incrementAndGet(), BabyResource.UNHAPPY_EAT_SAYING);
        }

        return this.latestSaying;
    }

}
