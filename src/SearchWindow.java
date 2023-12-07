import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class SearchWindow  extends JFrame implements ActionListener {

    JPanel newPanel;
    String currentTableName;
    JButton inputButton;
    JTextField inputText;
    String[] names;
    JTable table;
    MySQLAccess access = new MySQLAccess();
    JComboBox<String> tableMenu;
    JComboBox<String> columnMenu;
    Vector<String> tables;

    public SearchWindow() throws SQLException, ClassNotFoundException {
        tables = new Vector<String>();
        currentTableName = "Clients";
        tables.add("Клиенты");
        tables.add("Заказы");
        tables.add("Туры");
        tables.add("Отели");
        tables.add("Отзывы");
        tables.add("Услуги отелей");
        tables.add("Таблица экскурсий туров");
        tables.add("Таблица услуг отелей");
        tables.add("Таблица отзывов на туры");
        tableMenu = new JComboBox<>(tables);
        tableMenu.setSize(1000, 30);
        tableMenu.setSelectedIndex(0);
        tableMenu.addActionListener(this);
        names = new String[]{"client_id", "name", "email", "phone_number"};
        columnMenu = new JComboBox<>(names);
        inputText = new JTextField("search by");
        access.searchInTable("Clients", "client_id","1");
        try {
            table = new JTable(access.getTable(), names);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        inputButton = new JButton("Ввод");
        inputButton.addActionListener(this);
        newPanel = new JPanel(new GridLayout(5, 1));
        newPanel.add(tableMenu);
        newPanel.add(table);
        newPanel.add(columnMenu);
        newPanel.add(inputText);
        newPanel.add(inputButton);
        add(newPanel, BorderLayout.CENTER);
        setTitle("SEARCH");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String chosenTable = (String) tableMenu.getSelectedItem();
        if (e.getSource() == tableMenu) {

            switch (chosenTable) {
                case "Клиенты":
                    names = new String[]{"client_id", "name", "email", "phone_number"};
                    currentTableName = "Clients";

                    break;
                case "Заказы":
                    names = new String[]{"order_id", "client_demands", "client_id", "tour_id"};
                    currentTableName = "Orders";
                    break;
                case "Туры":
                    names = new String[]{"tour_id", "destination", "price", "tour_start_date", "tour_end_date", "hotel", "duration"};
                    currentTableName = "Tours";
                    break;
                case "Отели":
                    names = new String[]{"hotel_id", "hotel_name", "hotel_star_rating", "available_room_number"};
                    currentTableName = "Hotels";
                    break;
                case "Отзывы":
                    names = new String[]{"review_id", "review_contents", "review_rating"};
                    currentTableName = "Reviews";
                    break;
                case "Услуги отелей":
                    names = new String[]{"service_id", "service_name", "service_date", "service_description", "service_price"};
                    currentTableName = "Hotel_services";
                    break;
                case "Таблица экскурсий туров":
                    names = new String[]{"Excursions_excursion_id", "Tours_tour_id"};
                    currentTableName = "Excursion_list";
                    break;
                case "Таблица услуг отелей":
                    names = new String[]{"Hotels_hotel_id1", "Hotel_services_service_id"};
                    currentTableName = "Hotel_services_list";
                    break;
                case "Таблица отзывов на туры":
                    names = new String[]{"Tours_tour_id", "Reviews_review_id"};
                    currentTableName = "Tour_review_list";
                    break;

            }
            columnMenu = new JComboBox<>(names);
            newPanel.add(columnMenu, 3);
            newPanel.remove(2);
            newPanel.updateUI();
        }
        else if (e.getSource() == inputButton){
            String columnName = columnMenu.getSelectedItem().toString();
            System.out.println(columnName);
            String value = inputText.getText();
            try {
                access.searchInTable(currentTableName, columnName, value);
                table = new JTable(access.getTable(), names);
                newPanel.add(table, 2);
                newPanel.remove(1);
                newPanel.updateUI();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
