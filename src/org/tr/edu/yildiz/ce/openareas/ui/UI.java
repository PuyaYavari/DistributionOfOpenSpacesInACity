package org.tr.edu.yildiz.ce.openareas.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import org.tr.edu.yildiz.ce.openareas.utils.DistributionHistogramDrawer;

public class UI extends JApplet {
	private static String path;
	private static String dirName;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JButton selectButton = new JButton("select");
		JLabel selectedLabel = new JLabel("Please select a map.");
		JRadioButton singleModeButton = new JRadioButton("Single Mode");
		JRadioButton multiModeButton = new JRadioButton("Multi Mode");
		JPanel radioPanel = new JPanel();
		JPanel radiusPanel = new JPanel();
		JLabel radiusLabel = new JLabel("Radius: ");
		JTextField radiusTextField = new JTextField();
		JPanel singleModePanel = new JPanel();
		JPanel fromPanel = new JPanel();
		JLabel fromLabel = new JLabel("From Radius: ");
		JTextField fromTextField = new JTextField();
		JPanel toPanel = new JPanel();
		JLabel toLabel = new JLabel("To Radius: ");
		JTextField toTextField = new JTextField();
		JPanel deltaPanel = new JPanel();
		JLabel deltaLabel = new JLabel("Delta: ");
		JTextField deltaTextField = new JTextField();
		JPanel multiModePanel = new JPanel();
		MultiModeSequence multiModes[] = { new MultiModeSequence("Linear Sequence", true),
				new MultiModeSequence("Fibonacci Sequence", false) };
		JComboBox<MultiModeSequence> multiModesCB = new JComboBox<MultiModeSequence>(multiModes);
		JCheckBox isRandom = new JCheckBox("Evaluate for random points.");
		JButton confirm = new JButton("CONFIRM");
		JTextArea output = new JTextArea();

