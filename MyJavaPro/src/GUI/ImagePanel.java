package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Constant.Constant;

public class ImagePanel extends JPanel {
	 
	
	private static final long serialVersionUID = 1L;
	private Image img;
	private Dimension size;
	/**
	 * dep.....
	 * @param path
	 * @param x
	 * @param y
	 */
	public ImagePanel(String path,int x,int y) {
		BufferedImage bufferedImage = null;
		File file = new File(path);
		try {
			bufferedImage = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
		}
		img = resizeImg(bufferedImage,10,10);
		size = new Dimension(img.getWidth(null),img.getHeight(null)); 
		this.setSize(Constant.windowWidth,Constant.windowHeight);
		this.setMaximumSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setLayout(null);  
	}
	/**
	 * mainly in use
	 * @param bufferedImage
	 * @param x
	 * @param y
	 */
	public ImagePanel(BufferedImage bufferedImage,int x,int y) {
		img = resizeImg(bufferedImage,Constant.targetPicWidth,Constant.targetPicHeight);
		size = new Dimension(img.getWidth(null),img.getHeight(null)); 
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setLayout(null);  
	}
	
    public void paintComponent(Graphics g){
	   g.drawImage(img, 0, 0, null);
    }
    
    public Image resizeImg(BufferedImage bufferedImage,int thumbWidth,int thumbHeight) {
    	int width = bufferedImage.getWidth(null);
	    int height = bufferedImage.getHeight(null);
	    double i = (double) width / (double) height;
	    double j = (double) thumbWidth / (double) thumbHeight;
	    int[] d = new int[2];
	    int x = 0;
	    int y = 0;
	    if (i > j) {
	    	d[1] = thumbHeight;
	    	d[0] = (int) (thumbHeight * i);
	    	y = 0;
	    	x = (d[0] - thumbWidth) / 2;
	    } else {
	    	d[0] = thumbWidth;
	    	d[1] = (int) (thumbWidth / i);
	    	x = 0;
	    	y = (d[1] - thumbHeight) / 2;
	    }
	    /*等比例缩放*/
	    BufferedImage newImage = new BufferedImage(d[0],d[1],bufferedImage.getType());
	    Graphics g = newImage.getGraphics();
	    g.drawImage(bufferedImage,0,0,d[0],d[1],null);
	    g.dispose();
	    newImage = newImage.getSubimage(x, y, thumbWidth, thumbHeight);
	    //newImage.getGraphics().drawImage(bufferedImage.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH), 0, 0, null);
	    newImage.getGraphics().drawImage(newImage,0,0, null);
	    return newImage;
    }
   
}
