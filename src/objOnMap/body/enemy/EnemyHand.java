package objOnMap.body.enemy;

import java.util.ArrayList;

import method.Const;
import objOnMap.Collision;
import objOnMap.ObjOnMap;
import objOnMap.body.Body;

public abstract class EnemyHand extends Body{
	private float dirX,dirY;
	protected float centerX,centerY;
	private float motoX,motoY;//自機追いかけ始めた位置
	private float sakiX,sakiY;//追いかける場所
	private static float speed=6;
	protected float count;
	private boolean chaseFlag;
	private boolean chaseReturnFlag;
	private static float chaceThreshold=50;//どこまで追いかけて戻るか
	private static float radius=3;

	final private int DISTANCE=1000;//手の直径1000のところに入ったら追尾開始


	protected EnemyHand(float x, float y, String image_name) {
		super(x, y, image_name);
		// TODO 自動生成されたコンストラクター・スタブ
        this.hp=15;
        this.dirX=0;
        this.dirY=0;
        this.centerX=this.x;
        this.centerY=this.y;
        this.motoX=0;
        this.motoY=0;
        this.sakiX=0;
        this.sakiY=0;
        this.chaseFlag=false;
        this.chaseReturnFlag=false;
        this.width=Const.oneCell*8;
        this.height=Const.oneCell*8;
        this.count=0;
        this.shootInterval=180;
        this.isShoot=false;
	}

	@Override
	public void move(float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
		//System.out.println(this.shootCount);
        if(!this.chaseFlag){
            this.count += (Math.PI/360)*speed;
            this.speedX =  (float) (-radius*Math.sin(this.count));
            this.speedY =  (float) (radius*Math.cos(this.count));
            if(this.count >= Math.PI*2){
            	this.count = 0;
            }
        }
        else {
        	this.speedX=dirX*speed;
        	this.speedY=dirY*speed;
        }
        this.moveX(this.speedX);
        this.moveY(this.speedY);
	}

    private void decide_dir(float JikiX,float JikiY){
    	float diffX=JikiX-this.x;
    	float diffY=JikiY-this.y;
		this.dirX=(float) (diffX/Math.sqrt(diffX*diffX+diffY*diffY));
		this.dirY=(float) (diffY/Math.sqrt(diffX*diffX+diffY*diffY));
    }


	public void attack(float JikiX, float JikiY) {
		float diffSakiX=this.x-this.sakiX;
		float diffSakiY=this.y-this.sakiY;
		float diffMotoX=this.x-this.motoX;
		float diffMotoY=this.y-this.motoY;

		if(!this.chaseFlag && this.isShoot){
	       	//自機狙いに変化
	       	this.decide_dir(JikiX,JikiY);
	       	this.motoX=this.x;
	       	this.motoY=this.y;
	        this.sakiX=JikiX;
	        this.sakiY=JikiY;
	        this.chaseFlag=true;
	    }
		//目的地の閾値以内に入ったら帰る
	    if(!this.chaseReturnFlag && Math.abs(diffSakiX)<chaceThreshold && Math.abs(diffSakiY)<chaceThreshold){
	    	this.dirX*=-1;
	        this.dirY*=-1;
	        this.chaseReturnFlag=true;
	    }else if(this.chaseReturnFlag && (Math.abs(diffMotoX)<chaceThreshold && Math.abs(diffMotoY)<chaceThreshold)){
	        this.chaseFlag=false;
	        this.chaseReturnFlag=false;
	        this.isShoot=false;
	    }
	}

	private boolean nearJiki(float jikiX, float jikiY) {//手の近くに来たら追尾開始
		return Collision.circleCollision(this, jikiX, jikiY, DISTANCE, DISTANCE);
	}

	@Override
	public void shoot(ArrayList<ObjOnMap> list, float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
		if(!chaseFlag) {
            this.canShoot();
  		}
		if(!nearJiki(x,y)) {
			this.isShoot=false;//遠ければ追尾始めない
		}
		if(isShoot) {
			attack(x,y);
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

	@Override
	public abstract boolean collision(ObjOnMap obj);

}
