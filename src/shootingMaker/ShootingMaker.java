package shootingMaker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import manager.Manager;
import method.Const;

public class ShootingMaker extends JFrame{
	static Manager manager;
	//private static JPanel mainPanel;

	ShootingMaker(){
		manager=new Manager(this);
		this.setResizable(false);
		manager.setTitle();;
		this.setVisible(true);
		this.setBounds(100, 100, Const.showWidth, Const.showHeight);

	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    JMenuBar menubar = new JMenuBar();
	    JMenu menu1 = new JMenu("タイトルに戻る");
	    menubar.add(menu1);

	    JMenuItem menuitem1 = new JMenuItem("タイトルに戻る");
	    menu1.add(menuitem1);
	    menuitem1.addActionListener(new backTitle());
	    this.setJMenuBar(menubar);

	}

	public static void main(String[] args) {
		new ShootingMaker();
	}

	public void setMainPanel(JPanel mainPanel) {
		this.getContentPane().removeAll();
		this.add(mainPanel);
		this.validate();
		this.repaint();
		mainPanel.requestFocusInWindow();
	}

	public class backTitle implements ActionListener{//あとでstaticは外す
		public void actionPerformed(ActionEvent e) {
			manager.setTitle();
			//System.out.println("title");
		}
	}

	//ほかのモードジャンプも追加するかも

}
