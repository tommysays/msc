import java.io.File;
import java.net.URL;
import static org.apache.commons.io.FileUtils.copyURLToFile;
/**
 *
 * @author Kevin
 */
public class mp3downloader {
    public static void download(URL url, String filename) {
        try {
            File destination = new File(filename);
            copyURLToFile(url, destination);
        } catch (Exception e) {
            System.out.println("Unable to open file to save to");
        }
    }
    
}
