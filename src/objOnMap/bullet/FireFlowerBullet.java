package objOnMap.bullet;

import java.util.ArrayList;

import objOnMap.Collision;
import objOnMap.ObjOnMap;
import objOnMap.ObjOnMapFactory;

public class FireFlowerBullet extends Bullet {
	public FireFlowerBullet(float x, float y, float dirX, float dirY, float speed) {
		super(x, y, "enemy_bullet");
		// TODO 自動生成されたコンストラクター・スタブ
		shootInterval=180;
		this.width=10;//半径
		this.height=10;//半径
		this.speed=speed;
		this.dirX=dirX;
		this.dirY=dirY;
	}


	@Override
	public boolean collision(ObjOnMap obj) {
		return Collision.circleCollision(obj, this.x, this.y, this.width, this.height);
	}

	@Override
	public void shoot(ArrayList<ObjOnMap> list, float x, float y) {
		canShoot();
		// TODO 自動生成されたメソッド・スタブ
		if(isShoot) {
			ObjOnMapFactory.ewJikiBullet(this.x, this.y, x, y, list);
			this.removeFlag=true;
		}
	}

	@Override
	public void canShoot() {
		// TODO 自動生成されたメソッド・スタブ
        if(!this.isShoot&& this.shootCount < shootInterval) {
            this.shootCount += 1;
        }
        else {
            this.shootCount = 0;
            this.isShoot=true;
        }
	}


}
