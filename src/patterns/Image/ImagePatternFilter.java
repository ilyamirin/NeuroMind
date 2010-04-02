package patterns.Image;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public class ImagePatternFilter implements FileFilter {

    public boolean accept(File pathname) {
        if(pathname.isDirectory()) return false;

        Pattern regexp = Pattern.compile("^\\d+\\.jpg$");
        if(!regexp.matcher(pathname.getName()).find()) return false;

        return true;
    }

}
