package org.reactor.smieciopolis;

import static com.google.common.base.CharMatcher.JAVA_LETTER;
import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.fortuna.ical4j.model.ValidationException;

import org.reactor.smieciopolis.reader.PDFCalendarReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Converter {

    private final static Logger LOGGER = LoggerFactory.getLogger(Converter.class);
    private final File targetFolder;

    private PDFCalendarReader pdfCalendarReader;

    public Converter(String pdfFileLocation, String targetFolder) {
        this(new File(pdfFileLocation), new File(targetFolder));
    }

    public Converter(File pdfFileLocation, File targetFolder) {
        this.targetFolder = targetFolder;
        initializePDFReader(pdfFileLocation);
    }

    private ICALGenerator newICALGenerator() {
        return new ICALGenerator(targetFolder);
    }

    private void initializePDFReader(File pdfFileLocation) {
        pdfCalendarReader = new PDFCalendarReader(pdfFileLocation) {

            @Override
            protected void onSiteScheduleParsed(SiteSchedule siteSchedule) {
                onCalendarProcessingStarted(siteSchedule);
                
                ICALGenerator generator = newICALGenerator();
                List<ScheduleEntry> scheduleEntries = siteSchedule.getEntriesOrdered();
                for (ScheduleEntry scheduleEntry : scheduleEntries) {
                    generator.addEntry(scheduleEntry.getDate(), getEntryLabel(scheduleEntry));
                }
                try {
                    File calendarFile = generator.writeToFile(titleToFilename(siteSchedule.getScheduleTitle()));
                    onCalendarFileGenerated(calendarFile);
                } catch (IOException | ValidationException e) {
                    LOGGER.error("An error occured while generating calendar for site schedule: {}",
                        siteSchedule.getScheduleTitle(), e);
                    onCalendarGenerationException(e);
                }
            }
        };
    }

    private String getEntryLabel(ScheduleEntry scheduleEntry) {
        if (scheduleEntry.isCleaning()) {
            return format("%s + czyszczenie", scheduleEntry.getTrashType().getLabel());
        }
        return scheduleEntry.getTrashType().getLabel();
    }

    private String titleToFilename(String scheduleTitle) {
        return JAVA_LETTER.retainFrom(scheduleTitle);
    }

    public void generateCalendarFiles() {
        try {
            pdfCalendarReader.readSchedules();
        } catch (IOException e) {
            LOGGER.error("An error occured while reading schedules from PDF file", e);
            onPDFReadError(e);
        }
    }

    protected void onCalendarProcessingStarted(SiteSchedule siteSchedule) { }

    protected void onCalendarFileGenerated(File calendarFile) { }

    protected void onPDFReadError(IOException e) { }

    protected void onCalendarGenerationException(Exception e) { }

}
