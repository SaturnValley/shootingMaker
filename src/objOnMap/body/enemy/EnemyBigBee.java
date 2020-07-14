package objOnMap.body.enemy;

import java.util.ArrayList;

import method.Const;
import objOnMap.ObjOnMap;
import objOnMap.ObjOnMapFactory;
import objOnMap.body.Body;

public class EnemyBigBee extends Body {
	private boolean isShoot2,isShoot3;
	private double count;
	private static float speed=8;
	private static float radius=16;

	public EnemyBigBee(float x, float y) {
		super(x, y, "bee");
		// TODO 自動生成されたコンストラクター・スタブ
        this.hp = 30;
        this.width=Const.oneCell*8;
        this.height=Const.oneCell*8;
        this.isShoot=false;
        this.isShoot2=false;
        this.isShoot3=false;
	}

	public EnemyBigBee() {
		this(0,0);
	}

	@Override
	public void move(float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
        this.count += speed*Math.PI/360;
        if(this.count < Math.PI){
            this.speedX = (float) (radius*Math.sin(this.count));
            this.speedY = (float) (radius*Math.cos(this.count));
        }
        else if(this.count < Math.PI*3){
            this.speedX = (float) (-radius*Math.sin(this.count));
            this.speedY = (float) (radius*Math.cos(this.count));
        }else if(this.count < Math.PI*4){
            this.speedX = (float) (radius*Math.sin(this.count));
            this.speedY = (float) (radius*Math.cos(this.count));
        }
        else{
            this.count = 0;
        }
        this.moveX(this.speedX);
        this.moveY(this.speedY);
	}

	@Override
	public boolean collision(ObjOnMap obj) {
		// TODO 自動生成されたメソッド・スタブ
        float diffX = obj.getX() - this.x;
        float diffY = obj.getY() - this.y;
        if(diffX * diffX + diffY * diffY <= (this.width/2)*(this.height/2)){
            if(Math.abs(diffX)>25 && diffY >= this.height/2){
                return false;
            }
            return true;
        }
        return false;
	}

	@Override
	public void shoot(ArrayList<ObjOnMap> list, float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
        this.canShoot();
        if(this.isShoot == true){
            ObjOnMapFactory.ewJikiBullet(this.x, this.y, x,y,list);
            this.isShoot = false;
        }
        if(this.isShoot2 == true){
        	ObjOnMapFactory.ewJikiBullet(this.x, this.y, x,y, 22.5,list);
            this.isShoot2 = false;
        }
        if(this.isShoot3 == true){
        	ObjOnMapFactory.ewJikiBullet(this.x, this.y, x,y,list);
            this.isShoot3 = false;
        }
	}

	@Override
	public void canShoot() {
		// TODO 自動生成されたメソッド・スタブ
        if(this.isShoot3 == false){
            this.shootCount += 1;
            if( this.shootCount == 30){
                this.isShoot = true;
            }
            if( this.shootCount == 60){
                this.isShoot2 = true;
            }
            if( this.shootCount == 90){
                this.isShoot3 = true;
            }
            if(this.shootCount>180){
                this.shootCount = 0;
            }
        }
    }
}
