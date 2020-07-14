package objOnMap;

import java.util.ArrayList;

import method.Const;

public abstract class ObjOnMap {
	protected float x;
	protected float y;
	protected int hp;
	protected int width;
	protected int height;
	protected int atk;
	protected String img;
	protected boolean removeFlag;
	protected boolean isShoot;
	protected int shootCount;
	protected int shootInterval;
	protected boolean isShow;


	public abstract void move(float x,float y);//ここの引数は特定の場所に向けて移動するため
	abstract public boolean collision(ObjOnMap obj);

	public void damage(ObjOnMap obj) {//相手のatk分ダメージ
		this.hp-=obj.getAtk();
		if(this.hp<=0) {
			this.setRemoveFlag(true);
		}
	}
	public void damage(int i) {	//固定値ダメージ
		this.hp-=i;
		if(this.hp<=0) {
			this.setRemoveFlag(true);
		}
	}

	public void inShowRange(float x, float y) {//自機に対して画面内の一定の範囲に入っているか
		if(Math.abs(x-this.getX())<Const.isShowWidth && Math.abs(y-this.getY())<Const.isShowHeight) {
			this.setIsShow(true);
		}else {
			this.setIsShow(false);
		}
	}

	public boolean isXInMap(float x) {//x移動した後のX座標がマップの中に入っているか
		if(this.width/2<this.x +x&& this.x +x< Const.MapX-this.width/2) {//x座標の判定，入っていない場合
			return true;
		}
		return false;
	}

	public boolean isYInMap(float y) {//y移動した後のY座標がマップの中に入っているか
		if(this.height/2<this.y +y && this.y +y < Const.MapY-this.height/2) {//x座標の判定，入っていない場合
			return true;
		}
		return false;
	}

	public void inMap() {//マップの中にいるか
	    if(this.x-this.width < 0 || Const.MapX+this.width<=this.x) {
	        this.removeFlag=true;
	    }
	    if(this.y-this.height < 0 || Const.MapY+this.height<=this.y) {
	        this.removeFlag=true;
	    }
	}

	public abstract void shoot(ArrayList<ObjOnMap> list, float x, float y);//ここのx,yは自機狙いとかのため

    public abstract void canShoot();

	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}


	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public int getHp() {
		return hp;
	}


	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk=atk;
	}

	public boolean isRemoveFlag() {
		return removeFlag;
	}


	public void setRemoveFlag(boolean removeFlag) {
		this.removeFlag = removeFlag;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setIsShow(boolean is) {
		isShow=is;
	}

	public boolean isShow() {
		return isShow;
	}


}
