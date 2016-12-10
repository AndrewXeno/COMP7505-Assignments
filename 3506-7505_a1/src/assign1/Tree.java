package assign1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Tree {
	String data;
	Tree lChild;
	Tree rChild;
	Tree parent;
	int visitTimes;			//used for Euler Tour Traversal in getResultIt()
	
	
	/** 
	 * check if a string has operator(?, %, &).
	 * @param s - input string
	 * return true if there is any operator, otherwise return false
	 */
	static boolean hasOperator(String s){
		if (s.contains("?") || s.contains("%") ||s.contains("&")){
			return true;
		} else {
			return false;
		}
	}
	
	/** 
	 * check if prentheses are matched.(Reference: lecture slide)
	 * @param s - input string
	 * return true if prentheses are matched, else return false
	 */
	static boolean isMatched(String expression) {
		Stack<Character> buffer = new Stack<>();
		for(char c : expression.toCharArray()) {
			if(c == '(') // this is a left delimiter
				buffer.push(c);
			else if(c == ')') {// this is a right delimiter
				if(buffer.isEmpty()) // nothing to match with
				return false;
				buffer.pop();
			}
		}
		return buffer.isEmpty( ); // were all opening delimiters matched?
	}
	
	
	/** 
	 * Constructor should take a string representing the tree to be created.
	 * @param s - String represening the tree, e.g. (4 & 6) % (2 ? 3)
	 */
	public Tree(String s){
		s=s.replaceAll(" ", "");		//get rid of all spaces
		
		//check if the string is valid
		if (!isMatched(s)){
			System.err.println("Non-matching parenthesis");
			return;
		}
		int numCount = 0;
		int operatorCount = 0;
		final String validChar=" 1234567890()?%&";
		for(char c : s.toCharArray()) {
			if (validChar.indexOf(c) == -1){
				System.err.println("Invalid characher found");
				return;
			} else if (c=='?'||c=='%'||c=='&'){
				operatorCount++;
			}
		}

		String numList[]=s.split("\\?|\\%|\\&|\\)|\\(");		//convert the string to an string array,each element is a number or an operator or null
		for(String i: numList){
			if (!i.isEmpty()){
				if (Integer.parseInt(i)<=0){
					System.err.println("Non-positive number");
					return;
				}
				numCount++;
			}
		}
		if (numCount - operatorCount > 1){
			System.err.println("Missing operator");
			return;
		} else if (numCount - operatorCount < 1){
			System.err.println("Wrong number of argument for an operator");
			return;
		}
		

		//get rid of outer most parenthese
		if (s.startsWith("(") && s.endsWith(")") && isMatched(s.substring(1, s.length()-1))){
			s = s.substring(1, s.length()-1);
		}
		
		if (!hasOperator(s)){			//base case: s has only a number
			this.data=s;
		} else {
			char[] exp = s.toCharArray();
			Stack<Character> buffer = new Stack<>();
			int operatorIndex = 0;
			for (int i=0;i<exp.length;i++){
				if (exp[i]=='('){
					buffer.push(exp[i]);
				} else if (exp[i]==')'){
					buffer.pop();
				} else if ((exp[i]=='?'||exp[i]=='%'||exp[i]=='&') && buffer.isEmpty()){
					operatorIndex = i;		//the index of last evaluated operator
					break;
				}
			}
			this.data=String.valueOf(exp[operatorIndex]);
			String lString = new String();
			String rString = new String();
			for (int i=0;i<operatorIndex;i++){
				lString+=exp[i];
			}
			for (int i=operatorIndex+1;i<exp.length;i++){
				rString+=exp[i];
			}
			this.lChild=new Tree(lString);
			this.lChild.parent=this;
			this.rChild=new Tree(rString);
			this.rChild.parent=this;
		}
		
	}
	
	/**
	 * Return the height of the tree
	 * 
	 * Note: Height is defined as the number of nodes in the longest path 
	 * from the root node to a leaf node. (1 node = height 0, 2 layers = height 1)
	 */
	public int getHeight(){
		if(this.lChild==null && this.rChild==null){
			return 0;
		}else{
			return 1 + Math.max(this.lChild.getHeight(), this.rChild.getHeight());
		}
	}
	
	/**
	 * Return the depth of the tree
	 */
	public int getDepth(){
		if(this.parent==null){
			return 0;
		}else{
			return 1+this.parent.getDepth();
		}
	}
	
	/**
	 * Return the number of leaves in the tree
	 */
	public int nbLeaves(){
		if(this.lChild==null && this.rChild==null){
			return 1;
		}else{
			return this.lChild.nbLeaves()+this.rChild.nbLeaves();
		}
	}
	
	/**
	 * Return the result of evaluating each operation in the tree
	 * using recursion.
	 */
	public int getResultRec(){
		if(this.lChild==null && this.rChild==null){
			return Integer.parseInt(this.data);
		}else{
			int lResult=this.lChild.getResultRec();
			int rResult=this.rChild.getResultRec();
			if(lResult==-1 || lResult==-1){
				return -1;
			} else if (this.data.equals("?")){
				return Math.max(lResult, rResult);
			}else if(this.data.equals("%")){
				if (rResult==0){
					return -1;
				} else {
					return lResult % rResult;
				}
			}else{
				return lResult + rResult;
			}
		}
	}
	
	/**
	 * Return a list in which each item is a step in evaluating the tree. 
	 */
	public List<String> getResultBySteps(){
		List<String> output=new ArrayList<String>();
		Stack<Tree> buffer = new Stack<>();
		List<Tree> internal = new ArrayList<Tree>();
		List<Tree> evaluated = new ArrayList<Tree>();
		int maxDepth=0;
		Tree node=this;
		buffer.push(node);
		while (!buffer.empty()){
			node = buffer.pop();
			if (node.lChild!=null && node.rChild!=null){
				internal.add(node);
				buffer.push(node.rChild);
				buffer.push(node.lChild);
			}
		}
		int[] depth=new int[internal.size()];
		for(int i=0;i<internal.size();i++){
			depth[i]=internal.get(i).getDepth();
			if(internal.get(i).getDepth()>maxDepth){
				maxDepth = internal.get(i).getDepth();
			}
		}
		
		String temp=this.printExp(evaluated);
		if(temp.startsWith("(")){			//remove outer parentheses
			temp=temp.substring(1, temp.length()-1);
		}
		output.add(temp);
		for (int i=maxDepth;i>=0;i--){
			for(int j=0;j<internal.size();j++){
				if (internal.get(j).getDepth()==i){
					evaluated.add(internal.get(j));
					temp=this.printExp(evaluated);
					if(temp.startsWith("(")){
						temp=temp.substring(1, temp.length()-1);
					}
					output.add(temp);
					if (temp.contains(" 0")||temp.contains("(0")){
						output.add("-1");
						return output;
					}
				}
			}
		}
		return output;
	}
	
	/**
	 * Return the result of evaluating each operation in the tree
	 * using an iterative method.
	 */
	public int getResultIt(){
		Stack<Tree> visitStack = new Stack<>();
		Stack<Integer> numberStack = new Stack<>();
		Tree current=this;
		
		while(!visitStack.empty()||current!=null){
			if(current!=null){
				current.visitTimes=0;
				visitStack.push(current.rChild);
				current=current.lChild;
			}else{
				current=visitStack.pop();
			}
		}
		
		current=this;
		while (!visitStack.empty()||current!=null){
			if (current.lChild==null){
				numberStack.push(Integer.parseInt(current.data));
				current=visitStack.pop();
			} else if(current.visitTimes==0){
				visitStack.push(current);
				visitStack.push(current.rChild);
				visitStack.push(current);
				visitStack.push(current.lChild);
				current.visitTimes++;
				current=visitStack.pop();
			} else if(current.visitTimes==1){
				current.visitTimes++;
				current=visitStack.pop();
			} else if (current.visitTimes==2){
				int b=numberStack.pop();
				int a=numberStack.pop();
				if (current.data.equals("?")){
					numberStack.push(Math.max(a,b));
				}else if (current.data.equals("%")){
					if(a%b==0){
						return -1;
					}else{
						numberStack.push(a%b);
					}
				}else if (current.data.equals("&")){
					numberStack.push(a+b);
				}
				if (!visitStack.isEmpty()){
					current=visitStack.pop();
				} else {
					current=null;
				}
			}
		}
		return numberStack.pop();
	}
	
	/**
	 * Return the string expression of a tree
	 * the returned expression is wrapped in parentheses except if it is just a result
	 * @param evaluated - a list which contains all evaluated nodes. when the method 
	 * is called on these nodes, their result will be returned.
	 */
	public String printExp(List<Tree> evaluated){
		if (evaluated.contains(this)){
			return String.valueOf(this.getResultRec());
		}else if (this.lChild==null && this.rChild==null){
			return this.data;  
		} else {
			return "("+this.lChild.printExp(evaluated)+" "+this.data+" "+this.rChild.printExp(evaluated)+")";
		}
	}

}

