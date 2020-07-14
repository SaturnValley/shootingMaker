package objOnMap.body.jiki;

import java.util.ArrayList;

import objOnMap.Collision;
import objOnMap.ObjOnMap;
import objOnMap.ObjOnMapFactory;

public class Goddess extends Jiki {
	final private int jikiHP = 1;//デバッグするときは値を上げる

	public Goddess(float x, float y) {
		super(x, y, "goddess");
		// TODO 自動生成されたコンストラクター・スタブ
		this.hp = jikiHP;
		this.speedX = 9f;
		this.speedY = 9f;
		this.nowSpeedX = 0;
		this.nowSpeedY = 0;
		this.showCount = 0;
		this.shootInterval = 15;
		this.width = 32;
		this.height = 32;

	}

	public Goddess() {
		this(0f, 0f);
	}

	public void JikiReset() {
		this.hp = jikiHP;
		this.nowSpeedX = 0;
		this.nowSpeedY = 0;
		this.showCount = 0;
		this.removeFlag = false;
		this.isShoot = true;
	}

	@Override
	public boolean collision(ObjOnMap obj) {
		// TODO 自動生成されたメソッド・スタブ
		return Collision.circleCollision(obj, this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	@Override
	public void move(float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
		if (left) {
			this.nowSpeedX = -this.speedX;
		}
		if (right) {
			this.nowSpeedX = this.speedX;
		}
		if (!left && !right) {
			this.nowSpeedX = 0;
		}
		if (down) {
			this.nowSpeedY = this.speedY;
		}
		if (up) {
			this.nowSpeedY = -this.speedY;
		}
		if (!down && !up) {
			this.nowSpeedY = 0;
		}
		this.moveX(this.nowSpeedX);
		this.moveY(this.nowSpeedY);
	}

	@Override
	public void shoot(ArrayList<ObjOnMap> list, float x, float y) {
		// TODO 自動生成されたメソッド・スタブ
		this.canShoot();
		if (this.isShoot && this.isSpace()) {
			ObjOnMapFactory.myBulletNormal(this.x, this.y, list);
			this.isShoot = false;
			this.setSpace(false);
		}

	}
}
