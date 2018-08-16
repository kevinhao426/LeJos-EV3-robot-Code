import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
//import java.awt.Toolkit;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/*PANEL FOR CAMERA I,E USING THIS PANEL TO ZOOM IN AND ZOOM OUT*/

public class MapPanel extends JPanel
{	
	/*DEFINING VARIABLE AND COMPONENTS*/
	 public BufferedImage image;
	 public int x;
	 public int y;
	 public JSlider js =null;
	 public JLabel la= null;
	 
	 
	 public JLabel la2= null;
	 
	 
	 public JSpinner spinner = null ;
	 public float scale =(float) 0.5;
	 
	 
	 public MapPanel() 
	 {
		 init();
		 
	 }
	 
	 public void init()
	 {   
		 /*DEFINING LAYOUT AS I AM USING NULL LAYOUT SO USING CO-ORDINATE I AM SETTING THE COMPONENT POSITION ALSO DEFINING SPINNER
		  *FOR IMAGE ZOOM IN AND ZOOM OUT */
		 setLayout(null);		 
		 SpinnerNumberModel model = new SpinnerNumberModel(0.18, 0.1, 1.5, .01);
	     spinner = new JSpinner(model);
	     spinner.setPreferredSize(new Dimension(45, spinner.getPreferredSize().height));
	     spinner.setName("spin");			     
		 spinner.setBounds(130, 460, 60, 25);  
		 add(spinner);		 		 		 
		 la =new JLabel("Zoom In/Out:");
		 la.setBounds(30, 250, 450, 450);
		 add(la); 		 
		 add(image);		 
		 repaint();	
		 
		 
		 la2 =new JLabel("Download");
		 la2.setBounds(350,420,800,100);
		 add(la2);
		 add(image);
		 repaint();	
		 
		 
		 Component[] comps = spinner.getEditor().getComponents();
		 for (Component component : comps) {
		     component.setFocusable(false);
		 }
	 }
	    	    
	    private void add(BufferedImage image2) {
		// TODO Auto-generated method stub
		
	}
	    /*PAINT COMPONENT TO PUT IMAGE ON THE PANEL BY FETCHING THE IMAGE FILE AND FETCHING THE DETAILS TO PUT IMAGE IN PROPER POSITON
	     * AND DO ZOOM IN ZOOM OUT ACCORDINGLY*/
		@Override
	    public void paintComponent(Graphics g)
	    {  
	    	super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D)g;
	        String filename= "map.png";
            try {
				image = ImageIO.read(new File(filename));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}      
	        
	        
	        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	        int w = getWidth();
	        int h = getHeight();
	        int imageWidth = image.getWidth();
	        int imageHeight = image.getHeight();
	        double x = (w - scale * imageWidth)/2;
	        double y = (h - scale * imageHeight)/2;
	        AffineTransform at = AffineTransform.getTranslateInstance(x,y);
	        at.scale(scale, scale);
	        g2.drawRenderedImage(image, at);
	        	        	        
	    }
	    
		/*BELOW METHODS WILL GET THE VALUE FROM SPINNER COMPONENT,VALUE WILL BE PASSED FROM MAINFRAME CLASS WHEN SPINEER STATE IS CHANGE*/
	    public Dimension getPreferredSize()
	    {
	        int w = (int)(scale * image.getWidth());
	        int h = (int)(scale * image.getHeight());
	        return new Dimension(w, h);
	    }
	  
	    public void setScale(float s)
	    {
	        scale = s;
	        revalidate();      // update the scroll pane
	        repaint();
	    }
	    

}
