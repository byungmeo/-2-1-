import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CanvasViewer extends JFrame {	
	
	Image img;
	
	public CanvasViewer() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setLocation(300, 300);
		this.setVisible(true);
		
		receiveImage();
	}
	
	@Override
	public void paint(Graphics g) {
		if(img!=null) {
			g.drawImage(img,0,0,1400,900,null);
		}
	}
    
    public void receiveImage() {
    	while(true) {
    		try {
        		ServerSocket serverSocket = new ServerSocket(13085);
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();

                System.out.println("이미지 파일을 받는 중...");

                //이미지
                byte[] sizeAr = new byte[4];
                inputStream.read(sizeAr);
                int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                byte[] imageAr = new byte[size];
                inputStream.read(imageAr);

                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                this.img = image;
                setSize(img.getWidth(rootPane), img.getHeight(rootPane));
                repaint();
                System.out.println("이미지 파일 수신 완료");
                
                
                System.out.println("문제 출제자가 정답을 입력 중입니다..");
                //정답
                int i;
                StringBuffer buffer = new StringBuffer();
                byte[] b = new byte[4096];
                while( (i = inputStream.read(b)) != -1){
                	buffer.append(new String(b, 0, i));
                }
                
                
                String temp = buffer.toString();
                String solution = "";
                for(int j = 0; j < temp.length(); j++) {
                	if(temp.charAt(j) < 32) continue;
                	solution+=temp.charAt(j);
                }
                
                
                System.out.println("문제 출제자가 정답을 입력 완료하였습니다.");
                System.out.println("정답 : " + solution);
                solution.replace((char)0, (char)13);
                solution.replace((char)3, (char)13);
                System.out.println(solution);
                
                String answer = JOptionPane.showInputDialog(null, "이 그림의 정체를 알아맞춰보세요.", "조금 엉성한 캐치마인드", JOptionPane.OK_OPTION);
                System.out.println(answer);
                answer.trim();
                
                if(answer.equals(solution)) {
                	JOptionPane.showMessageDialog(null, "정답입니다!");
                } else {
                	JOptionPane.showMessageDialog(null, "틀렸습니다! 정답은 [" + solution + "] 입니다!!");
                }
//                ImageIO.write(image, "jpg", new File("receive.jpg"));

                serverSocket.close();
        	} catch(Exception e) {
        		e.printStackTrace();
        	}
    	}
  
    }
}
