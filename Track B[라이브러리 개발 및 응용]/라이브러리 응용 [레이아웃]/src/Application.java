import java.awt.Dimension;
import java.util.Random;

class AppFrame extends MyFrame {
	private int testCase = 0;
	private MyToolBar appToolBar;
	private MyButton btnGrid;
	private MyButton btnFlow;
	private MyButton btnAddButton;
	
	private MyGridLayout grid;
	private MyFlowLayout flow;
	
	public AppFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(1200,300);
		this.setSize(1000,500);
		this.setVisible(true);
		
		InitComponent();
	}
	
	public void InitComponent() {
		appToolBar = new MyToolBar();
		btnGrid = new MyButton("그리드");
		btnFlow = new MyButton("플로우");
		btnAddButton = new MyButton("버튼추가");
		appToolBar.add(btnGrid);
		appToolBar.add(btnFlow);
		appToolBar.add(btnAddButton);
		appToolBar.setActionListener(new LayoutButtonActionListener());
		this.addToolBar(appToolBar);
		
		grid = new MyGridLayout(3, 4);
		flow = new MyFlowLayout(MyFlowLayout.CENTER);
		
		for(int i = 0; i < testCase; i++) {
			MyButton btn = new MyButton("test" + i);
			add(btn);
			btn.setSize(100,50);
			btn.setLocation(i % 2 * 100, i / 2 * 50 + this.getTopArea());
		}
		
		repaint();
	}
	
	class LayoutButtonActionListener implements MyActionListener {
		public void actionPerformed(MyEvent e) {
			if(e.getSource() == btnGrid) {
				setLayout(grid);
			} else if(e.getSource() == btnAddButton) {
				MyButton btn = new MyButton("test" + testCase);
				add(btn);
				btn.setSize(100,50);
				btn.setLocation(testCase % 2 * 100, testCase / 2 * 50 + getTopArea());
				if(testCase % 5 == 0) {
					Random rand = new Random();
					btn.setPreferredSize(new Dimension(rand.nextInt(200), rand.nextInt(150)));
				}
				testCase++;
			} else if(e.getSource() == btnFlow) setLayout(flow);
			
			repaint();
		}
	}
}
public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AppFrame mf = new AppFrame();
	}

}
