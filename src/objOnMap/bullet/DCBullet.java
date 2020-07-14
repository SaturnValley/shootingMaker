package objOnMap.bullet;

import objOnMap.Collision;
import objOnMap.ObjOnMap;

//弾2つ出して変な軌道するやつ double curve
public class DCBullet extends Bullet{
	float haba;
	float kakudo;
	double hensa;
	int bit;

	public DCBullet(float x, float y, float dirX, float dirY, float speed, int bit) {
		super(x, y, "enemy_bullet");
		// TODO 自動生成されたコンストラクター・スタブ
	    kakudo=0;
	    hensa=5*Math.PI/360;
	    haba=1;
	    this.speed=speed;
		this.width=10;//半径
		this.height=10;//半径
		this.dirX=dirX;
		this.dirY=dirY;
		this.bit=bit;
	}

	@Override
	public boolean collision(ObjOnMap obj) {
		// TODO 自動生成されたメソッド・スタブ
		return Collision.circleCollision(obj, this.x, this.y, this.width, this.height);
	}

	@Override
	public void move(float x, float y) {
        this.kakudo+=this.hensa*bit;
        this.x += speed*dirX+this.haba*Math.sin(this.kakudo);
        this.y += speed*dirY+this.haba*Math.cos(this.kakudo);
        if(this.kakudo>=Math.PI*2) {
            this.kakudo=0;
        }
	}



}
