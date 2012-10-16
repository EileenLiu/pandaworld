/**
 *
 */
package student;

import java.util.Arrays;
import java.util.LinkedList;

public class FaultInjector {
    /**
     * Injects a fault for the given node in the given program
     * @param n the given node
     * @param root the given program
     * @return the resultant AST
     */
    public static Node injectFault(Node n, Program root) {
        //int index = (int)(Math.random()*ORIGINAL.length);
        //Node[] arr = n.toArray();
        //int i = (int)(Math.random()*arr.length);
        LinkedList<Integer> faultType = new LinkedList();
        faultType.addAll(Arrays.asList(1,2,3,4,5));
        int i;      
        while (!faultType.isEmpty()){ //while there are still fault types to try...
            i = (int)(Math.random()*faultType.size()); 
            switch (faultType.get(i)) {
                case 1: //the node is removed. if its parent node needs a replacement node, one of its children of the right kind is used. The child to be used is randomly selected. Thus rule nodes are simply removed, but binary operation nodes would be replaced with either their left or right child
                    if(n.getParent().deleteChild(n))
                    {
                        n.setMutationType(1);
                        return root;
                    }
                    break;
                case 2: //order of two children of the node is switched. for ex. allows swapping of positions of two rules
                    if (n.numChildren() >= 2) {
                        n.swapChildren();
                        n.setMutationType(2);
                        return root;
                    }
                    break;
                case 3: //the node and its children are replaced with a copy of another randomly selected node of the right kind, found somewhere in the rule set. the entire AST subtree rooted at the selected node is copied
                    /*Node pointer = n;
                    while (pointer.getParent().getParent() != null) //finds the topmost node in the AST
                    {
                        pointer = pointer.getParent();
                    }*/
                    //find selected
                    //Node selected = randomNode(pointer, n.getClass());
                    Node selected = randomNode(root, n.getClass());
                    if (selected != null) {
                        n.set(selected.copy());
                        n.setMutationType(3);
                        return root;
                    }
                    break;
                case 4:// the node is replaced with a randomly chosen node of the same kind( for example, replacing attack with eat, or + with *) but its children remain the same. Literal integer constants are adjusted up or down with the value of java.lang.Integer.MAX_VALUE/r.nextInt(), where legal, and where r is a java.util.random obj
                    //cannot: update, condition, node, program, rule				
                    if (n.randomize()) {
                        n.setMutationType(4);
                        return root;
                    }
                    break;
                case 5: // a newly created node is inserted as the parent of the node, taking its place in the tree. if the newly created node has more than one child, the children that are not the original node are copies of randomly chosen nodes of the right kind from the whole rule set
                    // TODO CASE 4
                    Node parent = n.getParent();
                    n.setMutationType(5); //assume the mutation is applicable for now
                    if (parent instanceof Access) {
                        BinaryArithmeticOperator bao = new BinaryArithmeticOperator((Expression)n, (Expression)(randomNode(root, Expression.class).copy()));
                        parent.replaceChild(n, bao);
                        return root;
                    } 
                    else if (parent instanceof BinaryArithmeticOperator) {
                        BinaryArithmeticOperator bao = new BinaryArithmeticOperator((Expression)n, (Expression)(randomNode(root, Expression.class).copy()));
                        parent.replaceChild(n, bao);
                        return root;
                    } 
                    else if (parent instanceof BinaryBooleanOperator) {
                        BinaryBooleanOperator boo = new BinaryBooleanOperator((Condition)n, (Condition)(randomNode(root, Condition.class).copy()));
                        parent.replaceChild(n, boo);
                        //don't create new BinaryRelation
                    } 
                    else if (parent instanceof BinaryRelation) {
                        BinaryArithmeticOperator bao = new BinaryArithmeticOperator((Expression)n, (Expression)(randomNode(root, Expression.class).copy()));
                        parent.replaceChild(n, bao);
                        return root;
                    } 
                    else if (parent instanceof Rule && n instanceof Condition) {
                        BinaryBooleanOperator boo = new BinaryBooleanOperator((Condition)n, (Condition)(randomNode(root, Condition.class).copy()));
                        parent.replaceChild(n, boo);
                    } 
                    else if (parent instanceof Tag) {
                        BinaryArithmeticOperator bao = new BinaryArithmeticOperator((Expression)n, (Expression)(randomNode(root, Expression.class).copy()));
                        parent.replaceChild(n, bao);
                        return root;
                    }
                    else //the mutation is not applicable
                        n.setMutationType(0); //reset--do not store mutation type since mutation was not applied
                    break;
            }
            faultType.remove(i); //deletes the last fault type from the list of possible fault types because it was already tried (and failed)
        }
        return null; //none of the possible fault types worked!
    }

    
    /**
     * Retrieves a random node of the given type from the subtree of the given start node
     * @param start the given start node
     * @param c the given type of node
     * @return 
     */
    public static Node randomNode(Node start, Class<? extends Node> c) {
        Node[] arr = start.toArray();
        if (c != null) {
            LinkedList<Node> sameType = new LinkedList();
            for (Node nd : arr) {
                if (nd.getClass().isAssignableFrom(c)) {
                    sameType.add(nd);
                }
            }
            if (sameType.size() > 0) {
                return sameType.get((int) (Math.random() * (sameType.size())));
            } else {
                return null;
            }
        } else {
            return arr[(int) (Math.random() * (arr.length))];
        }
    }
    //mem[0] length of critter's memory (immutable, always at least 9)
    //mem[1] defense ability (immutible, >= 1 )
    //mem[2] offense ability (immutible, >= 1 )
    //mem[3] size, variable but cannot be assigned directly >=1
    //mem[4] energy (variable, but cannot be assigned directly, always positive
    //mem[5] rule counter, explaination below, variable but cannot be assigned directly
    //mem[6] event log (variable, but cannot be assigned directly
    //mem[7] tag (variable, but cannot be assigned directly. always between 0 and 999
    //mem[8] posture (assignable but only to values between 0 and 999

    public Program injectFault(Program p) {
        //int index = (int)(Math.random()*ORIGINAL.length);
        int faultType = (int) (Math.random() * 5);
        switch (faultType) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
        return null;

    }
}
