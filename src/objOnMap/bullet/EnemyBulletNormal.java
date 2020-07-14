package objOnMap.bullet;

import objOnMap.Collision;
import objOnMap.ObjOnMap;
public class EnemyBulletNormal extends Bullet{

    public EnemyBulletNormal(float x, float y, float dirX, float dirY, float speed) {
		super(x, y, "enemy_bullet");
		// TODO 自動生成されたコンストラクター・スタブ
		this.speed=speed;
		this.width=16;//直径
		this.height=16;//直径
		this.dirX=dirX;
		this.dirY=dirY;
	}


	@Override
	public boolean collision(ObjOnMap obj) {
		// TODO 自動生成されたメソッド・スタブ
		return Collision.circleCollision(obj, this.x, this.y, this.width, this.height);
	}



}


