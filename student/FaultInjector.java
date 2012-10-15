/**
 * 
 */
package student;

import java.util.LinkedList;
import java.util.Random;

public class FaultInjector {
	public Node injectFault(Node n)
	{
		//int index = (int)(Math.random()*ORIGINAL.length);
		//Node[] arr = n.toArray();
		//int i = (int)(Math.random()*arr.length);
		int faultType = (int)(Math.random()*5);
		boolean go = true;
		while(go)
		{
			go = false;
		switch(faultType){
		case 0: //the node is removed. if its parent node needs a replacement node, one of its children of the right kind is used. The child to be used is randomly selected. Thus rule nodes are simply removed, but binary operation nodes would be replaced with either their left or right child
			if(!n.remove()) //attempts to remove node, if the node cannot be removed...
				go = true;
			
			/*if(n.parent instanceof BinaryOp)
			{
				//find selected
				Node selected;
				p.value = selected.value;
			}
			else
				n.parent.children = null;*/
			break;
		case 1: //order of two children of the node is switched. for ex. allows swapping of positions of two rules
			if(n.numChildren()>=2)
			{
			n.swapChildren();
			}
			else
				go = true;
			break;
		case 2: //the node and its children are replaced with a copy of another randomly selected node of the right kind, found somewhere in the rule set. the entire AST subtree rooted at the selected node is copied
			Node pointer = n;
			while(pointer.getParent().getParent()!=null) //finds the topmost node in the AST
			{
			pointer = pointer.getParent();
			}
			//find selected
			Node selected = randomNode(pointer, n.getClass());
			if(selected!=null)
			{
			n.set(selected.copy());
			}
			else
				go = true;
			break;
		case 3:// the node is replaced with a randomly chosen node of the same kind( for example, replacing attack with eat, or + with *) but its children remain the same. Literal integer constants are adjusted up or down with the value of java.lang.Integer.MAX_VALUE/r.nextInt(), where legal, and where r is a java.util.random obj
			Node selected;
			if(selected==null)
			{
				go = false;
				break;
			}
			if(selected instanceof BinaryBooleanOperator)
				((BinaryBooleanOperator)n).setConditionOp(((BinaryBooleanOperator) selected).getConditionOp());
			else if(selected instanceof BinaryArithmeticOperator)
				((BinaryArithmeticOperator)n).setBinaryOp(((BinaryArithmeticOperator) selected).getBinaryOp());
			/*else if(selected instanceof BinaryRelation)
				((BinaryRelation)n).setRelation(((BinaryRelation) selected).getRelation());
			  else if(selected instanceof Condition)
				????*/
			else if(selected instanceof Expression)
				((Expression)n).setValue(((Expression) selected).getValue());
			/*else if(selected instanceof Program)
				???
			else if(selected instanceof Rule)
				???*/
			else
				go = false;
			//n.setValue(selected.getValue());
			break;
		case 4: // a newly created node is inserted as the parent of the node, taking its place in the tree. if the newly created node has more than one child, the children that are not the original node are copies of randomly chosen nodes of the right kind from the whole rule set
			break;
		}
		}
		/*		for(int i=0; i<ORIGINAL.length; i++)
		{

		}*/
		return null;
	}
	public Node randomNode(Node start, Class<? extends Node> c)
	{
		Node[] arr = start.toArray();
		LinkedList<Node> sameType = new LinkedList<Node>();
		for(Node nd: arr)
		{
			if(nd.getClass().equals(c))
			sameType.add(nd);
		}
		if(sameType.size()>0)
		{
			Random r = new Random();
			return sameType.get((r.nextInt(sameType.size())));
		}
		else
			return null;
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
	public Program injectFault(Program p)
	{
		//int index = (int)(Math.random()*ORIGINAL.length);
		int faultType = (int)(Math.random()*5);
		switch(faultType){
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;}	
		return null;
	
	}
	}
