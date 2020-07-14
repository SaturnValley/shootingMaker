package objOnMap.body.enemy;

import java.util.ArrayList;

import method.Const;
import objOnMap.Collision;
import objOnMap.ObjOnMap;
import objOnMap.ObjOnMapFactory;
import objOnMap.body.Body;

public class EnemyBlackT extends Body{
	private boolean isShoot2;
	private boolean isShoot3;
	private int count;
	private static int moveInterval=60;
	private static int shootInterval2=150;
	private static int shootInterval3=180;


	public EnemyBlackT(float x, float y) {
		super(x, y, "t");
		this.shootInterval=120;
		// TODO 自動生成されたコンストラクター・スタブ
		isShoot=false;
		isShoot2=false;
		isShoot3=false;
		count=0;
		this.width=Const.oneCell;
		this.height=Const.oneCell;
	}

	public EnemyBlackT(){
		this(0,0);
	}

	@Override
	public void move(float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
        this.count += 1;
        if(this.count < moveInterval){
            this.y += 1;
        }
        else if(this.count < moveInterval * 2){
            this.y -= 1;
        }
        else if(this.count < moveInterval * 3){
            this.x -= 1;
        }
        else if(this.count < moveInterval * 5){
            this.x += 1;
        }
        else if(this.count < moveInterval * 6){
            this.x -= 1;
        }
        else{
            this.count = 0;
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
		this.canShoot() ;
		if(this.isShoot) {
			ObjOnMapFactory.enemyJikiBulletNormal(this.x, this.y, x, y , 1.5f,list);
            this.isShoot = false;
		}
        if(this.isShoot2) {
        	ObjOnMapFactory.enemyJikiBulletNormal(this.x, this.y, x, y , 1.5f,list);
        	this.isShoot2 = false;
        }
        if(this.isShoot3) {
        	ObjOnMapFactory.enemyJikiBulletNormal(this.x, this.y, x, y , 1.5f,list);
            this.isShoot3 = false;
        }
	}

	@Override
	public void canShoot() {
		// TODO 自動生成されたメソッド・スタブ
        if(this.isShoot3 == false){
            this.shootCount += 1;
            if( this.shootCount == this.shootInterval){
                this.isShoot = true;
            }
            if( this.shootCount == shootInterval2){
                this.isShoot2 = true;
            }
            if( this.shootCount == shootInterval3){
                this.isShoot3 = true;
            }
            if(this.shootCount> shootInterval3){
                this.shootCount = 0;
            }
        }
	}
}
