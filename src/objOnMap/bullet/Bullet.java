package objOnMap.bullet;

import java.util.ArrayList;

import objOnMap.ObjOnMap;


public abstract class Bullet extends ObjOnMap{
	float dirX;
	float dirY;
	float speed;

	public Bullet(float x,float y,String img_name){
	        this.x = x;
	        this.y = y;
	        this.hp=1;
	        this.removeFlag = false;
	        this.img = img_name;
	        this.atk=1;
	        this.isShoot=false;
	        this.shootCount=0;
	        this.isShow=true;
	}

	//x,yは自機を追尾するときに使う
	public void move(float x, float y) {
        this.x += this.dirX*speed;
        this.y += this.dirY*speed;
	}



    public abstract boolean collision(ObjOnMap obj);



	@Override
	public void shoot(ArrayList<ObjOnMap> list, float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
		//自己複製的な弾のために用意，基本は何もしない
	}

	@Override
	public void canShoot() {
		// TODO 自動生成されたメソッド・スタブ
		//自己複製的な弾のために用意，基本は何もしない
	}


}
