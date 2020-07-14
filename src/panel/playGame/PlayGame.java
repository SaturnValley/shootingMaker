package panel.playGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import manager.PanelManager;
import method.Const;
import method.LoadFile;
import objOnMap.Collision;
import objOnMap.ObjOnMap;
import objOnMap.body.jiki.Jiki;


public class PlayGame extends JPanel{
	private static boolean isChangeCollisionArea=true;//当たり判定のオンオフができるように，デバッグ用，基本はfalseに
	private static boolean isCollisionArea=false;//当たり判定が見えるようにする
	private static boolean isRader;
	PanelManager manager;
	Timer timer;
	Image map;
	Jiki jiki;//自機との相対位置をとるため
	HashMap<String, Image> images=new HashMap<String, Image>();
	HashMap<ObjOnMap, Image> colligionAreaImage=new HashMap<ObjOnMap, Image>();

	public PlayGame(PanelManager manager){
		this.setSize(Const.showWidth, Const.showHeight);
		this.manager=manager;
	    map = new ImageIcon(getClass().getClassLoader().getResource(Const.imagePath+"xp.png"))//画像読み込み
	    		.getImage().getScaledInstance(Const.showWidth, Const.showHeight, Image.SCALE_REPLICATE);//Imageに変換して小さく変形
	    //System.out.println("PlayGame loadMap "+getClass().getClassLoader().getResource(Const.imagePath+"xp.png"));
		putImage();
		timer = new Timer(1000/60 , new actionListener());//60フレーム
		timer.start();
		init();
		this.addKeyListener(new keyListener());//キーイベントの登録

	}

	public void init() {
		timer.restart();
		isRader=false;
		jiki=manager.returnJiki();//あとでinitの中に移す
	}




	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		/*
		int w=this.getSize().width;
		int h=this.getSize().height;
		*/

		AffineTransform at = g2d.getTransform();

		//平行移動する先の左上座標
		float posX=Const.centerX-jiki.getX();
		float posY=Const.centerY-jiki.getY();

		if(jiki.inXEdge() || jiki.inYEdge()) {//自機が端っこにいるとき，マップの外が移らないように
			//System.out.println("edge");
			if(jiki.inXRightEdge()) {//マップ右端
				posX=-Const.MapX+Const.showWidth;
			}else if(jiki.inXLeftEdge()) {//マップ左端
				posX=0  ;
			}
			if(jiki.inYUpEdge()) {//マップ上橋
				posY=0;
			}else if(jiki.inYDownEdge()) {//マップ下端
				posY=-Const.MapY+Const.showHeight;
			}
		}
		at.setToTranslation(posX,posY);//元座標の原点がここに移動
		g2d.setTransform(at);

		imageMap(g2d);
		image(g2d,jiki);
		collisionPoint(g2d, jiki);
		for (ObjOnMap oom:manager.returnAllObjList()) {//自機の弾
			if(oom.isShow() && !oom.isRemoveFlag()) {
				image(g2d, oom);
				collisionPoint(g2d, oom);
			}
		}

