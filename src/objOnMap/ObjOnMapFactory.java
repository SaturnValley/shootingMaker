package objOnMap;

import java.util.ArrayList;

import objOnMap.body.enemy.EnemyBigBee;
import objOnMap.body.enemy.EnemyBigFace;
import objOnMap.body.enemy.EnemyBlackD;
import objOnMap.body.enemy.EnemyBlackO;
import objOnMap.body.enemy.EnemyBlackR;
import objOnMap.body.enemy.EnemyBlackT;
import objOnMap.body.enemy.EnemyBlackU;
import objOnMap.body.enemy.EnemyLeftHand;
import objOnMap.body.enemy.EnemyRightHand;
import objOnMap.body.enemy.EnemySmallFace;
import objOnMap.body.enemy.EnemyYellowO;
import objOnMap.body.jiki.Arwin;
import objOnMap.body.jiki.Goddess;
import objOnMap.body.jiki.Jiki;
import objOnMap.bullet.BigBullet;
import objOnMap.bullet.DCBullet;
import objOnMap.bullet.EnemyBulletNormal;
import objOnMap.bullet.FireFlowerBullet;
import objOnMap.bullet.MyBulletNormal;

public class ObjOnMapFactory {
	//ここからJikiインスタンスの生成

	//classNameによって作成するクラスを振り分け
	public static Jiki createJiki(String className, float x, float y) {
		Jiki jiki=null;
		if(className.equals("Arwin")) {
			jiki=(Jiki)arwin(x,y);
		}else if(className.equals("Goddess")) {
			jiki=(Jiki)goddess(x,y);
		}
		return jiki;
	}

	public static Arwin arwin(float x,float y) {
		return new Arwin(x,y);
	}
	public static Goddess goddess(float x,float y) {
		return new Goddess(x,y);
	}


	//ここからEnemyインスタンスの生成

	//classNameによって作成するクラスを振り分け
	public static ObjOnMap createEnemy(String className, float x, float y, ArrayList<ObjOnMap> array) {
		ObjOnMap oon=null;
		if(className.equals("EnemyBigBee")) {
			oon=(ObjOnMap)enemyBigBee(x,y);
		}else if(className.equals("EnemyBigFace")) {
			oon=(ObjOnMap)enemyBigFace(x,y,array);
		}else if(className.equals("EnemyBlackD")) {
			oon=(ObjOnMap)enemyBlackD(x,y);
		}else if(className.equals("EnemyBlackO")) {
			oon=(ObjOnMap)enemyBlackO(x,y);
		}else if(className.equals("EnemyBlackR")) {
			oon=(ObjOnMap)enemyBlackR(x,y);
		}else if(className.equals("EnemyBlackT")) {
			oon=(ObjOnMap)enemyBlackT(x,y);
		}else if(className.equals("EnemyBlackU")) {
			oon=(ObjOnMap)enemyBlackU(x,y);
		}else if(className.equals("EnemyLeftHand")) {
			oon=(ObjOnMap)enemyLeftHand(x,y);
		}else if(className.equals("EnemyBlackRightHand")) {
			oon=(ObjOnMap)enemyRightHand(x,y);
		}else if(className.equals("EnemyYellowO")) {
			oon=(ObjOnMap)enemyYellowO(x,y);
		}else if(className.equals("EnemySmallFace")) {
			oon=(ObjOnMap)enemySmallFace(x,y);
		}
		return oon;
	}

	public static EnemyBigBee enemyBigBee(float x, float y) {
		return new EnemyBigBee(x,y);
	}

	public static EnemyBigFace enemyBigFace(float x, float y, ArrayList<ObjOnMap> o) {
		return new EnemyBigFace(x,y,o);
	}

	public static EnemyBlackD enemyBlackD(float x, float y){
		return new EnemyBlackD(x,y);
	}

