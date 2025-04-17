/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strikethefrog.utils;

import java.io.File;
import java.sql.Timestamp;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ASUS
 */
public class TournamentTools {

    public boolean isStartTimeAfterEndTime(Timestamp startTime, Timestamp endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and end time must not be null.");
        }

        return startTime.after(endTime);
    }

    public void deleteExistingImgFile(String imgFilePath, ServletContext context) {
    if (imgFilePath != null && !imgFilePath.trim().isEmpty()) {
        String fullPath = context.getRealPath("") + File.separator + imgFilePath;
        File existingFile = new File(fullPath);
        if (existingFile.exists()) {
            if (existingFile.delete()) {
                System.out.println("Successfully deleted existing file: " + fullPath);
            } else {
                System.out.println("Failed to delete existing file: " + fullPath);
            }
        }
    }
}


}
