/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diary_crud;

/**
 *
 * @author decastrodaniel
 */
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JTable;

public class DatabaseCon {

    private String schema;
    private String password;
    private String username;
    private String connectionString;

    //main constructor for the connection string
    public DatabaseCon(String schema, String username, String password) {
        this.schema = schema;
        this.username = username;
        this.password = password;
        this.connectionString = "jdbc:mysql://localhost:3306/" + this.schema;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.schema, this.username, this.password)) {
            System.out.println("connection successful\nconnected to " + this.schema);
        } catch (SQLException e) {
            System.out.println("Connection unsuccessful\n" + e);
        }

    }

    public DatabaseCon() {
        this.schema = "diary_crud";
        this.password = "";
        this.username = "root";
        this.connectionString = "jdbc:mysql://localhost:3306/" + this.schema;
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.schema, this.username, this.password)) {
            System.out.println("connection successful\nconnected to " + this.schema);
        } catch (SQLException e) {
            System.out.println("Connection unsuccessful\n" + e);
        }
    }

    //inserting a data, this needs an array of values and an array of columns
    public void insert(String tblName, String[] column, String[] values) {
        try (Connection con = DriverManager.getConnection(this.getConnectionString(), this.getUsername(), this.getPassword())) {
            Statement stmt = con.createStatement();
            String query = "Insert into `" + this.getSchema() + "`.`" + tblName + "`(";
            int i = 0;
            int j = 0;
            while (i != column.length - 1) {
                query += column[i] + ", ";
                i++;
            }
            query += column[column.length - 1];
            query += ") values(";
            while (j != values.length - 1) {
                query += "?, ";
                j++;
            }
            query += "?";
            query += ");";

            PreparedStatement ps = con.prepareStatement(query);
            for (int a = 0; a < values.length; a++) {
                ps.setString(a + 1, values[a]);
            }

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Connection unsuccessful\n" + e);
        }
    }

    public void insert(String tblName, String[] column, Boolean[] values) {
        try (Connection con = DriverManager.getConnection(this.getConnectionString(), this.getUsername(), this.getPassword())) {
            Statement stmt = con.createStatement();
            String query = "Insert into `" + this.getSchema() + "`.`" + tblName + "`(";
            int i = 0;
            int j = 0;
            while (i != column.length - 1) {
                query += column[i] + ", ";
                i++;
            }
            query += column[column.length - 1];
            query += ") values(";
            while (j != values.length - 1) {
                query += "?, ";
                j++;
            }
            query += "?";
            query += ");";

            PreparedStatement ps = con.prepareStatement(query);
            for (int a = 0; a < values.length; a++) {
                ps.setBoolean(a + 1, values[a]);
            }

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Connection unsuccessful\n" + e);
        }
    }

    //delete this needs table title and condition
    public void delete(String tblName, String condition) {
        try (Connection con = DriverManager.getConnection(this.getConnectionString(), this.getUsername(), this.getPassword())) {
            Statement stmt = con.createStatement();
            String query = "delete from `" + this.getSchema() + "`.`" + tblName + "` where " + condition + ";";
            int rs = stmt.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println("Connection unsuccessful\n" + e);
        }
    }

    //update.. this needs table name, field u want to update and the value u need to change and the cndition FUCK U!
    public void update(String tblName, String[] column, String[] value, String condition) {
        try (Connection con = DriverManager.getConnection(this.getConnectionString(), this.getUsername(), this.getPassword())) {
            int x = column.length - 1;

            String query = "update `" + this.getSchema() + "`.`" + tblName + "` set ";
            for (int i = 0; i < x; i++) {
                query += column[i] + "=?, ";
            }
            query += column[x] + "=? where " + condition;
            PreparedStatement ps = con.prepareStatement(query);
            for (int a = 0; a < value.length; a++) {
                ps.setString(a + 1, value[a]);
            }

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Connection unsuccessful\n" + e);
        }
    }

    public void update(String tblName, String[] column, Boolean[] value, String condition) {
        try (Connection con = DriverManager.getConnection(this.getConnectionString(), this.getUsername(), this.getPassword())) {
            Statement stmt = con.createStatement();
            int x = column.length - 1;

            String query = "update `" + this.getSchema() + "`.`" + tblName + "` set ";
            for (int i = 0; i < x; i++) {
                query += column[i] + "=?, ";
            }
            query += column[x] + "=? where " + condition;
            PreparedStatement ps = con.prepareStatement(query);
            for (int a = 0; a < value.length; a++) {
                ps.setBoolean(a + 1, value[a]);
            }

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Connection unsuccessful\n" + e);
        }
    }