	public static EnemyBlackO enemyBlackO(float x, float y){
		return new EnemyBlackO(x,y);
	}
	public static EnemyBlackR enemyBlackR(float x, float y){
		return new EnemyBlackR(x,y);
	}
	public static EnemyBlackT enemyBlackT(float x, float y){
		return new EnemyBlackT(x,y);
	}
	public static EnemyBlackU enemyBlackU(float x, float y){
		return new EnemyBlackU(x,y);
	}
	public static EnemyLeftHand enemyLeftHand(float x, float y){
		return new EnemyLeftHand(x,y);
	}
	public static EnemyRightHand enemyRightHand(float x, float y){
		return new EnemyRightHand(x,y);
	}
	public static EnemyYellowO enemyYellowO(float x, float y){
		return new EnemyYellowO(x,y);
	}

	public static EnemySmallFace enemySmallFace(float x, float y){
		return new EnemySmallFace(x,y);
	}


	//ここからBulletインスタンスの生成
	public static void bigJikiBullet(float x, float y, float jikiX, float jikiY, float speed, ArrayList<ObjOnMap> list) {
		bigBullet(x,y,jikiX,jikiY,speed, 0, list);
	}

	public static void bigBullet(float x, float y, float jikiX, float jikiY, float speed, double angle, ArrayList<ObjOnMap> list) {
		float dir[]=Direction.jikiDirectionAngle(x, y, jikiX, jikiY, angle);
		list.add(new BigBullet(x,y,dir[0],dir[1],speed));
	}

	public static void dCJikiBullet(float x, float y, float jikiX, float jikiY, float speed, ArrayList<ObjOnMap> list) {
		float dir[]=Direction.jikiDirection(x, y, jikiX, jikiY);
		list.add(new DCBullet(x,y,dir[0],dir[1],speed,1));
		list.add(new DCBullet(x,y,dir[0],dir[1],speed,-1));
	}

	public static void enemyBulletNormal(float x, float y, float dirX, float dirY, float speed, ArrayList<ObjOnMap> list) {
		list.add(new EnemyBulletNormal(x,y,dirX,dirY,speed));
	}
	public static void enemyJikiBulletNormal(float x, float y, float jikiX, float jikiY, float speed, ArrayList<ObjOnMap> list) {
		float dir[]=Direction.jikiDirection(x, y, jikiX, jikiY);
		list.add(new EnemyBulletNormal(x,y,dir[0],dir[1],speed));
	}

	public static void ewJikiBullet(float x, float y, float jikiX, float jikiY, ArrayList<ObjOnMap> bullet_list){
		ewJikiBullet(x, y, jikiX, jikiY, 0 , bullet_list);
	}

	public static void ewJikiBullet(float x, float y, float jikiX, float jikiY, double angle, ArrayList<ObjOnMap> bullet_list){
		//angleはラジアンでなく弧度法45度とか
		for(int i=0;i<8;i++) {
			float dir[]=Direction.jikiDirectionAngle(x, y, jikiX, jikiY, angle+45.*i);
			bullet_list.add(new EnemyBulletNormal(x, y, dir[0], dir[1], 1.5f));
		}
	}

	public static void eWBullet(float x, float y,double angle, ArrayList<ObjOnMap> bullet_list){
		ewJikiBullet(x,y,x+1,y,angle,bullet_list);
	}

	public static void fireFlowerBullet(float x, float y, float jikiX, float jikiY, float speed, ArrayList<ObjOnMap> list) {
		float dir[]=Direction.jikiDirection(x, y, jikiX, jikiY);
		list.add(new FireFlowerBullet(x,y,dir[0],dir[1],speed));
	}

	public static void myBulletNormal(float x, float y, ArrayList<ObjOnMap> list) {
		list.add(new MyBulletNormal(x,y));
	}


	//三方向弾,three way
		//自機狙い
	public static void twJikiBullet(float x, float y, float jikiX, float jikiY, ArrayList<ObjOnMap> bullet_list){
		twBullet(x,y,jikiX,jikiY, bullet_list);
	}

	static void twBullet(float x, float y, float jikiX, float jikiY, ArrayList<ObjOnMap> bullet_list){
		float angle=0;
		for(int i=-1;i<2;i++) {
			float dir[]=Direction.jikiDirectionAngle(x, y, jikiX, jikiY, angle+30*i);
			bullet_list.add(new EnemyBulletNormal(x, y, dir[0], dir[1], 1.5f));
		}
	}

}
