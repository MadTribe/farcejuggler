package com.madtribe.farcejuggler.intervals;

import java.util.Optional;

public abstract class AbstractIntervalSequence implements IntervalSequence {

    @Override
    public Optional<Interval> nextInterval(Interval now) {
        return nextInterval(now.getEnd());
    }

    @Override
    public Optional<Interval> lastInterval(Interval now) {
        return lastInterval(now.getStart());
    }
}
