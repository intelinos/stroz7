package request;
import Organization.Organization;

public class CommandArgument {
    private String login;
    private String password;
    private String keyArgument;
    private Organization organizationArgument;
    public CommandArgument(String login, String password){
        this.login=login;
        this.password=password;
    }
    public CommandArgument(String keyArgument, String login, String password){
        this.keyArgument=keyArgument;
        this.login=login;
        this.password=password;
    }
    public CommandArgument(Organization organizationArgument, String login, String password){
        this.organizationArgument=organizationArgument;
        this.login=login;
        this.password=password;
    }
    public CommandArgument(String keyArgument, Organization organizationArgument, String login, String password){
        this.keyArgument=keyArgument;
        this.organizationArgument=organizationArgument;
        this.login=login;
        this.password=password;
    }

    public String getKeyArgument(){
        return keyArgument;
    }
    public Organization getOrganizationArgument(){
        return organizationArgument;
    }
    public String getLogin(){return login;}
    public String getPassword(){return password;}
}
