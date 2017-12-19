package xiaochengxu; /**
 * txt,csv格式读写处理
 * 按行读，分裂，替代某个元素，
 */

import java.io.*;

public class train {

    public static void main(String[] args) {
        FileOutputStream fop = null;
        StringBuffer sb=new StringBuffer();
        sb.append(" \r\n");
        String saa = ",";
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream("C:\\Apps\\read.csv"), "GB2312");
            BufferedReader reader = new BufferedReader(isr);
//            BufferedReader reader = new BufferedReader(new FileReader());//换成你的文件名

           // BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            File file = new File("C:\\Apps\\new5.csv");
            fop = new FileOutputStream(file);
          //  StringBuffer sbf = new StringBuffer();
            int i=1;
            while((line=reader.readLine())!=null){
                String[] item = line.split(",");
//                System.out.println(line);//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String last2 = item[item.length-2];
                String last1 = item[item.length-1];
                last1=last1.replaceAll("\"","");
                last2=last2.replaceAll("\"","");
//                if(last1.ind?exOf("\"")!=-1){
//                    last2 = last2.substring(1,last2.length()-1);
//                    last1 = last1.substring(1,last1.length()-1);
//                }
                //Double last22 = Double.valueOf(last2);
                //Double last11 = Double.valueOf(last1);
                Double last22;
                Double last11;
                try{
                    last22 = Double.parseDouble(last2.trim());
                    last11 = Double.parseDouble(last1.trim());
                }
                catch (Exception e)
                {
                    continue;
                }

//                Double last22 = Double.parseDouble(last2.trim());
//                Double last11 = Double.parseDouble(last1.trim());
                //String last2 = item[13];//这就是你要的数据了
               // String last = item[item.length-1];//这就是你要的数据了
                //int value = Integer.parseInt(last);//如果是数值，可以转化为数值
                //System.out.println(last2);
                //System.out.println(last);
                Double last3 = last22/3600.0;
                Double last4 = last11/3600.0;
                /*System.out.println(last2);
                System.out.println(last1);
                System.out.println(last3);
                System.out.println(last4);*/
                //sbf.append(line).append(last3);
                fop.write(line.getBytes("GB2312"));
                fop.write(saa.getBytes("GB2312"));
                fop.write(last3.toString().getBytes());
                fop.write(saa.getBytes("GB2312"));
                fop.write(last4.toString().getBytes());
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