		selectButton.setBounds(10, 10, 84, 32);// x axis, y axis, width, height
		selectButton.setFont(new Font(selectButton.getFont().getName(), Font.PLAIN, 12));
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("/home/puya/Documents/FinalProject/Maps/"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("GEOJSON", "geojson");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					path = chooser.getSelectedFile().getPath();
					selectedLabel.setText(chooser.getSelectedFile().getName());
					dirName = chooser.getSelectedFile().getName().substring(0,
							chooser.getSelectedFile().getName().length() - 8);
					singleModeButton.setEnabled(true);
					multiModeButton.setEnabled(true);
					radioPanel.setEnabled(true);
					output.setText(String.format("Outputs will be saved to '%sOutputs/Matlab/Histogram/%s'",
							path.substring(0, (path.length() - (dirName.length() + 13))), dirName));
				}
			}
		});

		selectedLabel.setBounds(104, 10, 286, 32);
		selectedLabel.setFont(new Font(selectButton.getFont().getName(), Font.PLAIN, 12));
		selectedLabel.setBorder(new EmptyBorder(0, 4, 0, 4));// top,left,bottom,right
		selectedLabel.setForeground(Color.WHITE);
		selectedLabel.setBackground(Color.GRAY);
		selectedLabel.setOpaque(true);

		singleModeButton.setFont(new Font(selectButton.getFont().getName(), Font.PLAIN, 12));
		singleModeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				confirm.setEnabled(true);
				singleModePanel.setEnabled(true);
				radiusLabel.setEnabled(true);
				radiusTextField.setEnabled(true);
				multiModePanel.setEnabled(false);
				multiModesCB.setEnabled(false);
				fromTextField.setEnabled(false);
				fromLabel.setEnabled(false);
				toTextField.setEnabled(false);
				toLabel.setEnabled(false);
				deltaTextField.setEnabled(false);
				deltaLabel.setEnabled(false);
				isRandom.setEnabled(true);
			}
		});
		multiModeButton.setFont(new Font(selectButton.getFont().getName(), Font.PLAIN, 12));
		multiModeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				confirm.setEnabled(true);
				singleModePanel.setEnabled(false);
				radiusLabel.setEnabled(false);
				radiusTextField.setEnabled(false);
				multiModePanel.setEnabled(true);
				multiModesCB.setEnabled(true);
				isRandom.setEnabled(true);
				MultiModeSequence selected = (MultiModeSequence) multiModesCB.getSelectedItem();
				if (selected.isInputEnabled) {
					fromTextField.setEnabled(true);
					fromLabel.setEnabled(true);
					toTextField.setEnabled(true);
					toLabel.setEnabled(true);
					deltaTextField.setEnabled(true);
					deltaLabel.setEnabled(true);
				}
			}
		});
		ButtonGroup modeSelectGroup = new ButtonGroup();
		modeSelectGroup.add(singleModeButton);
		modeSelectGroup.add(multiModeButton);
		radioPanel.setLayout(new GridLayout(1, 2));
		radioPanel.add(singleModeButton);
		radioPanel.add(multiModeButton);
		radioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Select Mode"));
		radioPanel.setBounds(10, 52, 380, 42);
		singleModeButton.setEnabled(false);
		multiModeButton.setEnabled(false);
		radioPanel.setEnabled(false);

		radiusTextField.setEnabled(false);
		radiusLabel.setEnabled(false);
		radiusPanel.setLayout(new GridLayout(1, 2));
		radiusPanel.add(radiusLabel);
		radiusPanel.add(radiusTextField);
		radiusLabel.setFont(new Font(selectButton.getFont().getName(), Font.PLAIN, 12));
		((AbstractDocument) radiusTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
			Pattern regEx = Pattern.compile("\\d*");

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				Matcher matcher = regEx.matcher(text);
				if (!matcher.matches()) {
					return;
				}
				super.replace(fb, offset, length, text, attrs);
			}
		});

		fromTextField.setEnabled(false);
		fromLabel.setEnabled(false);
		fromPanel.setLayout(new GridLayout(1, 2));
		fromPanel.add(fromLabel);
		fromPanel.add(fromTextField);
		fromLabel.setFont(new Font(selectButton.getFont().getName(), Font.PLAIN, 12));
		((AbstractDocument) fromTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
			Pattern regEx = Pattern.compile("\\d*");

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				Matcher matcher = regEx.matcher(text);
				if (!matcher.matches()) {
					return;
				}
				super.replace(fb, offset, length, text, attrs);
			}
		});
		toTextField.setEnabled(false);
		toLabel.setEnabled(false);
		toPanel.setLayout(new GridLayout(1, 2));
		toPanel.add(toLabel);
		toPanel.add(toTextField);
		toLabel.setFont(new Font(selectButton.getFont().getName(), Font.PLAIN, 12));
		((AbstractDocument) toTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
			Pattern regEx = Pattern.compile("\\d*");

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				Matcher matcher = regEx.matcher(text);
				if (!matcher.matches()) {
					return;
				}
				super.replace(fb, offset, length, text, attrs);
			}
		});
		deltaTextField.setEnabled(false);
		deltaLabel.setEnabled(false);
		deltaPanel.setLayout(new GridLayout(1, 2));
		deltaPanel.add(deltaLabel);
		deltaPanel.add(deltaTextField);
		deltaLabel.setFont(new Font(selectButton.getFont().getName(), Font.PLAIN, 12));
		((AbstractDocument) deltaTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
			Pattern regEx = Pattern.compile("\\d*");

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				Matcher matcher = regEx.matcher(text);
				if (!matcher.matches()) {
					return;
				}
				super.replace(fb, offset, length, text, attrs);
			}
		});
		multiModesCB.setEnabled(false);
		multiModesCB.setFont(new Font(selectButton.getFont().getName(), Font.PLAIN, 12));
		multiModesCB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MultiModeSequence selected = (MultiModeSequence) multiModesCB.getSelectedItem();
				if (selected.isInputEnabled) {
					fromTextField.setEnabled(true);
					fromLabel.setEnabled(true);
					toTextField.setEnabled(true);
					toLabel.setEnabled(true);
					deltaTextField.setEnabled(true);
					deltaLabel.setEnabled(true);
				} else {
					fromTextField.setEnabled(false);
					fromLabel.setEnabled(false);
					toTextField.setEnabled(false);
					toLabel.setEnabled(false);
					deltaTextField.setEnabled(false);
					deltaLabel.setEnabled(false);
				}
			}
		});
		singleModePanel.setLayout(new GridLayout(1, 1));
		singleModePanel.add(radiusPanel);
		singleModePanel.setBounds(10, 104, 185, 50);
		singleModePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Single Mode"));
		singleModePanel.setEnabled(false);
		multiModePanel.setLayout(new GridLayout(4, 1));
		multiModePanel.add(multiModesCB);
		multiModePanel.add(fromPanel);
		multiModePanel.add(toPanel);
		multiModePanel.add(deltaPanel);
		multiModePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Multi Mode"));
		multiModePanel.setBounds(205, 104, 185, 120);
		multiModePanel.setEnabled(false);

		isRandom.setBounds(10, 230, 380, 40);
		isRandom.setEnabled(false);
		
		confirm.setBounds(10, 270, 380, 40);
		confirm.setEnabled(false);
		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean randomPoints = isRandom.isSelected();
				if (singleModeButton.isSelected()) {
					if (!radiusTextField.getText().equals("")) {
						confirm.setEnabled(false);
						double radius = Double.parseDouble(radiusTextField.getText());
						DistributionHistogramDrawer.evaluate(path, radius, radius, 1.0, dirName, ".jpg", randomPoints);
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					} else {
						output.setText("Fill Radius field please.\n");
					}
				} else {
					MultiModeSequence selected = (MultiModeSequence) multiModesCB.getSelectedItem();
					if (selected.isInputEnabled) {
						if (!fromTextField.getText().equals("") && !toTextField.getText().equals("")
								&& !deltaTextField.getText().equals("")) {
							confirm.setEnabled(false);
							double fromRadius = Double.parseDouble(fromTextField.getText());
							double toRadius = Double.parseDouble(toTextField.getText());
							double delta = Double.parseDouble(deltaTextField.getText());
							DistributionHistogramDrawer.evaluate(path, fromRadius, toRadius, delta, dirName, ".jpg", randomPoints);
							frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						} else {
							output.setText("");
							if (fromTextField.getText().equals(""))
								output.setText(output.getText() + "Fill From Radius field please.\n");
							if (toTextField.getText().equals(""))
								output.setText(output.getText() + "Fill To Radius field please.\n");
							if (deltaTextField.getText().equals(""))
								output.setText(output.getText() + "Fill Delta field please.\n");
						}
					} else {
						DistributionHistogramDrawer.evaluate(path, dirName, ".jpg", randomPoints);
					}
				}
			}
		});

		output.setBounds(10, 320, 380, 170);
		output.setBackground(Color.WHITE);
		output.setEditable(false);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);

		frame.add(selectButton);
		frame.add(selectedLabel);
		frame.add(radioPanel);
		frame.add(singleModePanel);
		frame.add(multiModePanel);
		frame.add(isRandom);
		frame.add(confirm);
		frame.add(output);

		frame.setSize(400, 500);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}