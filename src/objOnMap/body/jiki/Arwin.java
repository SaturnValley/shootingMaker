package objOnMap.body.jiki;

import java.util.ArrayList;

import objOnMap.Collision;
import objOnMap.ObjOnMap;
import objOnMap.ObjOnMapFactory;

public class Arwin extends Jiki{
	final private int jikiHP=1;//デバッグするときは値を上げる

	public Arwin(float x, float y) {
		super(x, y, "arwin");
			// TODO 自動生成されたコンストラクター・スタブ
		    this.hp=jikiHP;
		    this.speedX = 0.1f;
		    this.speedY = 0.1f;
		    this.nowSpeedX=0;
		    this.nowSpeedY=0;
		    this.speedXMax = 9f;
		    this.speedYMax = 9f;
		    this.showCount=0;
		    this.shootInterval=15;
		    this.width=32;
		    this.height=32;
		}

		public Arwin() {
			this(0f,0f);
		}


		public void JikiReset() {
			this.hp=jikiHP;
	        this.nowSpeedX=0;
	        this.nowSpeedY=0;
	        this.showCount=0;
	        this.removeFlag=false;
	        this.isShoot=true;
		}

		@Override
		public boolean collision(ObjOnMap obj) {
			// TODO 自動生成されたメソッド・スタブ
			return Collision.circleCollision(obj, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		}

		@Override
		public void move(float x,float y) {
			// TODO 自動生成されたメソッド・スタブ
	        if(left) {
	            this.nowSpeedX-=this.speedX;
	            if(-this.speedXMax >= this.nowSpeedX) {
	                this.nowSpeedX = -(this.speedXMax);
	            }
	        }
	        if(right) {
	            this.nowSpeedX+=this.speedX;
	            if(this.speedXMax <= this.nowSpeedX) {
	                this.nowSpeedX = (this.speedXMax);
	            }
	        }
	        if(down) {
	            this.nowSpeedY+=this.speedY;
	            if(this.speedYMax <= this.nowSpeedY) {
	                this.nowSpeedY = this.speedYMax;
	            }
	        }
	        if(up) {
	            this.nowSpeedY-=this.speedY;
	            if(-this.speedYMax >= this.nowSpeedY) {
	                this.nowSpeedY = -(this.speedYMax);
	            }
	        }
	       if(!this.moveX(this.nowSpeedX)) {
	    	   this.nowSpeedX=0;//動けないなら速度0に
	       }
	       if(!this.moveY(this.nowSpeedY)) {
	    	   this.nowSpeedY=0;//動けないなら速度0に
	       }

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


		@Override
		public void shoot(ArrayList<ObjOnMap> list, float x, float y) {
			// TODO 自動生成されたメソッド・スタブ
			this.canShoot();
	        if(this.isShoot && this.isSpace()) {
	            ObjOnMapFactory.myBulletNormal(this.x, this.y,list);
	            this.isShoot=false;
	            this.setSpace(false);
	        }

		}
	}


