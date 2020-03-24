package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Displays the new graphic for the user to visually check
 * @author Fool
 *
 */
public class ImagePanel extends JPanel {
	/**
	 * Autogen Serial ID
	 */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	private int zoomLevel;
	private int orgWidth;
	private int orgHeight;
	
	private boolean zoomChange;
	
	/**
	 * Creates the image panel with the provided image
	 * @param int width
	 * @param int height
	 * @param BufferedImage
	 */
	public ImagePanel(int width, int height, BufferedImage image) {
		super();
		super.setPreferredSize(new Dimension(width,height));
		this.image = image;
		
		zoomLevel = 1;
		zoomChange = false;
		
		orgWidth = width;
		orgHeight = height;
		
		// make the mouse crosshairs
		super.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		
		repaint();
	}
	
	/**
	 * Creates a blank image panel
	 * @param width
	 * @param height
	 */
	public ImagePanel(int width, int height) {
		super();
		super.setPreferredSize(new Dimension(width,height));
		
		zoomLevel = 1;
		
		orgWidth = width;
		orgHeight = height;
		
		// make the mouse crosshairs
		super.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.image = image;
		repaint();
	}
	
	/**
	 * Sets the image and redraws the panel
	 * @param image
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
		this.zoomLevel = 1;
		repaint();
	}
	
	/**
	 * Generates the new image based off the old
	 * @param Graphics
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0,0, (orgWidth * zoomLevel), (orgHeight * zoomLevel), null);
	}
	
	/**
	 * Increases the zoom level by 1 and redraws the image
	 */
	public void zoom() {
		if(this.zoomLevel < 15) {
			zoomLevel++; 
			repaint();
		}
	}
	
	/**
	 * Reduces the zoom level by 1 and redraws the image
	 */
	public void unzoom() {
		if(this.zoomLevel > 1) {
			zoomLevel--;
			repaint();
		}
	}
	
	/**
	 * Returns the current zoom level
	 * @return
	 */
	public double getZoomLevel() {
		return this.zoomLevel;
	}
}
