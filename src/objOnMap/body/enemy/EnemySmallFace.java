package objOnMap.body.enemy;

import java.util.ArrayList;

import method.Const;
import objOnMap.ObjOnMap;
import objOnMap.ObjOnMapFactory;
import objOnMap.body.Body;

public class EnemySmallFace extends Body {
	public EnemySmallFace(float x, float y) {
		super(x, y, "face");
		// TODO 自動生成されたコンストラクター・スタブ
        this.hp = 10;
        this.width=Const.oneCell*3;
        this.height=Const.oneCell*3;
        this.isShoot=false;
        this.shootInterval=60;
	}

	public EnemySmallFace(){
		this(0,0);
	}


	@Override
	public void move(float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public boolean collision(ObjOnMap obj) {
		// TODO 自動生成されたメソッド・スタブ
        if(this.removeFlag!=true){
           float x = obj.getX() - this.x;
           float y = obj.getY() - this.y;
            if(x * x + y * y <= (this.width/2)*(this.height)/2 && Math.abs(x)<= 65){
                return true;
            }
        }
        return false;
	}

	@Override
	public void shoot(ArrayList<ObjOnMap> list, float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
		this.canShoot();
		if(this.isShoot){
			//自機狙い
			ObjOnMapFactory.enemyJikiBulletNormal(this.x, this.y,x,y,1.5f,list);
			this.isShoot=false;
		}
	}

	@Override
	public void canShoot() {
		// TODO 自動生成されたメソッド・スタブ
        if(!this.isShoot && this.shootCount < this.shootInterval){
            this.shootCount += 1;
        }
        else{
            this.shootCount = 0;
            this.isShoot = true;
        }
	}

}
