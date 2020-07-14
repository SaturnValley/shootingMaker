package objOnMap.body.enemy;

import objOnMap.Collision;
import objOnMap.ObjOnMap;
public class EnemyLeftHand extends EnemyHand {

	public EnemyLeftHand(float x, float y) {
		super(x, y, "lefthand");
		// tODO 自動生成されたコンストラクター・スタブ
	}

	public EnemyLeftHand(){
		this(0,0);
	}

	@Override
	//ここ用検査
	public boolean collision(ObjOnMap obj) {
		// TODO 自動生成されたメソッド・スタブ
		return Collision.rectCollisionAngle(obj,this.x, this.y, this.width*3/4, this.height/3, -40);
	}
}
