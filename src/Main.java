import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // 創建並顯示首頁
                HomePage homePage = new HomePage();
                homePage.setVisible(true);
            }
        });
    }
}