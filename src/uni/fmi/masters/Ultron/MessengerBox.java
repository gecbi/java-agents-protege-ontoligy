package uni.fmi.masters.Ultron;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JInternalFrame;

public class MessengerBox extends JFrame{
	
private static final long serialVersionUID = 19L;
	
	
	
	private JTextField messageSpace;
	
	private boolean sendMessage;
	
	public boolean takeAction() { 
		return sendMessage; 
		}
	
	public void createAction(boolean action) {
		sendMessage = action; 
		}
	
	public MessengerBox() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(680, 386);
		getContentPane().setLayout(new FlowLayout());
		
		messageSpace = new JTextField(20);
		getContentPane().add(messageSpace);
		
		JButton buton = new JButton("sned me if you want..");
		buton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sendMessage = true;
			}
		} );
		
		getContentPane().add(buton);
	}
	public String getFieldValue() {
		return messageSpace.getText();
		}

}
