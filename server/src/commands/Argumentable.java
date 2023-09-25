package commands;

import Organization.Organization;
import request.CommandArgument;

public interface Argumentable {
    CommandArgument getCommandArgument();
    //void setCommandArgument(String key);
    //  void setCommandArgument(Organization organization);
    //void setCommandArgument(String key, Organization organization);
    void setCommandArgument(CommandArgument commandArgument);
}

