/**
 * 下载文件实例
 */

import java.util.List;


public class xls {
    public static void main(String[] args) {
       List<Dao> listcity = Dao.getcity();
       for(int i=0; i<listcity.size(); i++) {
           String city = listcity.get(i).getCitys().toString();
           try {
               txt.downloadtxt(city);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
    }
}
