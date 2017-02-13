package com.tsoft.app;

import com.tsoft.calculation.*;
import com.tsoft.model.ContainerOpitsModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tania on 6/23/16.
 */
public class MainT7 {


    public static void main(String[] args) {
//        init();
        try {
            String path = "Test2.xls";
            Workbook workbook = new HSSFWorkbook();
            ArrayList<String> strings = new ArrayList<String>(Arrays.asList("1", "4", "5"));
            Sheet sheet = workbook.createSheet("ter");

            for (int i = 0; i < strings.size(); i++) {
                int n = sheet.getPhysicalNumberOfRows();
                String string = strings.get(i);
                Row row = sheet.createRow(n);
                Cell cell = row.createCell(i);
                cell.setCellValue(string);

                FileOutputStream fileOut = new FileOutputStream(path);
                workbook.write(fileOut);
                fileOut.close();
                System.out.println("File is written successfully");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void init() {
        try {
            String path = "/home/tania/Documents/Радиоэк/opit/y5-06-22/22-06-2015.dat";
            ContainerOpitsModel.getInstance().loadOpitsFromFile(path);
            Report r = Report.getInstans();
            int numOp = 6975;
            int numSp = 6;
            int nCation = 3;
            int nAnion = 6;
            Map<String, ArrayList<Integer>> fazy = new HashMap<String, ArrayList<Integer>>();
            ArrayList<Integer> loparity = new ArrayList<Integer>(Arrays.asList(9, 10));
            fazy.put("Lop", loparity);
            ArrayList<Integer> l1 = new ArrayList<Integer>(Arrays.asList(9, 10));
            fazy.put("L1", l1);
            ArrayList<Integer> l2 = new ArrayList<Integer>(Arrays.asList(9, 10));
            fazy.put("L2", l2);

            CalculationByCation calculationByCation = new CalculationByCation();
            CalculationByAnion calculationByAnion = new CalculationByAnion();
            AddingBor add = new AddingBor();
            //  add.addBorForSpects(contener,numOp,numbSp);
            //  average.evaluateOxForFaza(average.getMapsForFaza(contener, tm, numbSp, numOp));
            //r.makeExcelformulaOneSp(calculationByAnion.getSpectrumFormul(contener, tm, numOp, numSp, nAnion), true);
            r.makeExcelformulaOneOp(numOp, nCation);
            //CalculationByCation calculationByCation=new CalculationByCation();
//            r.makeTXTformulaOneSp(calculationByCation.getFormulaForSpector(contener, tm, numOp, numSp, nCation));
//             r.makeTXTformulaOneOp(calculationByCation.getFormulaForOpit(contener,tm,numOp,nCation));
            // r.makeTXTaverage(average.evaluateOxForFaza(average.getMapsForFaza(contener,tm,numbSp,numOp)), numOp,faza);
            r.makeExcelAverage(fazy, numOp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
