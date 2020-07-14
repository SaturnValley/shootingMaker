package objOnMap.bullet;

import objOnMap.Collision;
import objOnMap.ObjOnMap;


public class BigBullet extends Bullet {

	public BigBullet(float x, float y, float dirX, float dirY, float speed) {
		super(x, y, "bigBullet");
		// TODO 自動生成されたコンストラクター・スタブ
		this.width=64;
		this.height=64;
		this.dirX=dirX;
		this.dirY=dirY;
		this.speed=speed;
	}

	@Override
	public boolean collision(ObjOnMap obj) {
		// TODO 自動生成されたメソッド・スタブ
		return Collision.circleCollision(obj, this.x, this.y, this.width, this.height);
	}


}
