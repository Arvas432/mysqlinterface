import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class ProcedureScreen  extends JFrame implements ActionListener {
    JTable table;
    JComboBox<String> procedureMenu;
    String[] names;
    JPanel newPanel;
    MySQLAccess access = new MySQLAccess();
    public ProcedureScreen() throws SQLException, ClassNotFoundException {
        table = new JTable();
        Vector<String> procedureNames = new Vector<>();
        procedureNames.add("Calculate_Tour_Length");
        procedureNames.add("get_cheap_excursions");
        procedureNames.add("get_tours_and_hotels");
        procedureNames.add("get_tours_and_reviews");
        procedureMenu = new JComboBox<>(procedureNames);
        procedureMenu.addActionListener(this);
        newPanel = new JPanel(new GridLayout(2, 1));
        newPanel.add(procedureMenu);
        newPanel.add(table);
        add(newPanel, BorderLayout.CENTER);
        setTitle("PROCEDURES");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String chosenProcedure = (String) procedureMenu.getSelectedItem();
        switch (chosenProcedure){
            case "Calculate_Tour_Length":
                names = new String[]{"tour_duration"};
                break;
            case "get_cheap_excursions":
                names = new String[]{"excursion_id", "excursion_name", "excursion_description", "price", "date"};
                break;
            case "get_tours_and_hotels":
                names = new String[]{"destination", "price", "hotel_name"};
                break;
            case "get_tours_and_reviews":
                names = new String[]{"destination", "price","review_contents","review_rating"};
                break;
        }
        try {
            access.getProcedure(chosenProcedure);
            table = new JTable(access.getTable(), names);
            newPanel.remove(1);
            newPanel.add(table);
            newPanel.updateUI();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }
}
