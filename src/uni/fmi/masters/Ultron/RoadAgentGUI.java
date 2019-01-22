package uni.fmi.masters.Ultron;

import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import uni.fmi.masters.Ultron.FirstAgent;
import uni.fmi.masters.Ultron.SecoundAgent;
import uni.fmi.masters.Ultron.RoadAgentGUI;
import uni.fmi.masters.Ultron.Go;

public class RoadAgentGUI extends JFrame {

	private static final long serialVersionUID = -7255509239879256175L;

	public RoadAgentGUI(SecoundAgent secoundAgent) {
		super(secoundAgent.getLocalName() + " - ontology settings");
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				secoundAgent.doDelete();
			}
		});
		
		Container cp = this.getContentPane();
		
		JTextField ontologyPathTextField = new JTextField();
		ontologyPathTextField.setMaximumSize(
				new Dimension(Integer.MAX_VALUE, ontologyPathTextField.getPreferredSize().height)
		);		
				
		JButton browseButton = new JButton("...");
		final JFileChooser fc = new JFileChooser();
		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				 if (fc.showOpenDialog(RoadAgentGUI.this) == JFileChooser.APPROVE_OPTION) {
					 File f = fc.getSelectedFile();
					 ontologyPathTextField.setText(f.getAbsolutePath());
				 }
			}
		});
		
		Box box = Box.createVerticalBox();
		box.add(new JLabel("Road/Ultron ontology file:"));
		
		Box inner2 = Box.createHorizontalBox();
		inner2.add(Box.createRigidArea(new Dimension(5, 1)));
		inner2.add(ontologyPathTextField);
		inner2.add(Box.createRigidArea(new Dimension(5, 1)));
		inner2.add(browseButton);
		inner2.add(Box.createRigidArea(new Dimension(5, 1)));
		
		box.add(inner2);
		box.add(Box.createRigidArea(new Dimension(5, 2)));
		
		Box inner3 = Box.createHorizontalBox();
		inner3.add(Box.createHorizontalGlue());

		JButton bOk = new JButton("Set");
		inner3.add(bOk);
		inner3.add(Box.createRigidArea(new Dimension(5, 2)));
		
		box.add(inner3);
		
		bOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File f = new File(ontologyPathTextField.getText());
				if (f.exists() && secoundAgent.setOntoFile(f)) {
					JOptionPane.showMessageDialog(RoadAgentGUI.this, "Successfully set ontology!", "Success",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(RoadAgentGUI.this, "The specified file cannot be set!",
							"File Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		cp.add(box);
		
		this.setSize(350, 120);
		this.setMaximumSize(this.getSize());
		this.setMinimumSize(this.getSize());
		this.setResizable(false);
	}
}
