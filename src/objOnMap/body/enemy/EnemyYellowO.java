package objOnMap.body.enemy;

import java.util.ArrayList;

import method.Const;
import objOnMap.Collision;
import objOnMap.ObjOnMap;
import objOnMap.ObjOnMapFactory;
import objOnMap.body.Body;

public class EnemyYellowO extends Body{
	private boolean isShoot2;
	private double count;
	private static float speed=8;
	private static float radius=3;

	public EnemyYellowO(float x, float y) {
		super(x, y, "yo");
		// TODO 自動生成されたコンストラクター・スタブ
		this.hp=3;
		isShoot2=false;
		count=0;
		this.width=Const.oneCell;
		this.height=Const.oneCell;
	}

	public EnemyYellowO(){
		this(0,0);
	}

	@Override
	public void move(float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
	    this.count +=speed*Math.PI/360;
	    if(this.count < 2*Math.PI) {
	    	this.speedX = (float) (radius*Math.sin(this.count));
	    	this.speedY = (float) (radius*Math.cos(this.count));
	    }else {
	    	this.count = 0;
	    }
        this.moveX(this.speedX);
        this.moveY(this.speedY);
	}

	@Override
	public boolean collision(ObjOnMap obj) {
		return Collision.circleCollision(obj, this.x, this.y, this.width, this.height);
	}

	@Override
	public void shoot(ArrayList<ObjOnMap> list, float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
		this.canShoot();
		if(this.isShoot){
			ObjOnMapFactory.ewJikiBullet(this.x, this.y, x, y, list);
            this.isShoot = false;
		}
        if(this.isShoot2){
        	ObjOnMapFactory.eWBullet(this.x, this.y, 22.5, list);
            this.isShoot2 = false;
        }
	}

	@Override
	public void canShoot() {
		// TODO 自動生成されたメソッド・スタブ
        if(this.isShoot2 == false){
            this.shootCount += 1;
            if( this.shootCount == 30){
                this.isShoot = true;
            }
            if( this.shootCount == 60){
                this.isShoot2 = true;
            }
            if(this.shootCount>120){
                this.shootCount = 0;
            }
        }
	}
}
