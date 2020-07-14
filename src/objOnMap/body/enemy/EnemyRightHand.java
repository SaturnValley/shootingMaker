package objOnMap.body.enemy;

import objOnMap.Collision;
import objOnMap.ObjOnMap;

public class EnemyRightHand extends EnemyHand{

	public EnemyRightHand(float x, float y) {
		super(x, y, "righthand");
		// TODO 自動生成されたコンストラクター・スタブ
		this.shootCount=this.shootInterval/2;
		this.count= (int)Math.PI;//適当にずらす
	}

	public EnemyRightHand(){
		this(0,0);
	}


	@Override
	//ここ用検査
	public boolean collision(ObjOnMap obj) {
		// TODO 自動生成されたメソッド・スタブ
		return Collision.rectCollisionAngle(obj,this.x, this.y, this.width*3/4, this.height/3, 40);
	}

}
