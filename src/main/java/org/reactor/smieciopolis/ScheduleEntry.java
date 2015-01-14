package org.reactor.smieciopolis;

import java.util.Date;

public class ScheduleEntry {

    private final Date date;
    private final TrashType trashType;
    private final boolean cleaning;

    public ScheduleEntry(Date date, TrashType trashType, boolean cleaning) {
        this.date = date;
        this.trashType = trashType;
        this.cleaning = cleaning;
    }

    public TrashType getTrashType() {
        return trashType;
    }

    public Date getDate() {
        return date;
    }

    public boolean isCleaning() {
        return cleaning;
    }
}
