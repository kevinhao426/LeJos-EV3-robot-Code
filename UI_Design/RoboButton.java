import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;


public class RoboButton extends JPanel
{

    public JButton Turnleft = null;
	public JButton Turnright = null;

	public JButton Stop =null;
	public JButton Up=null;
	public JButton Down=null;
	public JButton Shift=null;

	public Image image;
		
    public JLabel color=null;
    public JLabel ultrasonic=null;
    public JLabel cla1=null;
    public JLabel cla2=null;
    public JLabel tempe1=null;
    public JLabel tempe2=null;
    
    
    public JSeparator s=null;
    GridBagConstraints gbc = new GridBagConstraints();
    
    public RoboButton(){
    setLayout(null);
    
    ImageIcon forr = new ImageIcon("forward.png");    
    Turnleft= new JButton("",forr);
    Turnleft.setBounds(80,70,60,40);
    add(Turnleft);
    Turnleft.setName("Turnleft");
    Turnleft.setFocusable(false);
    
    ImageIcon back = new ImageIcon("back.png"); 	
    Turnright= new JButton("",back);
    Turnright.setBounds(220,70,60,40);
    add(Turnright);
    Turnright.setName("Turnright");
    Turnright.setFocusable(false);
    
    ImageIcon u = new ImageIcon("up.png"); 
    Up= new JButton("",u);
    Up.setBounds(150,20, 60, 40);
    add(Up);
    Up.setName("Up");
    Up.setFocusable(false);
    
    ImageIcon d = new ImageIcon("down.png");
    Down= new JButton("",d);
    Down.setBounds(150, 120, 60, 40);
    add(Down);
    Down.setName("Down");
    Down.setFocusable(false);
 
    ImageIcon stoo = new ImageIcon("stop.png"); 
    Stop= new JButton("",stoo);
    Stop.setBounds(30, 230, 100, 80);
    add(Stop);
    Stop.setName("Stop");
    Stop.setFocusable(false);

    cla1= new JLabel();
    cla1.setText("Stop button");
    cla1.setBounds(50,200, 100, 20);
    add(cla1);
    
    cla2= new JLabel();
    cla2.setText("Auto / Man");
    cla2.setBounds(220,200, 100, 20);
    add(cla2);
    
    ImageIcon o = new ImageIcon("open.png");
    Shift= new JButton("",o);
    Shift.setBackground(Color.white);
    Shift.setBounds(200,230, 100, 80);
    add(Shift);
    Shift.setName("Shift");
    Shift.setFocusable(false);
    
    ultrasonic= new JLabel();
    ultrasonic.setText("Ultrasonic Sensor");
    ultrasonic.setBounds(50,350, 150, 20);
    add(ultrasonic);
    
    tempe1= new JLabel();
    tempe1.setText("Value");
    tempe1.setBounds(30,400, 150, 20);
    add(tempe1);

    color= new JLabel();
    color.setText("Color Sensor");
    color.setBounds(220,350, 100, 20);
    add(color);
    
    tempe2= new JLabel();
    tempe2.setText("Value");
    tempe2.setBounds(200,400, 100, 20);
    add(tempe2);
   } 
    
    public void colorValue(String scolor)
    { 
     tempe1.setText(" " +scolor);
     repaint();
    }
    
    public void ultrasonicValue(String sdistance)
    {
     
     tempe2.setText(" " +sdistance);
     repaint();
    }
   

}
