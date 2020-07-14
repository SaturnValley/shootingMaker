package objOnMap.body.jiki;

import java.util.ArrayList;

import method.Const;
import objOnMap.ObjOnMap;
import objOnMap.body.Body;
public abstract class Jiki extends Body {
	protected float nowSpeedX, nowSpeedY;
	protected float speedXMax, speedYMax;
	protected int showCount;
	protected boolean right;
	protected boolean down;
	protected boolean left;
	protected boolean up;
	protected boolean space;

	public Jiki(float x, float y, String img) {
		super(x, y, img);
		// TODO 自動生成されたコンストラクター・スタブ
	    this.setRight(false);
	    this.setLeft(false);
	    this.setDown(false);
	    this.setUp(false);
	    this.setSpace(false);
	}

	@Override
	abstract public boolean collision(ObjOnMap obj);

    boolean inFrame(ObjOnMap obj) {
        if(obj.getX() >= (this.x + Const.showWidth * 1.5 )) {
            return false;
        }
        else if(obj.getX() <= (this.x - Const.showWidth * 1.5)) {
            return false;
        }
        if(obj.getY() >= (this.y + Const.showHeight * 1.5)) {
            return false;
    	}
        else if(obj.getY() <= (this.y - Const.showHeight * 1.5)) {
            return false;
        }
        return true;
    }

    public boolean inXEdge() {
    	return (inXLeftEdge() || inXRightEdge());
    }


    public boolean inYEdge() {
    	return (inYUpEdge() || inYDownEdge());
    }

    public boolean inXRightEdge() {//マップの右端にいる
    	return this.x>=Const.MapX-Const.showWidth/2;
    }

    public boolean inXLeftEdge() {//マップの左端にいる
    	return this.x<=Const.showWidth/2;
    }

    public boolean inYUpEdge() {//マップの上端にいる
    	return this.y<=Const.showHeight/2;
    }

    public boolean inYDownEdge() {//マップの下端にいる
    	return this.y>=Const.MapY-Const.showHeight/2;
    }


	@Override
	public void canShoot() {
		// TODO 自動生成されたメソッド・スタブ
        if(this.isShoot==false && this.shootCount<this.shootInterval) {
            this.shootCount+=1;
        }
        else {
            this.shootCount=0;
            this.isShoot=true;
        }
	}


	public boolean isUp() {
		return up;
	}


	public void setUp(boolean up) {
		this.up = up;
	}


	public boolean isDown() {
		return down;
	}


	public void setDown(boolean down) {
		this.down = down;
	}


	public boolean isRight() {
		return right;
	}


	public void setRight(boolean right) {
		this.right = right;
	}


	public boolean isLeft() {
		return left;
	}


	public void setLeft(boolean left) {
		this.left = left;
	}


	public boolean isSpace() {
		return space;
	}


	public void setSpace(boolean space) {
		this.space = space;
	}


	@Override
	abstract public void shoot(ArrayList<ObjOnMap> list, float x, float y) ;


}
