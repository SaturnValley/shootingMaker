package objOnMap;


public class Direction {
	public static float[] jikiDirectionAngle(float x, float y,float jikiX, float jikiY, double angle) {
		//angleはラジアンでなく45とか
		double rad=angle/180*Math.PI;
		float dirX=jikiX-x;
		float dirY=jikiY-y;
		float norm_diff_x=(float) (dirX/Math.sqrt(dirX*dirX+dirY*dirY));
		float norm_diff_y=(float) (dirY/Math.sqrt(dirX*dirX+dirY*dirY));
		float new_dirX=(float)(Math.cos(rad)*norm_diff_x-Math.sin(rad)*norm_diff_y);
		float new_dirY=(float)(Math.cos(rad)*norm_diff_y+Math.sin(rad)*norm_diff_x);
		float[] l= {new_dirX, new_dirY};
		return l;
	}

	public static float[] jikiDirection(float x, float y, float jikiX, float jikiY) {
		return jikiDirectionAngle(x,y,jikiX,jikiY,0);
	}


}
