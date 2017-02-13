package com.tsoft.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by tania on 6/17/16.
 */
public class ContainerOpitsModel {
    public ArrayList<OpitModel> aOppits = new ArrayList<OpitModel>();
    public static ContainerOpitsModel instance = null;

    private ContainerOpitsModel() {
    }

    public static ContainerOpitsModel getInstance() {
        if (instance == null) {
            instance = new ContainerOpitsModel();
        }
        return instance;
    }


    public void loadOpitsFromFile(String path) throws IOException {
        boolean flagStart = false;
        OpitModel currentOpitModel = null;
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        while ((line = br.readLine()) != null) { // метод readline построчное чтение
            if (flagStart && isOpitStart(line)) {
                aOppits.add(currentOpitModel);
                currentOpitModel.init();
                flagStart = false;
            }
            if (isOpitStart(line) && !flagStart) {
                flagStart = true;
                currentOpitModel = new OpitModel();
            }

            if (flagStart) {
                currentOpitModel.lines.add(line);
            }
        }
        br.close();
        fis.close();

        if (currentOpitModel != null) {
            aOppits.add(currentOpitModel);
            currentOpitModel.init();
        }

    }

    public boolean isOpitStart(String line) {
        return line.startsWith("Site:");

    }

    public Map<String, Double> getMapSpectr(int iOp, int iSp) {
        OpitModel op = this.aOppits.get(iOp);
        return op.spects.get(iSp).elWtMap;

    }

    public int getSizeSpInOp(int nOp) {
        /*возвращает кол-во спектров в опыте*/
        OpitModel op = this.aOppits.get(nOp);
        return op.spects.size();

    }


}
