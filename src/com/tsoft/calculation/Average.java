package com.tsoft.calculation;

import com.tsoft.model.ContainerOpitsModel;
import com.tsoft.model.TableMendeleeva;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tania on 6/28/16.
 */
public class Average {

    public Average() {

    }

    /**
     * шаг1) перевести wt%el в wt%OX
     * шаг2) (summ) найти сумму по всем элементам
     * шаг3) привести к 100%
     * или
     * шаг3) найти среднее по определенным спектрам
     */
//    public ArrayList<Map<String, Double>> evaluate(Map<String, ArrayList<Integer>> fazy, int nomerOpita) {
//        ArrayList<Map<String, Double>> result = new ArrayList<Map<String, Double>>();
//        for(String faza : fazy.keySet()){
//            Map<String, Double> averageForFaza = evaluateOxForFaza(fazy.get(faza),nomerOpita);
//            averageForFaza.put(faza,-1.0);
//            result.add(averageForFaza);
//        }
//        return result;
//    }
    public Calculation evaluateOxForFaza(ArrayList<Integer> nomeraSpectrov, int nomerOpita) {
        Map<String, Double> result = new HashMap<String, Double>();
        ArrayList<HashMap<String, Double>> allSpectry = new ArrayList<HashMap<String, Double>>();
        for (Integer nomerSp : nomeraSpectrov) {
            allSpectry.add(convertWeightToCompoundPercent(nomerOpita, nomerSp));
        }
        double sumByElement = 0.0;
        for (String key : allSpectry.get(0).keySet()) {
            for (HashMap<String, Double> sp : allSpectry) {
                if (sp.get(key) != null) {
                    sumByElement += sp.get(key);
                }else {
                    System.out.println("AVERAGE for faza: no '" +key+"'");
                }
            }
            result.put(key, sumByElement / allSpectry.size());
            sumByElement = 0.0;
        }
        return new Calculation(result, nomerOpita);
    }

    //step 1 and 2
    private HashMap<String, Double> convertWeightToCompoundPercent(int nomerOpita, int numSp) {
        int nOp = Calculation.getIndexOpit(nomerOpita);
        int nSp = Calculation.getIndexSp(nomerOpita, numSp);
        HashMap<String, Double> mapCP = new HashMap<String, Double>();
        Map<String, Double> mapSp = ContainerOpitsModel.getInstance().getMapSpectr(nOp, nSp);
        double sum = 0.0;
        System.out.println("mapCompoundPercent: #Op - " + nomerOpita + "#Sp - " + numSp);
        for (String key : mapSp.keySet()) {
            double v = mapSp.get(key) * TableMendeleeva.getInstance().tCoeftoWtOx.get(key);
            sum += v;
            mapCP.put(key, v);
            System.out.println(key + "    " + mapCP.get(key));
        }
        System.out.println("Total = " + sum);
        mapCP.put("Sum", sum);
        return mapCP;
    }

}

