import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private void getConnection() throws SQLException, ClassNotFoundException {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/tour_company2?"
                            + "user=root&password=MyNewPass");
            statement = connect.createStatement();



        } catch (Exception e) {
            throw e;
        }
    }
    public MySQLAccess() throws SQLException, ClassNotFoundException {
        getConnection();
    }
    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            //ТУТ ВЫ ДРОВА ПОЛУЧАЕТЕ ИХ НАДО В ClassPath ВСТАВИТЬ В ПРОЕКТЕ ЕЩЕ
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            //СЮДА ВАШИ ЛОГИН И ПАРОЛЬ ОТ СКУЛЯ
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/tour_company?"
                            + "user=root&password=не дам пароль");
            //ЕЩЕ У МЕНЯ В UI ВСЕ НАЗВАНИЯ КОЛОНОК ЗАХАРДКОДЕНЫ ТК СКУЛЬ НЕ ХОЧЕТ ИХ НОРМАЛЬНО ОТДАВАТЬ
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            //КУСОК КОДА ИЗ ТУТОРИАЛА
            // Result set get the result of the SQL query
//            ResultSet resultSet1 = statement.executeQuery("select * from Tours");
//            System.out.println(connect.getMetaData().getCatalogs().getMetaData().getSchemaName(1));
//            printTable(resultSet1);
//            resultSet = statement
//                    .executeQuery("select * from Tours");
//            writeResultSet(resultSet);

            // PreparedStatements can use variables and are more efficient
//            preparedStatement = connect
//                    .prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
//            preparedStatement.setString(1, "Test");
//            preparedStatement.setString(2, "TestEmail");
//            preparedStatement.setString(3, "TestWebpage");
//            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
//            preparedStatement.setString(5, "TestSummary");
//            preparedStatement.setString(6, "TestComment");
//            preparedStatement.executeUpdate();

//            preparedStatement = connect
//                    .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
//            resultSet = preparedStatement.executeQuery();
//            writeResultSet(resultSet);

            // Remove again the insert comment
//            preparedStatement = connect
//                    .prepareStatement("delete from feedback.comments where myuser= ? ; ");
//            preparedStatement.setString(1, "Test");
//            preparedStatement.executeUpdate();
//
//            resultSet = statement
//                    .executeQuery("select * from feedback.comments");
//            writeMetaData(resultSet);

        } catch (Exception e) {
            throw e;
        }

    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }
    public void printTable() throws SQLException{
        while (resultSet.next()) {
            ResultSetMetaData metadata = resultSet.getMetaData();
            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                String columnName = metadata.getColumnName(i);
                System.out.println(columnName + ": " + resultSet.getString(columnName));
            }
            System.out.println("\n");
        }
    }
    public void setTableResultSet(String input) throws SQLException {
        //resultSet = statement.executeQuery("select * from " + input);
        resultSet = statement.executeQuery("select * from " + input);
    }
    public void searchInTable(String tableName, String columnName, String value) throws SQLException {
        resultSet = statement.executeQuery("select * from " + tableName + " where " + columnName + " = '" + value + "'");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }

    public String[] getColumnNames() throws SQLException{
        String[] columnNames = new String[resultSet.getMetaData().getColumnCount()];
        ResultSetMetaData metadata = resultSet.getMetaData();
        for (int i = 1; i <= metadata.getColumnCount(); i++) {
            String columnName = metadata.getColumnName(i);
            columnNames[i-1] = columnName;
        }

       return columnNames;
//        if (resultSet.next()){
//            ResultSetMetaData metadata = resultSet.getMetaData();
//            for (int i = 1; i <= metadata.getColumnCount(); i++){
//                System.out.println(i);
//                columnNames[i] = resultSet.getMetaData().getColumnName(i);
//                System.out.println(resultSet.getMetaData().getColumnName(i));
//            }
//            resultSet.close();
//            return columnNames;
//        }
//        else {
//            return null;
//        }


    }
    public Object[][] getTable() throws SQLException{
        //printTable();
        List<String[]> rowlist = new ArrayList<>();
        ResultSetMetaData metadata = resultSet.getMetaData();
        while(resultSet.next()){
            String[] row = new String[metadata.getColumnCount()];
            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                String columnName = metadata.getColumnName(i);
                row[i-1] = resultSet.getString(columnName);
            }
            rowlist.add(row);
        }
        Object[][] output = new Object[rowlist.size()][];
        for (int i = 0; i < rowlist.size(); i++){
            output[i] = rowlist.get(i);
        }
            return output;
    }
    public void getProcedure(String procedureName) throws SQLException {
        resultSet =  statement.executeQuery("call tour_company2."+ procedureName + "();");
    }
    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String tourId = resultSet.getString("tour_id");
            String destination = resultSet.getString("destination");
            String price = resultSet.getString("price");
            System.out.println("Tour: " + tourId);
            System.out.println("destination: " + destination);
            System.out.println("price: " + price);
        }
    }
    public void addElementToTable(String tableName,String valuesList,String  columns) throws SQLException {
        statement.executeUpdate("INSERT INTO "+ tableName + " "  + valuesList+ " VALUES " + columns + ";");
        //resultSet = statement.executeQuery("INSERT INTO "+ tableName + " "  + valuesList+ " VALUES " + columns + ";");
    }
    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
}
