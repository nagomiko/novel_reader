import java.sql.*;

public class novel_in_sqlite {
    public static void main(String[] args) {
        Connection connection=null;
        Statement statement=null;
        int flag=0;
        int firstFlag=1;

        try{
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/C0116289/Documents/デスクトップ/com.sampleb3.novel_webnovel.db");
            statement = connection.createStatement();
            String sql = "select * from episode WHERE fetch_target_id=33 AND no=2";

            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String bun=rs.getString(6);
                String [] st=bun.split("\n");
                String st2 = null;
                for (int a=0;a<st.length;a++){

                    if (st[a].equals("<div id=\"novel_honbun\" class=\"novel_view\">2.<br />")){
                        flag=1;
//                        st2+=st[a]+"\n";
                    }else if(st[a].equals("</div>")){
                        flag=0;
                    }

                    if(flag==1){
                        if(st[a].equals("<br />")){
                        }else if(!(st[a].equals("<br />"))&&firstFlag!=0){
                            st2="";
                            firstFlag=0;
                        }else {
                            st2+=st[a].replaceAll("<br />","")+"\n";

                        }
                    }
                }

                System.out.println(st2);
//                System.out.println(rs.getString(6));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
