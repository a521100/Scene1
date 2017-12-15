/**
 * 数据库读数据写入txt文件
 * 生成文件
 */
import dbmanager.DbManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import static java.lang.System.out;

public class Entrance {
    public static void main(String[] args) throws Exception{

        DbManager dbm = new DbManager();
        Connection conn = null;
        Statement st = null;
        conn = dbm.in();

        FileOutputStream fop = null;
        File file;
        String str;
        String content = "This is the text content";
        String Table_Name = "ShuJu";
        StringBuffer sb=new StringBuffer();
        sb.append(" \r\n");
        String ss ="无,";
        String saa = ",";

        try {

            file = new File(Table_Name+".txt");
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            for (int y = 0; y < 7; y++) {
                out.println(y);
                st = conn.createStatement();
                int ar=20000*y+1;
                int br=20000*(y+1);
                String sql = "select * from " + Table_Name + " WHERE (id>='"+ar+"'AND  id<='"+br+"') ";
                ResultSet rs = st.executeQuery(sql);
                ResultSetMetaData rsmd = rs.getMetaData();
                int colnum = rsmd.getColumnCount();
                if(y==0){
                    for (int i = 1; i <= colnum; i++) {
                        String name = rsmd.getColumnName(i);
                        fop.write(name.toString().getBytes("UTF-8"));
                        fop.write(saa.getBytes("UTF-8"));
                    }
                    fop.write(sb.toString().getBytes());
                }
                while (rs.next()) {
                    for (int i = 1; i <= colnum; i++) {
                        str = rs.getString(i);
                        if (rs.getString(i)==null){
                            fop.write(ss.getBytes("UTF-8"));
                            continue;
                        }
                        fop.write(str.toString().getBytes("UTF-8"));
                        fop.write(saa.getBytes("UTF-8"));
                    }
                    fop.write(sb.toString().getBytes());
                }
                System.out.println("OK"+y);
            }
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




