package com.madtribe.farcejuggler.intervals;

import java.time.LocalDateTime;

public interface LimitedIntervalSequence extends IntervalSequence {
    LocalDateTime getActualStart() throws Exception;
    LocalDateTime getActualEnd() throws Exception;
}
