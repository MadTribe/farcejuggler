package com.madtribe.farcejuggler.intervals;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IntervalSequence {
    /**
     *
     * @param now
     * @return empty if now is not in an interval within this sequence
     */
    Optional<Interval> currentInterval(LocalDateTime now);
    Optional<Interval> lastInterval(LocalDateTime now);
    Optional<Interval> nextInterval(LocalDateTime now);
    Optional<Interval> nextInterval(Interval now);
    Optional<Interval> lastInterval(Interval now);

}
