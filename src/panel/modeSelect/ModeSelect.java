package panel.modeSelect;

import java.awt.Color;
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
import javax.swing.border.LineBorder;

import manager.PanelManager;
import method.Const;
import panel.GUIComponent;

public class ModeSelect extends JPanel{
	static PanelManager manager;
	static Image img;
	static JLabel l1;
	static JLabel l2;
	static JLabel l3;
	static String state;
	static final private String SELECT="ステージで遊ぶ";
	static final private  String CREATE="ステージを作る";
	static final private  String BACK="タイトルに戻る";



	public ModeSelect(PanelManager manager){
		Font f=new Font("ＭＳ ゴシック", Font.PLAIN, 32);
		this.setSize(Const.showWidth, Const.showHeight);
	    img=new ImageIcon(getClass().getClassLoader().getResource(Const.imagePath+Const.TITLE_IMAGE)).getImage();
		ModeSelect.manager=manager;
	    this.addKeyListener(new keyListener());//キーイベントの登録
	    //this.setFocusable(true);
	    this.setLayout(null);
	    //ステージ選択へ遷移するラベル
	    l1=createModeSelectLabel(0,SELECT, f);
	    //ステージ作成に遷移するラベル
	    l2=createModeSelectLabel(1, CREATE,f);
	    //タイトルへ戻るラベル
	    l3=createModeSelectLabel(2, BACK, f);
	    init();
	}

	public void init() {
		labelOn(l1);
		//System.out.println("init state "+ state);
	}

	private JLabel createModeSelectLabel(int col, String text, Font f) {
	    JLabel l=GUIComponent.createWhiteTextLabel(f, text);
		l.setBounds(this.getSize().width/2-f.getSize()*l.getText().length()/2, this.getSize().height*2/3 + f.getSize()*3*col, (int)(f.getSize()*l.getText().length()*1.1),f.getSize());
		this.add(l);
		l.addMouseListener(new clickListener());
		return l;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int w=this.getSize().width;
		int h=this.getSize().height;
		g2d.drawImage(img, 0, 0, w, h, this);
	}

	static private void labelAllOff() {
		l1.setBorder(null);
		l2.setBorder(null);
		l3.setBorder(null);
	}

	static private void labelOn(JLabel l) {//ラベルの枠線と現在のモードを同期
		labelAllOff();
		l.setBorder(new LineBorder(Color.RED, 1, true));
		state=l.getText();
	}

	private class keyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			char key=e.getKeyChar();
			int keyCode=e.getKeyCode();
	        if (key == ' ') {//スペースキー
	        	if(state==SELECT) {
	        		manager.initStageSelect();
	        		manager.setStageSelect();
	        	}else if(state==CREATE) {
	        		manager.initCreateStage();
	        		manager.setCreateStage();
	        	}else if(state==BACK) {
	        		manager.setTitle();
	        	}
	        }
			// TODO 自動生成されたメソッド・スタブ
			if(state.equals(SELECT)) {
		        if(key=='s' || keyCode==KeyEvent.VK_DOWN) {//下
					labelOn(l2);
		        }

			}else if(state.equals(CREATE)) {
		        if(key=='w' || keyCode==KeyEvent.VK_UP) {//上
					labelOn(l1);
		        }
		        if(key=='s' || keyCode==KeyEvent.VK_DOWN) {//下
					labelOn(l3);
		        }
			}else if(state.equals(BACK)) {
		        if(key=='w' || keyCode==KeyEvent.VK_UP) {//上
					labelOn(l2);
		        }
			}
			if(key=='t') {
				manager.setTitle();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
		}
	}

	private class clickListener extends MouseAdapter{
	    public void mouseClicked(MouseEvent e){
	    	JLabel label= (JLabel)e.getComponent();
	    	String mode=label.getText();
	    	//System.out.println("ModeSelect click",mode);
	    	if(mode.equals(ModeSelect.SELECT)) {//ステージ選択へ
	    		manager.initStageSelect();
	    		manager.setStageSelect();
	    	}else if(mode.equals(ModeSelect.CREATE)) {//ステージ作成へ
	    		manager.initCreateStage();
	    		manager.setCreateStage();
	    	}else if(mode.equals(ModeSelect.BACK)) {//タイトルへ
	    		manager.setTitle();
	    	}
	    }

	    public void mouseEntered(MouseEvent e){
	    	JLabel label= (JLabel)e.getComponent();
	    	ModeSelect.labelOn(label);
	    }
	}

}
