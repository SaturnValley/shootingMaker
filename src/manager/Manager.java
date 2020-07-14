package manager;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import objOnMap.ObjOnMap;
import objOnMap.body.jiki.Jiki;

public class Manager {
	private ObjManager objManager;
	private PanelManager panelManager;
	private JFrame jf;
	private boolean trial;
	private State state;
	ArrayList<ObjOnMap> allObjList;


	private enum State{
		Title,
		Playing,
		ModeSelect,
		CreateStage,
		StageSelect,
		GameClear,
		GameOver,
	}

	public Manager(JFrame jf) {
		state=State.Title;
		objManager=new ObjManager();
		panelManager=new PanelManager(this);
		trial=false;
		allObjList=new ArrayList<>();
		this.jf=jf;
	}


	public void checkGameClear() {
		if(objManager.gameClear()) {
			if(trial) {
				this.setCreateStage();//ステージ作成画面に戻る
			}else {
				this.setGameClear();
			}
		}
	}

	public void checkGameOver() {
		if(objManager.gameOver()) {
			if(trial) {
				this.setCreateStage();//ステージ作成画面に戻る
			}else {
				this.setGameOver();
			}
		}
	}

	public void calcObj() {
		objManager.calc();
		checkGameClear();
		checkGameOver();
	}

	public boolean setStage() {
		return objManager.setStage();
	}

	public boolean setStage(String stage) {
		return objManager.setStage(stage);
	}

	public Jiki returnJiki() {
		return objManager.jiki;
	}

	public ArrayList<ObjOnMap> returnAllObjList() {//もし別個のArrayListができた場合はそっちも加える，ゲームクリア条件変えたときとか
		allObjList.clear();
		allObjList.addAll(objManager.enemy_list);
		allObjList.addAll(objManager.enemy_bullet_list);
		allObjList.addAll(objManager.jiki_bullet_list);
		return allObjList;
	}

	public ArrayList<ObjOnMap> returnEnemyList(){
		return objManager.enemy_list;
	}

	public void setTitle() {
		state=State.Title;
		setMainPanel(panelManager.getTitlePanel());
	}

	public boolean isTitle() {
		return this.state==State.Title;
	}

	public void setPlaying() {//普通にプレイする時は引数なし
		setPlaying(false);
	}


	public void setPlaying(boolean trial) {//試し打ちの時は引数あり
		state=State.Playing;
		this.trial=trial;
		setMainPanel(panelManager.getPlayGamePanel());
	}

	public boolean isPlaying() {
		return this.state==State.Playing;
	}


	public void setModeSelect() {
		state=State.ModeSelect;
		setMainPanel(panelManager.getModeSelectPanel());
	}


	public boolean isModeSelect() {
		return this.state==State.ModeSelect;
	}

	public void setCreateStage() {
		state=State.CreateStage;
		setMainPanel(panelManager.getCreateStagePanel());
	}


	public boolean isCreateStage() {
		return this.state==State.CreateStage;
	}


	public void setStageSelect() {
		state=State.StageSelect;
		setMainPanel(panelManager.getStageSelectPanel());
	}


	public boolean isStageSelect() {
		return this.state==State.StageSelect;
	}

	public void setGameClear() {
		state=State.GameClear;
		setMainPanel(panelManager.getGameClearPanel());

	}
	public boolean isGameClear() {
		return this.state==State.GameClear;
	}

	public void setGameOver() {
		state=State.GameOver;
		setMainPanel(panelManager.getGameOverPanel());

	}
	public boolean isGameOver() {
		return this.state==State.GameOver;
	}

	public void jibaku() {//自爆用，自機のHPを0に，強制終了
		objManager.jiki.setRemoveFlag(true);
	}

	public void jikiSetSpace(boolean b) {
		objManager.jiki.setSpace(b);
	}
	public void jikiSetUp(boolean b) {
		objManager.jiki.setUp(b);
	}
	public void jikiSetLeft(boolean b) {
		objManager.jiki.setLeft(b);
	}
	public void jikiSetRight(boolean b) {
		objManager.jiki.setRight(b);
	}
	public void jikiSetDown(boolean b) {
		objManager.jiki.setDown(b);
	}

	public void setMainPanel(JPanel mainPanel) {
		jf.getContentPane().removeAll();
		jf.add(mainPanel);
		jf.validate();
		jf.repaint();
		mainPanel.requestFocusInWindow();
	}

}
