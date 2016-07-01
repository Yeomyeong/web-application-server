package action;

import http.Request;

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
    public String act(Request request) {
        BufferedReader reader = getFileReader(request);
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

    private BufferedReader getFileReader(Request request) {
        BufferedReader reader = null;
        try {
            String fileName = getFileName(request);
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return reader;
    }

    private String getFileName(Request request) {
        if (isEmpty(request.getPath())) {
            return "./webapp/index.html";
        }
        return  "./webapp" + request.getPath();
    }
}
