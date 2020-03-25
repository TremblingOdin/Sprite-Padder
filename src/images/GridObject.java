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
		this.newRGBArray = new int[newWidth][newHeight][4];
		this.baseCell = baseImageCell;
		
		this.oldPixels =((DataBufferByte) baseImageCell.getRaster().getDataBuffer()).getData();
		this.baseRBGArray = new int[baseImageCell.getWidth()][baseImageCell.getHeight()][4];
		this.hasAlpha = baseImageCell.getAlphaRaster() != null;
		
		if(this.hasAlpha) this.pixelDataLength = 4;
		else this.pixelDataLength = 3;
		
		generateNewCell();
	}
	
	public void generateNewCell() {
		this.newPixels = this.oldPixels;
		this.newCell = this.baseCell;
	}
	
	public byte[] getNewImage() {
		return this.newPixels;
	}
	
	public BufferedImage getNewCell() {
		return this.newCell;
	}
}
