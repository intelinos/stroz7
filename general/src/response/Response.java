package response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Organization.Organization;

public class Response implements Serializable {
    //private static final long serialVersionUID = 343423422434234L;
    private String responseMessage="";
    private Map<Integer, Organization> responseArgument;
    public Response(String responseMessage){
        this.responseMessage = responseMessage;
    }
    public Response(String responseMessage, Map<Integer, Organization> responseArgument){
        this.responseMessage = responseMessage;
        this.responseArgument = responseArgument;
    }
    public String getResponseMessage() {
        return responseMessage;
    }
    public Map<Integer, Organization> getResponseArgument(){return responseArgument;}
}
