package org.reactor.smieciopolis;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ScheduledDays {

    private static final String CLEANING_MARKER = "*";
    private static final String DAYS_SEPARATOR = ",";
    private static final String EMPTY_FIELD_UNDERSCORE = "_";

    public static ScheduledDays fromRowCellText(String rowCellText) {
        ScheduledDays scheduledDays = new ScheduledDays();
        String[] daysStrings = rowCellText.split(DAYS_SEPARATOR);
        for (int index = 0; index < daysStrings.length; index++) {
            String dayString = daysStrings[index].trim();
            if (dayString.endsWith(CLEANING_MARKER)) {
                dayString = dayString.substring(0, dayString.length() - 1);
                scheduledDays.addScheduledDay(parseInt(dayString), true);
                continue;
            } else if (isEmpty(dayString)) {
                continue;
            }
            scheduledDays.addScheduledDay(parseInt(dayString), false);
        }
        return scheduledDays;
    }

    private static boolean isEmpty(String dayString) {
        return dayString.length() == 0 || dayString.equals(EMPTY_FIELD_UNDERSCORE);
    }

    private final List<ScheduledDay> scheduledDays = new ArrayList<>();

    public void addScheduledDay(int dayOfMonth, boolean cleaning) {
        scheduledDays.add(new ScheduledDay(dayOfMonth, cleaning));
    }
    
    public List<ScheduledDay> getDays() {
        return scheduledDays;
    }
}
