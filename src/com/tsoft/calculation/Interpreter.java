package com.tsoft.calculation;

import java.io.*;
import java.util.*;

/**
 * Created by tania on 11.02.17.
 */
public class Interpreter {

    private Report report = new Report();

    public boolean executeFromFile() {
        report.createExcelFile();
        report.setFileName("Report7090-formula.xls");
        String path = "/home/tania/Documents/Радиоэк/calculation/progCalc.txt";
        ArrayList<String> inputArgs;
        File file = new File(path);
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) { // метод readline построчное чтение
                inputArgs = new ArrayList<String>(Arrays.asList(line.split(" ")));
                String command = inputArgs.get(0);
                inputArgs.remove(0);
                System.out.println("\nRUN Av: " + command.equalsIgnoreCase("average"));
                if (command.equalsIgnoreCase("average")) {
                    makeAvReport(inputArgs);
                } else {
                    makeFormulaReport(inputArgs);
                }
            }
            fis.close();
            br.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void makeFormulaReport(ArrayList<String> inputArgs) {
        Integer opit = new Integer(inputArgs.get(0));
        Integer nCat = new Integer(inputArgs.get(1));

        report.makeExcelformulaOneOp(opit, nCat);
    }

    private void makeAvReport(ArrayList<String> inputArgs) {
        Integer opit = new Integer(inputArgs.get(0));
        inputArgs.remove(0);
        Map<String, ArrayList<Integer>> fazy = new HashMap<String, ArrayList<Integer>>();
        ArrayList<Integer> nSp = null;
        String fazaName = null;
        for (String s : inputArgs) {
            if (s.contains(",")) {
                ArrayList<String> nomeraSp = new ArrayList<String>(Arrays.asList(s.split(",")));
                nSp = convertToListIntegers(nomeraSp);
            } else {
                fazaName = s;
            }
            if (fazaName != null && nSp != null) {
                fazy.put(fazaName, nSp);
                fazaName = null;
                nSp = null;
            }
        }
        try {
            report.makeExcelAverage(fazy, opit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Integer> convertToListIntegers(ArrayList<String> nomeraSp) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (String s : nomeraSp) {
            if (s.contains("-")) {
                ArrayList<String> boundaries = new ArrayList<String>(Arrays.asList(s.split("-")));
                Integer start = new Integer(boundaries.get(0));
                Integer finish = new Integer(boundaries.get(1));
                for (int i = start; i <= finish; i++) {
                    result.add(i);
                }
                continue;
            }
            Integer num = new Integer(s);
            result.add(num);
        }
        return result;
    }
}
