package xiaochengxu;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 坐标转换
 * 百度坐标系转大地坐标系
 * WGS84坐标系：即地球坐标系，国际上通用的坐标系。设备一般包含GPS芯片或者北斗芯片获取的经纬度为WGS84地理坐标系,
 * 谷歌地图采用的是WGS84地理坐标系（中国范围除外）;
 * 经度：longitude 是大的
 * 纬度：latitude 是小的
 */

public class Transform {

    static double x_PI = 3.14159265358979324 * 3000.0 / 180.0;
    static double PI = 3.1415926535897932384626;
    static double a = 6378245.0;
    static double ee = 0.00669342162296594323;
    private static double EARTH_RADIUS = 6371.393;
    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    public static void main(String [] args) {
        FileOutputStream fop = null;
        StringBuffer sb = new StringBuffer();
        sb.append(" \r\n");
        String saa = ",";
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream("C:\\Apps\\test.csv"), "utf-8");
            BufferedReader reader = new BufferedReader(isr);
            File file = new File("C:\\Apps\\final.csv");
            fop = new FileOutputStream(file);
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] item = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分

                String originallng = item[item.length - 5];
                String originallat = item[item.length - 4];
                originallng = originallng.replaceAll("\"", "");
                originallat = originallat.replaceAll("\"", "");

                Double originallng1 = Double.parseDouble(originallng.trim());
                Double originallat1 = Double.parseDouble(originallat.trim());

                String last2 = item[item.length - 3];
                String last1 = item[item.length - 2];
                last1 = last1.replaceAll("\"", "");
                last2 = last2.replaceAll("\"", "");

                Double lng = Double.parseDouble(last2.trim());
                Double lat = Double.parseDouble(last1.trim());

                Double lng1 = null;
                Double lat1 = null;
                Double lng2 = null;
                Double lat2 = null;

                System.out.println(lng);
                System.out.println(lat);

                try{

                    Map bd09togcj02 = bd09togcj02(lng,lat);
                    lng1= Double.valueOf((Double) bd09togcj02.get("lng"));
                    lat1= Double.valueOf((Double) bd09togcj02.get("lat"));
                    System.out.println(lng1);
                    System.out.println(lat1);
                    Map gcj02towgs84 = gcj02towgs84(lng1,lat1);
                    lng2= Double.valueOf((Double) gcj02towgs84.get("lng"));
                    lat2= Double.valueOf((Double) gcj02towgs84.get("lat"));
                    System.out.println(lng2);
                    System.out.println(lat2);
                }
                catch (Exception e)
                {
                    continue;
                }

                Double juli = GetDistance (originallat1,originallng1,lat2,lng2);
                System.out.println(juli);

                int one = line.lastIndexOf(",");
                line = line.substring(0,one);

                System.out.println(line);
                i++;
                System.out.println(i);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换
     * 即 百度 转 谷歌、高德
     * @param bd_lon
     * @param bd_lat
     * @returns {*[]}
     */
    public static Map<String, Double>bd09togcj02(double bd_lon, double bd_lat){
    //public map bd09togcj02(double bd_lon, double bd_lat){
        double x = bd_lon - 0.0065;
        double y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_PI);
        double gg_lng = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        Map<String,Double> map1=new HashMap<String, Double>();
        map1.put("lng", gg_lng);
        map1.put("lat", gg_lat);
       // Map map=new HashMap(gg_lng, gg_lat);
        return map1;
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
     * 即谷歌、高德 转 百度
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    public Map<String, Double>gcj02tobd09(double lng, double lat){
    //public Point gcj02tobd09(double lng, double lat){
        double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * x_PI);
        double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * x_PI);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        Map<String,Double> map2=new HashMap<String, Double>();
        map2.put("lng", bd_lng);
        map2.put("lat", bd_lat);
        // Point point=new Point(bd_lng, bd_lat);
        return map2;
    };

    /**
     * WGS84转GCj02
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    public Map<String, Double>wgs84togcj02(double lng, double lat){
    //public Point wgs84togcj02(double lng, double lat){
        double dlat = transformlat(lng - 105.0, lat - 35.0);
        double dlng = transformlng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        Map<String,Double> map3=new HashMap<String, Double>();
        map3.put("lng", mglat);
        map3.put("lat", mglng);
        //Point point=new Point(mglng, mglat);
        return map3;
    };

    /**
     * GCJ02 转换为 WGS84
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    public static Map<String, Double>gcj02towgs84(double lng, double lat){
    //public Point gcj02towgs84(double lng, double lat){
        double dlat = transformlat(lng - 105.0, lat - 35.0);
        double dlng = transformlng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        Map<String,Double> map4=new HashMap<String, Double>();
        map4.put("lat", mglat);
        map4.put("lng", mglng);
        //Point point=new Point(mglng, mglat);
        return map4;
    };


    private static double transformlat(double lng, double lat){
        double ret= -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformlng(double lng,double lat){
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
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
