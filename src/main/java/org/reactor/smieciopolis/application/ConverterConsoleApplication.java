package org.reactor.smieciopolis.application;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.reactor.smieciopolis.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConverterConsoleApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterConsoleApplication.class);

    private static final String CONVERTER_CMDLINE = "converter-cmdline";
    private static final String DEFAULT_TARGET_FOLDER = ".";
    private static final String OPT_INPUT_PDF = "i";
    private static final String OPT_OUTPUT_FOLDER = "o";
    private Options cmdLineOptions;

    public static void main(String[] args) {
        new ConverterConsoleApplication().run(args);
    }

    private ConverterConsoleApplication() {
        initializeCommandLine();
    }

    private void initializeCommandLine() {
        cmdLineOptions = new Options();
        Option inputOption = new Option(OPT_INPUT_PDF, "inputPDF", true, "PDF file with calendars schedule.");
        inputOption.setRequired(true);
        cmdLineOptions.addOption(inputOption);
        cmdLineOptions.addOption(new Option(OPT_OUTPUT_FOLDER, "outputFolder", true, "Folder location when generated calendars will be placed in."));
    }

    private void run(String[] args) {
        try {
            CommandLine commandLine = parseCommandLine(args);
            new Converter(commandLine.getOptionValue(OPT_INPUT_PDF), commandLine.getOptionValue(OPT_OUTPUT_FOLDER,
                DEFAULT_TARGET_FOLDER)) {

                @Override
                protected void onCalendarFileGenerated(File calendarFile) {
                    LOGGER.debug("Generated calendar file: {}", calendarFile.getAbsolutePath());
                }
            }.generateCalendarFiles();
            LOGGER.debug("Finished processing.");
        } catch (ParseException e) {
            printUsage();
            return;
        }
    }

    private CommandLine parseCommandLine(String[] args) throws ParseException {
        return new PosixParser().parse(cmdLineOptions, args);
    }

    private void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(CONVERTER_CMDLINE, cmdLineOptions, true);
    }
}
