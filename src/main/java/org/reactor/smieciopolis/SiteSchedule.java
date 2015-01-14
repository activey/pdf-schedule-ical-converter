package org.reactor.smieciopolis;

import java.time.Instant;
import java.util.*;

public class SiteSchedule {

    private final String scheduleTitle;
    private List<ScheduleEntry> scheduleEntries = new ArrayList<ScheduleEntry>();
    
    public SiteSchedule(String scheduleTitle) {
        this.scheduleTitle = scheduleTitle;
    }

    public void addScheduleEntry(Month month, ScheduledDays scheduledDays, TrashType trashType) {
        int monthValue = month.getMonthValue();
        List<ScheduledDay> days = scheduledDays.getDays();
        for (ScheduledDay day : days) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, monthValue);
            calendar.set(Calendar.DAY_OF_MONTH, day.getDayOfMonth());
            scheduleEntries.add(new ScheduleEntry(calendar.getTime(), trashType, day.isCleaning()));
        }
    }
    
    public List<ScheduleEntry> getEntriesOrdered() {
        scheduleEntries.sort(new Comparator<ScheduleEntry>() {
            @Override
            public int compare(ScheduleEntry entry1, ScheduleEntry entry2) {
                return entry1.getDate().compareTo(entry2.getDate());
            }
        });
        return scheduleEntries;
    }
    
    public String getScheduleTitle() {
        return scheduleTitle;
    }
}
