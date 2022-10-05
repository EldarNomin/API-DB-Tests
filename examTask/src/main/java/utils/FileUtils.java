package utils;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import enums.Parameters;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    private static final Logger log = AqualityServices.getLogger();

    public static void savePngScreenshot(byte[] file){
        try (FileOutputStream fos = new FileOutputStream(Parameters.SCREENSHOTS_PATH.getValue());
            BufferedOutputStream bos = new BufferedOutputStream(fos)){
            log.debug("Saving screenshot to .png file");
            bos.write(file);
        } catch (IOException e) {
            log.debug(e.getMessage(), "Screenshot cannot be save");
        }
    }
}