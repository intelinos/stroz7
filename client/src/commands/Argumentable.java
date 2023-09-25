package commands;

import Organization.Organization;
import request.CommandArgument;

public interface Argumentable {
    CommandArgument getCommandArgument();
    void setCommandArgument(String login, String password);
    void setCommandArgument(String key, String login, String password);
    void setCommandArgument(Organization organization, String login, String password);
    void setCommandArgument(String key, Organization organization, String login, String password);
}
