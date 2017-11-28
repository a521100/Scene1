import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by wxnc on 2017/7/27.
 */
public class BaiduMap {
    public static BaiduMap getInstance()
    {
        BaiduMap bm=new BaiduMap();
        return bm;
    }
    public String getStreet(String lat,String lng)
    {
        String street=null;
        String location=lat+","+lng;
        String url = "http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location="+location+"&output=json&pois=1&ak=X70hO8ed25gxzSNA6jeH3gnc";
        String json=loadJSON(url);
        JSONObject obj=JSONObject.fromObject(json);
        if(obj.get("status").toString().equals("0"))
        {
            street=obj.getJSONObject("result").getJSONObject("addressComponent").get("street").toString();
            System.out.println(obj.getJSONObject("result").getJSONObject("addressComponent").get("street").toString());
        }else
        {
            System.out.println("未找到地址");
        }
        return  street;
    }

    public static Map<String,Double> getLngAndLat(String address){
        Map<String,Double> map=new HashMap<String, Double>();
        String url = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=F454f8a5efe5e577997931cc01de3974";
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

    public String getCity(String lat,String lng)
    {
        String city=null;
        String location=lat+","+lng;
        String url = "http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location="+location+"&output=json&pois=1&ak=X70hO8ed25gxzSNA6jeH3gnc";//通过百度url获取json数据
        String json=loadJSON(url); //获取到json数据后进行解析
        JSONObject obj=JSONObject.fromObject(json);//将解析后的数据转换成json
        if(obj.get("status").toString().equals("0"))
        {
            city=obj.getJSONObject("result").getJSONObject("addressComponent").get("city").toString();
//            System.out.println(obj.getJSONObject("result").getJSONObject("addressComponent").get("street").toString());
        }else
        {
            System.out.println("未找到地址");
        }
        return  city;
    }

    /* 通过访问第三方URL返回json数据 */

    /*通过URL获取json数据，调用方式
    * String url ="  ";
      通过url获取json数据
      String json = loadJson(url);
      获取到json数据后再进行解析
    * */

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
        String s=json.substring(29,json.length()-1);

        return s;
    }
}






