package com.tsoft.calculation;

import com.tsoft.model.ContainerOpitsModel;
import com.tsoft.model.TableMendeleeva;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tania on 6/27/16.
 */
public class CalculationByCation {
    private TableMendeleeva tm = TableMendeleeva.getInstance();
    private ContainerOpitsModel container = ContainerOpitsModel.getInstance();
    /**расчет минерала на формулу по катионам
        * 1) шаг. ищем атом кол-во - следует разделить содержание каждого элемента в массовых процентах
        * на его атомную массу для получения атомного количества этого элемента
        * 2)шаг.(summ2) ищем сумму атом кол-во катионов
        * 3)шаг.(nCation) задаем кол-во катионов в формуле (по теор формуле минерала)
        * 4)шаг.(coef) ищем общий делитель: делитель = сумма из шага2/кол-во катионов в формуле из шага 3
        * 5)шаг. (AL formula) находим формульные единицы эл: атом кол-во каждого эл (из шага1) делим на общий делитель(из шага4)
        *возвращает объект Calculation , содержащий номер спектра и опыта,
        *и hashmap "mapSpFormula"эл-формульные+SummCation-сумма катионов ф.единици для этого спектра*/
    public Calculation getFormulaForSpector(int numOp, int numSp, int nCation) {
        Map<String, Double> mapSpFormula = new HashMap<String, Double>();
        int nOp = Calculation.getIndexOpit(numOp);
        int nSp = Calculation.getIndexSp(numOp, numSp);
        Map<String, Double> mapSp = container.getMapSpectr(nOp, nSp);
        Double summ2 = 0.0;
        double s3 = 0.0;
        for (String key : mapSp.keySet()) {
            Double val = mapSp.get(key);
            summ2 += val / tm.getElMass(key);
            System.out.println(key + " = " + val);
        }
        System.out.println(" formula ");
        for (String key : mapSp.keySet()) {
            Double val = mapSp.get(key);
            double coef = (tm.getElMass(key) * summ2) / nCation; // с учетом перехода к ат колво (*tm.getElMass(key))
            double formE = val / coef;
            System.out.println(key + " = " + formE);
            mapSpFormula.put(key, formE);
            s3 += formE;
        }
        System.out.println("summ3 = " + s3);
        mapSpFormula.put("SumCation", s3);
        Calculation calculation = new Calculation(mapSpFormula, numOp, numSp);
        return calculation;
    }

    public ArrayList<Calculation> getFormulaForOpit(int numOp, int nCation) {
        ArrayList<Calculation> allSpects = new ArrayList<Calculation>();
        int nOp = Calculation.getIndexOpit(numOp);
        CalculationByCation calculationByCation = new CalculationByCation();
        for (int i = 0; i < container.getSizeSpInOp(nOp); i++) {
            int nomerSp = container.aOppits.get(nOp).spects.get(i).numerSpectra;
            System.out.println("#Spectrum: " + nomerSp);
            allSpects.add(i, calculationByCation.getFormulaForSpector(numOp, nomerSp, nCation));
        }
        return allSpects;
    }

}
