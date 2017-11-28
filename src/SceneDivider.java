import dbmanager.DbManager;
import jxl.Workbook;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jxl.*;
import  jxl.Workbook;
import  jxl.write.Label;
import  jxl.write.WritableSheet;
import  jxl.write.WritableWorkbook;
/**
 * Created by wxnc on 2017/9/1.
 */
public class SceneDivider {
    public static void main(String [] args) {
        //获取边界信息
        // List<Boundary> lb=Boundary.XmltoList("C:\\360Downloads\\xml.xml");
      /* for (int y = 0; y < 6; y++) {
            System.out.println(y);
            boolean isInpoly;
            List<Dao> listyuan = Dao.getYuan(y);
            List<Dao> listpipei = Dao.getPipei();
            for (int j = 0; j < listyuan.size(); j++) {
                   for (int k = 0 ;k <listpipei.size();k++){
                        String city = listyuan.get(j).getCitys().toString();
                       if (city.equals(listpipei.get(k).getCitys().toString())){
                               isInpoly = isPtInPoly(Double.parseDouble(listyuan.get(j).getLongitude()), Double.parseDouble(listyuan.get(j).getLatitude()), (listpipei.get(k)).getlPoint1());
                               if (isInpoly == true) {
                                   update(isInpoly, y, j);
                                   break;
                               }
                               }
                           }
                       }
            System.out.println("OK"+y);
        }*/
      up0();
        for (int y = 0; y < 7; y++) {
            System.out.println(y);
            boolean isInpoly;
            List<Dao> listyuan = Dao.getb(y);
            List<Dao> listpipei = Dao.geta();
            for (int j = 0; j < listyuan.size(); j++) {
                for (int k = 0 ;k <listpipei.size();k++){
                    String city = listyuan.get(j).getCitys().toString();
                    if (city.equals(listpipei.get(k).getCitys().toString())){
                        isInpoly = isPtInPoly(Double.parseDouble(listyuan.get(j).getLongitude()), Double.parseDouble(listyuan.get(j).getLatitude()), (listpipei.get(k)).getlPoint1());
                        if (isInpoly == true) {
                            up1(isInpoly, y, j);
                            break;
                        }
                    }
                }
            }
            System.out.println("OK"+y);
        }
        up2();
        System.out.println("ALLOK");

    }


    public static boolean isPtInPoly (double ALon , double ALat , Point[] ps) {
        int iSum, iCount, iIndex;
        double dLon1 = 0, dLon2 = 0, dLat1 = 0, dLat2 = 0, dLon;
        if (ps.length < 3) {
            return false;
        }
        iSum = 0;
        iCount = ps.length;
        for (iIndex = 0; iIndex<iCount;iIndex++) {
            if (iIndex == iCount - 1) {
                dLon1 = ps[iIndex].getX();
                dLat1 = ps[iIndex].getY();
                dLon2 = ps[0].getX();
                dLat2 = ps[0].getY();
            } else {
                dLon1 = ps[iIndex].getX();
                dLat1 = ps[iIndex].getY();
                dLon2 = ps[iIndex + 1].getX();
                dLat2 = ps[iIndex + 1].getY();
            }
            // A点是否在边的两端点的水平平行线之间，在则可能有交点，开始判断交点是否在左射线上
            if (((ALat >= dLat1) && (ALat < dLat2)) || ((ALat >= dLat2) && (ALat < dLat1))) {
                if (Math.abs(dLat1 - dLat2) > 0) {
                    //得到 A点向左射线与边的交点的x坐标：
                    dLon = dLon1 - ((dLon1 - dLon2) * (dLat1 - ALat) ) / (dLat1 - dLat2);
                    // 如果交点在A点左侧（说明是做射线与 边的交点），则射线与边的全部交点数加一：
                    if (dLon < ALon) {
                        iSum++;
                    }
                }
            }
        }
        if ((iSum % 2) != 0) {
            return true;
        }
        return false;
    }


    /*public static void insertjihe(List<Boundary> lb) {
        DbManager dbm = new DbManager();
        Connection conn = null;
        Statement st = null;
        conn = dbm.in();
        try {
            st = conn.createStatement();
            for (int i=0;i<lb.size();i++){
                String sql = "insert into pipei (city, zuobiaojihe, getlPoint) value ('"+lb.get(i).getCity()+"','"+lb.get(i).getsCoordinates()+"','"+lb.get(i).getlPoint()+"')";
                st.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            dbm.closeSt(st, conn);
        }
    }*/

   /* public static void insert(List a,int y) {
        DbManager dbm = new DbManager();
        Connection conn = null;
        Statement st = null;
        conn = dbm.in();
        try {
            st = conn.createStatement();
            for (int i=0;i<a.size();i++){
                 //String sql = "UPDATE ss SET tf='"+a.get(i)+"' ORDER BY id ASC LIMIT 50000";
                //String sql = "UPDATE ss SET tf='"+a.get(i)+"' WHERE (id>50000 AND id<100001)";
                //String sql = "UPDATE ss SET tf='"+a.get(i)+"' WHERE (id>100000 AND id<150001)";
                //String sql = "UPDATE ss SET tf='"+a.get(i)+"' WHERE (id>150000 AND id<200001)";
                //String sql = "UPDATE ss SET tf='"+a.get(i)+"' WHERE (id>200000 AND id<250001)";
                //String sql = "UPDATE ss SET tf='"+a.get(i)+"' WHERE (id>250000 AND id<300001)";
                int x=(50000*y+i+1);
                String sql = "UPDATE ss SET tf='"+a.get(i)+"' WHERE id='"+x+"'";
                st.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            dbm.closeSt(st, conn);
        }
    }*/

    public static void update(boolean a,int y,int j) {
        DbManager dbm = new DbManager();
        Connection conn = null;
        Statement st = null;
        conn = dbm.in();
        try {
            st = conn.createStatement();
                int x=20000*y+j+1;
                String sql = "UPDATE shuju SET tf='"+a+"' WHERE id='"+x+"'";
                st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            dbm.closeSt(st, conn);
        }
    }


    public static void update2() {
        DbManager dbm = new DbManager();
        Connection conn = null;
        Statement st = null;
        conn = dbm.in();
        try {
            st = conn.createStatement();
            String sql = "UPDATE shuju SET tf='false' WHERE tf IS NULL ";
            st.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            dbm.closeSt(st, conn);
        }
    }

    public static void up0() {
        DbManager dbm = new DbManager();
        Connection conn = null;
        Statement st = null;
        conn = dbm.in();
        try {
            st = conn.createStatement();
            CallableStatement c=conn.prepareCall("{CALL up0()}");
            c.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            dbm.closeSt(st, conn);
        }
    }

    public static void up1(boolean a,int y,int j) {
        DbManager dbm = new DbManager();
        Connection conn = null;
        Statement st = null;
        conn = dbm.in();
        try {
            st = conn.createStatement();
            CallableStatement c=conn.prepareCall("{CALL up1(?,?,?)}");
            c.setBoolean(1,a);
            c.setInt(2,y);
            c.setInt(3,j);
            c.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            dbm.closeSt(st, conn);
        }
    }

    public static void up2() {
        DbManager dbm = new DbManager();
        Connection conn = null;
        Statement st = null;
        conn = dbm.in();
        try {
            st = conn.createStatement();
            CallableStatement c=conn.prepareCall("{CALL up2()}");
            c.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            dbm.closeSt(st, conn);
        }
    }
}
