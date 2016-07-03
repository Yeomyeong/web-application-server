package action;

import http.HttpRequest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static util.StringUtil.isEmpty;

/**
 * Created by wymstar on 7/1/16.
 */
public class StaticFileReadAction implements Action {

    @Override
    public String act(HttpRequest httpRequest) {
        BufferedReader reader = getFileReader(httpRequest);
        StringBuilder responseData = readFile(reader);
        return responseData.toString();
    }

    private StringBuilder readFile(BufferedReader reader) {
        StringBuilder responseData = new StringBuilder();
        if (reader == null) {
            responseData.append("404 error");
            return responseData;
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
        return responseData;
    }

    private BufferedReader getFileReader(HttpRequest httpRequest) {
        BufferedReader reader = null;
        try {
            String fileName = getFileName(httpRequest);
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return reader;
    }

    private String getFileName(HttpRequest httpRequest) {
        if (isEmpty(httpRequest.getPath())) {
            return "./webapp/index.html";
        }
        return  "./webapp" + httpRequest.getPath();
    }
}