		if(isRader) {
			rader(g2d);
		}
	}


	void imageMap(Graphics2D g2d) {
		g2d.drawImage(this.map,0,0,	Const.MapX, Const.MapY,this);
	}


	void putImage() {
		String[] paths=LoadFile.loadFileNames(Const.imagePath,Const.DATA_JAR_FILE);
		/*
		for(String p:paths) {
			System.out.println("PlayGame putimage "+ p);
		}*/
		if(paths!=null) {
			for(String path:paths) {
				//System.out.println("PlayGame putImage "+path);
				putImage(path);
			}
		}else {
			System.out.println("null");
		}
	}

	Image loadImage(String path) {
		//System.out.println("PlayGame loadImage "+path+","+getClass().getClassLoader().getResource(path));
		return (new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(path))).getImage();
	}

	void putImage(String path) {
		String name=path.split("\\.")[0];
		this.images.put(name,loadImage(Const.imagePath+path));
	}

	void image(Graphics2D g2d, ObjOnMap obj) {
		image(g2d, obj, images.get(obj.getImg()));
	}

	void image(Graphics2D g2d, ObjOnMap obj, Image img) {
		g2d.drawImage(img, (int)(obj.getX()-obj.getWidth()/2),
				(int)(obj.getY()-obj.getHeight()/2)
				, obj.getWidth(), obj.getHeight(),this);
	}



	/*
	void imageCenter(Graphics2D g2d, ObjOnMap obj, ObjOnMap jiki, Image image) {
		int diffX=(int) (obj.getX()-jiki.getX());
		int diffY=(int) (obj.getY()-jiki.getY());
		g2d.drawImage(image, Const.centerX+diffX-obj.getWidth()/2, Const.centerY+diffY-obj.getHeight()/2, obj.getWidth(), obj.getHeight(),this);
	}
	 */
	void imageCenter(Graphics2D g2d, ObjOnMap obj) {
		g2d.drawImage(images.get(obj.getImg()), (int)(Const.centerX-obj.getWidth()/2),
				(int)(Const.centerY-obj.getHeight()/2)
				, obj.getWidth(), obj.getHeight(),this);
	}


	void collisionImage(ObjOnMap obj) {//当たり判定ある場所の画像とオブジェクトを対応付け
		boolean[] bool=Collision.collisionPoints(obj);//当たり判定を1次元にした配列
		//ArrayList<int[]> i=Collision.collisionPoints(obj);
		int width=obj.getWidth();
		int height=obj.getHeight();
		BufferedImage bi=new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		for(int i=0;i<bool.length;i++) {
			if(bool[i]) {
				bi.setRGB(i%width, i/height, Const.ALPHA_RED);//半透明の赤
			}
			else {
				bi.setRGB(i%width, i/height, Const.ALPHA);//透明
			}
		}
		this.colligionAreaImage.put(obj, bi);
	}

	void collisionPoint(Graphics2D g2d, ObjOnMap obj) {//当たり判定をみえるようにするため
		if(PlayGame.isCollisionArea) {
			if(!this.colligionAreaImage.containsKey(obj)) {
				collisionImage(obj);
			}
			image(g2d, obj, this.colligionAreaImage.get(obj));
		}
	}

	void rader(Graphics2D g2d) {//ななめ4方向のどこに敵がいるかを写すレーダー
		int rader_diameter=200;
		int enemy_diameter=10;
		g2d.setColor(new Color(0,255,0,128));//薄い緑
		g2d.fillOval((int)(jiki.getX()-rader_diameter/2),(int)(jiki.getY()-rader_diameter/2),rader_diameter,rader_diameter);//とりあえず円
		g2d.setColor(new Color(255,0,0,64));//薄い赤
	    for(ObjOnMap enemy:manager.returnEnemyList()) {
	    	if(!enemy.isRemoveFlag()) {
	    		float x= enemy.getX()-jiki.getX();
	    		float y= enemy.getY()-jiki.getY();
	    		double theta=0;
	    		if(x<0) {
	    			if(y<0) {
	    				theta=(Math.PI/4)*5;
	    			}else {
	    				theta=(Math.PI/4)*3;
	    			}
	    		}else {

	    			if(y<0) {

	    				theta=(Math.PI/4)*7;
	    			}else {
	    				theta=(Math.PI/4);
	    			}
	    		}
	    		g2d.fillOval((int)(jiki.getX()+rader_diameter/2*Math.cos(theta)),
	    				 (int)(jiki.getY()+rader_diameter/2*Math.sin(theta)),
	    				 enemy_diameter, enemy_diameter);//小さな円をレーダーの円周上に
	    	}
	    }
	    g2d.setColor(Color.BLACK);//色戻す
	}

	static void onOffRader() {
		isRader=!isRader;
	}

	static void onOffIsCollisionArea() {
		if(PlayGame.isChangeCollisionArea) {
			isCollisionArea=!isCollisionArea;
		}
	}


	private class actionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			if(manager.isPlaying()) {//ゲーム中のみ動かす
				manager.calcObj();
			    repaint();
			}
		}
	}

	private class keyListener implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
		}


		@Override
		public void keyPressed(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			char key=e.getKeyChar();
			int keyCode=e.getKeyCode();
	        if (key == ' ') {//スペースキー
	        	manager.jikiSetSpace(true);
	        }
	        if (key == 'r') {//スペースキー
	        	PlayGame.onOffRader();;
	        }
	        if(keyCode==KeyEvent.VK_UP || key=='w') {
	        	manager.jikiSetUp(true);
	        }
	        if(keyCode==KeyEvent.VK_DOWN || key=='s') {
	        	manager.jikiSetDown(true);
	        }
	        if(keyCode==KeyEvent.VK_RIGHT  || key=='d') {
	        	manager.jikiSetRight(true);
	        }
	        if(keyCode==KeyEvent.VK_LEFT  || key=='a') {
	        	manager.jikiSetLeft(true);
	        }
	        if(key=='j') {
	        	manager.jibaku();;
	        }
	        if(key=='c') {
	        	PlayGame.onOffIsCollisionArea();
	        }
		}


		@Override
		public void keyReleased(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			char key=e.getKeyChar();
			int keyCode=e.getKeyCode();
	        if (key == ' ') {//スペースキー
	        	manager.jikiSetSpace(false);
	        }
	        if(keyCode==KeyEvent.VK_UP || key=='w') {
	        	manager.jikiSetUp(false);
	        }
	        if(keyCode==KeyEvent.VK_DOWN || key=='s') {
	        	manager.jikiSetDown(false);
	        }
	        if(keyCode==KeyEvent.VK_RIGHT || key=='d') {
	        	manager.jikiSetRight(false);
	        }
	        if(keyCode==KeyEvent.VK_LEFT  || key=='a') {
	        	manager.jikiSetLeft(false);
	        }


		}
	}


}


