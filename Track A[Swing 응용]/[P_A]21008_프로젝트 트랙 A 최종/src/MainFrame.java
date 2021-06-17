import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
class MainFrame extends JFrame {
	MainPanel mp;
	
	public MainFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1500,1000);
		this.setLocation(500, 300);
		this.setResizable(false);
		
		initMenuBar();
		
		mp = new MainPanel();
		this.setContentPane(mp);
		this.setVisible(true);
	}
	
	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuFile = new JMenu("File");
		JMenuItem itemNew = new JMenuItem("New");
		JMenuItem itemOpen = new JMenuItem("Load");
		JMenuItem itemSave = new JMenuItem("Save"); 
		JMenuItem itemExit = new JMenuItem("Exit"); 
		
		JMenu menuServer = new JMenu("Game");
		JMenuItem itemCapture = new JMenuItem("화면 캡처");
		JMenuItem itemSendImage = new JMenuItem("문제 전송");
		
		JMenu menuHelp = new JMenu("Help");
		JMenuItem itemAbout = new JMenuItem("About");
		
		//////////////////////////////////////////////////
		ActionListener itemAction = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
//				MainPanel mp = (MainPanel) getContentPane();
				if(e.getSource() == itemNew) {
					mp = new MainPanel();
					setContentPane(mp);
					revalidate();
					repaint();
				}
				else if(e.getSource() == itemOpen) mp.load("save/data.md");
				else if(e.getSource() == itemSave) mp.save("save/data.md");
				else if(e.getSource() == itemExit) System.exit(0);
				else if(e.getSource() == itemCapture) mp.captureImage();
				else if(e.getSource() == itemSendImage) mp.sendImage();
				else if(e.getSource() == itemAbout) System.out.println("About");
			}
		};
		
		itemNew.addActionListener(itemAction);
		itemOpen.addActionListener(itemAction);
		itemSave.addActionListener(itemAction);
		itemExit.addActionListener(itemAction);
		itemCapture.addActionListener(itemAction);
		itemSendImage.addActionListener(itemAction);
		itemAbout.addActionListener(itemAction);
		
		//////////////////////////////////////////////////
		menuFile.add(itemNew);
		menuFile.add(itemOpen);
		menuFile.addSeparator();
		menuFile.add(itemSave);
		menuFile.addSeparator();
		menuFile.add(itemExit);
		menuBar.add(menuFile);
		
		menuServer.add(itemCapture);
		menuServer.add(itemSendImage);
		menuBar.add(menuServer);
		
		menuHelp.add(itemAbout);
		menuBar.add(menuHelp);
		
		setJMenuBar(menuBar);
	}
}