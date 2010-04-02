package patterns.Image;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public class ImagePatternDirFilter implements FileFilter {

    public boolean accept(File pathname) {
        if(pathname.isFile()) return false;

        Pattern regexp = Pattern.compile("^\\d+$");
        if(!regexp.matcher(pathname.getName()).find()) return false;
        
        if(pathname.listFiles(new ImagePatternFilter()).length == 0)return false;

        return true;
    }

}
