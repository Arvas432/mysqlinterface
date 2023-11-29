import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class AdminScreen extends JFrame implements ActionListener {

    JButton deleteItem;
    String currentTableName;
    JLabel currentTableLabel;
    JPanel newPanel;
    JTable inputTable;
    JButton inputButton;
    String[] names;
    JTable table;
    MySQLAccess access = new MySQLAccess();
    JLabel chooseTable;
    JComboBox<String> tableMenu;
    Vector<String> tables;
    JLabel deleteItemById;

    public String formValuesList(Vector<String> values) {
        String output = "(";
        for (String value : values) {
            output += value + ", ";
        }
        output = output.substring(0, output.length() - 2);
        output += ")";
        return output;
    }

    public String formInputValuesList(Vector<String> values) {
        String output = "(";
        for (String value : values) {
            boolean isNumeric = value.chars().allMatch(Character::isDigit);
            if (isNumeric) {
                output += value + ", ";
            } else {
                output += "'" + value + "', ";
            }
        }
        output = output.substring(0, output.length() - 2);
        output += ")";
        return output;
    }

    AdminScreen() throws SQLException, ClassNotFoundException {
        chooseTable = new JLabel("Выберите таблицу");
        chooseTable.setSize(1000, 30);
        deleteItem = new JButton("Удалить строку");
        currentTableLabel = new JLabel("Выбранная таблица");
        inputTable = new JTable();
        tables = new Vector<String>();
        tables.add("Клиенты");
        tables.add("Заказы");
        tables.add("Туры");
        tables.add("Отели");
        tables.add("Отзывы");
        tables.add("Услуги отелей");
        tables.add("Таблица экскурсий туров");
        tables.add("Таблица услуг отелей");
        tables.add("Таблица отзывов на туры");
        table = new JTable();
        tableMenu = new JComboBox<>(tables);
        tableMenu.setSize(1000, 30);
        tableMenu.setSelectedIndex(0);
        tableMenu.addActionListener(this);
        inputButton = new JButton("Ввод");
        inputButton.addActionListener(this);
        newPanel = new JPanel(new GridLayout(4, 1));
        newPanel.add(chooseTable);
        newPanel.add(tableMenu);
        newPanel.add(currentTableLabel);
        newPanel.add(table);
        newPanel.add(inputTable);
        newPanel.add(inputButton);
        add(newPanel, BorderLayout.CENTER);
        setTitle("ADMIN");


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String chosenTable = (String) tableMenu.getSelectedItem();
        if (e.getSource() == tableMenu) {

            switch (chosenTable) {
                case "Клиенты":
                    try {
                        access.setTableResultSet("Clients");
                        names = new String[]{"client_id", "name", "email", "phone_number"};
                        currentTableName = "Clients";
                        currentTableLabel.setText("Clients");

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "Заказы":
                    try {
                        names = new String[]{"order_id", "client_demands", "client_id", "tour_id"};
                        access.setTableResultSet("Orders");
                        currentTableName = "Orders";
                        currentTableLabel.setText("Orders");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "Туры":
                    try {
                        names = new String[]{"tour_id", "destination", "price", "tour_start_date", "tour_end_date", "hotel", "duration"};
                        access.setTableResultSet("Tours");
                        currentTableName = "Tours";
                        currentTableLabel.setText("Tours");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "Отели":
                    try {
                        names = new String[]{"hotel_id", "hotel_name", "hotel_star_rating", "available_room_number"};
                        access.setTableResultSet("Hotels");
                        currentTableName = "Hotels";
                        currentTableLabel.setText("Hotels");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "Отзывы":
                    try {
                        names = new String[]{"review_id", "review_contents", "review_rating"};
                        access.setTableResultSet("Reviews");
                        currentTableName = "Reviews";
                        currentTableLabel.setText("Reviews");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "Услуги отелей":
                    try {
                        names = new String[]{"service_id", "service_name", "service_date", "service_description", "service_price"};
                        access.setTableResultSet("Hotel_services");
                        currentTableName = "Hotel_services";
                        currentTableLabel.setText("Hotel_services");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "Таблица экскурсий туров":
                    try {
                        names = new String[]{"Excursions_excursion_id", "Tours_tour_id"};
                        access.setTableResultSet("Excursion_list");
                        currentTableName = "Excursion_list";
                        currentTableLabel.setText("Excursion_list");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "Таблица услуг отелей":
                    try {
                        names = new String[]{"Hotels_hotel_id1", "Hotel_services_service_id"};
                        access.setTableResultSet("Hotel_services_list");
                        currentTableName = "Hotel_services_list";
                        currentTableLabel.setText("Hotel_services_list");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "Таблица отзывов на туры":
                    try {
                        names = new String[]{"Tours_tour_id", "Reviews_review_id"};
                        access.setTableResultSet("Tour_review_list");
                        currentTableName = "Tour_review_list";
                        currentTableLabel.setText("Tour_review_list");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;

            }
            try {
                table = new JTable(access.getTable(), names);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            Vector<Vector<String>> cells = new Vector<>();
            cells.add(new Vector<>());
            List<String> list = Arrays.asList(names);
            System.out.println(list);
            Vector<String> dummyCells = new Vector<String>(list);
            System.out.println(dummyCells);
            cells.add(dummyCells);
            inputTable = new JTable(cells, dummyCells);
            newPanel.remove(3);
            newPanel.add(table);
            inputTable.setBorder(new LineBorder(Color.black, 2));
            newPanel.remove(3);
            newPanel.add(inputTable);
            newPanel.remove(2);
            newPanel.add(inputButton);
            newPanel.updateUI();
        } else if (e.getSource() == inputButton) {
            Vector<String> tableData;
            List<String> list;
            Vector<String> vectoredNames;
            tableData = new Vector<>();
            list = Arrays.asList(names);
            vectoredNames = new Vector<>(list);
            for (int i = 0; i < inputTable.getColumnCount(); i++) {
                System.out.println(inputTable.getValueAt(1, i));
                tableData.add(inputTable.getValueAt(1, i).toString());
            }
            try {
                access.addElementToTable(currentTableName, formValuesList(vectoredNames), formInputValuesList(tableData));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);

            }
        }
    }
}
