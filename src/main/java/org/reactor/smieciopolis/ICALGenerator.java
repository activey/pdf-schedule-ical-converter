package org.reactor.smieciopolis;

import static java.io.File.createTempFile;
import static net.fortuna.ical4j.model.Property.DTSTART;
import static net.fortuna.ical4j.model.parameter.Value.DATE;
import static net.fortuna.ical4j.model.property.CalScale.GREGORIAN;
import static net.fortuna.ical4j.model.property.Version.VERSION_2_0;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Date;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.util.UidGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ICALGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ICALGenerator.class);

    private static final String GENERATOR = "-//activey//iCal4j 1.0//EN";
    private static final String PID = "1234567890";
    private static final String CALENDAR_EXTENSION = ".ics";
    private final File targetFolder;

    private Calendar calendar;
    private UidGenerator uidGenerator;

    public ICALGenerator(File targetFolder) {
        this.targetFolder = targetFolder;
        initializeCalendar();
        try {
            initializeUidGenerator();
        } catch (SocketException e) {
            LOGGER.error("An error occurred while initializing UID generator", e);
        }
    }

    public void addEntry(Date date, String entryLabel) {
        VEvent event = new VEvent(new net.fortuna.ical4j.model.Date(date.getTime()), entryLabel);
        PropertyList properties = event.getProperties();
        properties.add(uidGenerator.generateUid());
        
        calendar.getComponents().add(event);
    }

    private void initializeCalendar() {
        calendar = new Calendar();
        PropertyList calendarProperties = calendar.getProperties();
        calendarProperties.add(new ProdId(GENERATOR));
        calendarProperties.add(VERSION_2_0);
        calendarProperties.add(GREGORIAN);
    }

    private void initializeUidGenerator() throws SocketException {
        uidGenerator = new UidGenerator(PID);
    }

    public File writeToFile(String fileName) throws IOException, ValidationException {
        File tempFile = createTempFile(fileName, CALENDAR_EXTENSION, targetFolder);
        CalendarOutputter outputter = new CalendarOutputter();
        OutputStream stream = new FileOutputStream(tempFile);
        outputter.output(calendar, stream);
        stream.close();
        return tempFile;
    }
}
