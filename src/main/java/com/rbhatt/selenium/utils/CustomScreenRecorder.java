package com.rbhatt.selenium.utils;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class CustomScreenRecorder extends ScreenRecorder {

    private final String fileName;

    /**
     * Custom ScreenRecorder constructor to specify the scenario name as the file name.
     *
     * @param cfg           Graphics configuration for screen capture.
     * @param captureArea   The screen area to capture.
     * @param fileFormat    The file format for the video.
     * @param screenFormat  The video encoding format.
     * @param mouseFormat   The mouse cursor encoding format.
     * @param audioFormat   The audio encoding format.
     * @param movieFolder   The directory where videos will be saved.
     * @param fileName      The desired name for the video file.
     * @throws Exception If initialization fails.
     */
    public CustomScreenRecorder(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat,
                                Format mouseFormat, Format audioFormat, File movieFolder, String fileName) throws Exception {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        // Add date and timestamp to the file name
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        this.fileName = fileName + "_" + timestamp;;
    }

    @Override
    protected File createMovieFile(Format fileFormat) {
        return new File(movieFolder, fileName + ".avi"); // Use the scenario name
    }
}
