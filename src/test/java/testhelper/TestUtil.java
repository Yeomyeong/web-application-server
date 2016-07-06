package testhelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by aymonwoo on 2016. 7. 6..
 */
public class TestUtil {
    public static String readFile(String filename) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder fileText = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                fileText.append(line + "\r\n");
            }
        } catch (IOException e) {
        }

        return fileText.toString();
    }
}
