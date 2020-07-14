package panel.title;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import manager.PanelManager;
import method.Const;
import panel.GUIComponent;

public class Title extends JPanel{

	static PanelManager manager;
	static Image img;
	static JLabel l;

	public Title(PanelManager manager){
		this.setSize(Const.showWidth, Const.showHeight);
		Title.manager=manager;
	    this.addKeyListener(new keyListener());//キーイベントの登録
	    this.setFocusable(true);
	    //System.out.println("title "+getClass().getClassLoader().getResource(Const.imagePath+Const.TITLE_IMAGE));
	    img=new ImageIcon(getClass().getClassLoader().getResource(Const.imagePath+Const.TITLE_IMAGE)).getImage();
	    Font f=new Font("ＭＳ ゴシック", Font.PLAIN, 32);
	    l=GUIComponent.createWhiteTextLabel(f, "キーボードかマウスを押してください");
	    this.setLayout(null);
		l.setBounds(this.getSize().width/2-f.getSize()*l.getText().length()/2, this.getSize().height*2/3,f.getSize()*l.getText().length(),f.getSize());
		this.add(l);
		this.addMouseListener(new clickListener());

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int w=this.getSize().width;
		int h=this.getSize().height;
		g2d.drawImage(img, 0, 0, w, h, this);
	}

	private class keyListener implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			manager.setModeSelect();//ゲームモード選択
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
		}
	}

	private class clickListener extends MouseAdapter{
	    public void mouseClicked(MouseEvent e){
	    	//System.out.println("Title click");
	    	manager.initModeSelect();
		   	manager.setModeSelect();
	    }
	}
}


