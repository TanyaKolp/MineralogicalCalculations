package com.tsoft.model;


import java.util.HashMap;

/**
 * Created by tania on 6/20/16.
 */
public class TableMendeleeva {
    public static TableMendeleeva instance = null;

    private TableMendeleeva() {
    }

    public static TableMendeleeva getInstance() {
        if (instance == null) {
            instance = new TableMendeleeva();
            instance.filTable();
            instance.fillCoefToWtOx();
        }
        return instance;
    }

    //  ArrayList<String> lines = new ArrayList<String>();
    private HashMap<String, Double> tMendeleeva = new HashMap<String, Double>();
    public HashMap<String, Double> tCoeftoWtOx = new HashMap<String, Double>();

    private void filTable() {
        tMendeleeva.put("O", 15.999);
        tMendeleeva.put("Fe", 56.0);
        tMendeleeva.put("Na", 22.99);
        tMendeleeva.put("Al", 26.98);
        tMendeleeva.put("Si", 28.09);
        tMendeleeva.put("Ca", 40.08);
        tMendeleeva.put("Mg", 24.3);
        tMendeleeva.put("Ti", 47.9);
        tMendeleeva.put("Cs", 132.905);
        tMendeleeva.put("La", 138.905);
        tMendeleeva.put("Ce", 140.1);
        tMendeleeva.put("Sr", 87.62);
        tMendeleeva.put("B ", 10.81);
        tMendeleeva.put("K ", 39.09);
        tMendeleeva.put("Nb", 92.906);
        tMendeleeva.put("Y ", 88.90);
        tMendeleeva.put("Dy", 162.5);
        tMendeleeva.put("Nd", 144.242);
        tMendeleeva.put("Sm", 150.36);
    }

    public double getElMass(String element) {
        Double a = 0.0;
        try {
            a = tMendeleeva.get(element);
            return a;
        } catch (NullPointerException e) {
            System.out.println("В Таблице Менделеева нет данных по элементу: " + element);
            System.exit(1);
        }
        return -1;
    }

    /** создает мап со значениями = muOx/(muEl*n), где n - кол-во атомов El в Ox*/
    private void fillCoefToWtOx() {
        for (String key : tMendeleeva.keySet()) {
            if (key.matches("Si||Ti")) {
                tCoeftoWtOx.put(key, ((getElMass("O") * 2 + getElMass(key)) / getElMass(key)));
            } else if (key.matches("Al||La||Ce||B ||Y ||Dy||Sm||Nd")) {
                tCoeftoWtOx.put(key, ((getElMass("O") * 3 + getElMass(key) * 2) / getElMass(key) / 2));
            } else if (key.matches("Mg||Ca||Sr")) {
                tCoeftoWtOx.put(key, ((getElMass("O") + getElMass(key)) / getElMass(key)));
            } else if (key.matches("Na||Cs||K ")) {
                tCoeftoWtOx.put(key, ((getElMass("O") + getElMass(key) * 2) / getElMass(key) / 2));
            } else if (key.matches("Nb")) {
                tCoeftoWtOx.put(key, ((getElMass("O") * 5 + getElMass(key) * 2) / getElMass(key) / 2));
            }
        }

    }

}