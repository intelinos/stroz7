package utility;

import response.Response;

import java.util.ArrayList;
import java.util.List;

public class ResponseList {
    private List<Response> responseList = new ArrayList<>();
    public static List<Response> responseScriptList;
    public ResponseList(Response response) {
        responseList.add(response);
    }

    public List<Response> getResponseList() {
        return responseList;
    }
    public void saveScriptList(){
        responseList = responseScriptList;
    }
}
