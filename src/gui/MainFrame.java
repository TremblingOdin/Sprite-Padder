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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import images.GridObject;
import images.ImageStitcher;


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
	
	private final FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "png", "jpg", "bmp", ".png", ".jpg"); // image files only
	
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
				}
				else if (SwingUtilities.isRightMouseButton(e)) {
					orgImage.unzoom();
				}
			}
		});
		
		newImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					newImage.zoom();
				}
				else if (SwingUtilities.isRightMouseButton(e)) {
					newImage.unzoom();
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
					fileChooser.setFileFilter(filter);
					
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
				stackPane.printError("Attempting to generate image");
				if(!(xdimOrg.getText().equals("") || ydimOrg.getText().equals("") || xdimChange.getText().equals("") || ydimChange.getText().equals(""))) {
					String xdimString = xdimOrg.getText();
					String ydimString = ydimOrg.getText();
					String xchangeString = xdimChange.getText();
					String ychangeString = ydimChange.getText();
					
					//I think this is the right regex, the original dimensions have to be positive, the change dimensions may be negative
					if(!(xdimString.matches("^[0-9]+") && ydimString.matches("^[0-9]+"))) {
						stackPane.printError("Please only use positive integers for the X dimension and Y dimension");
						return;
					}
					
					int xdim = Integer.parseInt(xdimString);
					int ydim = Integer.parseInt(ydimString);
					int xchange = Integer.parseInt(xchangeString);
					int ychange = Integer.parseInt(ychangeString);
					
					int maxWidth = orgImage.getImage().getWidth();
					int maxHeight = orgImage.getImage().getHeight();
					
					if(maxWidth%xdim != 0 || maxHeight%ydim != 0) {
						int confirm = JOptionPane.showConfirmDialog(null, "The dimensions you entered don't perfectly fit the image. Continuing may result in sprites near the edges being cut off. Continue?");
						//0=yes 1=no 2=cancel
						if(confirm > 0) {
							return;
						}
						
						//Some people might not have their cells exactly fit image size, I'm going to cut it short and ask them if they want to continur
						//doing if statements and modulos wasn't mathing right
						int adjustWidth = 0;
						while((adjustWidth+xdim < maxWidth-1)) {
							adjustWidth += xdim;
						}
						
						maxWidth = adjustWidth;
						
						int adjustHeight = 0;
						while((adjustHeight+ydim < maxHeight-1)) {
							adjustHeight += ydim;
						}
						
						maxHeight = adjustHeight;
					}
					
					stackPane.printError("Processing image cells");
					
					int xdimChange = xdim + 2*(xchange);
					int ydimChange = ydim +2*(ychange);
					
					GridObject[] cells = new GridObject[(maxWidth*maxHeight)/(xdim*ydim)];
					int cellIndex = 0;
					
					//Go through row by row by cell
					for(int i = 0; i < maxWidth; i += ydim) { //I always forget so I'm reminding myself, go through each y = row by row
						for(int j = 0; j < maxHeight; j += xdim) { //go through each x = column by column
							cells[cellIndex] = new GridObject(xdimChange, ydimChange, orgImage.getImage().getSubimage(j, i, xdim, ydim));
							cellIndex++;
						}
					}
					
					try{
						ImageStitcher stitch = new ImageStitcher(cells, maxHeight/ydim, xdimChange, ydimChange);
						newImage.setImage(stitch.getBigImage());
					} catch (Exception er) {
						er.printStackTrace();
						stackPane.printError(er.getStackTrace());
					}
				}
				
				saveButton.setEnabled(true);
			}
		});
		
		saveButton = new JButton("Save");
		saveButton.setEnabled(false);
		
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Save File");
					fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
					fileChooser.setFileFilter(filter);
					
					int result = fileChooser.showSaveDialog(self);
					
					if(result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						ImageIO.write(newImage.getImage(), "png", selectedFile);
					}
				} catch (IOException e1) {
					stackPane.printError(e1.getStackTrace());
				}
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
