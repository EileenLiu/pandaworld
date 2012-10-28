/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

/**
 *
 * @author haro
 */
public class World {
    HexGrid<Entity> world;
    int siz;
    
    public World(int _siz) {
        siz = _siz;
        world = new ArrayHexGrid<Entity>(siz);
    }
    
}
