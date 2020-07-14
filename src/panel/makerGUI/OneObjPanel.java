package panel.makerGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import method.Const;

public class OneObjPanel extends JPanel{//選択画面でのパネル
	static ObjMode objMode;//全OneObjPanelインスタンスで同じObjModeインスタンスを使いまわす
	String type;//
	String name;//クラス名
	int x,y;//子の座標を保存するよう，親なら0
	int width;//升目で使うときのサイズ
	int height;
	String img;
	ImageIcon icon;
	private ArrayList<OneObjPanel> children;

	public OneObjPanel(String name, String type, int w, int h, String img) {
		this.name=name;
		width=(int)w/Const.oneCell;
		height=(int)h/Const.oneCell;
		this.img=img;
		this.type=type;
		icon = createIcon(img,w,h);
	    JLabel label = new JLabel(icon);
	    this.add(label);
	    this.addMouseListener(new clickListener());
	    this.setFrameOff();
	    this.setSize(w, h);
	    this.setMaximumSize(new Dimension(w,h));//パネルのサイズ確定
	    this.x=0;
	    this.y=0;
	    this.setChildren(new ArrayList<OneObjPanel>());
	}

	public OneObjPanel(String name, int w, int h, int x, int y, String img) {//子のオブジェクト用
		this.name=name;
		width=(int)w/Const.oneCell;
		height=(int)h/Const.oneCell;
		icon = createIcon(img,w,h);
	    this.x=(int)x/Const.oneCell;
	    this.y=(int)y/Const.oneCell;
	    //System.out.println("OneObjPanel "+x+","+y);
	    this.setChildren(new ArrayList<OneObjPanel>());
	}

	ImageIcon createIcon(String img, int w, int h) {//w,hにリサイズしたアイコンの作成
		return new ImageIcon((new ImageIcon(getClass().getClassLoader().getResource(Const.imagePath+img+Const.iconExtention)))
				.getImage().getScaledInstance(w, h, Image.SCALE_REPLICATE));
	}

	void addChildPanel(OneObjPanel child) {
		getChildren().add(child);
	}


	void setObjMode(ObjMode o){
		objMode=o;
	}

	void setFrameOn(){
	    this.setBorder(new LineBorder(Color.red, 3));
	}

	void setFrameOff(){
	    this.setBorder(new LineBorder(Color.black, 1));
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<OneObjPanel> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<OneObjPanel> children) {
		this.children = children;
	}

	/*
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
*/
	  public class clickListener extends MouseAdapter{
	    public void mouseClicked(MouseEvent e){
	    	OneObjPanel j= (OneObjPanel)e.getComponent();
	    	OneObjPanel pre=objMode.getNowPanel();
	    	if(pre!=null) {
	    		pre.setFrameOff();
	    	}
	    	j.setFrameOn();
		    objMode.setMode(j.img);
		    objMode.setNowPanel(j);
	    }
	  }
}
