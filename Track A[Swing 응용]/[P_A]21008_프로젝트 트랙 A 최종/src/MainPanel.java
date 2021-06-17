import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

enum DrawMode{NONE,RECT,OVAL,GROUP,CANCLE_GROUP,MOVE,COPY}

@SuppressWarnings("serial")
class MainPanel extends JPanel {
	//tab 기능을 추가하면서 전역변수로 전환..
	static DrawMode drawMode = DrawMode.NONE;
	static int thickMode = 1; //새로 그리는 도형의 선 두께를 1로할지 3으로할지..
	static Color fillColor = null; //도형안을 무슨 색으로 채울지..
	static Color outColor = Color.BLACK; //도형의 선을 무슨 색으로 할지..
	
	TabPanel tp;

	public MainPanel() {
		this.setBackground(Color.white);
		
		
		InitComponent();
	}
	
	private void InitComponent() {
		SetToolBar();
		SetTab(false);
	}
	
	private void setAddTabButton() {
		JButton addBtn = new JButton("+");
		addBtn.setOpaque (false); //
	    addBtn.setBorder (null);
	    addBtn.setContentAreaFilled (false);
	    addBtn.setFocusPainted (false);
	    
	    ActionListener listener = new ActionListener () {
	        @Override
	        public void actionPerformed (ActionEvent e) {
	        	if(tp.getTabCount() >= 25) {
	        		System.out.println("너무 많음");
	        		return;
	        	}
	        	tp.removeTabAt(tp.getTabCount() - 1);
	            String title = "Tab " + String.valueOf (tp.getTabCount ());
	            DrawPanel newDp = new DrawPanel();
	            newDp.setPreferredSize(new Dimension(1400,850));
	            newDp.setName(title);
	            tp.addTab(title, newDp);
	            setAddTabButton();
	            tp.setSelectedIndex(tp.getTabCount() - 2);
	        }
	    };
	    
	    addBtn.setFocusable(false);
	    addBtn.addActionListener(listener);
	    
	    tp.addTab("+",  addBtn);
	    
	    return;
	}
	private void SetTab(boolean isLoading) {
		tp = new TabPanel();
		tp.setOpaque(false);
		tp.setPreferredSize(new Dimension(1400, 925));
		if(!isLoading) {
			DrawPanel dp0 = new DrawPanel();
			dp0.setPreferredSize(new Dimension(1400,850));
			dp0.setName("Tab0");
			tp.addTab ("Tab0", dp0);
			setAddTabButton();
		}
		
	    tp.setVisible (true);
	    add(tp);
	}

