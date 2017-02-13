package com.tsoft.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by tania on 6/17/16.
 */
public class AnalizValueModel {
    Map<String, Double> value = new HashMap<String, Double>();
    ArrayList<String> allValue = new ArrayList<String>();
    public String lineValues;
    public Double wtEl;

    public String getKeyValue() {
        return lineValues.substring(0, 2);
    }

    public void init() {
        makeColumns();
        getValue();
    }

    public void makeColumns() {
        /*метод разбивает 1 строку из массива aLines,  игнорируя "<"
        минус метода: пробелы в столбцах он учитывает
        */
        String word;
        StringTokenizer st = new StringTokenizer(lineValues);
        while (st.hasMoreTokens()) {
            word = st.nextToken();
            if (!word.equalsIgnoreCase("<")) {
                allValue.add(word);
                // 1 столбец (0 индекс массива) - индекс элемента,
                // 5 столбец (4 индекс массива)  - wt%эл, 8 столбец - вес%оксида
            }
        }
    }

    public Map<String, Double> getValue() {
        /*метод добавляет в hashmap "value" из элемента (его индекса в виде строки)
        и значения wt%Эл (в виде double)
         */

        try {
            Double wt1 = new Double(allValue.get(4));
            value.put(allValue.get(0), wt1);
            wtEl =wt1;
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат строки!");
            Double wt1 = 0.0;
            value.put(allValue.get(0), wt1);
        }

        return value;
    }
}
