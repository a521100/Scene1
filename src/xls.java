import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dbmanager.DbManager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import static java.lang.System.out;

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
