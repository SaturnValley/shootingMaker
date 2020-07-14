package objOnMap;

import objOnMap.body.jiki.Arwin;

public class Collision {

	public static boolean circleCollision(ObjOnMap obj, float thisX, float thisY, int width, int height) {
        float x = obj.getX() - thisX;
        float y = obj.getY() - thisY;
        if(x * x + y * y <= (width/2)*(height/2)) {
            return true;
        }
		return false;
	}
	public static boolean rectCollision(ObjOnMap obj, float thisX, float thisY, int width, int height) {
		return rectCollisionAngle(obj, thisX, thisY, width, height, 0);
	}

	public static boolean rectCollisionAngle(ObjOnMap obj, float thisX, float thisY, int width, int height, double angle) {
        float x = obj.getX() - thisX;
        float y = obj.getY() - thisY;
        double rad=-angle/180*Math.PI;
		float rotateX=(float)(Math.cos(rad)*x-Math.sin(rad)*y);
		float rotateY=(float)(Math.cos(rad)*y+Math.sin(rad)*x);
       	if(Math.abs(rotateX) <= width/2 && Math.abs(rotateY)<=height/2){
            return true;
        }
		return false;
	}


	public static boolean[] collisionPoints(ObjOnMap obj) {//objの当たり判定のある位置を返す
		int width=obj.getWidth();
		int height=obj.getHeight();
		boolean[] bool=new boolean[width*height];
		ObjOnMap test_o=(ObjOnMap)new Arwin(0, 0); //テスト用として自機を作成，別のやつでもよし
		int leftUpX=(int) obj.getX()-width/2;
		int leftUpY=(int) obj.getY()-height/2;
		int count=0;
		for(int y=leftUpY;y<leftUpY+height;y++) {
			test_o.setY(y);
			for(int x=leftUpX;x<leftUpX+width;x++) {
				test_o.setX(x);
				if(obj.collision(test_o)) {
					bool[count]=true;
				}
				count++;
			}
		}
		return bool;
	}
}
