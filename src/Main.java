

public class Main {
    public static void main(String[] args) throws Exception {
        MySQLAccess dao = new MySQLAccess();
       // dao.readDataBase();
        AdminScreen as = new AdminScreen();
        as.setSize(1000,500);
        //GUI loginGUI = new GUI();
        //loginGUI.setSize(1000,500);
       // loginGUI.setVisible(true);
        as.setVisible(true);
    }
}