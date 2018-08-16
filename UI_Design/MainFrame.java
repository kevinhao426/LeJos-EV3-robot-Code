import java.awt.Container;
import java.awt.EventQueue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame implements MouseListener, ChangeListener {

	Robopanel r = null;
	Intropanel k = null;
	RoboButton b = null;
	MapPanel i = null;

	public float scale;
	public int motion;
	public JLabel speedmsg = null;
	public JLabel speedmsg1 = null;
	public JLabel speedmsg2 = null;
	public JLabel speedmsg3 = null;

	public String sentence;
	public String modifiedSentence;
	public static Socket clientSocket;
	public static DataOutputStream outToServer;
	public static BufferedReader inFromServer;

	public MainFrame() {
		startGUI();

	}

	private void startGUI() {
		Container content = getContentPane();
		content.setLayout(null);

		r = new Robopanel();
		r.setBounds(450, 0, 340, 150);
		r.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Motion"));
		content.add(r);

		i = new MapPanel();
		i.setBounds(0, 0, 450, 500);
		content.add(i);
		i.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Map"));

		b = new RoboButton();
		b.setBounds(450, 150, 340, 500);
		content.add(b);
		b.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Robot Control"));

		k = new Intropanel();
		k.setBounds(0, 500, 450, 150);
		content.add(k);
		k.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Instruction"));

		b.Turnleft.addMouseListener(this);
		b.Turnright.addMouseListener(this);
		// b.Left.addMouseListener(this);
		// b.Right.addMouseListener(this);
		b.Stop.addMouseListener(this);
		b.Up.addMouseListener(this);
		b.Down.addMouseListener(this);
		b.Shift.addMouseListener(this);

		i.spinner.addChangeListener(this);

		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setSize(800, 700);
		setResizable(false);
		setTitle("PG24---Lego EV3");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		JComponent source = (JComponent) arg0.getSource();
		String s = source.getName();

		switch (s) {
		case "Stop":
			sentence = r.Stop();
			try {
				outToServer.writeBytes(sentence + '\n');
			} catch (IOException e) {

				e.printStackTrace();
			}
			rcvData();

			break;

		case "Shift":
			sentence = r.Shift();
			try {
				outToServer.writeBytes(sentence + '\n');
			} catch (IOException e) {

				e.printStackTrace();
			}
			rcvData();
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		JComponent source = (JComponent) arg0.getSource();
		String s = source.getName();

		switch (s) {
		case "Turnleft":
			sentence = r.Turnleft();
			try {
				outToServer.writeBytes(sentence + '\n');
			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		case "Turnright":
			sentence = r.Turnright();
			try {
				outToServer.writeBytes(sentence + '\n');
			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		case "Up":
			sentence = r.Up();
			try {
				outToServer.writeBytes(sentence + '\n');
			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		case "Down":
			sentence = r.Down();
			try {
				outToServer.writeBytes(sentence + '\n');
			} catch (IOException e) {

				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		JComponent source = (JComponent) arg0.getSource();
		String s = source.getName();

		switch (s) {
		case "reTurnleft":

			sentence = r.Stop();
			try {
				outToServer.writeBytes(sentence + '\n');
			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		case "reTurnright":
			sentence = r.Stop();
			try {
				outToServer.writeBytes(sentence + '\n');
			} catch (IOException e) {

				e.printStackTrace();
			}
			break;
		case "reUp":
			sentence = r.Stop();
			try {
				outToServer.writeBytes(sentence + '\n');
			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		case "reDown":
			sentence = r.Stop();
			try {
				outToServer.writeBytes(sentence + '\n');
			} catch (IOException e) {

				e.printStackTrace();
			}
			break;
		default:
			break;
		}

	}

	public static void main(String[] args) {
		// String sentence;
		// String modifiedSentence;

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				MainFrame ex = new MainFrame();
				ex.setVisible(true);

				try {

					clientSocket = new Socket("10.0.1.1", 1111);
					inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					outToServer = new DataOutputStream(clientSocket.getOutputStream());

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

	}

	@Override
	public void stateChanged(ChangeEvent arg0) {

		JComponent source = (JComponent) arg0.getSource();
		String s = source.getName();

		switch (s) {

		case "spin":
			float scale = ((Double) i.spinner.getValue()).floatValue();
			i.setScale(scale);
			break;

		}
	}

	public void rcvData() {
		String[] data = null;
		try {

			sentence = inFromServer.readLine();
			data = sentence.split(" ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		b.colorValue(data[1]);
		b.ultrasonicValue(data[0]);
	}

}
