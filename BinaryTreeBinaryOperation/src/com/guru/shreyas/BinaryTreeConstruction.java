package com.guru.shreyas;

import com.guru.shreyas.BinaryTreeInterface;

public class BinaryTreeConstruction implements BinaryTreeInterface{
	
	private
	   String binaryExp;
	   char symbol;
	   int position = 0;
	  
    public 
	  	
     StringBuffer treeBuffer= new StringBuffer();
	 
	 static public class Node {
	    Object element;
	    Node left, right;
	  }
	
	  Node root;
	  
	  public BinaryTreeConstruction() {   // this constructor initializes an empty tree
		  root = null ;
		  }
	  
	  @Override
	  public void treeBuilding(String binaryExp){
		 this.binaryExp=binaryExp;
		 
		 root=new Node();
		 root = binaryExpTreeConstruction(root);
		 
		 
	 }
	  @Override
	public StringBuffer getTree() {
		// TODO Auto-generated method stub
		return treeBuffer;
	}
	  private Node binaryExpTreeConstruction(Node n){
		  Node lTree = new Node();
		  lTree = factorTree(lTree);
		  
		  readChar();
		  
		  if (symbol == '+' || symbol == '-') {
		      position++;
		      n.left = lTree;  
		      n.element = symbol;
		      n.right = binaryExpTreeConstruction(new Node());
		      treeBuffer.append(n.element);
		    } 
		  else  if (symbol == '*' || symbol == '/') {
		      position++;
		      n.left = lTree;
		      n.element = symbol;
		      n.right = binaryExpTreeConstruction(new Node());
		      treeBuffer.append(n.element);
		    } 
		  
		  else {
		      n = lTree;
		      
		    }
		    return n;
	  }
	  

	  private Node factorTree(Node n) {
		  
	    readChar();
	    if (symbol == '(') {
	      position++;
	      n.element=symbol;
	      n = binaryExpTreeConstruction(new Node());
	      readChar();
	      if (symbol == ')')
	        position++;
	      else
	        System.out.println(" Missing ')'");
	      
	    } else {
	      n.element = getConstant();
	      treeBuffer.append(n.element);
	    }
	    return n;
	  }

	  private String getConstant() {
	    StringBuilder sb = new StringBuilder();

	    for (int i = position; i < binaryExp.length(); i++) {
	      if (binaryExp.substring(i, i + 1).matches("[0-9,.]")) {
	        sb.append(binaryExp.charAt(i));
	        continue;
	      }
	      position = i;
	      return sb.toString();
	    }
	    position = binaryExp.length();
	    return sb.toString();
	  }

	  
	  private void readChar() {
	    for (int i = position; i < binaryExp.length(); i++) {
	      if (binaryExp.substring(i, i + 1).matches("[0-9]"))
	        continue;
	      symbol = binaryExp.charAt(i);
	      return;
	    }
	    symbol = ' ';
	  }

	  @Override
	  public double treeEvalution() {
	    return treeTraverse(root);
	  }

	
	  private double treeTraverse(Node n) {
	    if (n != null) {
	      if (n.left != null && n.right != null) {
	        double right = treeTraverse(n.right);
	        double left = treeTraverse(n.left);
	        if (n.element.equals('+')) {
	          return left + right;
	        } else if (n.element.equals('-')) {
	          return left - right;
	        } else if (n.element.equals('*')) {
	          return left * right;
	        } else if (n.element.equals('/')) {
	          return left / right;
	        }
	        System.out.println("Error - symbol not found.");
	        return 0.0d;
	      }
	      try {
	        return Double.parseDouble((String) n.element);
	      } catch (NumberFormatException ex) {
	        System.out.println("Error - double not found.");
	        return 0.0d;
	      }
	    }
	    System.out.println("Null Node Reached");
	    return 0.0d;
	  }

	  @Override
	  public String toString() {
	    return getTreeExpression(root);
	  }

	
	  private String getTreeExpression(Node n) {
	    StringBuilder sb = new StringBuilder();

	    if (n != null) {
	      if (n.left != null) {
	        sb.append("(");
	        sb.append(getTreeExpression(n.left));
	      }
	      sb.append(n.element);
	      if (n.right != null) {
	        sb.append(getTreeExpression(n.right));
	        sb.append(")");
	      }
	    }
   
	    return sb.toString();
	  }
	 
}
