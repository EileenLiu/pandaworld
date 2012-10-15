/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

/**
 *
 * @author haro
 */
class Tag extends Action {
    public Tag(Expression ind) {
        super(ind);
    }
    
    public Expression ind() {
        return children.get(0);
    }

    @Override
    public void execute() {
        throw new Error("Can't execute yet!");
    }
}
