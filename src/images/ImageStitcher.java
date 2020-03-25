package images;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageStitcher {
	BufferedImage bigImage;
	
	/**
	 * Given the array of GridObjects, get the piece byte arrays and put them in one image
	 * @param pieces
	 * @throws IOException
	 */
	public ImageStitcher(GridObject[] pieces, int columns, int width, int height) throws IOException {
		//double totalSize = 0;
		/*for(int i = 0; i < pieces.length; i++) {
			totalSize += pieces[i].getNewImage().length;
		}*/
		
		int index = 0;
		//byte[] bigArray = new byte[totalSize];
		//for(int i = 0; i < pieces.length; i++) {
		//	int newIndex = index + pieces[i].getNewImage().length;
		//	System.arraycopy(pieces[i].getNewImage(), 0, bigArray, index, newIndex);
		//}
		
		//ByteArrayInputStream bais = new ByteArrayInputStream(bigArray);
		
		//bigImage = ImageIO.read(bais);
		
		ArrayList<ArrayList<BufferedImage>> imageStrips = new ArrayList<ArrayList<BufferedImage>>();
		
		int maxSize = pieces.length/columns;
		while(index < (int)(maxSize)) {
			ArrayList<BufferedImage> imageStrip = new ArrayList<BufferedImage>();
			for(int i = 0; i < columns; i++) {
				imageStrip.add(pieces[i+(columns*index)].getNewCell());
			}
			imageStrips.add(imageStrip);
			index++;
		}
		
		ArrayList<BufferedImage> rows = stitchHorizontal(imageStrips, width);
		bigImage = stitchVertical(rows, height);
	}
	
	/**
	 * Returns the final image
	 * @return BufferedImage
	 */
	public BufferedImage getBigImage() {
		return bigImage;
	}
	
	/**
	 * Puts together each row of images from the individual cells
	 * @param imageStrips
	 * @param width
	 * @return ArrayList<BufferedImage>
	 */
	private ArrayList<BufferedImage> stitchHorizontal(ArrayList<ArrayList<BufferedImage>> imageStrips, int width) {
		ArrayList<BufferedImage> rows = new ArrayList<BufferedImage>();
		int maxWidth = imageStrips.get(0).size() * width;
		int height = imageStrips.get(0).get(0).getHeight();
		
		for(int i = 0; i < imageStrips.size(); i++) {
			BufferedImage newImage = new BufferedImage(maxWidth, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = newImage.createGraphics();
			
			for(int j = 0; j < imageStrips.get(i).size(); j++) {
				g.drawImage(imageStrips.get(i).get(j), null, j*width, 0);
			}
			g.dispose();
			rows.add(newImage);
		}
		
		return rows;
	}
	
	/**
	 * Puts together a final image from rows of images
	 * The sheer size of some images discourages using arrays, use arraylists
	 * @param rows
	 * @param height
	 * @return BufferedImage
	 */
	private BufferedImage stitchVertical(ArrayList<BufferedImage> rows, int height) {
		int width = rows.get(0).getWidth();
		int maxHeight = height * rows.size();
		
		BufferedImage bigDaddy = new BufferedImage(width, maxHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bigDaddy.createGraphics();
		
		for(int i = 0; i < rows.size(); i++) {
			g.drawImage(rows.get(i), null, 0, i*height);
		}
		
		return bigDaddy;
	}
}
