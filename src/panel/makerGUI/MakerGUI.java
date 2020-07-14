package panel.makerGUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import manager.PanelManager;


public class MakerGUI extends JPanel{
	static ObjMode objMode;
	static MapPanel mapPanel;
	static PanelManager manager;

	public MakerGUI(PanelManager manager){
	    MakerGUI.manager=manager;
	    init();
	}

	public void init() {
		this.removeAll();
	    objMode=new ObjMode();
	    mapPanel=new MapPanel(objMode);
		ActionPanel actionPanel=new ActionPanel(objMode);
	    ObjSelectPanel objSelectPanel=new ObjSelectPanel(objMode);
	    this.setLayout(new GridLayout());
	    JPanel jp=new JPanel();
	    jp.setLayout(new FlowLayout());
	    jp.add(actionPanel);
	    jp.add(objSelectPanel);
	    this.add(mapPanel);
	    this.add(jp);
	}

}

