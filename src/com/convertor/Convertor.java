package com.convertor;

import java.awt.Color;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class Convertor extends JFrame {
	private String commentStr = "__Comment";
	private int commentIndex = 1;
	private String startFlagOfTranslation = "<'";
	private static String endFlagOfTranslation = "'>";
	private Map<String, String> transTextMap = new HashMap<String, String>();
	private Map<String, String> sourceTextMap = new HashMap<String, String>();
	private String targetDirectory;
	private DefaultTableModel tableModel;
	private JTextField message;
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
					updateTable();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
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

				try {
					importSourceLGF(theFrame);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		row1P.add(importSourceLGF);

		JButton translate = new JButton("Translate");
		translate.addActionListener(new ActionListener() { // add event
															// listener
			public void actionPerformed(ActionEvent e) {

				try {
					translate();
				} catch (UnsupportedEncodingException | FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		row1P.add(translate);

		container.add(row1P);
		tableModel = new DefaultTableModel(new Object[] { "ID", "SearchText", "TranlateText" }, 0);
		JTable table = new JTable(tableModel);
		container.add(new JScrollPane(table));

		// JComboBox sourceEncodingComboBox = new JComboBox();

		message = new JTextField(20);
		container.add(message);

		// DrawIcon icon = new DrawIcon(15, 15);
		// JLabel jl = new JLabel("This is a JFramw window", icon,
		// SwingConstants.CENTER);
		// jl.setHorizontalAlignment(SwingConstants.CENTER);
		// container.add(jl);

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
		transTextMap.clear();
	}

	private void loadEncodingList(JComboBox comboBox) {
		SortedMap m = Charset.availableCharsets();
		Set k = m.keySet();
		System.out.println("Canonical name, Display name," + " Can encode, Aliases");
		Iterator i = k.iterator();
		while (i.hasNext()) {
			String n = (String) i.next();
			Charset e = (Charset) m.get(n);
			String d = e.displayName();
			boolean c = e.canEncode();
			System.out.print(n + ", " + d + ", " + c);
			Set s = e.aliases();
			Iterator j = s.iterator();
			while (j.hasNext()) {
				String a = (String) j.next();
				System.out.print(", " + a);
			}
			System.out.println("");
		}
	}

	private void importTargetLGF(JFrame theFrame) throws FileNotFoundException, IOException {
		FileDialog fd = new FileDialog(theFrame, "Choose a file", FileDialog.LOAD);
		fd.setDirectory("C:\\");
		fd.setFile("*.lgf");
		fd.setVisible(true);
		String filename = fd.getFile();
		if (filename == null)
			System.out.println("You cancelled the choice");
		else {
			String completeFileName = fd.getDirectory() + filename;
			targetDirectory = fd.getDirectory();
			System.out.println("You chose " + completeFileName);
			BufferedReader br = new BufferedReader(new FileReader(completeFileName));
			try {
				StringBuilder sb = new StringBuilder();
				String lineText;
				int startOfLabel = 0;
				int lengthOfLabel;
				int startOfTranslation;
				int endOfTranslation;
				int lengthOfTranslation;
				String label;
				String translationText;
				String newLineText;

				while ((lineText = br.readLine()) != null) {
					lengthOfLabel = lineText.indexOf(startFlagOfTranslation);
					if (lineText.startsWith(";") == true) {
						label = commentStr + commentIndex++;
						translationText = lineText;
					}
					if (lengthOfLabel <= 0) {
						continue;
					}

					label = lineText.substring(startOfLabel, lengthOfLabel - 1);

					label = label.trim();
					if (label.startsWith("E0x")) {
						// Normally, for every machine,error text is different.
						// So ignored the translation in source LGF
						continue;
					}
					startOfTranslation = lineText.indexOf(startFlagOfTranslation);
					endOfTranslation = lineText.indexOf(endFlagOfTranslation);
					lengthOfTranslation = endOfTranslation - startOfTranslation - startFlagOfTranslation.length();
					startOfTranslation += startFlagOfTranslation.length();

					if (startOfTranslation >= 0 && lengthOfTranslation >= 0) {
						translationText = lineText.substring(startOfTranslation,
								startOfTranslation + lengthOfTranslation);
					} else {
						translationText = "Something wrong";
					}
					if (transTextMap.containsKey(label) == false) {
						// Translation newTrans = new Translation(label,
						// translationText);
						transTextMap.put(label, translationText);
					}
					sb.append(lineText);
					sb.append(System.lineSeparator());

				}
				String everything = sb.toString();
				System.out.println("Result: " + everything);
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				br.close();
			}
		}

	}

	private void importSourceLGF(JFrame theFrame) throws FileNotFoundException, IOException {
		FileDialog fd = new FileDialog(theFrame, "Choose a file", FileDialog.LOAD);
		fd.setDirectory("C:\\");
		fd.setFile("*.lgf");
		fd.setVisible(true);
		String filename = fd.getFile();
		if (filename == null)
			System.out.println("You cancelled the choice");
		else {
			String completeFileName = fd.getDirectory() + filename;
			System.out.println("You chose " + completeFileName);
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(completeFileName), "UTF-16"));
			// Reader in = new InputStreamReader(new
			// FileInputStream(completeFileName), "UTF-16");
			// BufferedReader br = new BufferedReader(new
			// FileReader(completeFileName));
			try {
				StringBuilder sb = new StringBuilder();
				String lineText;
				int startOfLabel = 0;
				int lengthOfLabel;
				int startOfTranslation;
				int endOfTranslation;
				int lengthOfTranslation;
				String label;
				String translationText;
				String newLineText;

				while ((lineText = br.readLine()) != null) {
					lengthOfLabel = lineText.indexOf(startFlagOfTranslation);
					if (lengthOfLabel <= 0) {
						continue;
					}

					label = lineText.substring(startOfLabel, lengthOfLabel - 1);

					label = label.trim();
					if (label.startsWith("E0x")) {
						// Normally, for every machine,error text is different.
						// So ignored the translation in source LGF
						continue;
					}
					startOfTranslation = lineText.indexOf(startFlagOfTranslation);
					endOfTranslation = lineText.indexOf(endFlagOfTranslation);
					lengthOfTranslation = endOfTranslation - startOfTranslation - startFlagOfTranslation.length();
					startOfTranslation += startFlagOfTranslation.length();

					if (startOfTranslation >= 0 && lengthOfTranslation >= 0) {
						translationText = lineText.substring(startOfTranslation,
								startOfTranslation + lengthOfTranslation);
					} else {
						translationText = "Something wrong";
					}
					if (sourceTextMap.containsKey(label) == false) {
						// Translation newTrans = new Translation(label,
						// translationText);
						sourceTextMap.put(label, translationText);
					}
					
				
					sb.append(lineText);
					sb.append(System.lineSeparator());

				}
				for (Map.Entry<String, String> entry : sourceTextMap.entrySet()) {
					if(transTextMap.containsKey(entry.getKey())){
						transTextMap.put(entry.getKey(), entry.getValue());
					}
					
				}
				
				
				
				String everything = sb.toString();
				System.out.println("Result: " + everything);
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				br.close();
			}
		}

	}

	private void translate() throws UnsupportedEncodingException, FileNotFoundException {
		String newLineText;
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : transTextMap.entrySet()) {
			if (entry.getKey().contains(commentStr)) {
				newLineText = entry.getValue();
			} else {
				newLineText = getFullLabel(entry.getKey()) + "<'" + entry.getValue() + "'>";
			}
			sb.append(newLineText);
			sb.append(System.lineSeparator());
		}
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetDirectory + "translated.LGF"), "UTF-16"));
		try {
			out.write(sb.toString());
			out.flush();
			message.setText("Translate OK now.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private String getFullLabel(String label) {
		String fullLabel = "";
		if (label.length() < 32) {
			fullLabel = padRight(label, 32);
		} else if (label.length() < 64) {
			fullLabel = padRight(label, 64);
		} else {
			fullLabel = padRight(label, 128);
		}
		return fullLabel;
	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	private void updateTable() {
		for (Map.Entry<String, String> entry : transTextMap.entrySet()) {
			String[] row = { "0", entry.getKey(), entry.getValue() };
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			tableModel.addRow(row);
		}

	}
}
