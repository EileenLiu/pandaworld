package student.remote.login;

import java.util.EnumSet;

/**
 *
 * @author haro
 */
public interface LLogin {
    public boolean verifyRequest(String uname, byte []token, Permission permission);
    
    public boolean addUser(String user, String pass);
    public boolean addUser(String user, byte []passhash);
    
    public void delUser(String user);
    
    public EnumSet<Permission> userPermissions(String user);
  
    public void grantPermission(String user, Permission...p);
    public void revokePermission(String user, Permission...p);
}
