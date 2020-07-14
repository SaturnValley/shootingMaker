package panel.gameClear;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import manager.PanelManager;
import method.Const;
import panel.GUIComponent;

public class GameClear extends JPanel{
	static PanelManager manager;

	public GameClear(PanelManager manager) {

		this.setSize(Const.showWidth, Const.showHeight);
		this.addMouseListener(new clickListener());
		GameClear.manager=manager;
		this.setLayout(null);
		this.addKeyListener(new keyListener());


		Font f=new Font("ＭＳ ゴシック", Font.PLAIN, 32);
		createLabel(-1,"ゲームクリア",f);
		createLabel(1,"ボタンを押してタイトルに戻る",f);

	}

	private JLabel createLabel(int col, String text, Font f) {
	    JLabel l=GUIComponent.createBlackTextLabel(f, text);
		l.setBounds(this.getSize().width/2-f.getSize()*l.getText().length()/2, this.getSize().height/2 + f.getSize()*col, (int)(f.getSize()*l.getText().length()*1.1),f.getSize());
		//System.out.println((this.getSize().width/2-f.getSize()*l.getText().length()/2)+","+ (this.getSize().height/2 + f.getSize()*col)+ ","+((int)(f.getSize()*l.getText().length()*1.1))+f.getSize());
		this.add(l);
		l.addMouseListener(new clickListener());
		return l;
	}

	private class keyListener implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			manager.setTitle();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
		}
	}
	private class clickListener extends MouseAdapter{
	    public void mouseClicked(MouseEvent e){
	    	manager.setTitle();
	    }
	}

}
