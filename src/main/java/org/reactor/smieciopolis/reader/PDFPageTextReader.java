package org.reactor.smieciopolis.reader;

import org.nerdpower.tabula.Page;
import org.nerdpower.tabula.TextElement;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class PDFPageTextReader {

    private final Page pdfPage;
    private double lastY;
    private StringBuffer lineBuffer = new StringBuffer();

    public PDFPageTextReader(Page pdfPage) {
        this.pdfPage = pdfPage;
    }

    public List<String> readLines(int... lineNumbers) {
        List<String> lines = newArrayList();
        List<TextElement> textElements = pdfPage.getText();
        int lineNumber = 1;
        for (TextElement textElement : textElements) {
            double newY = textElement.getY();
            if (newY != lastY && lastY > 0) {
                if (isLineInRange(lineNumber, lineNumbers)) {
                    lines.add(lineBuffer.toString());
                }
                lineBuffer = new StringBuffer();
                lineNumber++;
            }
            lastY = newY;
            lineBuffer.append(textElement.getText());
        }
        return lines;
    }

    public String readLine(int line) {
        List<String> lines = readLines(line);
        return lines.iterator().next();
    }

    private boolean isLineInRange(int line, int... lines) {
        if (lines.length == 0) {
            return true;
        }
        for (int lineNumber : lines) {
            if (line == lineNumber) {
                return true;
            }
        }
        return false;
    }
}
