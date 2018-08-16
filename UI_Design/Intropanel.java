import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Intropanel extends JPanel
{

JLabel speedmsg=null;
JLabel speedmsg1=null;
JLabel speedmsg2=null;
JLabel speedmsg3=null;



	public Intropanel()
	{
		
		setLayout(null);
		repaint();
		
		speedmsg= new JLabel();
		speedmsg.setText("Left Key");
		speedmsg.setBounds(10,25,80,20);
	    add(speedmsg);

	    speedmsg1= new JLabel();
		speedmsg1.setText("Turnleft");
		speedmsg1.setBounds(100,25,80,20);
	    add(speedmsg1);
	    
	    speedmsg2= new JLabel();
		speedmsg2.setText("Right Key");
		speedmsg2.setBounds(10,45,80,20);
	    add(speedmsg2);
	    
	    speedmsg3= new JLabel();
		speedmsg3.setText("Turnright");
		speedmsg3.setBounds(100,45,80,20);
	    add(speedmsg3);
	    
	    speedmsg= new JLabel();
		speedmsg.setText("Up Key");
		speedmsg.setBounds(10,65,80,20);
	    add(speedmsg);

	    speedmsg1= new JLabel();
		speedmsg1.setText("Forward");
		speedmsg1.setBounds(100,65,80,20);
	    add(speedmsg1);
	    
	    speedmsg2= new JLabel();
		speedmsg2.setText("Down Key");
		speedmsg2.setBounds(10,85,80,20);
	    add(speedmsg2);
	    
	    speedmsg3= new JLabel();
		speedmsg3.setText("Back");
		speedmsg3.setBounds(100,85,80,20);
	    add(speedmsg3); 
	    
	    
	    // map
	    speedmsg3= new JLabel();
		speedmsg3.setText("Black: Boundary");
		speedmsg3.setBounds(220,25,100,20);
	    add(speedmsg3);
	    
/*	    speedmsg= new JLabel();
		speedmsg.setText("Black: boundary");
		speedmsg.setBounds(300,25,80,20);
	    add(speedmsg);*/

	    speedmsg1= new JLabel();
		speedmsg1.setText("Red: NGZ");
		speedmsg1.setBounds(220,45,100,20);
	    add(speedmsg1);
	    
/*	    speedmsg2= new JLabel();
		speedmsg2.setText("3: Green");
		speedmsg2.setBounds(300,45,80,20);
	    add(speedmsg2);*/
	    
	    speedmsg3= new JLabel();
		speedmsg3.setText("Green: Primary radiation zone");
		speedmsg3.setBounds(220,65,200,20);
	    add(speedmsg3);
	    
/*	    speedmsg= new JLabel();
		speedmsg.setText("5: Red");
		speedmsg.setBounds(300,65,80,20);
	    add(speedmsg);
*/
	    speedmsg1= new JLabel();
		speedmsg1.setText("Yellow: Secondary radiation zone");
		speedmsg1.setBounds(220,85,200,20);
	    add(speedmsg1);
	    
	    speedmsg2= new JLabel();
		speedmsg2.setText("Blue: Tertiary radiation zone");
		speedmsg2.setBounds(220,105,200,20);
	    add(speedmsg2);
	    		
	}
	
	/*PAINT METHOD*/
	 @Override
		public void paintComponent(Graphics g) 
	    {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
		    g.drawLine(185,15,185,99);
			
		}

}
