
import java.io.*;
import java.sql.*;

public class test {
    private static int count = 0;
    private static String name;//書籍名
    private static String path;//書籍のフォルダ名
    private static String rootPath = "C:\\Users\\C0116289\\Documents\\library_test\\";//書籍のフォルダを入れるフォルダ
    private static String fullPath = "";//書籍のフォルダのパス
    private static String passfile="./pass.txt";
    private static String url;
    private static String user;
    private static String pass;
    private static String db;


    public static void main(String[] args) {

        connectSQL();

    }

    //ファイル数を取得
    //フォルダ内のファイル数を数える
    private static void fileCount(File[] list) {
        for (File f : list) {
            if (f.isDirectory()) {
                fileCount(f.listFiles());
            } else if (f.isFile()) {
                count++;
            }
        }
        //ファイル数を出力
        System.out.println(count);
    }


    //ファイルを開く
    private static void fileOpen(String fullPath) {
        File dir = new File(fullPath);
        fileCount(dir.listFiles());

        //fileCountで数えた分だけ繰り返す
        for (int i = 1; i <= count; i++) {
            try {
                String page = String.format("%04d", i);
                //String page="0010";
                //txtファイルの読み込み
                File file = new File(fullPath + "\\" + "\\" + page + ".txt");

                //BufferedReader br = new BufferedReader(new FileReader(file));

                //文字コードをShift-JISで読み込み
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Shift-JIS"));

                //txtファイルの中身
                String line;
                while ((line = br.readLine()) != null) {

                    //txtファイルの中身を出力
                    System.out.println(line);

                }

                br.close();

            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    private static void connectSQL() {
        Connection con = null;
        try {
            BufferedReader pf=new BufferedReader(new FileReader(new File(passfile)));
            String line="";
            while ((line=pf.readLine())!=null){
                String [] ps=line.split(",");
                url=ps[0];
                user=ps[1];
                pass=ps[2];
                db=ps[3];
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //SQL接続
            con = DriverManager.getConnection(url, user, pass);

            String sql = "SELECT * FROM library WHERE name > ?";

            //PreparedStatementの作成
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,"SAO");
            ResultSet rs=ps.executeQuery();

            while (rs.next()) {
                name = rs.getString("name");
                path = rs.getString("path");

                fullPath = rootPath + path;

                //書籍名と書籍のフォルダ名を出力
                System.out.println(name + " : " + path);

                //fullpathを与えてtxtファイルを開きに行く
                fileOpen(fullPath);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {

                con.close();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
