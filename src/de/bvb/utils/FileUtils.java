package de.bvb.utils;

import java.io.File;

public class FileUtils {
    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }

    }

    public static boolean isEmptyDirectory(File file) {
        if (file != null && file.exists() && file.isDirectory()) {
            String[] list = file.list();
            if (list == null || list.length == 0) {
                return true;
            }
        }
        return false;
    }
}
