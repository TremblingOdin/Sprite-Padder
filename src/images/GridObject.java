package images;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import com.sun.prism.paint.Color;

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
		
		generateNewCell(newWidth, newHeight);
	}
	
	/**
	 * Check if the new width is less than the old width. 
	 * If so delete outer pixels, if not fill outer pixels with their adjacent pixel.
	 * Check if the new height is less than the old height.
	 * If so delete outer pixels, if not fill outer pixels with their adjacent pixel,
	 */
	private void generateNewCell(int newWidth, int newHeight) {
		this.newPixels = this.oldPixels;
		int width = this.baseCell.getWidth();
		int height = this.baseCell.getHeight();
		
		newCell = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		
		boolean widthSmaller = newWidth < width;
		boolean heightSmaller = newHeight < height;
		
		int widthOffset;
		int heightOffset;
		
		if(widthSmaller) {
			widthOffset = (width - newWidth)/2;
			
			if(heightSmaller) {
				heightOffset = (height - newHeight)/2;
				for(int i = 0; i < newHeight; i++) {
					for(int j = 0; j < newWidth; j++) {
						newCell.setRGB(j, i, baseCell.getRGB(j+widthOffset, i+heightOffset));
					}
				}
				
			} else {
				heightOffset = (newHeight - height)/2;
				int heightIndex = 0;
				
				//Fill in the extra height on the top and bottom with the last pixels in the base image, while shrinking the size 
				while(heightIndex < heightOffset) {
					for(int i = 0; i < newWidth; i++) {
						newCell.setRGB(i, heightIndex, baseCell.getRGB(i+widthOffset, 0));
						newCell.setRGB(i, newHeight-heightIndex-1, baseCell.getRGB(i+widthOffset, height-1));
					}
					
					heightIndex++;
				}
				
				//Offset the width, then fill in the new cells information
				for(int i = 0; i < height; i++) {
					for(int j = 0; j < newWidth; j++) {
						newCell.setRGB(j,i+heightIndex, baseCell.getRGB(j+widthOffset, i));
					}
				}
			}
			
		} else {
			widthOffset = (newWidth - width)/2;
			
			if(heightSmaller) {
				heightOffset = (height - newHeight)/2;
				int widthIndex = 0;
				
				while(widthIndex < widthOffset) {
					for(int i = 0; i < newHeight; i++) {
						newCell.setRGB(widthIndex, i, baseCell.getRGB(0, i+heightOffset));
						newCell.setRGB(newWidth-widthIndex-1, i, baseCell.getRGB(width-1, i+heightOffset));
					}
					
					widthIndex++;
				}
				
				//offset the height, then iterate through the new rows until you reach the new height
				for(int i = 0; i < newHeight; i++) {
					for(int j = 0; j < width; j++) {
						newCell.setRGB(j+widthIndex, i, baseCell.getRGB(j, i+heightOffset));
					}
				}
				
			} else {
				heightOffset = (newHeight-height)/2;
				int heightIndex = 0;
				int widthIndex = 0;
				
				//Add the extra pixels on the width
				//height is the base cell height because we're going to go back through the top and bottom after we setup the width
				while(widthIndex < widthOffset) {
					for(int i = 0; i < height; i++) {
						newCell.setRGB(widthIndex, i+heightOffset, baseCell.getRGB(0, i));
						newCell.setRGB(newWidth-widthIndex-1, i+heightOffset, baseCell.getRGB(width-1, i));
					}
					
					widthIndex++;
				}
				
				//Fill in the inner square
				for(int i = 0 + widthOffset; i < newWidth - widthOffset; i++) {
					for(int j = 0 + heightOffset; j < newHeight - heightOffset; j++) {
						newCell.setRGB(i, j, baseCell.getRGB(i-widthOffset, j-heightOffset));
					}
				}
				
				//Add the extra pixels on the height
				//THIS NEEDS TO COME LAST
				//BECAUSE IT RELIES OF THE IMAGE BEING FILLED
				//DON'T FORGET
				while(heightIndex < heightOffset) {
					for(int i = 0; i < newWidth; i++) {
						newCell.setRGB(i, heightIndex, newCell.getRGB(i, heightOffset));
						newCell.setRGB(i, newHeight-heightIndex-1, newCell.getRGB(i, height-1));
					}
					
					heightIndex++;
				}
			}
		}
	}
	
	/**
	 * Return the byte array.
	 * Using bytes might be more effective, will pursue this after bas functionality is established.
	 * @return byte[]
	 */
	public byte[] getNewImage() {
		return this.newPixels;
	}
	
	/**
	 * return the altered image cell,
	 * @return BufferedImage
	 */
	public BufferedImage getNewCell() {
		return this.newCell;
	}
}
