import dbmanager.DbManager;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Created by wxnc on 2017/9/1.
 */
public class Boundary {
    public static List<Boundary> XmltoList(String filePath) {

        List<Boundary> lb = new ArrayList<>();
        SAXReader saxReader = new SAXReader();
        Document doc=null;
        String xpath="//coordinates";
        try
        {
            doc=saxReader.read(filePath);//读取xml，dom用于获取、更改、添加或删除XML元素的标准
            List<Element> le=doc.selectNodes(xpath);//获取xml文档中标签<coordinates>的内容
            int i=1;
            for(Element e:le)//循环list le
            {
                System.out.println("\n************开始第"+i+"个区域导入***********************");
                Boundary bd=new Boundary();
                bd.sCoordinates=e.getText().replaceAll("\n","\t").replaceAll("\t","");
                String s[]=bd.sCoordinates.split(" "); //第一次切割，通过空格切
                String ss[]=s[0].split(",");//第二次切割，通过“，”切
                System.out.print(ss[0]);
                System.out.print(ss[1]);
                BaiduMap bm=new BaiduMap();//调用百度地图的方法
                String city=bm.getCity(ss[1],ss[0]); //将纬度，经度传给百度api，获得返回的城市（百度地图api 是纬度，经度）
                //String city="";
                System.out.println("该区域归属地市为"+city+",共有"+s.length+"条边界");
                //System.out.print(city);
                bd.setCity(city);
                lb.add(bd);
                System.out.println("该区域导入完毕\n");
                i++;
            }

        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return lb;

        }

//        if (em.elementIterator().hasNext()) {
//            System.out.print("3");
//            Element e = (Element) em.elementIterator().next();
//            List<Attribute> eattr = e.attributes();
//            for (Attribute attr : eattr) {
//                if (attr.getName().equals("coordinates")) {
//                    String s[] = attr.getValue().split(" ");
//                    for (String c : s) {
//                        String subc[] = c.split(",");
//                        Boundary bd = new Boundary();
//                        bd.setLat(subc[0]);
//                        bd.setLon(subc[1]);
//                        lb.add(bd);
//
//                    }
//
//
//                }
//            }
//
//
//        }


    public Point[] getlPoint() {
        String []s= this.sCoordinates.split(" ");
        Point []ps=new Point[s.length];
        int i=0;
        for(String c:s)
        {
            String []ss=c.split(",");
            Point p=new Point(Double.parseDouble(ss[0]),Double.parseDouble(ss[1]));
            ps[i]=p;
            i++;
        }
        return ps;
    }




    private String sCoordinates;
    private String City;

    public String getsCoordinates() {
        return sCoordinates;
    }

    public void setsCoordinates(String sCoordinates) {
        this.sCoordinates = sCoordinates;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        this.City = city;
    }


}