	private void SetToolBar() {
		JToolBar toolBar = new JToolBar(1);
		ButtonGroup toggleGroup1 = new ButtonGroup();
		JToggleButton btnHand = new JToggleButton(new ImageIcon("img/Hand.png"));
		JToggleButton btnMove = new JToggleButton(new ImageIcon("img/Move.png"));
		JToggleButton btnCopy = new JToggleButton(new ImageIcon("img/Copy.png"));
		JToggleButton btnGroup = new JToggleButton(new ImageIcon("img/Group.png"));
		JToggleButton btnCancleGroup = new JToggleButton(new ImageIcon("img/CancleGroup.png"));
		JToggleButton btnRect = new JToggleButton(new ImageIcon("img/Rect.png"));
		JToggleButton btnOval = new JToggleButton(new ImageIcon("img/Oval.png"));
//		JToggleButton btnLine = new JToggleButton("선형");
		
		JLabel lbFill = new JLabel("채우기");
		ButtonGroup toggleGroup2 = new ButtonGroup();
		JToggleButton btnFillNone = new JToggleButton(new ImageIcon("img/Fill_None.jpg"));
		JToggleButton btnFillBlack = new JToggleButton(new ImageIcon("img/Fill_Black.jpg"));
		JToggleButton btnFillRed = new JToggleButton(new ImageIcon("img/Fill_Red.jpg"));
		JToggleButton btnFillYellow = new JToggleButton(new ImageIcon("img/Fill_Yellow.jpg"));
		
		JLabel lbOutlineColor = new JLabel("테두리색");
		ButtonGroup toggleGroup3 = new ButtonGroup();
		JToggleButton btnOutBlack = new JToggleButton(new ImageIcon("img/Fill_Black.jpg"));
		JToggleButton btnOutRed = new JToggleButton(new ImageIcon("img/Fill_Red.jpg"));
		JToggleButton btnOutYellow = new JToggleButton(new ImageIcon("img/Fill_Yellow.jpg"));
		
		JLabel lbThick = new JLabel("선두께");
		ButtonGroup toggleGroup4 = new ButtonGroup();
		JToggleButton btnThick1 = new JToggleButton(new ImageIcon("img/Thick_1.png"));
		JToggleButton btnThick3 = new JToggleButton(new ImageIcon("img/Thick_3.png"));
		JToggleButton btnThick5 = new JToggleButton(new ImageIcon("img/Thick_5.png"));
		
		//////////////////////////////////////////////
		ActionListener toolAction = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnHand) drawMode = DrawMode.NONE;
				else if(e.getSource() == btnMove) drawMode = DrawMode.MOVE;
				else if(e.getSource() == btnCopy) drawMode = DrawMode.COPY;
				else if(e.getSource() == btnGroup) drawMode = DrawMode.GROUP;
				else if(e.getSource() == btnCancleGroup) drawMode = DrawMode.CANCLE_GROUP;
				else if(e.getSource() == btnRect) drawMode = DrawMode.RECT;
				else if(e.getSource() == btnOval) drawMode = DrawMode.OVAL;
//				else if(e.getSource() == btnLine) dp.setDrawMode(DrawMode.LINE);
				
				else if(e.getSource() == btnFillNone) fillColor = null;
				else if(e.getSource() == btnFillBlack) fillColor = Color.BLACK;
				else if(e.getSource() == btnFillRed) fillColor = Color.RED;
				else if(e.getSource() == btnFillYellow) fillColor = Color.YELLOW;
				
				else if(e.getSource() == btnOutBlack) outColor = Color.BLACK;
				else if(e.getSource() == btnOutRed) outColor = Color.RED;
				else if(e.getSource() == btnOutYellow) outColor = Color.YELLOW;

				else if(e.getSource() == btnThick1) thickMode = 1;
				else if(e.getSource() == btnThick3) thickMode = 3;
				else if(e.getSource() == btnThick5) thickMode = 5;
			}
		};
		
		btnHand.addActionListener(toolAction);
		btnMove.addActionListener(toolAction);
		btnCopy.addActionListener(toolAction);
		btnGroup.addActionListener(toolAction);
		btnCancleGroup.addActionListener(toolAction);
		btnRect.addActionListener(toolAction);
		btnOval.addActionListener(toolAction);
//		btnLine.addActionListener(toolAction);
		
		btnFillNone.addActionListener(toolAction);
		btnFillBlack.addActionListener(toolAction);
		btnFillRed.addActionListener(toolAction);
		btnFillYellow.addActionListener(toolAction);
		
		btnOutBlack.addActionListener(toolAction);
		btnOutRed.addActionListener(toolAction);
		btnOutYellow.addActionListener(toolAction);
		
		btnThick1.addActionListener(toolAction);
		btnThick3.addActionListener(toolAction);
		btnThick5.addActionListener(toolAction);
		
		//////////////////////////////////////////////
		
		toggleGroup1.add(btnHand);
		toggleGroup1.add(btnMove);
		toggleGroup1.add(btnCopy);
		toggleGroup1.add(btnGroup);
		toggleGroup1.add(btnCancleGroup);
		toggleGroup1.add(btnRect);
		toggleGroup1.add(btnOval);
//		toggleGroup.add(btnLine);
		toolBar.add(btnHand);
		toolBar.add(btnMove);
		toolBar.add(btnCopy);
		toolBar.add(btnGroup);
		toolBar.add(btnCancleGroup);
		toolBar.add(btnRect);
		toolBar.add(btnOval);
