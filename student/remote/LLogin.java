package student.remote;

import java.util.EnumSet;

/**
 *
 * @author haro
 */
public interface LLogin<Permissions extends Enum<Permissions>> {
    public boolean verifyRequest(String uname, byte []token, EnumSet<Permissions> permissions);
    
    public boolean addUser(String user, String pass);
    public boolean addUser(String user, byte []passhash);
    
    public void delUser(String user);
    
    public EnumSet<Permissions> userPermissions(String user);
  
    public void grantPermission(String user, Permissions...p);
    public void revokePermission(String user, Permissions...p);
}
