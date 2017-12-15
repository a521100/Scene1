package xiaochengxu;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import org.apache.commons.io.FileUtils;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

/**
 * Created by youxinyang on 2017/7/10.
 */

public class newhttp {

    public static void main(String[] args) {
        FileOutputStream fop = null;
        StringBuffer sb=new StringBuffer();
        sb.append(" \r\n");
        String saa = ",";
        String sbb = "Content-Length: ";
        String scc = "Content-Type: application/x-www-form-urlencoded\n" +
                "Host: 42.99.18.34:8080\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Mozilla/5.0 (Linux; U; Android 6.0.1; zh-cn; SM-G9009W Build/MMB29M) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1\n" +
                "Accept-Encoding: gzip";
        String t1 = "userId=zzy122827_112&provinceId=112&param=%3C%3Fxml+version%3D%221.0%22+encoding%3D%22utf-8%22%3F%3E%0A%3Cparams%3E%0A++%3CAttachments%3E%0A++++%3Cattachmentsmodel%3E%0A++++++%3Cattachfileid%3E%3C%2Fattachfileid%3E%0A++++++%3Cfileext%3E%3C%2Ffileext%3E%0A++++++%3Cfilename%3E%3C%2Ffilename%3E%0A++++++%3Cfilepath%3E%3C%2Ffilepath%3E%0A++++++%3Cfileremark%2F%3E%0A++++++%3Cuploadtime%3E%3C%2Fuploadtime%3E%0A%3Cuploaduserid%3E%3C%2Fuploaduserid%3E%0A++++++%3Cuploadusername%3E%3C%2Fuploadusername%3E%0A++++%3C%2Fattachmentsmodel%3E%0A++%3C%2FAttachments%3E%0A%3Cgrablimit%3E3%3C%2Fgrablimit%3E%3Cgrabprovincelimit%3E1%3C%2Fgrabprovincelimit%3E%3Cprocessingtimelimit%3E15%3C%2Fprocessingtimelimit%3E%3Crequiredtime%3E";
        String t2 = "%3C%2Frequiredtime%3E%3CsendUserId%3Ezzy122827_112%3C%2FsendUserId%3E%3Cmaxgrabnum%3E10%3C%2Fmaxgrabnum%3E%3Cprovincescope%3E112%3C%2Fprovincescope%3E%3Csendtime%3E";
        String t3 = "%3C%2Fsendtime%3E%3Cwfordercause%3E";
        String t4 = "%3C%2Fwfordercause%3E%3Cwfsendsourcetype%3E3%3C%2Fwfsendsourcetype%3E%3CbusinessType%3E2%3C%2FbusinessType%3E%3Cwfsn%3E%3C%2Fwfsn%3E%3Cwftitle%3E";
        String t5 = "%3C%2Fwftitle%3E%0A%3C%2Fparams%3E%0A&fileNameList=&orderType=3";

        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream("C:\\Apps\\aa.csv"), "GB2312");
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            String len = null;


            File file = new File("C:\\Apps\\aa4.csv");
            fop = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉

