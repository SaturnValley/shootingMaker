package panel.makerGUI;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import method.Const;

public class OnMap {//マップの上に置かれたときのオブジェクト
	private OnMap parent;
	private boolean haveChildren;
	private int x,y;//升目上での位置
	private int w,h;//幅と高さ，それぞれ升目単位
	private ArrayList<int[]> childrenPos;//子のx,y座標を格納
	private Image icon;
	private String name;
	private String type;

	public OnMap(OnMap parent) {//自身が一番下の子の時
		this.parent=parent;
		this.haveChildren=false;
	}

	public OnMap(int x, int y) {//おけない場所を表すためのコンストラクタ
		this.haveChildren=true;//個を持つことにすれば描画される
		this.parent=this;//parentあればセーブはされない
		this.icon=new ImageIcon(Const.imagePath+Const.DUMMY_IMAGE).getImage();
		this.w=1;//幅と高さは１
		this.h=1;
		this.x=x;
		this.y=y;
	}


	public OnMap(OnMap parent, OneObjPanel self) {//自身が従属する子の親の時，子の核
		this(self,parent.getX()+self.x,parent.getY()+self.y);
		this.parent=parent;
	}

	public OnMap(OneObjPanel oop, int x, int y) {//自身が親の時
		this.name=oop.name;
		this.parent=null;
		this.haveChildren=true;
		this.childrenPos=new ArrayList<int[]>();
		this.x=x;
		this.y=y;
		this.w=oop.width;
		this.h=oop.height;
		this.icon=oop.icon.getImage();
		this.type=oop.type;
	}

	public void addChildPos(int[] pos) {
		if(childrenPos!=null) {
			childrenPos.add(pos);
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public String getType() {
		return type;
	}


	public boolean haveChildren() {
		return haveChildren;
	}

	public OnMap getParent() {
		return parent;
	}

	public ArrayList<int[]> getChildrenPos() {
		return childrenPos;
	}

	public Image getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}
}