//	para namn sa select query hahah
    public String[] select(String col, String tbl, String condition) {
        String[] values = new String[]{};
        try (Connection con = DriverManager.getConnection(this.getConnectionString(), this.getUsername(), this.getPassword())) {
            String query = "Select " + col + " from " + tbl + " where " + condition;
            String selectTableSQL = query;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            String[] tempVal = new String[numberOfColumns];
            int i;
            while (rs.next()) {
                for (i = 1; i <= numberOfColumns; i++) {
                    tempVal[i - 1] = rs.getString(i);
                }
            }
            values = tempVal;

        } catch (Exception e) {
            System.out.println(e);
        }
        return values;
    }

    public String[] search(String que, String col) {
        String[] values = new String[]{};
        int i = 0;
        try (Connection con = DriverManager.getConnection(this.getConnectionString(), this.getUsername(), this.getPassword())) {
            String query = que;

            String selectTableSQL = query;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try (Connection con = DriverManager.getConnection(this.getConnectionString(), this.getUsername(), this.getPassword())) {
            String query = que;
            String selectTableSQL = query;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            ResultSetMetaData rsmd = rs.getMetaData();
            String[] tempval = new String[i];
            int j = 0;
            while (rs.next()) {
                j++;
                tempval[j - 1] = rs.getString(col);
            }
            values = tempval;
        } catch (Exception e) {
            System.out.println(e);
        }

        return values;
    }
    //para namn sa pagkuha ng value for maintenace

    public String[] selectMaintenance(String col, String tbl) {
        String[] values = new String[]{};
        int i = 0;
        try (Connection con = DriverManager.getConnection(this.getConnectionString(), this.getUsername(), this.getPassword())) {
            String query = "Select " + col + " from " + tbl + ";";

            String selectTableSQL = query;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try (Connection con = DriverManager.getConnection(this.getConnectionString(), this.getUsername(), this.getPassword())) {
            String query = "Select " + col + " from " + tbl + ";";
            String selectTableSQL = query;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            ResultSetMetaData rsmd = rs.getMetaData();
            String[] tempval = new String[i];
            int j = 0;
            while (rs.next()) {
                j++;
                tempval[j - 1] = rs.getString(col);
            }
            values = tempval;
        } catch (Exception e) {
            System.out.println(e);
        }

        return values;
    }

    public String[] selectMaintenance(String col, String tbl, String cond) {
        String[] values = new String[]{};
        int i = 0;
        try (Connection con = DriverManager.getConnection(this.getConnectionString(), this.getUsername(), this.getPassword())) {
            String query = "Select " + col + " from " + tbl + ";";
            //   System.out.println(query);
            String selectTableSQL = query;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try (Connection con = DriverManager.getConnection(this.getConnectionString(), this.getUsername(), this.getPassword())) {
            String query = "Select " + col + " from " + tbl + " where " + cond + ";";
            String selectTableSQL = query;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            ResultSetMetaData rsmd = rs.getMetaData();
            String[] tempval = new String[i];
            int j = 0;
            while (rs.next()) {
                j++;
                tempval[j - 1] = rs.getString(col);
            }
            values = tempval;
        } catch (Exception e) {
            System.out.println(e);
        }
        return values;
    }

    /**
     * @return the schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the connectionString
     */
    public String getConnectionString() {
        return connectionString;
    }

    public void setTableSQL(String sql, JTable tbl) {
        ArrayList columnNames = new ArrayList();
        int lenggg = count(sql)[0];
        int cols = count(sql)[1];
        Object[][] fullData = new Object[lenggg][cols];
        try (Connection connection = DriverManager.getConnection(this.connectionString, this.username, this.password);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            Object[][] xyz = new Object[lenggg][columns];
            for (int i = 1; i <= columns; i++) {
                columnNames.add(md.getColumnName(i));
            }
            int x = 0;
            while (rs.next()) {
                ArrayList row = new ArrayList(columns);

                for (int i = 1; i <= columns; i++) {
                    row.add(rs.getObject(i));
                }
                Object[] singleRow = new Object[columns];
                singleRow = row.toArray(singleRow);
                for (int y = 0; y < columns; y++) {
                    //System.out.println(x+ " "+y+" :"+singleRow[y]);
                    xyz[x][y] = singleRow[y];
                }
                x++;
            }
            fullData = xyz;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String[] colnames = new String[columnNames.size()];
        tbl.setModel(new javax.swing.table.DefaultTableModel(fullData, columnNames.toArray(colnames)));
    }

    public int[] count(String a) {
        int[] bb = new int[2];
        try (Connection con = DriverManager.getConnection(this.getConnectionString(), this.getUsername(), this.getPassword())) {
            String selectTableSQL = a;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();

            int i = 0;
            while (rs.next()) {
                i++;
            }
            bb[0] = i;
            bb[1] = numberOfColumns;
        } catch (Exception e) {
            System.out.println(e);
        }
        return bb;
    }

}