            StringBuffer sbf = new StringBuffer();
            int i=1;
            while((line=reader.readLine())!=null){
                String[] item = line.split(",");
                String first = item[0];  // 题目
                String second = item[1]; // 问题内容
                String firstHexString= URLEncoder.encode(first, "utf-8");
                String secondHexString= URLEncoder.encode(second, "utf-8");
                System.out.println(firstHexString);
                System.out.println(secondHexString);

                    Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
                    int year = c.get(Calendar.YEAR);
                    int mon = c.get(Calendar.MONTH);
                    int date = c.get(Calendar.DATE);
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);
                    int miao = c.get(Calendar.SECOND);
                    int date2 = date + 10;
                    int month = mon + 1;
                    System.out.println(year + "/" + month + "/" + date + " " + hour + ":" + minute + ":" + miao);
                    String time1 = year + "-" + month + "-" + date + "+" + date2 + "%3A" + minute + "%3A" + miao ;
                    String time2 = year + "-" + month + "-" + date + "+" + hour + "%3A" + minute + "%3A" + miao;

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    writer.write("userId=zzy122827_112&provinceId=112&param=%3C%3Fxml+version%3D%221.0%22+encoding%3D%22utf-8%22%3F%3E%0A%3Cparams%3E%0A++%3CAttachments%3E%0A++++%3Cattachmentsmodel%3E%0A++++++%3Cattachfileid%3E%3C%2Fattachfileid%3E%0A++++++%3Cfileext%3E%3C%2Ffileext%3E%0A++++++%3Cfilename%3E%3C%2Ffilename%3E%0A++++++%3Cfilepath%3E%3C%2Ffilepath%3E%0A++++++%3Cfileremark%2F%3E%0A++++++%3Cuploadtime%3E%3C%2Fuploadtime%3E%0A%3Cuploaduserid%3E%3C%2Fuploaduserid%3E%0A++++++%3Cuploadusername%3E%3C%2Fuploadusername%3E%0A++++%3C%2Fattachmentsmodel%3E%0A++%3C%2FAttachments%3E%0A%3Cgrablimit%3E3%3C%2Fgrablimit%3E%3Cgrabprovincelimit%3E1%3C%2Fgrabprovincelimit%3E%3Cprocessingtimelimit%3E15%3C%2Fprocessingtimelimit%3E%3Crequiredtime%3E");
                    writer.write(year + "-" + month + "-" + date + "+" + date2 + "%3A" + minute + "%3A" + miao);//时间加15天
                    writer.write("%3C%2Frequiredtime%3E%3CsendUserId%3Ezzy122827_112%3C%2FsendUserId%3E%3Cmaxgrabnum%3E10%3C%2Fmaxgrabnum%3E%3Cprovincescope%3E112%3C%2Fprovincescope%3E%3Csendtime%3E");
                    writer.write(year + "-" + month + "-" + date + "+" + hour + "%3A" + minute + "%3A" + miao);//发单时间
                    writer.write("%3C%2Fsendtime%3E%3Cwfordercause%3E");
                    writer.write(secondHexString);//发单问题内容
                    writer.write("%3C%2Fwfordercause%3E%3Cwfsendsourcetype%3E3%3C%2Fwfsendsourcetype%3E%3CbusinessType%3E2%3C%2FbusinessType%3E%3Cwfsn%3E%3C%2Fwfsn%3E%3Cwftitle%3E");
                    writer.write(firstHexString);//发单题目
                    writer.write("%3C%2Fwftitle%3E%0A%3C%2Fparams%3E%0A&fileNameList=&orderType=3");
                    writer.flush();

                    byte[] ct = outputStream.toByteArray();
                    int length = ct.length;
                    len = String.valueOf(length);
                    System.out.println(length);



                fop.write(sbb.getBytes("GB2312"));
                fop.write(len.getBytes("GB2312"));
                fop.write(scc.getBytes("GB2312"));
                fop.write(saa.getBytes("GB2312"));

                fop.write(t1.getBytes("GB2312"));
                fop.write(time1.getBytes("GB2312"));
                fop.write(t2.getBytes("GB2312"));
                fop.write(time2.getBytes("GB2312"));
                fop.write(t3.getBytes("GB2312"));
                fop.write(saa.getBytes("GB2312"));
                fop.write(secondHexString.getBytes("GB2312"));
                fop.write(saa.getBytes("GB2312"));
                fop.write(firstHexString.getBytes("GB2312"));
                fop.write(saa.getBytes("GB2312"));


//                fop.write(last3.toString().getBytes());
                fop.write(saa.getBytes("GB2312"));
//                fop.write(last4.toString().getBytes());
                fop.write(sb.toString().getBytes());
                i++;
                System.out.println(i);
                //bw.write(line.getBytes());

            }
            System.out.println("ok");

        } catch (Exception e) {
            ;
        }
    }

}
