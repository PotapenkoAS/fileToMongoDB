package mysqlManager;

import model.Counter;
import model.LineModel;

import java.sql.*;
import java.util.List;

public class MySQLManager {

    private static Connection con;
    private static PreparedStatement lines_st;
    private static PreparedStatement counters_st;

    private static String INSERT_LINES_SQL = "insert into `lines` (id, number, name, address, period, sum) values (?,?,?,?,?,?);";
    private static String INSERT_COUNTERS_SQL = "insert into counters (id, name, value, line_id) values (?,?,?,?);";

    public static void doGood(List<LineModel> lines) {

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/formongo", "root", "1111");
            lines_st = con.prepareStatement(INSERT_LINES_SQL);
            counters_st = con.prepareStatement(INSERT_COUNTERS_SQL);
            int i = 0;
            for (LineModel line : lines) {
                lines_st.setString(1, line.getId());
                lines_st.setString(2, line.getNumber());
                lines_st.setString(3, line.getName());
                lines_st.setString(4, line.getAddress());
                lines_st.setString(5, line.getPeriod());
                lines_st.setString(6, line.getSum());
                lines_st.addBatch();

                for (Counter item : line.getCounters()) {
                    counters_st.setString(1, item.getId());
                    counters_st.setString(2, item.getCounterName());
                    counters_st.setString(3, item.getCounterValue());
                    counters_st.setString(4, line.getId());
                    counters_st.addBatch();
                }
                i++;
                if (i % 250 == 0) {
                    lines_st.executeBatch();
                    counters_st.executeBatch();
                }
            }
            lines_st.executeBatch();
            counters_st.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lines_st.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                counters_st.close();
            } catch (SQLException se) {/*can't do anything */}
            try {
                con.close();
            } catch (SQLException se) {/*can't do anything */}
        }
    }
}
