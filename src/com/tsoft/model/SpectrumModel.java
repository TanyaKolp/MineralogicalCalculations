package com.tsoft.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by tania on 6/17/16.
 */
public class SpectrumModel {
    public String faza;
    public int numerSpectra;
    private Map<String, AnalizValueModel> spectrumMap = new HashMap<String, AnalizValueModel>();
    public Map<String, Double> elWtMap = new HashMap<String, Double>();
    public ArrayList<String> lines = new ArrayList<String>();
    private ArrayList<AnalizValueModel> analizs = new ArrayList<AnalizValueModel>();

    public void init() {
        loadNumbSpectrum();
        loadAnalizValueModels();
    }

    public void loadAnalizValueModels() {
        boolean flagStart = false;
        AnalizValueModel analizValueModel = null;
        for (String line : lines) {
            if (isFinish(line) && flagStart) {
                flagStart = false;

            }
            if (flagStart) {
                analizValueModel.lineValues = line;
                analizs.add(analizValueModel);
                analizValueModel.init();
                //spectrumMap.put("Ka", analizValueModel)
                spectrumMap.put(analizValueModel.getKeyValue(), analizValueModel);
                elWtMap.put(analizValueModel.getKeyValue(), analizValueModel.wtEl);
                analizValueModel = new AnalizValueModel();
            }
            if (isStartLines(line) && !flagStart) {
                analizValueModel = new AnalizValueModel();
                flagStart = true;
            }
        }
    }

    private boolean isStartLines(String line) {
        if (line.length() > 8) {
            String find1 = line.substring(0, 4);
            if (find1.equalsIgnoreCase("Elmt")) { //поиск стр Elmt:

                System.out.println(line);
                return true;
            }
        }
        return false;
    }

    private boolean isFinish(String line) {
        String find1 = line.substring(0, 1);
        if (find1.equalsIgnoreCase("O")) {
            return true;
        }
        return false;
    }


    public void loadNumbSpectrum() {
        String l = lines.get(0);
        StringTokenizer st = new StringTokenizer(l);
        while (st.hasMoreTokens()) {
            try {
                numerSpectra = new Integer(st.nextToken());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Неверный формат строки!");
            }
        }
    }
}
