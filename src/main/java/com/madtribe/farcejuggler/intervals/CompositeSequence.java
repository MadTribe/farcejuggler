package com.madtribe.farcejuggler.intervals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Intervals are first subtracted from the base, then additions are added.
 */
public class CompositeSequence extends AbstractIntervalSequence {

    private IntervalSequence baseSequence;
    private List<IntervalSequence> additions = new ArrayList<>();
    private List<IntervalSequence> subtractions = new ArrayList<>();

    public CompositeSequence(IntervalSequence baseSequence) {
        this.baseSequence = baseSequence;
    }

    public void add(IntervalSequence add){
        additions.add(add);
    }

    public void subtract(IntervalSequence sub){
        subtractions.add(sub);
    }

    @Override
    public Optional<Interval> currentInterval(LocalDateTime now) {
        Optional<Interval> returnVal = baseSequence.currentInterval(now);
        if (returnVal.isPresent()){
            for (IntervalSequence neg : subtractions) {
                if (neg.currentInterval(now).isPresent()){
                    returnVal = Optional.empty();
                }
            }
        } else {
            for (IntervalSequence add : additions) {
                Optional<Interval> extra = add.currentInterval(now);

                if (extra.isPresent()){
                    returnVal = extra;
                }
            }
        }

        return returnVal;
    }

    @Override
    public Optional<Interval> lastInterval(LocalDateTime now) {
        Optional<Interval> returnVal = baseSequence.lastInterval(now);


        //Optional<Interval>  = additions.lastInterval(now);

        return returnVal;
    }

    @Override
    public Optional<Interval> nextInterval(LocalDateTime now) {
        Optional<Interval> returnVal = baseSequence.nextInterval(now);

        return returnVal;
    }
}
