package org.reactor.smieciopolis;

public class ScheduledDay {

    private final int dayOfMonth;
    private final boolean cleaning;

    public ScheduledDay(int dayOfMonth, boolean cleaning) {
        this.dayOfMonth = dayOfMonth;
        this.cleaning = cleaning;
    }

    public boolean isCleaning() {
        return cleaning;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }
}
