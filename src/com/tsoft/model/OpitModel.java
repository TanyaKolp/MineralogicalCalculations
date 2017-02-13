package com.tsoft.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by tania on 6/17/16.
 */
public class OpitModel {
    public int numerOpita;
    public String numerOpitaFull;
    public ArrayList<SpectrumModel> spects = new ArrayList<SpectrumModel>();

    public ArrayList<String> lines;

    public OpitModel() {
        lines = new ArrayList<String>();
    }

    public void init() {
        loadNumbOpit();
        loadSpectrumModals();
    }

    public void loadNumbOpit() {
        String l = lines.get(0);
        StringTokenizer st = new StringTokenizer(l);
        while (st.hasMoreTokens()) {
            try {
                numerOpita = new Integer(st.nextToken());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Неверный формат строки!");
            }
        }
    }

    public void loadSpectrumModals() {
        boolean flagStart =false;
        SpectrumModel spectrumModel = null;
        for (String line : lines){
            if (isFinish(line) && flagStart){
                flagStart =false;
                spects.add(spectrumModel);
                spectrumModel.init();
            }
            if (flagStart){
                spectrumModel.lines.add(line);
            }
            if(isStartLines(line) && !flagStart){
                spectrumModel = new SpectrumModel();
                spectrumModel.lines.add(line);
                flagStart =true;
            }


        }
    }

    private boolean isFinish(String line) {
        String find1 = line.substring(0, 5);
        if (find1.equalsIgnoreCase("Total")) {
            return true;
        }
        return false;
    }

    private boolean isStartLines(String line) {
        if (line.length() > 8 ) {
            String find1 = line.substring(0, 9);
            if (find1.equalsIgnoreCase("Spectrum:")) { //поиск стр Spectrum

                System.out.println(line);
                return true;
            }
        }
        return false;
    }
}
