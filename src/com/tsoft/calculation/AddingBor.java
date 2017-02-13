package com.tsoft.calculation;

import com.tsoft.model.ContainerOpitsModel;
import com.tsoft.model.TableMendeleeva;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by tania on 9/28/16.
 */
public class AddingBor {

    public void addBor(int numOp, int numSp) {
        double total = 0;
        int iSp = Calculation.getIndexSp(numOp, numSp);
        int iOp = Calculation.getIndexOpit(numOp);
        Map<String, Double> map = ContainerOpitsModel.getInstance().getMapSpectr(iOp, iSp);
        for (String key : map.keySet()) {
            total += map.get(key);
        }
        ArrayList<String> lines = ContainerOpitsModel.getInstance().aOppits.get(iOp).spects.get(iSp).lines;
        String line = lines.get(lines.size() - 1);
        StringTokenizer st = new StringTokenizer(line);
        double oValue = 0;
        while (st.hasMoreTokens()) {
            try {
                oValue = new Double(st.nextToken());
                break;
            } catch (NumberFormatException e) {
                System.out.println("no number");
            }
        }
        total = total + oValue;

        double bor = 100 - total;
        map.put("B ", bor);

    }

    public void addBorForSpects(int numOp, ArrayList<Integer> numsSp) {
        for (int i = 0; i < numsSp.size(); i++) {
            addBor(numOp, numsSp.get(i));
        }
    }
}
