package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ErrorStack extends JPanel {
	
	/**
	 * Default generated VersionID
	 */
	private static final long serialVersionUID = 1L;

	private final JScrollPane scrollPane;
	private final JTextArea errorText;
	
	/**
	 * Creates a pane to display errors
	 */
	public ErrorStack(int width, int height) {
		super();
		
		super.setPreferredSize(new Dimension(width, height));
		super.setLayout(new BorderLayout());
		
		errorText = new JTextArea();
		
		errorText.setEnabled(false);
		errorText.setText("No Errors");
		errorText.setPreferredSize(new Dimension(width, height));
		errorText.setDisabledTextColor(new Color(255,0,0));
		
		scrollPane = new JScrollPane(errorText);
		
		super.add(scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * Display the Stack Trace which caused the error
	 * @param args
	 */
	public void printError(StackTraceElement[] args) {
		String errorMsg = "";
		
		for(int i = 0; i < args.length; i++) {
			errorMsg += args[0];
			errorMsg += "\n";
		}
		
		errorText.setText(errorMsg);
	}
	
	
	public void printError(String msg) {
		errorText.setText(msg);
	}
}
