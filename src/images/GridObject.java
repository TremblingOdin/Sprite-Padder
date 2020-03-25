package images;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class GridObject {
	int[][][] baseRBGArray;
	int[][][] newRGBArray;
	
	private byte[] oldPixels;
	private byte[] newPixels;
	private int pixelDataLength;
	private boolean hasAlpha;
	
	BufferedImage baseCell;
	BufferedImage newCell;
	
	/**
	 * Create a gridObject with the defined base parameters, and the new width and height parameters
	 * @param newWidth
	 * @param newHeight
	 * @param baseImageCell
	 */
	public GridObject(int newWidth, int newHeight, BufferedImage baseImageCell) {
		newRGBArray = new int[newWidth][newHeight][4];
		
		oldPixels =((DataBufferByte) baseImageCell.getRaster().getDataBuffer()).getData();
		baseRBGArray = new int[baseImageCell.getWidth()][baseImageCell.getHeight()][4];
		hasAlpha = baseImageCell.getAlphaRaster() != null;
		
		if(hasAlpha) pixelDataLength = 4;
		else pixelDataLength = 3;
		
		generateNewCell();
	}
	
	public void generateNewCell() {
		newPixels = oldPixels;
		newCell = baseCell;
	}
	
	public byte[] getNewImage() {
		return newPixels;
	}
	
	public BufferedImage getNewCell() {
		return newCell;
	}
}
