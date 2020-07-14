package objOnMap.body.enemy;

import java.util.ArrayList;

import method.Const;
import objOnMap.Collision;
import objOnMap.ObjOnMap;
import objOnMap.ObjOnMapFactory;
import objOnMap.body.Body;

public class EnemyBlackR extends Body{
	private int hanten;
	private float theta;

	public EnemyBlackR(float x, float y) {
		super(x, y, "r");
		// TODO 自動生成されたコンストラクター・スタブ
		hanten=1;
		theta=0;
	    this.shootInterval=120;
		this.width=Const.oneCell;
		this.height=Const.oneCell;
	}

	public EnemyBlackR(){
		this(0,0);
	}

	@Override
	public void move(float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
        this.theta += Math.PI/360;
        this.speedX = (float) (Math.cos(this.theta)*Math.cos(this.theta)*this.hanten);
        this.speedY = (float) (Math.sin(this.theta)*Math.sin(this.theta)*this.hanten);
        this.moveX(this.speedX);
        this.moveY(this.speedY);
        if(this.theta >= Math.PI*2){
        	this.theta = 0;
            this.hanten*=-1;
        }
	}

	@Override
	public boolean collision(ObjOnMap obj) {
		// TODO 自動生成されたメソッド・スタブ
		return Collision.circleCollision(obj, this.x, this.y, this.width, this.height);
	}
	@Override
	public void shoot(ArrayList<ObjOnMap> list, float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
	     this.canShoot();
         if(this.isShoot){
        	 ObjOnMapFactory.dCJikiBullet(this.x, this.y, x, y, 1.5f, list);
             this.isShoot = false;
         }

	}

	@Override
	public void canShoot() {
		// TODO 自動生成されたメソッド・スタブ
	       if(!this.isShoot && this.shootCount < shootInterval){
	            this.shootCount += 1;
	       }
	        else{
	            this.shootCount = 0;
	            this.isShoot = true;
	        }
	}

}
