package objOnMap.body;

import objOnMap.ObjOnMap;

public abstract class Body extends ObjOnMap{
	protected float speedX, speedY;
	protected Body(float x,float y,String image_name){
        this.x=x;
        this.y=y;
        this.img=image_name;
        this.removeFlag=false;
        this.isShoot=true;
        this.shootCount=0;
        this.atk=1;
        this.speedX=0;
        this.speedY=0;
        this.isShow=true;
	}

	protected boolean moveX(float diffX) {//基本的な移動，動けないとfalse
		if(this.isXInMap(diffX)) {
			this.x+=diffX;
			return true;
		}
		return false;
	}

	protected boolean moveY(float diffY) {//基本的な移動，動けないとfalse
		if(this.isYInMap(diffY)) {
			this.y+=diffY;
			return true;
		}
		return false;
	}



}
