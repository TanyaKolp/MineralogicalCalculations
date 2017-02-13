package com.tsoft.calculation;

import com.tsoft.model.ContainerOpitsModel;
import com.tsoft.model.TableMendeleeva;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tania on 6/28/16.
 */
public class Calculation {
    Map<String, Double> mapSpFormula = new HashMap<String, Double>();
    public int nomerOpita = 0;
    public int nomerSpectra = 0;


    public Calculation(Map<String, Double> map, int nomerOp, int nomerSp) {
        nomerOpita = nomerOp;
        nomerSpectra = nomerSp;
        mapSpFormula = map;
    }

    public Calculation(Map<String, Double> mapValues, int nomerOpita) {
        this.nomerOpita = nomerOpita;
        mapSpFormula = mapValues;
    }

    public static int getIndexOpit(int nomerOpita) {
        int nOp = 0;
        for (int i = 0; i < ContainerOpitsModel.getInstance().aOppits.size(); i++) {
            if (nomerOpita == ContainerOpitsModel.getInstance().aOppits.get(i).numerOpita) {
                nOp = i;
                break;
            }
        }
        return nOp;
    }

    public static int getIndexSp(int nomerOpita,int nomerSpectra) {
        int nOp = getIndexOpit(nomerOpita);
        int nSp = 0;
        for (int i = 0; i < ContainerOpitsModel.getInstance().aOppits.get(nOp).spects.size(); i++) {
            if (nomerSpectra == ContainerOpitsModel.getInstance().aOppits.get(nOp).spects.get(i).numerSpectra) {
                nSp = i;
                break;
            }
        }
        return nSp;
    }
}
