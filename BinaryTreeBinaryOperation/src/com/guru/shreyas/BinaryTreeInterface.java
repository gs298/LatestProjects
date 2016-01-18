package com.guru.shreyas;

public interface BinaryTreeInterface {
	/**
	   * Builds the tree from the passed expression.
	   * 
	   * @param expression the string containing an equation
	   */
	  public void treeBuilding(String expression);

	  public StringBuffer getTree();
	  /**
	   * Evaluates the expression being stored in the tree.
	   * 
	   * @return the evaluated expression
	   */
	  public double treeEvalution();
}
