package objOnMap.bullet;

import objOnMap.Collision;
import objOnMap.ObjOnMap;

public class MyBulletNormal extends Bullet {

	public MyBulletNormal(float x, float y) {
		super(x, y, "bullet");
		this.width=10;
		this.height=20;
		this.dirX=0f;
		this.dirY=-1f;
		this.speed=10;

		// TODO 自動生成されたコンストラクター・スタブ
	}


	@Override
	public boolean collision(ObjOnMap obj) {
		// TODO 自動生成されたメソッド・スタブ
		return Collision.rectCollision(obj, this.x, this.y, this.width, this.height);
	}




}
