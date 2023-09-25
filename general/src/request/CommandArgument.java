package request;
import Organization.Organization;

public class CommandArgument {
    private String keyArgument;
    private Organization organizationArgument;
    public CommandArgument(String keyArgument){
        this.keyArgument=keyArgument;
    }
    public CommandArgument(Organization organizationArgument){
        this.organizationArgument=organizationArgument;
    }
    public CommandArgument(String keyArgument, Organization organizationArgument){
        this.keyArgument=keyArgument;
        this.organizationArgument=organizationArgument;
    }

    public String getKeyArgument(){
        return keyArgument;
    }
    public Organization getOrganizationArgument(){
        return organizationArgument;
    }
}
