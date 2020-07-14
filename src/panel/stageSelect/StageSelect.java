package panel.stageSelect;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import manager.PanelManager;
import method.Const;
import method.LoadFile;
import panel.GUIComponent;

public class StageSelect extends JPanel {
	static PanelManager manager;
	private static Image img;
	private int stagesNum;//全ステージ数
	private int cursor;
	private ArrayList<JLabel> labelList;
	private JLabel arrowUp;
	private JLabel arrowDown;
	private static LinkedList<JLabel> nowLabelList;
	private int maxShow = Const.NUM_SHOW_SATGE;//一度に表示する数
	private static String state;
	private int cursorCenter;

	public StageSelect(PanelManager manager) {
		this.setSize(Const.showWidth, Const.showHeight);
		img = new ImageIcon(getClass().getClassLoader().getResource(Const.imagePath + Const.TITLE_IMAGE)).getImage();
		StageSelect.manager = manager;
		Font f = new Font("ＭＳ ゴシック", Font.PLAIN, 32);
		arrowUp = GUIComponent.createWhiteTextLabel(f, "↑");
		arrowDown = GUIComponent.createWhiteTextLabel(f, "↓");
		arrowUp.addMouseListener(new arrowUpListener());
		arrowDown.addMouseListener(new arrowDownListener());
		init();
		this.addKeyListener(new keyListener());//キーイベントの登録
		this.setLayout(null);

	}

	public void init() {
		for (Component c : this.getComponents()) {
			this.remove(c);
		}
		String[] stages = LoadFile.stageAllNames();
		Font f = new Font("ＭＳ ゴシック", Font.PLAIN, 32);

		this.setSize(Const.showWidth, Const.showHeight);
		cursor = 0;
		cursorCenter = cursor;
		labelList = new ArrayList<JLabel>();
		nowLabelList = new LinkedList<JLabel>();
		this.setLayout(null);

		if (stages != null) {
			createLabels(stages, f);
		}
		stagesNum = labelList.size();
		if (maxShow > stagesNum) {
			maxShow = stagesNum;
		}

		for (int i = 0; i < maxShow; i++) {
			nowLabelList.addLast(labelList.get(i));
			setBounds(i, nowLabelList.get(i));
			this.add(nowLabelList.get(i));
		}
		setBounds(-1, arrowUp);
		this.add(arrowUp);
		setBounds(maxShow, arrowDown);
		this.add(arrowDown);

		if (maxShow > 0) {
			labelOn(nowLabelList.get(cursor));
		}
	}

	private JLabel createStageSelectLabel(String text, Font f) {
		JLabel l = GUIComponent.createWhiteTextLabel(f, text);
		l.addMouseListener(new clickListener());
		return l;
	}

	private void createLabels(String[] stages, Font f) {//ラベル全部をとりあえず作成
		for (int i = 0; i < stages.length; i++) {
			if (stages[i] != null) {
				labelList.add(createStageSelectLabel(stages[i], f));
			}
		}
	}

	private boolean isCursorEdge() {//カーソルの位置がスクロールできない端にあるか
		return (cursor < (int) (maxShow / 2) || (stagesNum - 1 - (int) (maxShow / 2) < cursor));
	}

	private void updateNowLabel(boolean up) {//上に行くならup=true,下に行くならup=false
		//System.out.println("corsor"+cursor);
		if (maxShow > 0) {
			if (up) {
				nowLabelList.removeLast();
				nowLabelList.addFirst(labelList.get(cursor - (int) (maxShow / 2)));
			} else {
				nowLabelList.removeFirst();
				nowLabelList.addLast(labelList.get(cursor + (int) (maxShow / 2)));
			}

			//this.removeAll();
			for (Component c : this.getComponents()) {
				this.remove(c);
			}

			this.setSize(Const.showWidth, Const.showHeight);
			for (int i = 0; i < maxShow; i++) {
				setBounds(i, nowLabelList.get(i));
				this.add(nowLabelList.get(i));
				//System.out.println(nowLabelList.get(i).getText());
			}
			this.add(arrowUp);
			this.add(arrowDown);
		}

	}

