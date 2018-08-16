import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Robopanel extends JPanel {

	public Robopanel() {   

	}
	
	public String Turnleft()         
	{
		return "Turnleft";
		
	}

	public String Turnright() {       

		return "Turnright";
		
	}
	
	public String Up() {         

		return "Up";
		
	}
	
	public String Down() {       

		return "Down";
		
	}
	
	public String Stop() {        
		return "Stop";
		
	}
	
	public String Shift() {        

		return "Auto";
		
	}
	
	public String reTurnleft()         
	{
		
		return "Stop";
		
	}

	public String reTurnright() {        

		return "Stop";
		
	}
	
	public String reUp() {       

		return "Stop";
		
	}
	
	public String reDown() {         

		return "Stop";
		
	}


}
