package manager;

import java.util.ArrayList;

import javax.swing.JPanel;

import objOnMap.ObjOnMap;
import objOnMap.body.jiki.Jiki;
import panel.gameClear.GameClear;
import panel.gameOver.GameOver;
import panel.makerGUI.MakerGUI;
import panel.modeSelect.ModeSelect;
import panel.playGame.PlayGame;
import panel.stageSelect.StageSelect;
import panel.title.Title;

public class PanelManager {
	static private Title titlePanel;
	static private StageSelect stageSelect;
	static private ModeSelect modeSelect;
	static private PlayGame playGame;
	static private MakerGUI makerGUI;
	static private GameOver gameOver;
	static private GameClear gameClear;
	static private Manager manager;

	PanelManager(Manager manager){
		PanelManager.manager=manager;
		titlePanel=new Title(this);
		stageSelect=new StageSelect(this);
		modeSelect=new ModeSelect(this);
		playGame =new PlayGame(this);
		makerGUI=new MakerGUI(this);
		gameOver=new GameOver(this);
		gameClear=new GameClear(this);
	}

	JPanel getTitlePanel() {
		titlePanel.setFocusable(true);//このパネルにフォーカスを合わせる，キーイベント取得のため
		return titlePanel;
	}

	JPanel getStageSelectPanel() {
		stageSelect.setFocusable(true);
		return stageSelect;
	}

	public void initStageSelect() {
		stageSelect.init();
	}

	JPanel getModeSelectPanel() {
		initModeSelect();
		modeSelect.setFocusable(true);
		return modeSelect;
	}

	public void initModeSelect() {
		modeSelect.init();
	}


	JPanel getPlayGamePanel() {
		initPlaying();
		playGame.setFocusable(true);
		return playGame;
	}

	public void initPlaying() {
		playGame.init();
	}

	JPanel getCreateStagePanel() {
		makerGUI.setFocusable(true);
		return makerGUI;
	}

	public void initCreateStage() {
		makerGUI.init();
	}

	JPanel getGameClearPanel() {
		gameClear.setFocusable(true);
		return gameClear;
	}
	JPanel getGameOverPanel() {
		gameOver.setFocusable(true);
		return gameOver;
	}

	//ここより下はManagerと各パネルをつなぐため
	public ArrayList<ObjOnMap> returnAllObjList() {
		return manager.returnAllObjList();
	}

	public ArrayList<ObjOnMap> returnEnemyList() {
		return manager.returnEnemyList();
	}

	public Jiki returnJiki() {
		return manager.returnJiki();
	}

	public void jibaku() {//自爆用，自機のHPを0に，強制終了
		manager.jibaku();
	}

	public void jikiSetSpace(boolean b) {
		manager.jikiSetSpace(b);
	}
	public void jikiSetUp(boolean b) {
		manager.jikiSetUp(b);
	}
	public void jikiSetLeft(boolean b) {
		manager.jikiSetLeft(b);
	}
	public void jikiSetRight(boolean b) {
		manager.jikiSetRight(b);
	}
	public void jikiSetDown(boolean b) {
		manager.jikiSetDown(b);
	}

	public void calcObj() {
		manager.calcObj();
	}

	public void setTitle() {
		manager.setTitle();
	}

	public void setStage() {
		manager.setStage();//テストステージを読み込み
	}

	public void setPlaying(boolean b) {
		manager.setPlaying(true);//試し打ちにセッティング
	}

	public void setStageSelect() {
		manager.setStageSelect();
	}

	public void setCreateStage() {
		manager.setCreateStage();
	}

	public boolean isPlaying() {
		return manager.isPlaying();
	}

	public void setPlaying() {
		manager.setPlaying();
	}

	public void setModeSelect(){
		manager.setModeSelect();
	}


	public boolean setStage(String name) {
		return manager.setStage(name);
	}
}

