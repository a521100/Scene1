package xiaochengxu;

/**
 * 调用百度地图API
 * 根据地址获取对应经纬度
 * 比较经纬度距离
 * 直接读取csv，写入csv，边读边写
 */

import net.sf.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class address {

    private static double EARTH_RADIUS = 6371.393;
    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    public static void main(String [] args) {
        FileOutputStream fop = null;
        StringBuffer sb=new StringBuffer();
        sb.append(" \r\n");
        String saa = ",";
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream("C:\\Apps\\new4.csv"), "GB2312");
            BufferedReader reader = new BufferedReader(isr);
            File file = new File("C:\\Apps\\final.csv");
            fop = new FileOutputStream(file);
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            int i=0;
            while((line=reader.readLine())!=null){
                String[] item = line.split(",");
//                System.out.println(line);//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String last2 = item[item.length-2];
                String last1 = item[item.length-1];
                last1=last1.replaceAll("\"","");
                last2=last2.replaceAll("\"","");
                String first1 = item[1];
                String first2 = item[2];
                String first3 = item[3];
                String first4 = item[4];
                String first5 = item[5];
                String add = first1.concat(first2).concat(first3).concat(first4).concat(first5);
//                System.out.println(add);

                Double lng1 = null;
                Double lat1 = null;
                Double lng2 = null;
                Double lat2 = null;

                try{

                    Map a = getLngAndLat(add);
                    lng1= Double.valueOf((Double) a.get("lng"));
                    lat1= Double.valueOf((Double) a.get("lat"));

                    lng2 = Double.parseDouble(last2.trim());
                    lat2 = Double.parseDouble(last1.trim());  // trim 去掉字符串左右两边的空格

//                    System.out.println(lng1);
//                    System.out.println(lat1);
                }
                catch (Exception e)
                {
                    continue;
                }

                Double juli = GetDistance (lat1,lng1,lat2,lng2);
                System.out.println(juli);

                fop.write(line.getBytes());
                fop.write(saa.getBytes());
                fop.write(lng2.toString().getBytes());
                fop.write(saa.getBytes());
                fop.write(lat2.toString().getBytes());
                fop.write(saa.getBytes());
                fop.write(juli.toString().getBytes());
                fop.write(sb.toString().getBytes());
                i++;
                System.out.println(i);
            }
            System.out.println("ok");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public static Map<String,Double> getLngAndLat(String addresss){
        Map<String,Double> map=new HashMap<String, Double>();
        String url = "http://api.map.baidu.com/geocoder/v2/?address="+addresss+"&output=json&ak=F454f8a5efe5e577997931cc01de3974";
        String json = loadJSON(url);
        JSONObject obj = JSONObject.fromObject(json);
        if(obj.get("status").toString().equals("0")){
            double lng=obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
            double lat=obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
            map.put("lng", lng);
            map.put("lat", lat);

            //System.out.println("经度："+lng+"---纬度："+lat);
        }else{
            //System.out.println("未找到相匹配的经纬度！");
        }
        return map;
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        String s=json.substring(0,json.length());

        return s;
    }

    public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;
    }

}
