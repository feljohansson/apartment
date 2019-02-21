package abs.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/*
        A time span that an apartment may be occupied or ... if not,
        possible to book.
 */

public class Period {
    private final LocalDateTime start;
    private final LocalDateTime end;

    // Assume input correct i.e. end after start
    public Period(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    // String like:  2018-01-12X2018-01-16 (X used because many chars troublesome in URL's)
    public Period(String periodString) {
        String[] ps = periodString.split("X");
        String start = ps[0] + "T14:00:00";
        String end = ps[1] + "T10:00:00";
        this.start = LocalDateTime.parse(start);
        this.end = LocalDateTime.parse(end);
    }

    public long getNDays() {
        // +2 because of inclusive start and end
        return ChronoUnit.DAYS.between(start, end) + 2;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public boolean overlaps(Period other) {
         // TODO   Hint: start.isBefore()
        return start.isBefore(other.end) && other.start.isBefore(end);
    }

    // To get a useful output. By using this we don't need a lot of getters
    // to be able to display start, end n the web pages (NOT used in real life)
    @Override
    public String toString() {
        return "{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