//		toolBar.add(btnLine);
		toolBar.addSeparator(); // ↑ ToggleGroup1
		
		toggleGroup2.add(btnFillNone);
		toggleGroup2.add(btnFillBlack);
		toggleGroup2.add(btnFillRed);
		toggleGroup2.add(btnFillYellow);
		toolBar.add(lbFill);
		toolBar.add(btnFillNone);
		toolBar.add(btnFillBlack);
		toolBar.add(btnFillRed);
		toolBar.add(btnFillYellow);
		toolBar.addSeparator(); // ↑ 채우기
		
		toggleGroup3.add(btnOutBlack);
		toggleGroup3.add(btnOutRed);
		toggleGroup3.add(btnOutYellow);
		toolBar.add(lbOutlineColor);
		toolBar.add(btnOutBlack);
		toolBar.add(btnOutRed);
		toolBar.add(btnOutYellow);
		toolBar.addSeparator(); // ↑ 테두리색
		
		toggleGroup4.add(btnThick1);
		toggleGroup4.add(btnThick3);
		toggleGroup4.add(btnThick5);
		toolBar.add(lbThick);
		toolBar.add(btnThick1);
		toolBar.add(btnThick3);
		toolBar.add(btnThick5);
//		toolBar.addSeparator(); // ↑ 선 두께
		
		toolBar.setFloatable(false);
		this.add(toolBar);
	}

	public void save(String path) {
		if(tp == null) return;
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(path);
			oos = new ObjectOutputStream(fos);
			
			for(int i = 0; i < tp.getTabCount() - 1; i++) {
				oos.writeObject(tp.getDrawPanel(i));
			}
			oos.writeObject(null);
			
			System.out.println("저장 완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void load(String path) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(path);
			ois = new ObjectInputStream(fis);
			
			remove(tp);
			SetTab(true);
			
			Object obj = null;
			try {
				while((obj = ois.readObject()) != null) {
					DrawPanel dp = (DrawPanel) obj;
					tp.addTab(dp.getName(), dp);
				}
			} catch (EOFException e) {
				e.printStackTrace();
			}
			
			
			setAddTabButton();
			//add(tp);
			repaint();
			tp.repaint();
			System.out.println("불러오기 완료");
			System.out.println("탭 갯수 : " + tp.getTabCount());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void captureImage() {
		BufferedImage imagebuf=null;
	    try {
	        imagebuf = new Robot().createScreenCapture(tp.getSelectedComponent().getBounds());
	    } catch (AWTException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    }  
	     Graphics2D graphics2D = imagebuf.createGraphics();
	     tp.getSelectedComponent().paint(graphics2D);
	     try {
	        ImageIO.write(imagebuf,"jpg", new File("capture.jpg"));
	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	    	System.out.println("이미지를 캡쳐하는 중 오류가 발생!");
	    }
	}
	
	public void sendImage() {
		try {
			System.out.println("소켓을 엽니다.");
			String address = JOptionPane.showInputDialog(null, "초대할 유저의 ip를 입력하세요.", "초대하기", JOptionPane.OK_OPTION);
			
			Socket socket = new Socket(address, 13085);
	        OutputStream outputStream = socket.getOutputStream();

	        BufferedImage image = ImageIO.read(new File("capture.jpg"));

	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        ImageIO.write(image, "jpg", byteArrayOutputStream);

	        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
	        outputStream.write(size);
	        outputStream.write(byteArrayOutputStream.toByteArray());
	        outputStream.flush();
	        
	        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

	        String solution = JOptionPane.showInputDialog(null, "정답은 무엇인가요?", "조금 엉성한 캐치마인드", JOptionPane.OK_OPTION);
			System.out.println(solution);

	        // write the message we want to send
	        dataOutputStream.writeUTF(solution);
	        dataOutputStream.flush(); // send the message
	        dataOutputStream.close(); // close the output stream when we're done.

	        System.out.println("소켓을 닫습니다.");

	        Thread.sleep(100);
	        System.out.println("Closing: " + System.currentTimeMillis());
	        socket.close();
		} catch(ConnectException e) {
			System.out.println("접속된 클라이언트가 없습니다");
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
}