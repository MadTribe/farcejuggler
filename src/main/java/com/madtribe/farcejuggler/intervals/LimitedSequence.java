package com.madtribe.farcejuggler.intervals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LimitedSequence extends AbstractIntervalSequence implements LimitedIntervalSequence {
    private final Duration duration;
    private final IntervalSequence intervalSequence;
    private final LocalDateTime startAfter;
    private LocalDateTime actualStart;
    private LocalDateTime actualEnd;
    private List<Interval> intervals = new ArrayList<>();

    public LimitedSequence(LocalDateTime startAfter, Duration duration, IntervalSequence intervalSequence) {
        this.intervalSequence = intervalSequence;
        this.startAfter = startAfter;
        this.duration = duration;
    }

    private void calcEndDate() throws Exception {
        Optional<Interval> availableIntervalOptional = intervalSequence.currentInterval(startAfter);


        if (!availableIntervalOptional.isPresent()) {
            availableIntervalOptional = intervalSequence.nextInterval(startAfter);

            System.err.println("Available interval hours " + availableIntervalOptional.get().getDuration().toHours());
        }




        Duration uncountedTime = this.duration;

        if (availableIntervalOptional.isPresent()) {
            Interval availableInterval= availableIntervalOptional.get();

            if (startAfter.isBefore(availableInterval.getStart())) {
                actualStart = availableInterval.getStart();
            } else {
                actualStart = startAfter;
            }
            Duration durationLeftInInterval = Duration.between(actualStart, availableInterval.getEnd());

            LocalDateTime currentIntervalStart = actualStart;
            LocalDateTime currentIntervalEnd = null;
            while (uncountedTime.getSeconds() > 0) {
                Duration remaining = uncountedTime.minus(durationLeftInInterval);

                if (remaining.isNegative() || remaining.isZero()) {
                    currentIntervalEnd = availableInterval.getStart().plus(uncountedTime);

                } else {
                    currentIntervalEnd = availableInterval.getEnd();

                }

                intervals.add(new Interval(currentIntervalStart, currentIntervalEnd));
                availableInterval = intervalSequence.nextInterval(availableInterval).get();

                if (currentIntervalEnd.isBefore(availableInterval.getStart())) {
                    currentIntervalStart = availableInterval.getStart();
                } else {
                    currentIntervalStart = currentIntervalEnd;
                }

                durationLeftInInterval = Duration.between(availableInterval.getStart(), availableInterval.getEnd());

                uncountedTime = remaining;
            }
        } else {
            throw new Exception("Interval not specified");
        }


        if (intervals.size() > 0) {
            this.actualStart = intervals.get(0).getStart();
            this.actualEnd = intervals.get(intervals.size() - 1).getEnd();
        }
    }

    private void log(String s) {
        System.out.println(s);
    }

    @Override
    public LocalDateTime getActualStart() throws Exception {
        if (actualEnd == null){
            calcEndDate();
        }
        return actualStart;
    }

    @Override
    public LocalDateTime getActualEnd() throws Exception {
        if (actualEnd == null){
            calcEndDate();
        }
        return actualEnd;
    }

    @Override
    public Optional<Interval> currentInterval(LocalDateTime now) {
        return intervalSequence.currentInterval(now);
    }

    @Override
    public Optional<Interval> lastInterval(LocalDateTime now) {
        Optional<Interval> optionalInterval = intervalSequence.lastInterval(now);
        return constrainInterval(optionalInterval);
    }

    private Optional<Interval> constrainInterval(Optional<Interval> optionalInterval) {
        if (optionalInterval.isPresent()){
            Interval pure = optionalInterval.get();
            LocalDateTime lastStart = pure.getStart();
            LocalDateTime lastEnd = pure.getEnd();
            if (pure.getStart().isBefore(this.actualStart)){
                lastStart = actualStart;
            }

            if (pure.getEnd().isAfter(this.actualEnd)){
                lastEnd = actualEnd;
            }

            if(pure.getEnd().isBefore(actualStart) || pure.getStart().isAfter(actualEnd)) {
                return Optional.empty();
            }

            return Optional.of(new Interval(lastStart,lastEnd));
        } else {
            return optionalInterval;
        }
    }

    @Override
    public Optional<Interval> nextInterval(LocalDateTime now) {
        Optional<Interval> optionalInterval = intervalSequence.nextInterval(now);
        return constrainInterval(optionalInterval);
    }

    public List<Interval> getIntervals() {
        return intervals;
    }
}
