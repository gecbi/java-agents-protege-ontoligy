package uni.fmi.masters.Ultron;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import uni.fmi.masters.Ultron.SecoundAgent;
import uni.fmi.masters.RoadOnto.Box;
import uni.fmi.masters.Ultron.FirstAgent;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import java.awt.Window.Type;

public class TestWindow {

	JFrame frmChoseTheObject;
	private JLabel lblTheObjectYou;
	private JTextField txtPutYouEver;
	private JButton btnPushTheButton;
	private JLabel lblNewLabel;
	private JTextArea classTextArea;
	private JTextArea textArea;
	
	private Box v;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestWindow window = new TestWindow();
					window.frmChoseTheObject.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */

	
	public TestWindow() {
		initialize();
		createEvents();
		
		
	}

	
	/////////////////////////////////////////////////////////////////////
	/// This code create components!
	////////////////////////////////////////////////////////////////////
	private void initialize() {
		frmChoseTheObject = new JFrame();
		frmChoseTheObject.setIconImage(Toolkit.getDefaultToolkit().getImage(TestWindow.class.getResource("/uni/fmi/masters/Resourses/radar_128.png")));
		frmChoseTheObject.setTitle("What Objects Are");
		frmChoseTheObject.setBounds(100, 100, 731, 347);
		frmChoseTheObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		lblTheObjectYou = new JLabel("The Object You Are Looking For:");
		lblTheObjectYou.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		txtPutYouEver = new JTextField();
		txtPutYouEver.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtPutYouEver.setText("put your credit card number here :) ...");
		txtPutYouEver.setColumns(10);
		
		btnPushTheButton = new JButton("Push the button..");
		btnPushTheButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		
		classTextArea = new JTextArea();
		classTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		textArea = new JTextArea();
		textArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		lblNewLabel = new JLabel("Class of the Object");
		lblNewLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JLabel lblNewLabel_1 = new JLabel("Properties of the Object");
		lblNewLabel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GroupLayout groupLayout = new GroupLayout(frmChoseTheObject.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblTheObjectYou)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnPushTheButton, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtPutYouEver, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
						.addComponent(classTextArea, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
						.addComponent(textArea, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTheObjectYou)
						.addComponent(txtPutYouEver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnPushTheButton)
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(classTextArea, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
					.addContainerGap())
		);
		frmChoseTheObject.getContentPane().setLayout(groupLayout);
	}
	
	/////////////////////////////////////////////////////////////////////
	/// This code create events!
	////////////////////////////////////////////////////////////////////
	
	private void createEvents() {
		btnPushTheButton.addActionListener(new ActionListener() {
			
			
			private List<String> b;

	
			//@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				act = true;
				
//				b = new ArrayList<String>();
//				
//				this.b=  (List<String>) ((List<Object>) (agent.v.getData())).stream().map(item  -> {
//					return (String) item;
//				});
//				String c = String.join(", ", b);
//				classTextArea.setText(c);
//			
//				classTextArea.setText(b.toString());
//				
//				return;
				
			}
			
			
			
		});
		
	}
	
	public void setTOF (String text) {
		classTextArea.setText(text);
		
		
	}
	
	public void setTPF (String text2) {
		textArea.setText(text2);
		
		
	}

private boolean act= false;

	public boolean takeAction() {
		
		return act;
	}


	public void createAction(boolean b) {
		act = b;
		
	}


	public String getFieldValue() {
	return	txtPutYouEver.getText();

	}
}
