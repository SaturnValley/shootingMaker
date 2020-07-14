package objOnMap.body.enemy;

import java.util.ArrayList;

import method.Const;
import objOnMap.Collision;
import objOnMap.ObjOnMap;
import objOnMap.ObjOnMapFactory;
import objOnMap.body.Body;

public class EnemyBlackU extends Body {
	private int hanten;
	private double count;
	public EnemyBlackU(float x, float y) {
		super(x, y, "u");
		// TODO 自動生成されたコンストラクター・スタブ
		hanten=1;
		count=0;
		this.width=Const.oneCell;
		this.height=Const.oneCell;
	}

	public EnemyBlackU(){
		this(0,0);
	}

	@Override
	public void shoot(ArrayList<ObjOnMap> list, float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
		this.canShoot();
		if(isShoot) {
			ObjOnMapFactory.twJikiBullet(this.x, this.y, x,y,list);
			this.isShoot=false;
		}
	}

	@Override
	public void canShoot() {
		// TODO 自動生成されたメソッド・スタブ
        if(!this.isShoot && this.shootCount < 240) {
            this.shootCount += 1;
        }
        else {
            this.shootCount = 0;
            isShoot=true;
        }
	}

	@Override
	public void move(float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
		this.count += 2*Math.PI/360;
		if(this.count < Math.PI*2/4) {
			this.speedY = 1;
		}
		else if(this.count < Math.PI * 3*2/4){
			this.speedX = (float) (2*Math.cos(this.count)*this.hanten);
			this.speedY = (float) (2*Math.sin(this.count));
		}
		else if(this.count < Math.PI* 4*2/4){
			this.speedY =- 1;
		}else {
			this.count = 0;
			this.hanten*=-1;
		}
		this.moveX(this.speedX);
		this.moveY(this.speedY);
	}

	@Override
	public boolean collision(ObjOnMap obj) {
		// TODO 自動生成されたメソッド・スタブ
		return Collision.circleCollision(obj, this.x, this.y, this.width, this.height);
	}

}
