package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class PicFullViewPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private int x ; 
	private int y ;
	private Image img;
	
	public PicFullViewPanel(Image img,int x , int y) {
		this.x = x;
		this.y = y;
		this.img = img;
		Dimension size;
		size = new Dimension(img.getWidth(null),img.getHeight(null)); 
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setLayout(null);  
	}
    public void paintComponent(Graphics g){
	   g.drawImage(img, x, y, null);
    }
	
}
