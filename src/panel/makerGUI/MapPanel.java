package panel.makerGUI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import method.Const;
import method.LoadFile;
public class MapPanel extends JPanel{
	ObjMode objMode;
	OnMap[][] onMap;//マップ上にあるオブジェクトの配列
	private int moveX, moveY;//どっちに動くか,+-1か0
	private int nowX, nowY;//現在の座標左上の座標．セル番号
	private int cursorX, cursorY;//カーソルの現在位置，セル番号
	private int moveDelta=Const.oneCell;//倍率変更とかしたいとき
	private static boolean nagaoshi=false;
	private Timer timer;
	private Font f=new Font("ＭＳ ゴシック", Font.PLAIN, 16);



	public MapPanel(ObjMode objMode){
		this.objMode=objMode;
		this.setBackground(Color.WHITE);
		this.addMouseListener(new clickListener());
		this.addMouseMotionListener(new clickListener());
		timer = new Timer(1000/30 , new nagaoshiListener());//30フレーム
		//nagaoshi=false;
		init();
	}

	public void init() {
		timer.start();
		this.onMap=new OnMap[(int)Const.MapX/this.moveDelta][(int)Const.MapY/this.moveDelta];//マップ配列のサイズ
		moveX=0;
		moveY=0;
		nowX=0;
		nowY=0;
		cursorX=0;
		cursorY=0;
		int bottom=onMap[0].length-1;
		for(int i=0;i<onMap.length;i++) {//マップの一番下端にはおけないようにダミーを置く，そもそもおけなそうでもある(表示のバグ，仕様にする)
			onMap[i][bottom]=new OnMap(i,bottom);//おけないやつ
		}
		repaint();
	}

	private boolean isObj() {//削除用
		return !(onMap[cursorX+nowX][cursorY+nowY]==null);
	}

