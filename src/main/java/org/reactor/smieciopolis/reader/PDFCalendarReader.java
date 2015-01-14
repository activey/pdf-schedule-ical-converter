package org.reactor.smieciopolis.reader;

import static org.reactor.smieciopolis.Month.byRowAndCol;
import static org.reactor.smieciopolis.ScheduledDays.fromRowCellText;
import static org.reactor.smieciopolis.TrashType.byRow;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.nerdpower.tabula.ObjectExtractor;
import org.nerdpower.tabula.Page;
import org.nerdpower.tabula.PageIterator;
import org.nerdpower.tabula.RectangularTextContainer;
import org.nerdpower.tabula.Table;
import org.nerdpower.tabula.extractors.SpreadsheetExtractionAlgorithm;
import org.reactor.smieciopolis.Month;
import org.reactor.smieciopolis.ScheduledDays;
import org.reactor.smieciopolis.SiteSchedule;
import org.reactor.smieciopolis.TrashType;

public class PDFCalendarReader {

    private static final String HARMONOGRAM_LINE_DISCRIMINATOR = "HARMONOGRAM";
    private final File pdfFileLocation;

    private int currentPage;
    private int currentRow;
    private int currentCol;
    private Month currentMonth;
    private TrashType currentTrashType;

    public PDFCalendarReader(File pdfFileLocation) {
        this.pdfFileLocation = pdfFileLocation;
    }
    
    public void readSchedules(final int... pageNumbers) throws IOException {
        resetCurrentPage();
        
        PDDocument pdfDocument = PDDocument.load(pdfFileLocation);
        ObjectExtractor objectExtractor = new ObjectExtractor(pdfDocument);
        PageIterator pageIterator = objectExtractor.extract();
        pageIterator.forEachRemaining(new Consumer<Page>() {

            @Override
            public void accept(Page page) {
                if (!isCurrentPageInRange(pageNumbers)) {
                    currentPage++;
                    return;
                }
                resetCurrentRow();
                SiteSchedule singleSiteSchedule = createScheduleFromPage(page);
                onSiteScheduleParsed(singleSiteSchedule);
                
                currentPage++;
            }
        });
    }
    
    private boolean isCurrentPageInRange(int... pageNumbers) {
        if (pageNumbers.length == 0) {
            return true;
        }
        for (int pageNumber : pageNumbers) {
            if (currentPage + 1 == pageNumber) {
                return true;
            }
        }
        return false;
    }
    
    protected void onSiteScheduleParsed(SiteSchedule siteSchedule) {

    }

    private SiteSchedule createScheduleFromPage(Page page) {
        SiteSchedule siteSchedule = new SiteSchedule(readScheduleTitle(page));
        SpreadsheetExtractionAlgorithm extractionAlgorithm = new SpreadsheetExtractionAlgorithm();
        List<? extends Table> tables = extractionAlgorithm.extract(page);
        for (Table siteScheduleTable : tables) {
            fillSiteScheduleFromTable(siteSchedule, siteScheduleTable);
            return siteSchedule;
        }
        return siteSchedule;
    }

    private String readScheduleTitle(Page page) {
        PDFPageTextReader textReader = new PDFPageTextReader(page);
        String textLine = textReader.readLine(2);
        if (isHarmonogramLine(textLine)) {
            return textReader.readLine(4);
        }
        return textLine;        
    }

    private boolean isHarmonogramLine(String textLine) {
        return textLine.startsWith(HARMONOGRAM_LINE_DISCRIMINATOR);
    }

    private void fillSiteScheduleFromTable(SiteSchedule siteSchedule, Table siteScheduleTable) {
        List<List<RectangularTextContainer>> rows = siteScheduleTable.getRows();
        for (List<RectangularTextContainer> row : rows) {
            fillSiteScheduleFromTableRow(siteSchedule, row);
            currentRow++;
        }
    }

    private void fillSiteScheduleFromTableRow(SiteSchedule siteSchedule, List<RectangularTextContainer> scheduleTableRow) {
        resetCurrentCol();
        for (RectangularTextContainer rowCell : scheduleTableRow) {
            Month month = byRowAndCol(currentRow, currentCol);
            if (month == null) {
                continue;
            }
            currentMonth = month;
            
            TrashType trashType = byRow(currentRow);
            if (trashType != null) {
                currentTrashType = trashType;

                if (isDaysCell()) {
                    ScheduledDays scheduledDays = fromRowCellText(rowCell.getText());
                    siteSchedule.addScheduleEntry(currentMonth, scheduledDays, currentTrashType);
                }
            }
            currentCol++;
        }
    }

    private boolean isDaysCell() {
        return currentCol % 2 == 1;
    }

    private void resetCurrentPage() {
        currentPage = 0;
    }
    
    private void resetCurrentRow() {
        currentRow = 0;
    }

    private void resetCurrentCol() {
        currentCol = 0;
    }
}
