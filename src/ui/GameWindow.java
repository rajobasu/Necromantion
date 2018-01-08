package ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import framework.Constants;

public class GameWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -710554517401167116L;
	private JPanel contentPane;
	private JPanel panel;

	/**
	 * Create the frame.
	 */
	public GameWindow(Canvas canvas) {
		setTitle("Necromantion");
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 30 + Constants.BREADTH * Constants.UNIT_DISTANCE,
				80 + Constants.LENGTH * Constants.UNIT_DISTANCE);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBounds(10, 11, Constants.BREADTH * Constants.UNIT_DISTANCE,
				Constants.LENGTH * Constants.UNIT_DISTANCE);
		contentPane.add(panel);
		panel.setLayout(null);

		// canvas = new Canvas();
		canvas.setBackground(new Color(255, 255, 255));
		canvas.setForeground(new Color(216, 191, 216));
		canvas.setBounds(0, 0, Constants.BREADTH * Constants.UNIT_DISTANCE, Constants.LENGTH * Constants.UNIT_DISTANCE);
		panel.add(canvas);
		
		

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setBounds(10, Constants.LENGTH * Constants.UNIT_DISTANCE+15, Constants.BREADTH*Constants.UNIT_DISTANCE, 30);
		contentPane.add(panel_1);

		JLabel lblProgrammerrajarshiBasu = new JLabel("Programmer ::Rajarshi Basu(2016 mar)");
		lblProgrammerrajarshiBasu.setBackground(Color.DARK_GRAY);
		lblProgrammerrajarshiBasu.setForeground(Color.GRAY);
		lblProgrammerrajarshiBasu.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel_1.add(lblProgrammerrajarshiBasu);
		
//		JPanel panel_2 = new JPanel();
//		panel_2.setBackground(Color.DARK_GRAY);
//		panel_2.setBounds(10, 372, 484, 21);
//		contentPane.add(panel_2);
		
		Image icon=null;
		try {
			icon = ImageIO.read(GameWindow.class.getResource("/GameImage.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		setIconImage(icon);
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});

	}
}
