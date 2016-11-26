package com.convertor;

import java.awt.Color;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class Convertor extends JFrame {
	private String startFlageOfTranslation = "<'";
	private static String endFlagOfTranslation = "'>";
	public Convertor() {
		super("Convertor");
		Container container = getContentPane();

		setLayout(new GridLayout(3, 1, 5, 5));
		JFrame theFrame = this;
		JPanel row1P = new JPanel(new GridLayout(1, 3, 2, 2));
		JButton openTargetLGF = new JButton("Open Target LGF");
		openTargetLGF.addActionListener(new ActionListener() { // add event
																// listener
			public void actionPerformed(ActionEvent e) {
				try {
					importTargetLGF(theFrame);
				}
				catch(FileNotFoundException e1){
					e1.printStackTrace();
				}
				catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
			}
		});
		row1P.add(openTargetLGF);

		JButton importSourceLGF = new JButton("Import Source LGF");
		importSourceLGF.addActionListener(new ActionListener() { // add event
																	// listener
			public void actionPerformed(ActionEvent e) {
				
				// TODO
			}
		});
		row1P.add(importSourceLGF);

		

		JButton translate = new JButton("Translate");
		translate.addActionListener(new ActionListener() { // add event
																// listener
			public void actionPerformed(ActionEvent e) {

				// TODO
			}
		});
		row1P.add(translate);
		
		container.add(row1P);
		DefaultTableModel tableModel = new DefaultTableModel(new Object[] { "ID", "SearchText","TranlateText" }, 10);
		JTable table = new JTable(tableModel);
		container.add(new JScrollPane(table));

//		JComboBox sourceEncodingComboBox = new JComboBox();
		
		
	

		DrawIcon icon = new DrawIcon(15, 15);
		JLabel jl = new JLabel("This is a JFramw window", icon, SwingConstants.CENTER);
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(jl);

		// JButton bl = new JButton("open XML");
		// bl.setBounds(10, 10, 100, 21);
		// bl.addActionListener(new ActionListener() { //add event listener
		// public void actionPerformed(ActionEvent e) {
		//
		// new MyJDialog(Convertor.this).setVisible(true);
		// }
		// });
		// container.add(bl);
		container.setBackground(Color.white);
		setVisible(true);
		setSize(500, 500);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	private void loadEncodingList(JComboBox comboBox){
		SortedMap m = Charset.availableCharsets();
	      Set k = m.keySet();
	      System.out.println("Canonical name, Display name,"
	         +" Can encode, Aliases");
	      Iterator i =  k.iterator();
	      while (i.hasNext()) {
	         String n = (String) i.next();
	         Charset e = (Charset) m.get(n);
	         String d = e.displayName();
	         boolean c = e.canEncode();
	         System.out.print(n+", "+d+", "+c);
	         Set s = e.aliases();
	         Iterator j = s.iterator();
	         while (j.hasNext()) {
	            String a = (String) j.next();         
	            System.out.print(", "+a);
	         }
	         System.out.println("");
	      }
	}
	
	private void importTargetLGF(JFrame theFrame) throws FileNotFoundException,IOException {
		FileDialog fd = new FileDialog(theFrame, "Choose a file", FileDialog.LOAD);
		fd.setDirectory("C:\\");
		fd.setFile("*.lgf");
		fd.setVisible(true);
		String filename = fd.getFile();
		if (filename == null)
		  System.out.println("You cancelled the choice");
		else{
			String completeFileName = fd.getDirectory() + filename;
			System.out.println("You chose " + completeFileName);
			BufferedReader br = new BufferedReader(new FileReader(completeFileName));
			try {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    String everything = sb.toString();
			    System.out.println("Result: " + everything);
			} finally {
			    br.close();
			}
		}
		  
		
	}
	
	
	
}
