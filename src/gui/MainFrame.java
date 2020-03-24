package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


/**
 * Main Frame class for the application, hosts the other GUI images
 * @author Fool
 *
 */
public class MainFrame extends JFrame {	
	/**
	 * Generated serial ID 
	 */
	private static final long serialVersionUID = 3519548356396034705L;
	
	private final JSplitPane horzSplitPane;
	private final JSplitPane vertSplitPane;   // split window top and bottom
	private final ImagePanel orgImage;        // host the original image
	private final ImagePanel newImage;        // hosts the generated image
	private final JPanel bottomPanel;         // host buttons, error pane
	private final JPanel bottomRightPanel;    // holds file and gen panel
	private final JPanel genPanel;            // holds genbutton and text areas
	private final JPanel filePanel;           // host buttons
	private final ErrorStack stackPane;       // the error pane
	private final JTextField xdimChange;       // the amount to change the x dimension
	private final JTextField ydimChange;       // the amount to change the y dimension
	private final JTextField xdimOrg;       // the amount to change the x dimension
	private final JTextField ydimOrg;       // the amount to change the y dimension
	private final JLabel xTextLabel;          // label for the x dimension text
	private final JLabel yTextLabel;          // label for the y dimension text
	private final JLabel xOrgTextLabel;          // label for the x dimension text
	private final JLabel yOrgTextLabel;          // label for the y dimension text
	private final JButton genButton;          // generate image button
	private final JButton openButton;		  // open image button
	private final JButton saveButton;         // save image button
	
	public MainFrame self;                    // needed this for the button events
	
	public MainFrame() {
		self = this;
		horzSplitPane = new JSplitPane();
		vertSplitPane = new JSplitPane();
		
		orgImage = new ImagePanel(200,200);
		newImage = new ImagePanel(200,200);
		
		orgImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					orgImage.zoom();
					System.out.println("leftClick");
				}
				else if (SwingUtilities.isRightMouseButton(e)) {
					orgImage.unzoom();
					System.out.println("rightClick");
				}
			}
		});
		
		newImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					newImage.zoom();
					System.out.println("leftClick");
				}
				else if (SwingUtilities.isRightMouseButton(e)) {
					newImage.unzoom();
					System.out.println("rightClick");
				}
			}
		});
		
		bottomPanel = new JPanel();
		bottomRightPanel = new JPanel();
		genPanel = new JPanel();
		filePanel = new JPanel();
		
		xTextLabel = new JLabel("Change in the X axis: ");
		yTextLabel = new JLabel("Change in the Y axis: ");
		
		xOrgTextLabel = new JLabel("Size of X axis: ");
		yOrgTextLabel = new JLabel("Size of Y axis: ");
		
		xdimChange = new JTextField(5);
		xTextLabel.setMinimumSize(new Dimension(xTextLabel.getMinimumSize().width, xdimChange.getMinimumSize().height + 3));
		xdimChange.setMaximumSize(new Dimension(Integer.MAX_VALUE, xdimChange.getMinimumSize().height));
		ydimChange = new JTextField(5);
		yTextLabel.setMinimumSize(new Dimension(yTextLabel.getMinimumSize().width, xdimChange.getMinimumSize().height + 3));
		ydimChange.setMaximumSize(new Dimension(Integer.MAX_VALUE, xdimChange.getMinimumSize().height));
		xdimOrg = new JTextField(5);
		xOrgTextLabel.setMinimumSize(new Dimension(xOrgTextLabel.getMinimumSize().width, xdimChange.getMinimumSize().height + 3));
		xdimOrg.setMaximumSize(new Dimension(Integer.MAX_VALUE, xdimChange.getMinimumSize().height));		
		ydimOrg = new JTextField(5);
		yOrgTextLabel.setMinimumSize(new Dimension(yOrgTextLabel.getMinimumSize().width, xdimChange.getMinimumSize().height + 3));
		ydimOrg.setMaximumSize(new Dimension(Integer.MAX_VALUE, xdimChange.getMinimumSize().height));		
		
		// In the bottom panel put the buttons, error stack, and file browsing
		stackPane = new ErrorStack(200,100);
		
		openButton = new JButton("Open");
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
					int result = fileChooser.showOpenDialog(self);
					
					if(result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						orgImage.setImage(ImageIO.read(selectedFile));
						
						genButton.setEnabled(true);
					}
				} catch (IOException e1) {
					stackPane.printError(e1.getStackTrace());
				}
			}
		});
		
		genButton = new JButton("Generate image");
		genButton.setEnabled(false);
		
		genButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!(xdimOrg.getText().equals("") || ydimOrg.getText().equals("") || xdimChange.getText().equals("") || ydimChange.getText().equals(""))) {
					
				}
			}
		});
		
		saveButton = new JButton("Save");
		saveButton.setEnabled(false);
		
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JPanel label = new JPanel();
		label.setLayout(new BoxLayout(label, BoxLayout.Y_AXIS));
		label.add(xOrgTextLabel);
		label.add(yOrgTextLabel);
		label.add(xTextLabel);
		label.add(yTextLabel);
		
		JPanel field = new JPanel();
		field.setLayout(new BoxLayout(field, BoxLayout.Y_AXIS));
		field.add(xdimOrg);
		field.add(ydimOrg);
		field.add(xdimChange);
		field.add(ydimChange);
		
		genPanel.setLayout(new BorderLayout());
		genPanel.add(label, BorderLayout.WEST);
		genPanel.add(field, BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(800, 800));
		getContentPane().setLayout(new GridLayout());
		getContentPane().add(vertSplitPane);
		
		horzSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		horzSplitPane.setDividerLocation(400);
		horzSplitPane.setLeftComponent(orgImage);
		horzSplitPane.setRightComponent(newImage);
		
		vertSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		vertSplitPane.setDividerLocation(400);
		vertSplitPane.setTopComponent(horzSplitPane);
		vertSplitPane.setBottomComponent(bottomPanel);
		
		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.Y_AXIS));
		
		filePanel.add(openButton);
		filePanel.add(genButton);
		filePanel.add(saveButton);
		
		bottomRightPanel.setLayout(new BorderLayout());
		bottomRightPanel.add(genPanel, BorderLayout.WEST);
		bottomRightPanel.add(filePanel, BorderLayout.EAST);
		
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		
		bottomPanel.add(stackPane);
		bottomPanel.add(bottomRightPanel);
		
		pack();
	}
	
	public ErrorStack getErrorStack() {
		return this.stackPane;
	}
	
	public ImagePanel getOriginalImage() {
		return this.orgImage;
	}
}
