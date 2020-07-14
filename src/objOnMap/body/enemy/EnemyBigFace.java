package objOnMap.body.enemy;

import java.util.ArrayList;

import method.Const;
import objOnMap.ObjOnMap;
import objOnMap.ObjOnMapFactory;
import objOnMap.body.Body;

public class EnemyBigFace extends Body {

	EnemyHand leftHand;
	EnemyHand rightHand;
	boolean doubleHand;
	public EnemyBigFace(float x, float y, ArrayList<ObjOnMap> list) {
		super(x, y, "face");
		// TODO 自動生成されたコンストラクター・スタブ
        this.hp = 30;
        this.width=Const.oneCell*8;
        this.height=Const.oneCell*8;
        this.isShoot=false;
        leftHand=new EnemyLeftHand(x+this.width, y+this.height/2);
        rightHand=new EnemyRightHand(x-this.width, y+this.height/2);
        list.add(leftHand);
        list.add(rightHand);
        doubleHand=true;
        this.shootInterval=40;
	}

	public EnemyBigFace(){
		this(0,0,new ArrayList<ObjOnMap>());
	}

	public void damage(ObjOnMap obj) {//相手のatk分ダメージ
		this.hp-=obj.getAtk();
		if(this.hp<=0) {
			this.setRemoveFlag(true);
			//顔死んだら両手も死ぬ
			leftHand.setRemoveFlag(true);
			rightHand.setRemoveFlag(true);
		}
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
		//両手あるなら何もしない
		if(doubleHand) {
			handRemove();
		}
		else {
	        this.canShoot();
	        if(this.isShoot){
	        	//自機狙い
	        	ObjOnMapFactory.bigJikiBullet(this.x, this.y,x,y,1.5f,list);
	            this.isShoot=false;
	        }
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

	private void handRemove() {
		if(leftHand.isRemoveFlag() && rightHand.isRemoveFlag()) {
			doubleHand=false;
		}
	}




}
