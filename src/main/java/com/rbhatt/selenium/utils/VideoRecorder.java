package com.rbhatt.selenium.utils;

import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class VideoRecorder {

    private CustomScreenRecorder screenRecorder;

    /**
     * Starts video recording.
     *
     * @param methodName The test method name for unique filename.
     * @throws Exception If screen recording fails.
     */
    public void startRecording(String methodName) throws Exception {
        File videosDir = new File("videos");
        if (!videosDir.exists()) {
            videosDir.mkdir(); // Create the videos folder id it doesn't exist
        }

        // Sanitize the scenario name to ensure it can be used as a valid file name
        String sanitizedFileName = sanitizeFileName(methodName);

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        screenRecorder = new CustomScreenRecorder(
                gc,
                gc.getBounds(),
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_MJPG, CompressorNameKey, ENCODING_AVI_MJPG,
                        DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                        QualityKey, 1.0f, KeyFrameIntervalKey, 15),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                null,
                videosDir,
                sanitizedFileName);
        screenRecorder.start();
    }

    /**
     * Stops video recording.
     *
     * @throws Exception If stopping screen recording fails.
     */
    public void stopRecording() throws Exception {
        if (screenRecorder != null) {
            screenRecorder.stop();
        }
    }

    /**
     * Sanitizes the file name by replacing invalid characters.
     *
     * @param name The original file name.
     * @return The sanitized file name.
     */
    private String sanitizeFileName(String name) {
        return name.replaceAll("[^a-zA-Z0-9_]", "_");
    }
}
