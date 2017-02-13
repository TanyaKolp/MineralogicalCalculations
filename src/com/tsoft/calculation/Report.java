package com.tsoft.calculation;


import com.tsoft.model.TableMendeleeva;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.*;

/**
 * Created by tania on 6/27/16.
 */
public class Report {
    public static Report instance = null;

    private ArrayList<String> oderElement = new ArrayList<String>();
    private ArrayList<String> neededElementSequence;
    private String fileName = "test.xls";
    private Workbook workbook;

    public Report() {
        filAnOderElement();
    }

    public static Report getInstans() {
        if (instance == null) {
            instance = new Report();
            instance.filAnOderElement();
        }
        return instance;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private void filAnOderElement() {
        oderElement.add(0, "Si");
        oderElement.add(1, "Ti");
        oderElement.add(2, "Al");
        oderElement.add(3, "Ca");
        oderElement.add(4, "Mg");
        oderElement.add(5, "Na");
        oderElement.add(6, "K ");
        oderElement.add(7, "Nb");
        oderElement.add(8, "La");
        oderElement.add(9, "Ce");
        oderElement.add(10, "Sr");
        oderElement.add(11, "Cs");
        oderElement.add(12, "B ");

    }

    public void makeTXTformulaOneSp(Calculation calculation) {
        File f = new File("report.txt");
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
            writer.println("Опыт№  " + calculation.nomerOpita);
            writer.println("Spectrum# " + calculation.nomerSpectra);
            for (int i = 0; i < oderElement.size(); i++) {
                String key = oderElement.get(i);
                if (calculation.mapSpFormula.containsKey(key)) {
                    writer.println(key + "  " + calculation.mapSpFormula.get(key));
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeTXTformulaOneOp(ArrayList<Calculation> aCalc) {
        File f = new File("report.txt");
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
            for (int i = 0; i < aCalc.size(); i++) {
                writer.println("Опыт№  " + aCalc.get(i).nomerOpita);
                writer.println("Spectrum# " + aCalc.get(i).nomerSpectra);
                for (int j = 0; j < oderElement.size(); j++) {
                    String key = oderElement.get(j);
                    if (aCalc.get(i).mapSpFormula.containsKey(key)) {
                        writer.println(key + "  " + aCalc.get(i).mapSpFormula.get(key));
                    }
                    writer.flush();
                    writer.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeTXTaverage(Map<String, Double> mapAverageCF, int nomerOpita, String faza) {
        File f = new File("report.txt");
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
            writer.println("Опыт№  " + nomerOpita);
            writer.println("Фаза: " + faza);
            for (int i = 0; i < oderElement.size(); i++) {
                String key = oderElement.get(i);
                if (mapAverageCF.containsKey(key)) {
                    writer.println(key + "  " + mapAverageCF.get(key));
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeExcelAverage(Map<String, ArrayList<Integer>> fazy, int nomerOpita) throws IOException {
        Average average = new Average();
//        try {
        //getWorkbookFromFile("Среднее");
        Sheet sheet = workbook.getSheet("Среднее");
        int n = sheet.getPhysicalNumberOfRows();// колво определен строк
        boolean noShapka = true;
        for (String faza : fazy.keySet()) {
            Calculation result = average.evaluateOxForFaza(fazy.get(faza), nomerOpita);
            if (noShapka) {
                createShapka(result, sheet, n);
                noShapka = false;
            }
            Row valuesForAv = sheet.createRow(n + 1);
            Cell fazaName = valuesForAv.createCell(0);
            fazaName.setCellValue(faza);
            createValuesForAverage(result, valuesForAv);
            n++;
        }
        // Записываем всё в файл
        wtiteToFile(fileName, workbook);
        System.out.print(": average");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//        }
    }

    private void wtiteToFile(String path, Workbook workbook) {
        try {
            FileOutputStream out = new FileOutputStream(path);
            workbook.write(out);
            out.close();
            System.out.println("\nFILE rewrite.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createValuesForAverage(Calculation result, Row valuesForAv) {
        for (int i = 0; i < neededElementSequence.size(); i++) {
            String el = neededElementSequence.get(i);
            Cell value = valuesForAv.createCell(i + 1);
            value.setCellValue(result.mapSpFormula.get(el));
        }
    }

    private String getWorkbookFromFile(String sheetName) throws IOException {
        // вставить параметр String - путь к файлу от пользователя
        String fpath = "report.xls";
        File file = new File(fpath);
        boolean needWrite = false;
        if (!file.exists()) {
            fpath = "report.xls";
            needWrite = true;
        }
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            workbook.createSheet(sheetName);
            needWrite = true;
        }
        if (needWrite) {
            wtiteToFile(fpath, workbook);
        }
        return fpath;
    }

    private void makeExcelformulaOneSp(Calculation calculation, boolean noShapka, Sheet sheet) throws IOException {
        int n = sheet.getPhysicalNumberOfRows();// колво определен строк
        System.out.println("\n LINES = " + n);
//        if (noShapka) { //есть ли шапка с №опыта и элементами, true - если нет.
//            Row shapka = createShapka(calculation, sheet, n);
        Row valuesForSp = createValuesForSp(calculation, sheet, n);
//            writeSumm(calculation, shapka, valuesForSp);
//        } else {
//        Row valuesForSp = createValuesForSp(calculation, sheet, n);
//            writeSumm(calculation, valuesForSp);
//        }
    }

//    private void writeSumm(Calculation calculation, Row shapka, Row valuesForSp) {
//        for (String key : calculation.mapSpFormula.keySet()) {
//            if (key.startsWith("Sum")) {
//                Cell summ = shapka.createCell(shapka.getLastCellNum());
//                summ.setCellValue(key);
//                Cell valueSC = valuesForSp.createCell(valuesForSp.getLastCellNum());
//                valueSC.setCellValue(calculation.mapSpFormula.get(key));
//                System.out.println("mapSpFormula" + calculation.mapSpFormula);
//                break;
//            }
//        }
//    }

//    private void writeSumm(Calculation calculation, Row valuesForSp) {
//        for (String key : calculation.mapSpFormula.keySet()) {
//            if (key.startsWith("Sum")) {
//                Cell valueSC = valuesForSp.createCell(valuesForSp.getLastCellNum());
//                valueSC.setCellValue(calculation.mapSpFormula.get(key));
//                System.out.println("mapSpFormula" + calculation.mapSpFormula);
//                break;
//            }
//        }
//    }

    private Row createValuesForSp(Calculation calculation, Sheet sheet, int n) {
        Row valuesForSp = sheet.createRow(n);
        Cell nomerSp = valuesForSp.createCell(0);
        nomerSp.setCellValue("Sp: " + calculation.nomerSpectra);
        for (int i = 0; i < neededElementSequence.size(); i++) {
            String el = neededElementSequence.get(i);
            Cell value = valuesForSp.createCell(i + 1);
            value.setCellValue(calculation.mapSpFormula.get(el));
        }

        return valuesForSp;
    }

    private Row createShapka(Calculation calculation, Sheet sheet, int n) {
        String sumName = null;
        Row shapka = sheet.createRow(n);
        Cell nomerOp = shapka.createCell(0);
        nomerOp.setCellValue("Опыт№ " + calculation.nomerOpita);
        neededElementSequence = new ArrayList<String>();
        for (String key : calculation.mapSpFormula.keySet()) {
            if (key.startsWith("Sum")) {
                sumName = key;
                continue;
            }
            neededElementSequence.add(key);
        }
        Collections.sort(neededElementSequence, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int indexO1 = oderElement.indexOf(o1);
                int indexO2 = oderElement.indexOf(o2);
                if (indexO1 == indexO2) {
                    return 0;
                } else if (indexO1 == -1) {
                    return 1;
                } else if (indexO2 == -1) {
                    return -1;
                } else if (indexO1 > indexO2) {
                    return 1;
                }
                return -1;
            }
        });

        neededElementSequence.add(sumName);
        int i = 1;
        for (String element : neededElementSequence) {
            Cell el = shapka.createCell(i);
            el.setCellValue(element);
            i++;
        }
        return shapka;
    }

    public void makeExcelformulaOneOp(int nomerOpita, int nCation) {
        boolean noAcEl = true;
        CalculationByCation calcCat = new CalculationByCation();
        ArrayList<Calculation> formulaForOpit = calcCat.getFormulaForOpit(nomerOpita, nCation);
        try {
            Sheet sheet = workbook.getSheet("Формула");
            int n = sheet.getPhysicalNumberOfRows();
            Row shapka = createShapka(formulaForOpit.get(0), sheet, n);
            for (int i = 0; i < formulaForOpit.size(); i++) {
                makeExcelformulaOneSp(formulaForOpit.get(i), noAcEl, sheet);
                noAcEl = false;
            }
            wtiteToFile(fileName, workbook);
            System.out.print(": formula");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Workbook createExcelFile() {
        workbook = new HSSFWorkbook();
        workbook.createSheet("Среднее");
        workbook.createSheet("Формула");
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    private void XLSmakeSheet() throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Формула");
        book.write(new FileOutputStream("report.xls"));
        book.close();
    }
}