	public void loadStage(String stageName) {//stageNameのステージファイルを読み込む
		if(stageName!=null) {
			init();
			String[][] stage=LoadFile.loadStage2ObjManager(stageName);
			if(stage!=null) {
				boolean first=true;
				for(String[] obj:stage) {
					if(first) {//1行目にはファイル名が入っているはず
						objMode.setFileName(obj[0]);
						first=false;
					}
					if(obj[1]!=null && obj[2]!= null) {//ステージ名とクラス名同じだとバグりそう
						if(ObjSelectPanel.oneObjPanelList.containsKey(obj[0])){//パネルに存在するオブジェクトなら
							int x=Integer.parseInt(obj[1]);//升目上のx座標
							int y=Integer.parseInt(obj[2]);//升目上のy座標
							putObj(ObjSelectPanel.oneObjPanelList.get(obj[0]), x, y);//設置する
						}
					}
				}
				repaint();
			}
		}
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		int w=this.getSize().width;
		int h=this.getSize().height;
		int row=(int)(w/this.moveDelta);
		int col=(int)(h/this.moveDelta);

		mesh(g2d,w,h,row,col);//格子を書く
		drawImages(g2d,row,col);

		if(objMode.getNowPanel()!=null) {
			OneObjPanel o=objMode.getNowPanel();
			AlphaComposite ac=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);//透明度0.5，おけないとき;
			if(!canPut(o)) {//おけないときは透過適用
				g2d.setComposite(ac);
			}
			g2d.drawImage(o.getIcon().getImage(),(cursorX-(int)o.width/2)*this.moveDelta,(cursorY-(int)o.height/2)*this.moveDelta,this);//親オブジェクトの置かれる位置
			if(o.getChildren()!=null) {
				for(OneObjPanel child:o.getChildren()) {//子オブジェクトがあれば描画
					g2d.drawImage(child.getIcon().getImage(),(cursorX+child.x-(int)child.width/2)*this.moveDelta,(cursorY+child.y-(int)child.height/2)*this.moveDelta,this);
				}
			}
		}
		g2d.setFont(f);
		g2d.drawString((this.cursorX+this.nowX)+","+(this.cursorY+this.nowY), this.moveDelta, this.moveDelta);
	}

	private void mesh(Graphics2D g2,int w,int h,int row,int col) {
		for(int r=0;r<=row;r++) {//ちょっと多めに格子描画
			g2.drawLine(this.moveDelta*r, -this.moveDelta*2, this.moveDelta*r, h+this.moveDelta*2);
		}
		for(int c=0;c<=col;c++) {
			g2.drawLine(-this.moveDelta*2, this.moveDelta*c, w+this.moveDelta*2, this.moveDelta*c);
		}
	}

	private void drawImages(Graphics2D g2, int row, int col) {
		g2.setColor(Color.RED);//赤線引くため
		for(OnMap[] oArray:onMap) {
			for(OnMap o:oArray) {
				/*
				if(o!=null) {
					System.out.println(o.getX()+","+o.getY()+","+o.haveChildren());
				}
				*/
				if(o!=null && o.haveChildren()){//MAPに何か置かれていて，親オブジェクトなら
					g2.drawImage(o.getIcon(),(o.getX()-nowX-(int)o.getW()/2)*this.moveDelta,(o.getY()-nowY-(int)o.getH()/2)*this.moveDelta,this);
					//置いたやつの周りを枠で囲う，置けない場所
					g2.drawRect((o.getX()-nowX-(int)(o.getW()/2))*this.moveDelta, (o.getY()-nowY-(int)o.getH()/2)*this.moveDelta, o.getW()*this.moveDelta, o.getH()*this.moveDelta);
				}
			}
		}
		g2.setColor(Color.BLACK);
	}

	private void putObj() {//ステージ作成中に使うのはこっち
		OneObjPanel oop=objMode.getNowPanel();
		int centerX=nowX+cursorX;
		int centerY=nowY+cursorY;
		putObj(oop, centerX, centerY);
	}

	private void putObj(OneObjPanel oop, int x, int y) {//ステージを読み込むときに使うのはこっち
		int centerX=x;
		int centerY=y;

		OnMap om=new OnMap(oop,centerX,centerY);
		onMap[centerX][centerY]=om;
		int bitW;//幅が奇数の時にintで切り捨てられる問題を解決したい
		int bitH;
		if(oop.width%2==0) {
			bitW=0;
		}else {
			bitW=1;
		}
		if(oop.height%2==0) {
			bitH=0;
		}else {
			bitH=1;
		}
		for(int i=centerX-(int)oop.width/2;i<centerX+oop.width/2+bitW;i++) {
			for(int j=centerY-(int)oop.height/2;j<centerY+oop.height/2+bitH;j++) {
				//System.out.println(i+","+j);
				if(i!=centerX && j!=centerY) {//親の場所でなかったら
					onMap[i][j]=new OnMap(om);
					int[] xy={i,j};
					om.addChildPos(xy);//子の座標を追加
				}
			}
		}
		for(OneObjPanel child:oop.getChildren()){//子のオブジェクトを配置
			OnMap oneChildCore = new OnMap(om,child);
			int childX=oneChildCore.getX();
			int childY=oneChildCore.getY();
			//System.out.println("center"+centerX+","+centerY);
			//System.out.println("child "+childX+","+childY);
			onMap[childX][childY] =oneChildCore;
			int[] coreXY= {childX,childY};
			om.addChildPos(coreXY);
			if(oneChildCore.getW()%2==0) {
				bitW=0;
			}else {
				bitW=1;
			}
			if(oneChildCore.getH()%2==0) {
				bitH=0;
			}else {
				bitH=1;
			}
			for(int i=childX-(int)oop.width/2;i<childX+oop.width/2+bitW;i++) {
				for(int j=childY-(int)oop.height/2;j<childY+oop.height/2+bitH;j++) {
					//System.out.println(i+","+j);
					if(!(i==childX && j==childY) && onMap[i][j]==null) {//子の核の場所でなかったら,何もなかったら
						onMap[i][j]=new OnMap(om);
						int[] xy={i,j};
						om.addChildPos(xy);//子の座標を追加
					}
				}
			}
		}
		if(om.getType().equals(Const.TYPE_JIKI)) {
			objMode.setIsJiki(true);
		}
	}

	private void deleteObj() {
		OnMap del=onMap[cursorX+nowX][cursorY+nowY];
		if(del!=null) {
			if(del.getParent()!=null) {
				del=del.getParent();
			}
			for(int[] xy:del.getChildrenPos()) {//子を削除
				onMap[xy[0]][xy[1]]=null;
			}
			onMap[del.getX()][del.getY()]=null;//核を削除
		}
		if(del.getType().equals(Const.TYPE_JIKI)) {
			objMode.setIsJiki(false);
		}
	}


	private boolean canPut(OneObjPanel oop) {
		//System.out.println((nowX+cursorX-(int)oop.width/2+1)+","+(nowX+cursorX+oop.width/2));
		//System.out.println(oop.type.equals(Const.TYPE_JIKI));

		if(this.objMode.isJiki() && oop.type.equals(Const.TYPE_JIKI)){ //自機を2つは置けない
			return false;
		}
		//System.out.println("canput0");


		int bitW;
		int bitH;
		if(oop.width%2==0) {
			bitW=0;
		}else {
			bitW=1;
		}
		if(oop.height%2==0) {
			bitH=0;
		}else {
			bitH=1;
		}
		for(int i=nowX+cursorX-(int)oop.width/2;i<nowX+cursorX+oop.width/2+bitW;i++) {//親オブジェクト配置できるか
			for(int j=nowY+cursorY-(int)oop.height/2;j<nowY+cursorY+oop.height/2+bitH;j++) {
				if(i<0 || j<0 || Const.MapX/this.moveDelta<i || Const.MapY/this.moveDelta<j) {
					//System.out.println("okenai"+nowX+","+nowY+","+i+","+j);
					return false;
				}
				if(onMap[i][j]!=null) {
					return false;
				}
			}
		}
		//System.out.println("canput1");

		for(OneObjPanel child:oop.getChildren()){//子のオブジェクトを配置できるか
			int childX=nowX+cursorX+child.x;
			int childY=nowY+cursorY+child.y;
			if(child.width%2==0) {
				bitW=0;
			}else {
				bitW=1;
			}
			if(child.height%2==0) {
				bitH=0;
			}else {
				bitH=1;
			}
			for(int i=childX-(int)oop.width/2;i<childX+oop.width/2+bitW;i++) {
				for(int j=childY-(int)oop.height/2;j<childY+oop.height/2+bitH;j++) {
					//System.out.println(i+","+j);
					if(i<0 || j<0 || Const.MapX/this.moveDelta<i || Const.MapY/this.moveDelta<j) {
						//System.out.println("okenai"+nowX+","+nowY+","+i+","+j);
						return false;
					}
					if(onMap[i][j]!=null) {
						return false;
					}
				}
			}
		}
		//System.out.println("canput2");

		return true;
	}

	private void isMove() {//マップをずらせるかどうか，むりならmoveを0にする
	   	int h=this.getSize().height;
	   	int w=this.getSize().width;
	   	if((nowX+1)*this.moveDelta+w>Const.MapX || nowX-1<0) {//xはずらせる
	   		moveX=0;
	   	}
	   	if((nowY+1)*this.moveDelta+h>Const.MapY || nowY-1<0) {//yはずらせる
	   		moveY=0;
	   	}
	}

	private class clickListener extends MouseAdapter{//あとでpushとreleaseに分ける，cellX,cellYの初期化に
		//削除のことも考える
		public void mousePressed(MouseEvent e) {
			Point p=e.getPoint();
			MapPanel m= (MapPanel)e.getComponent();
		   	//m.cellX=p.x;
		   	//m.cellY=p.y;
			int button=e.getButton();

		   	int h=m.getSize().height;
		   	int w=m.getSize().width;
	   		m.cursorX=(int)(p.x/m.moveDelta);
		   	m.cursorY=(int)(p.y/m.moveDelta);

		   	if(button==MouseEvent.BUTTON1) {//左クリック
			   	if(w-p.x<m.moveDelta && (nowX+1)*m.moveDelta+w<=Const.MapX) {//右端
			   		moveX=1;
			   	}else if(p.x<m.moveDelta && nowX-1>=0) {//左端
			   		moveX=-1;
			   	}
			   	if(h-p.y<m.moveDelta && (nowY+1)*m.moveDelta+h<=Const.MapY) {//下端
			   		moveY=1;
			   	}else if(p.y<m.moveDelta && nowY-1>=0) {//上端
			   		moveY=-1;
			   	}

			   	if(moveX!=0 || moveY!=0) {//端っこ触って移動したらこっち
					nowX+=moveX;
					nowY+=moveY;
					nagaoshi=true;
			   		m.repaint();
			   	}else if(objMode.getNowPanel()!=null) {//マップの移動判定じゃなくて物を置くときはこっち
				   	if(m.canPut(objMode.getNowPanel())) {//ここでカーソル位置におけるか判定
				   		m.putObj();
				   		m.repaint();
				   	}
			   	}
		   	}else if(button==MouseEvent.BUTTON3) {//右クリック
		   		//System.out.println(nowX+","+nowY+","+cursorX+","+cursorY);
			   	if(m.isObj()) {//カーソル位置にオブジェクトがあるか
			   		//System.out.println("delete");
			   		m.deleteObj();
			   		m.repaint();
			   	}
		   	}

		}
		public void mouseReleased(MouseEvent e) {//移動の初期化
			MapPanel m= (MapPanel)e.getComponent();
		   	m.moveX=0;
		   	m.moveY=0;
		   	nagaoshi=false;
		}

		public void mouseMoved(MouseEvent e) {
			MapPanel m= (MapPanel)e.getComponent();
			Point p=e.getPoint();
		   	m.cursorX=(int)(p.x/m.moveDelta);
		   	m.cursorY=(int)(p.y/m.moveDelta);
		   	m.repaint();
		}
	}
	private  class nagaoshiListener implements ActionListener {//長押しすると早くずらせる
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			if(nagaoshi) {
				isMove();
				nowX+=moveX;
				nowY+=moveY;
				repaint();
			}
		}
	}

}
