package panel.makerGUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import method.Const;
import method.LoadFile;

public class ActionPanel extends JPanel{
	static ObjMode objMode;

	public ActionPanel(ObjMode objMode) {
		ActionPanel.objMode=objMode;
	    JButton button1=new JButton("新規");
	    JButton button2=new JButton("開く");
	    JButton button3=new JButton("保存");
	    JButton button4=new JButton("名前を付けて保存");
	    JButton button5=new JButton("試し打ち");
	    JButton button6=new JButton("ステージの削除");

	    button1.addActionListener(new newMapListener());
	    button2.addActionListener(new loadFileListener());
	    button3.addActionListener(new saveFileListener());
	    button4.addActionListener(new saveNamedFileListener());
	    button5.addActionListener(new trialListener());
	    button6.addActionListener(new deleteFileListener());

	    this.setLayout(new FlowLayout());
	    this.add(button1);
	    this.add(button2);
	    this.add(button3);
	    this.add(button4);
	    this.add(button5);
	    this.add(button6);

	    this.setPreferredSize(this.getPreferredSize());
	}

	private class newMapListener implements ActionListener{//新しくステージを作る
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			objMode.init();
			MakerGUI.mapPanel.init();
		}
	}

	private class loadFileListener implements ActionListener {//ステージ読み込み
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			String[] stageNames=LoadFile.stageAllNames();
			if(stageNames!=null) {
		    String name = (String) JOptionPane.showInputDialog(null, "開くステージを選択",
		            "開くステージを選択", JOptionPane.QUESTION_MESSAGE, null, // Use,default,icon
		            stageNames, // Array of choices
		            null); // Initial choice
			MakerGUI.mapPanel.loadStage(name);
			}else {
		    	JOptionPane.showMessageDialog(null, "ステージがありません");
			}
		}
	}

	private class saveFileListener implements ActionListener{//ステージ名が今のままで保存
		public void actionPerformed(ActionEvent e) {
			if(objMode.getFileName() == null || LoadFile.isPresetStageName(objMode.getFileName())){
		    	JOptionPane.showMessageDialog(null, "名前を付けて保存してください");
			}else if(!saveStage(MakerGUI.mapPanel.onMap, MakerGUI.objMode.getFileName(), false)) {
		    	JOptionPane.showMessageDialog(null, "出力に失敗しました");
		    }else {
		    	MakerGUI.manager.initStageSelect();//ステージリストを更新
		    }
		}
	}

	private class saveNamedFileListener implements ActionListener{//ステージを名前を付けて保存
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("ファイル名を入力");
			if(!objMode.isJiki()) {
		    	JOptionPane.showMessageDialog(null, "自機が置かれていません");
			}
			else if (name == null){
		    	JOptionPane.showMessageDialog(null, "その名前は使えません");
		    }else if(LoadFile.isPresetStageName(name)) {
		    	JOptionPane.showMessageDialog(null, "その名前は使えません");
			}else{
		        if(!saveStage(MakerGUI.mapPanel.onMap, name,false)) {
			    	JOptionPane.showMessageDialog(null, "出力に失敗しました");
		        }else {
			    	MakerGUI.manager.initStageSelect();//ステージリストを更新
		        }
		    }
		}
	}

	private class trialListener implements ActionListener{//試し打ち
		public void actionPerformed(ActionEvent e) {
			if(!objMode.isJiki()) {
		    	JOptionPane.showMessageDialog(null, "自機が置かれていません");
			}else {
		    	if(!saveStage(MakerGUI.mapPanel.onMap, Const.TEST_STAGE, true)) {
		    		JOptionPane.showMessageDialog(null, "ステージが未完成です");
			    }else {
			    	MakerGUI.manager.setStage();//テストステージを読み込み
			    	MakerGUI.manager.setPlaying(true);//試し打ちにセッティング
				}
			}
		}
	}

	private class deleteFileListener implements ActionListener {//ステージ削除
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			String[] stageNames=LoadFile.stageExternalNames();
			if(stageNames!=null) {
		    String name = (String) JOptionPane.showInputDialog(null, "削除するステージを選択",
		            "削除するステージを選択", JOptionPane.QUESTION_MESSAGE, null, // Use,default,icon
		            stageNames, // Array of choices
		            null); // Initial choice
		    File del=new File(Const.stagePath+name+Const.stageExtention);
		    if(del.exists()){
		    	if(!del.delete()) {
					JOptionPane.showMessageDialog(null, "削除に失敗しました");
		    	}
		    }
			}else {
		    	JOptionPane.showMessageDialog(null, "ステージがありません");
			}
		}
	}


	private static boolean saveStage(OnMap[][] onMap, String stageName, boolean test) {//試し打ちならtestがtrue
		/*ファイルの形式は
		 * Enemy1?1?1!で一区切り，間がEnemyが名前，1,1がx,y座標
		 *Conts.Num_OBJ_ATTRで要素数を決めておく,追加したらここも変える
		 */
		//一応こっちでも使える名前か判定
		if(LoadFile.isPresetStageName(stageName) && !test) {
			return false;
		}
		//保存は外部ファイルにする
		File dir=new File(Const.stageExternalPath);
		if(!dir.exists()){//外部ファイルがなかったら作る
			System.out.println("external save dir dont exist "+ dir);
			if(dir.mkdir()) {//作れなかったらfalse返す
				System.out.println("external save dir cant create");
				return false;
			}
		}
		//System.out.println("external save dir dont exist "+ dir);
		File f=new File(Const.stageExternalPath+"/"+stageName+Const.stageExtention);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter (f);
			writer.print(stageName+Const.DIV_OBJ_SIGN);
			for(OnMap[] om:onMap) {
				for(OnMap o:om) {
					if(o!=null && o.getParent()==null) {//親オブジェクトの核だったら
						writer.print(o.getName()+Const.DIV_ATTR_SIGN+o.getX()+Const.DIV_ATTR_SIGN+o.getY()+Const.DIV_OBJ_SIGN);
					}
				}
			}
			//writer.print(calcCheckSum(onMap));
			writer.close();//完成したら出力
		}catch (FileNotFoundException ex) {
			return false;
		}finally {
			if (writer != null) {
				//writer.close();とりあえず途中出力
			}
		}
		return true;
	}
}
