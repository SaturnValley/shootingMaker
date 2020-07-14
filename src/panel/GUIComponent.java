package panel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class GUIComponent {
	static public JLabel createWhiteTextLabel(Font f, String text) {
	    JLabel l=new JLabel(text);
	    l.setBackground(Color.BLACK);
	    l.setForeground(Color.WHITE);
	    l.setFont(f);
	    return l;
	}

	static public JLabel createBlackTextLabel(Font f, String text) {
	    JLabel l=new JLabel(text);
	    l.setBackground(Color.WHITE);
	    l.setForeground(Color.BLACK);
	    l.setFont(f);
	    return l;
	}

}
