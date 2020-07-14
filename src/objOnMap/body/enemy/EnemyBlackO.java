package objOnMap.body.enemy;

import java.util.ArrayList;

import method.Const;
import objOnMap.Collision;
import objOnMap.ObjOnMap;
import objOnMap.ObjOnMapFactory;
import objOnMap.body.Body;

public class EnemyBlackO extends Body {
	private double theta;
	private float centerX,centerY;
	private static float radius=50;
	private static float speed=3;

	public EnemyBlackO(float x, float y) {
		super(x, y, "o");
		// TODO 自動生成されたコンストラクター・スタブ
	    this.hp = 1;
	    this.width = Const.oneCell;//直径
	    this.height = Const.oneCell;//直径
	    this.isShoot=false;
	    this.shootInterval=600;
	    this.centerX=x;
	    this.centerY=y;
	    this.x=(float) (this.centerX+radius*Math.cos(this.theta));
	    this.y=(float) (this.centerY+radius*Math.sin(this.theta));
	}

	public EnemyBlackO(){
		this(0,0);
	}

	@Override
	public boolean collision(ObjOnMap obj) {
		// TODO 自動生成されたメソッド・スタブ
		return Collision.circleCollision(obj, this.x, this.y, this.width, this.height);
	}

	@Override
	public void move(float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
	    this.theta +=Math.PI/360;
    	this.speedX = (float) -Math.sin(this.theta)*speed;
    	this.speedY = (float) Math.cos(this.theta)*speed;
	    if(this.theta >= 2*Math.PI) {
	    	this.theta = 0;
	    }
        this.moveX(this.speedX);
        this.moveY(this.speedY);
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

	@Override
	public void shoot(ArrayList<ObjOnMap> list, float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
	      this.canShoot();
	      if(this.isShoot) {
	    	  //list.add(new EnemyBullet_normal(this.x,this.y,10f,10f,1f));
	    	  ObjOnMapFactory.ewJikiBullet(this.x, this.y, x, y, list);
	    	  this.isShoot=false;
	      }
	}



}
