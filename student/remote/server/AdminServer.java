package student.remote.server;

import java.rmi.RemoteException;
import student.parse.Action;
import static student.remote.login.Permission.ADMIN;

/**
 * An AdminServer exposes the administrative interface to the critter system.
 */
public interface AdminServer extends PlayerServer {

    /**
     * Restart the simulation with the specified world definition.
     *
     * @param worldFileContent The file content of the world definition file
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public void loadWorld(byte []token, String uname, String worldFileContent) throws RemoteException;

    /**
     * Advance the simulation by one step.
     *
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public void simStep(byte []token, String uname) throws RemoteException;

    /**
     * Continuously advance the simulation.
     *
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public void startSim(byte []token, String uname) throws RemoteException;

    /**
     * Pause the simulation.
     *
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public void pauseSim(byte []token, String uname) throws RemoteException;

    /**
     * Reset the world to its original state, free of critters.
     *
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public void resetSim(byte []token, String uname) throws RemoteException;

    //methods for managing the simulation
    /**
     * Set the simulation rate.
     *
     * @param rate Maximum number of steps per minute.
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public void setSimRate(byte []token, String uname, long rate) throws RemoteException;

    /**
     * Remove all critters from the world.
     *
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public void killAll(byte []token, String uname) throws RemoteException;

    /**
     * Remove the specified critter from the world.
     *
     * @param id Critter ID
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public void kill(byte []token, String uname, int id) throws RemoteException;

    /**
     * Set an action a critter should take, overriding regular behavior.
     *
     * @param critter A reference to a critter object
     * @param a The overriding action, or {@code null} if the critter should
     * resume its regular behavior.
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public void control(byte []token, String uname, RemoteCritter critter, Action a) throws RemoteException;

    //Uploads
    @RemoteVisibility(ADMIN)
    public boolean uploadsOn(byte []token, String uname) throws RemoteException;

    @RemoteVisibility(ADMIN)
    public void setCritterUploads(byte []token, String uname, boolean on) throws RemoteException;

    @RemoteVisibility(ADMIN)
    public boolean downloadsOn(byte []token, String uname) throws RemoteException;

    @RemoteVisibility(ADMIN)
    public void setCritterDownloads(byte []token, String uname, boolean on) throws RemoteException;

    /*/
    @RemoteVisibility(ADMIN)
    public String[] listCritterFiles(byte []token, String uname) throws RemoteException;
    /*/
    
    //Methods for managing user credentials
    /**
     * Return the list of players.
     *
     * @return An array containing all usernames of players.
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public String[] getPlayerList(byte []token, String uname) throws RemoteException;

    /**
     * Return the list of pending player requests.
     *
     * @return An array containing all usernames of pending players
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public String[] getPlayerRequests(byte []token, String uname) throws RemoteException;

    /**
     * Approve the player request and add the player to the player list.
     *
     * @param name The username of approved pending player
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN) 
    public void addPlayer(byte []token, String uname, String name) throws RemoteException;

    /**
     * Reject the player request.
     *
     * @param name The username of rejected pending player
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public void rejectPlayer(byte []token, String uname, String name) throws RemoteException;

    /**
     * Remove the player from the player list.
     *
     * @param name The username of player to be removed
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public void removePlayer(byte []token, String uname, String name) throws RemoteException;

    /**
     * Return the list of admins.
     *
     * @return An array containing all usernames of admins.
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public String[] getAdminList(byte []token, String uname) throws RemoteException;

    /**
     * Return the list of pending admin requests.
     *
     * @return An array containing all usernames of pending admins
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public String[] getAdminRequests(byte []token, String uname) throws RemoteException;

    /**
     * Approve the admin request and add the admin to the player list.
     *
     * @param name The username of approved pending admin
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public void addAdmin(byte []token, String uname, String name) throws RemoteException;

    /**
     * Reject the admin request.
     *
     * @param name The username of rejected pending admin
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public void rejectAdmin(byte []token, String uname, String name) throws RemoteException;

    /**
     * Remove the admin from the player list.
     *
     * @param name The username of admin to be removed
     * @throws RemoteException
     */
    @RemoteVisibility(ADMIN)
    public void removeAdmin(byte []token, String uname, String name) throws RemoteException;
}
