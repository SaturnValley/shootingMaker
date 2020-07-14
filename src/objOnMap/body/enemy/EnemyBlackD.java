package objOnMap.body.enemy;

import java.util.ArrayList;

import method.Const;
import objOnMap.Collision;
import objOnMap.ObjOnMap;
import objOnMap.ObjOnMapFactory;
import objOnMap.body.Body;

public class EnemyBlackD extends Body {

	public EnemyBlackD(float x, float y) {
		super(x, y, "d");
		// TODO 自動生成されたコンストラクター・スタブ
		this.shootInterval=240;
		this.width=Const.oneCell;
		this.height=Const.oneCell;
	}

	public EnemyBlackD(){
		this(0,0);
	}

	@Override
	public void shoot(ArrayList<ObjOnMap> list, float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
        this.canShoot();
        if(this.isShoot) {
        	ObjOnMapFactory.fireFlowerBullet(this.x, this.y, x, y, 1.5f,list);
            isShoot=false;
        }

	}

	@Override
	public void canShoot() {
		// TODO 自動生成されたメソッド・スタブ
        if(!this.isShoot && this.shootCount < this.shootInterval){
            this.shootCount += 1;
        }else{
            this.shootCount = 0;
            isShoot=true;
        }
	}

	@Override
	public void move(float x, float y) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean collision(ObjOnMap obj) {
		// TODO 自動生成されたメソッド・スタブ
		return Collision.circleCollision(obj, this.x, this.y, this.width, this.height);
	}

}
