package com.tsoft.calculation;

import com.tsoft.model.ContainerOpitsModel;
import com.tsoft.model.TableMendeleeva;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tania on 6/28/16.
 */
public class CalculationByAnion {
    private TableMendeleeva tm = TableMendeleeva.getInstance();
    /*расчет минерала на формулу по аниону
    * 1) шаг. (atCation)ищем атом кол-во - следует разделить содержание каждого элемента в массовых процентах
    * на его атомную массу для получения атомного количества этого элемента
    * 2)шаг. (summ) найти сумму атомных колво кислорода
    * 3)шаг.(nAnion) задаем кол-во кислорода в формуле (по теор формуле минерала)
    * 4)шаг.(coef) ищем общий делитель: делитель = сумма из шага2/число атомов кислорода в формуле из шага 3
    * 5)шаг. (AL formula) находим формульные единицы эл: атом кол-во каждого эл (из шага1) делим на общий делитель(из шага4)*/
// возвращает объект Calculation , содержащий номер спектра и опыта,
// и hashmap "mapSpFormula"эл-формельные+SummCation-сумма катионов единици для этого спектра

    public Calculation getSpectrumFormul(int numOp, int numSp, int nAnion) {
        Map<String, Double> mapSpFormula = new HashMap<String, Double>();
        int nOp = Calculation.getIndexOpit( numOp);
        int nSp = Calculation.getIndexSp(numOp, numSp);
        Map<String, Double> mapSp = ContainerOpitsModel.getInstance().getMapSpectr(nOp, nSp);
        double summ = 0.0;
        double sAnion = 0.0;
        for (String key : mapSp.keySet()) {
            Double val = mapSp.get(key);
            double atCation = val / tm.getElMass(key);
            summ += getAtOxigen(atCation, key);
            System.out.println(key + " = " + val);

        }

        for (String key : mapSp.keySet()) {
            Double val = mapSp.get(key);
            double coef = (tm.getElMass(key) * summ) / nAnion; // с учетом перехода к ат колво (*tm.getElMass(key))
            double formE = val /coef;
            System.out.println(key + " = " + formE);
            mapSpFormula.put(key, formE);
            sAnion += getAtOxigen(formE,key);
        }
        mapSpFormula.put("S.Anion",sAnion);
        return new Calculation(mapSpFormula, numOp, numSp);

    }

    private double getAtOxigen(double atCation, String key) {
        double atAnion = 0.0;
        if (key.equals("Si") || key.equals("Ti")) {
            atAnion = atCation * 2;
        } else if (key.equals("Al") || key.equals("La") || key.equals("Ce")) {
            atAnion = atCation * 2 / 3;
        } else if (key.equals("Mg") || key.equals("Ca") || key.equals("Sr")) {
            atAnion = atCation;
        } else if (key.equals("Na") || key.equals("Cs")) {
            atAnion = atCation / 2;
        }
        return atAnion;
    }
}
