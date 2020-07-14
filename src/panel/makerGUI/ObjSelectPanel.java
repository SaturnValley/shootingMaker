package panel.makerGUI;

import java.awt.FlowLayout;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import method.Const;
import method.LoadFile;
import objOnMap.ObjOnMap;

//一つのオブジェクトが他のオブジェクトと紐づいている場合
public class ObjSelectPanel extends JTabbedPane{
	static public HashMap<String,OneObjPanel> oneObjPanelList;//マップをロードする時に使う
	ObjSelectPanel(ObjMode objMode){
		oneObjPanelList = new HashMap<String,OneObjPanel>();
		addTab(Const.JIKI_PK_NAME,objMode,"自機　幅：",Const.TYPE_JIKI);
	    addTab(Const.ENEMY_PK_NAME,objMode,"敵　幅：",Const.TYPE_ENEMY);
	}

	private void addTab(String packageName, ObjMode objMode, String label, String type) {
		ArrayList<OneObjPanel> objPanelList=getObjects(packageName,type);
		Collections.sort(objPanelList, new ObjPanelComparator());//widthでソート
		int w=0;
	    int count=0;
		JPanel row_p=null;//横一列分
		JPanel col_p=null;//縦に並べる
		FlowLayout flow=null;
		BoxLayout box=null;
	    JScrollPane jsp=null;
		for(OneObjPanel o: objPanelList) {
			oneObjPanelList.put(o.getName(), o);
			o.setObjMode(objMode);//モード登録，すべてのパネルに共通
			count++;

			if(w!=o.width) {
				if(w!=0) {//初回以外，同じwidthのやつが追加し終わったら
				    if(count<(int)Const.SELECT_PANEL_WIDTH/w){//横一列がぴったり作り終わらなかったと起用
				    	col_p.add(row_p);
				    }
					this.addTab(label+w, jsp);
				}
				//初期化
				w=o.width;

				row_p=new JPanel();
			    flow=new FlowLayout();
			    row_p.setLayout(flow);

			    col_p=new JPanel();
			    box=new BoxLayout(col_p,BoxLayout.Y_AXIS);
			    col_p.setLayout(box);

			    jsp=new JScrollPane(col_p);
			}
			//System.out.println(o.getSize());
			if(count<(int)Const.SELECT_PANEL_WIDTH/w) {
				row_p.add(o);
			}else {
				count=0;
				col_p.add(row_p);

				row_p=new JPanel();
			    flow=new FlowLayout();
			    row_p.setLayout(flow);
				row_p.add(o);

			}
		}
		if(jsp!=null) {//念のため
		    if(count<(int)Const.SELECT_PANEL_WIDTH/w){
		    	col_p.add(row_p);
		    }
			this.addTab(label+w, jsp);
		}
	}

	private class ObjPanelComparator implements Comparator<OneObjPanel> {//widthで昇順ソートするため
		@Override
		public int compare(OneObjPanel p1, OneObjPanel p2) {
			return p1.width < p2.width ? -1 : 1;
		}

	}

	private static void haveChild(Class<?> cla, ArrayList<OneObjPanel> oopArray) {//従属する子オブジェクトを持つか
		Constructor<?>[] cs=cla.getConstructors();
		int count=0;
		boolean haveChild=false;
		for(Constructor<?> c:cs) {
			Parameter[] ps=c.getParameters();
			for(Parameter p:ps) {
				if(p.getType()==java.util.ArrayList.class) {//ArrayListがあると，従属するenemyをもつ
					//System.out.println("従属あり");
					haveChild=true;
					break;
				}
			}
			if(haveChild) {
				break;
			}
			count++;
		}
		if(haveChild) {
			ArrayList<ObjOnMap> array=new ArrayList<>();//このarrayにはこのオブジェクトだけが入っているはず
			try {
				//ここでarrayに子オブジェクトが入る
				cs[count].newInstance(0f,0f,array);//子持ちのコンストラクタの引数は(float,float,ArrayList<>())とする
				for(ObjOnMap obj:array) {
					oopArray.add(new OneObjPanel(cs.getClass().getName(),obj.getWidth(),obj.getHeight(),(int)obj.getX(),(int)obj.getY(),obj.getImg()));
					haveChild(cs.getClass(),oopArray);//ここで再帰で呼び出している，自身と同じ子を無限に持たなければ大丈夫
					//一番親，ルートオブジェクトの子として並列に扱う
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}//中心点(0,0)の子オブジェクトのインスタンス生成
		}
	}

	private static ArrayList<OneObjPanel> getObjects(String packageName ,String type){
		//widh:arrayListの辞書型を返す
		ArrayList<OneObjPanel> objPanels=new ArrayList<>();
		List<Class<?>> classes;
		try {
			classes = LoadFile.getClasses(packageName,Const.OBJONMAP_JAR_FILE);
			for (Class<?> class1 : classes) {
				String name=class1.getName();
				int a=class1.getModifiers();
				if(a<Modifier.ABSTRACT) {//クラスがabstractじゃなければ,public abstractだと1+1024=1025になるっぽい
					ObjOnMap inst;
					try {
						inst = (ObjOnMap) class1.getDeclaredConstructor().newInstance();
						OneObjPanel oop=new OneObjPanel(name,type,inst.getWidth(),inst.getHeight(),inst.getImg());
						haveChild(class1, oop.getChildren());
						objPanels.add(oop);
					} catch (InstantiationException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}
			}
		} catch (ClassNotFoundException | IOException | URISyntaxException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return objPanels;
	}

}

