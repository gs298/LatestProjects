package com.guru.shreyas;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class BinaryTreeMain extends JFrame {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	 private JTextField expressionText;
	 private JLabel labelRes,labelRes1;
	 
	 public BinaryTreeMain() {
		// TODO Auto-generated constructor stub
		 setTitle("Binary Tree Construction & Evalution of Binary Expression");
		    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    setBounds(150, 150, 450, 175);
		    contentPane = new JPanel();
		    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		    contentPane.setLayout(null);
		    setContentPane(contentPane);
		    
		    JLabel lblExpressionTreeApplication = new JLabel("Please Enter An Expression:");
		    lblExpressionTreeApplication.setBounds(10, 11, 398, 20);
		    contentPane.add(lblExpressionTreeApplication);
		    
		    expressionText = new JTextField();
		    expressionText.setText("((2*5)+(5*2)-10)/10");
		    expressionText.setBounds(180, 11, 200, 20);
		    contentPane.add(expressionText);
		    
		    labelRes = new JLabel("");
		    labelRes.setHorizontalAlignment(SwingConstants.CENTER);
		    labelRes.setBounds(20, 80, 388, 26);
		    contentPane.add(labelRes);
		   
		    
		    labelRes1 = new JLabel("");
		    labelRes1.setHorizontalAlignment(SwingConstants.CENTER);
		    labelRes1.setBounds(30, 100, 388, 26);
		    contentPane.add(labelRes1);
		    
		    JButton evaluateBtn = new JButton("Build and Evalute Tree");
		    evaluateBtn.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        BinaryTreeInterface tree = new BinaryTreeConstruction();

		        tree.treeBuilding(expressionText.getText());
		        labelRes.setText("Constructed Tree :\t" + tree.getTree());
		        labelRes1.setText("\nResult of Expression :" + tree.treeEvalution());
		        
		        
		      }
		    });
		    evaluateBtn.setBounds(92, 50, 253, 23);
		    contentPane.add(evaluateBtn);
		    
		    
		    
	}
	 
	  public static void main(String[] args) {
//		  System.out.println("\tPlease Enter An Expression:");
//		  Scanner input = new Scanner(System.in);
//		  String exp=input.nextLine();
//		  BinaryTreeInterface tree = new BinaryTreeConstruction();
//
//	        tree.treeBuilding(exp);
//	        System.out.println("Constructed Tree :" + tree.getTree());
//	        System.out.println("\nResult of Expression :" + tree.treeEvalution());
	        
		    EventQueue.invokeLater(new Runnable() {
		      public void run() {
		        try {
		        	BinaryTreeMain frame = new BinaryTreeMain();
		          frame.setVisible(true);
		        } catch (Exception e) {
		          e.printStackTrace();
		        }
		      }
		    });
		  }
}
