package panel.makerGUI;

public class ObjMode {//GUIの現在の状態を保持
	private String mode;
	private OneObjPanel nowPanel;//新たにクリックされたときに前のパネルの枠を消すため
	private String fileName;
	private boolean isJiki;
	ObjMode(){
		init();
	}

	public void init() {
		mode="";
		nowPanel=null;
		fileName="";
		isJiki=false;
	}

	String getMode(){
		return mode;
	}

	void setMode(String m){
		mode=m;
	}

	OneObjPanel getNowPanel(){
		return nowPanel;
	}


	void setNowPanel(OneObjPanel p){
		nowPanel=p;
	}

	boolean isJiki(){
		return isJiki;
	}

	void setIsJiki(boolean b) {
		isJiki=b;
	}

	void setFileName(String name) {
		fileName=name;
	}

	String getFileName() {
		return fileName;
	}
}