	private void setBounds(int col, JLabel l) {
		l.setBounds(this.getSize().width / 2 - l.getFont().getSize() * l.getText().length() / 2,
				this.getSize().height * 2 / 3 + l.getFont().getSize() * col,
				(int) (l.getFont().getSize() * l.getText().length() * 1.1),
				l.getFont().getSize());

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int w = this.getSize().width;
		int h = this.getSize().height;
		g2d.drawImage(img, 0, 0, w, h, this);
	}

	static private void labelAllOff() {
		for (JLabel l : nowLabelList) {
			l.setBorder(null);
		}
	}

	static private void labelOn(JLabel l) {//ラベルの枠線と現在のモードを同期
		l.setBorder(new LineBorder(Color.RED, 1, true));
		state = l.getText();
		//System.out.println("label on "+state);
	}

	private void cursorDown() {
		if (cursor < stagesNum - 1) {
			cursor++;
			if (isCursorEdge()) {
				cursorCenter++;
			} else {
				if (cursorCenter == (int) maxShow / 2) {
					updateNowLabel(false);
				} else {
					cursorCenter = (int) maxShow / 2;
				}
			}
		}
	}

	private void cursorUp() {
		if (cursor > 0) {
			cursor--;
			if (isCursorEdge()) {
				cursorCenter--;
			} else {
				if (cursorCenter == (int) maxShow / 2) {
					updateNowLabel(true);
				} else {
					cursorCenter = (int) maxShow / 2;
				}
			}
		}
	}

	private void updateLabel() {
		labelAllOff();
		labelOn(nowLabelList.get(cursorCenter));
		state = nowLabelList.get(cursorCenter).getText();
	}

	private class keyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			char key = e.getKeyChar();
			int keyCode = e.getKeyCode();
			if(key=='t') {
				manager.setTitle();
			}
			if (key == ' ') {//スペースキー
				if (state != null) {
					for (String[] s : Const.STAGE_SAMPLE_JP) {
						if (state.equals(s[1])) {//プリセットのステージ名をもとに戻している
							state = s[0];
						}
					}
					if (manager.setStage(state)) {
						manager.initPlaying();
						manager.setPlaying();
					} else {
						//System.out.println("StageSelectPanel keyPressed" + state);
					}
				}
			}
			if (key == 's' || keyCode == KeyEvent.VK_DOWN) {//下
				if (maxShow > 0) {
					cursorDown();
					updateLabel();
				}
			} else if (key == 'w' || keyCode == KeyEvent.VK_UP) {//上
				if (maxShow > 0) {
					cursorUp();
					updateLabel();
				}

			}
			//System.out.println(state);

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
		}
	}

	private class clickListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			String name = label.getText();
			//System.out.println("StageSelectPanel Click"+name);
			for (String[] s : Const.STAGE_SAMPLE_JP) {
				if (name.equals(s[1])) {//プリセットのステージ名をもとに戻している
					name = s[0];
				}
			}
			if (manager.setStage(name)) {
				manager.initPlaying();
				manager.setPlaying();
			} /*else {
				System.out.println("stageSelectPanel mouseClicked"+label.getName());
				}*/

		}

		public void mouseEntered(MouseEvent e) {
			System.out.println("StageSelectPanel Click"+e.getComponent().getName());
			JLabel label = (JLabel) e.getComponent();
			System.out.println("StageSelectPanel Click"+label.getName());
			StageSelect.labelAllOff();
			StageSelect.labelOn(label);
			StageSelect.state = label.getName();
		}
	}

	private class arrowUpListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (maxShow > 0) {
				cursorUp();
				updateLabel();
			}
		}
	}

	private class arrowDownListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (maxShow > 0) {
				cursorDown();
				updateLabel();
			}
		}
	}

}
