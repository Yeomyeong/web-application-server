package action;

import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by wymstar on 7/5/16.
 */
public class ActionUtil {
    static Logger log = LoggerFactory.getLogger(ActionUtil.class);

    static void readFile(HttpResponse response, String fileName) {
        BufferedReader reader = getBufferedReader(fileName);

        StringBuilder responseData = new StringBuilder();
        if (reader == null) {
            responseData.append("404 error");
            response.write(responseData.toString());
            return;
        }

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                responseData.append(line + "\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            responseData = new StringBuilder();
            responseData.append("500 error");
        }
        response.write(responseData.toString());

    }

    private static BufferedReader getBufferedReader(String fileName) {
        try {
            return new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
