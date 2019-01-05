package com.madtribe.farcejuggler.core;

import com.madtribe.farcejuggler.intervals.DailyIntervalSequence;
import com.madtribe.farcejuggler.intervals.Interval;
import com.madtribe.farcejuggler.intervals.LimitedSequence;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalDateTime.of;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LimitedSequenceTest {

    @Test
    public void schedule_9hourTaskAt3hoursPerDay_endDate3daysLater() throws Exception {
        LocalDateTime startDate = of(2018,11,15, 0, 0);

        LimitedSequence scheduled = new LimitedSequence(startDate,Duration.ofHours(9),new DailyIntervalSequence(11,30, 14, 30) );

        assertThat( scheduled.getActualEnd(), equalTo(of(2018,11,17, 14, 30)));
        assertThat(scheduled.getActualStart(), equalTo(of(2018,11,15, 11, 30)));

        Optional<Interval> next1 = scheduled.nextInterval(startDate);
        assertThat(next1.get().getStart(), equalTo(of(2018,11,15,11,30)));
        assertThat(next1.get().getEnd(), equalTo(of(2018,11,15,14,30)));

        Optional<Interval> next2 = scheduled.nextInterval(of(2018,11,15,14,30));
        assertThat(next2.get().getStart(), equalTo(of(2018,11,16,11,30)));
        assertThat(next2.get().getEnd(), equalTo(of(2018,11,16,14,30)));

        Optional<Interval> next3 = scheduled.nextInterval(of(2018,11,16,14,25));
        assertThat(next3.get().getStart(), equalTo(of(2018,11,17,11,30)));
        assertThat(next3.get().getEnd(), equalTo(of(2018,11,17,14,30)));

        Optional<Interval> next4 = scheduled.nextInterval(of(2018,11,17,15,30));
        assertThat(next4.isPresent(), is(false));

        Optional<Interval> next5 = scheduled.lastInterval(startDate);
        assertThat(next5.isPresent(), is(false));

    }

    @Test
    public void schedule_6hourTaskBrokenOver2days() throws Exception {
        LocalDateTime startDate = of(2018,11,15, 15, 0);
        LimitedSequence scheduled = new LimitedSequence(
                startDate,
                Duration.ofHours(6),
                new DailyIntervalSequence(
                        9,0, 17, 0
                )
        );

        final LocalDateTime actualEnd = scheduled.getActualEnd();

        assertThat(actualEnd, equalTo(of(2018,11,16, 13, 0)));
    }

}