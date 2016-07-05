package action;

import http.HttpRequest;
import http.HttpResponse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static action.ActionUtil.readFile;
import static util.StringUtil.isEmpty;

/**
 * Created by wymstar on 7/1/16.
 */
public class StaticFileReadAction implements Action {

    @Override
    public void act(HttpRequest httpRequest, HttpResponse response) {
        readFile(response, getFileName(httpRequest) );
    }

    private String getFileName(HttpRequest httpRequest) {
        String path = httpRequest.getPath().replaceAll("^[/]", "");

        if ( isEmpty(path)) {
            return "./webapp/index.html";
        }
        return  "./webapp/" + path;
    }
}
