package manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import method.Const;
import method.LoadFile;
import objOnMap.ObjOnMap;
import objOnMap.ObjOnMapFactory;
import objOnMap.body.jiki.Jiki;

public class ObjManager {
	public ArrayList<ObjOnMap> enemy_bullet_list;
	public ArrayList<ObjOnMap> jiki_bullet_list;
	public ArrayList<ObjOnMap> enemy_list;
	private List<String> enemy_class_name;
	private List<String> jiki_class_name;
	public Jiki jiki;


	public ObjManager(){
		init();
		enemy_class_name=Arrays.asList(LoadFile.pack2ClassName(Const.ENEMY_PK_NAME, Const.OBJONMAP_JAR_FILE));
		jiki_class_name=Arrays.asList(LoadFile.pack2ClassName(Const.JIKI_PK_NAME, Const.OBJONMAP_JAR_FILE));

	}

	void init() {
		this.enemy_bullet_list = new ArrayList<ObjOnMap>();
		this.jiki_bullet_list = new ArrayList<ObjOnMap>();
		this.enemy_list = new ArrayList<ObjOnMap>();

	}

	private <T> void remove_list(ArrayList<T> bl){
		Iterator<T> it = bl.iterator();
        while(it.hasNext()){
            T b = it.next();
            if(((ObjOnMap) b).isRemoveFlag()) {
            	it.remove();
            }
        }
	}

	boolean setStage() {
		init();
		return createObjList(LoadFile.loadStage2ObjManager("test"));
	}


	boolean setStage(String stageName) {
		init();
		return createObjList(LoadFile.loadStage2ObjManager(stageName));
	}

	private void collision(ObjOnMap obj1, ObjOnMap obj2) {
		if(!obj1.isRemoveFlag() && !obj2.isRemoveFlag()) {
			if(obj1.collision(obj2) || obj2.collision(obj1)) {
				obj1.damage(obj2);
				obj2.damage(obj1);
			}
		}
	}

	private boolean createObjList(String[][] stage){
		if(stage!=null) {
			for(String[] obj:stage) {
				String className=LoadFile.splitClassName(obj[0]);
				//System.out.println(obj[0]+","+obj[1]+","+obj[2]);
				if(obj[1]!=null && obj[2]!= null) {//ステージ名とクラス名同じだとバグりそう
					if(this.enemy_class_name.contains(className) || this.jiki_class_name.contains(className)) {//自機か敵か
						float x=Float.parseFloat(obj[1])*Const.oneCell;
						float y=Float.parseFloat(obj[2])*Const.oneCell;
						//System.out.println("ObjManager createObjList "+className+"."+x+"."+y);
						if(this.enemy_class_name.contains(className)) {
							ObjOnMap o=ObjOnMapFactory.createEnemy(className, x, y, this.enemy_list);
							if(o!=null) {
								this.enemy_list.add(o);
							}
						}
						if(this.jiki_class_name.contains(className)) {
							this.jiki=ObjOnMapFactory.createJiki(className, x, y);
						}
					}
				}
			}
			if(jiki!=null) {
				return true;
			}else {
				System.out.println("ObjManager no Jiki");
				return false;
			}
		}else {
			System.out.println("objManager CreateObjList null");
			return false;
		}
	}



	public void calc() {
		//自機の移動と弾発射
		jiki.move(0,0);//0,0は使ってない引数
		jiki.shoot(jiki_bullet_list,0,0);//0,0は使ってない引数
		//自機の弾の移動
		float x=jiki.getX();
		float y=jiki.getY();
		for(ObjOnMap b: jiki_bullet_list) {
			b.move(x,y);
			b.inShowRange(x, y);
		}
		//敵の移動と弾発射
		for(ObjOnMap e: enemy_list) {
			e.inShowRange(x, y);
			if(!e.isRemoveFlag()) {
				e.move(x,y);
				if(e.isShow()) {//見えるときだけ弾を打つ
					e.shoot(enemy_bullet_list, x,y);
				}
			}
		}
		//敵の弾の弾発射（弾分裂とか）
		for(int i=0;i<enemy_bullet_list.size();i++) {
			enemy_bullet_list.get(i).shoot(enemy_bullet_list, x, y);
		}
		//敵の弾の移動
		for(ObjOnMap b: enemy_bullet_list) {
			b.inShowRange(x, y);//見えるところにいるか
			b.move(x,y);
			b.inMap();//マップ外に出たらremoveFlagをtrueに
		}
		//敵の弾と自機の当たり判定
		for(ObjOnMap b: enemy_bullet_list) {
			collision(jiki,b);
		}
		//敵と自機の弾の当たり判定，敵と自機の当たり判定
		for(ObjOnMap e: enemy_list) {
			//この下敵と自機の弾
			if(!e.isRemoveFlag()) {
				for(ObjOnMap jb: jiki_bullet_list) {
					collision(e,jb);
				}//この下敵と自機
				collision(jiki,e);
			}
		}
		//削除
		remove_list(enemy_bullet_list);
		remove_list(jiki_bullet_list);
		//remove_list(enemy_list);


	}

	public boolean gameClear() {
		for(ObjOnMap o:enemy_list) {
			if(!o.isRemoveFlag()) {//倒せていない敵がいる
				return false;
			}
		}
		return true;

	}

	public boolean gameOver() {
		return jiki.isRemoveFlag();
	}
}
