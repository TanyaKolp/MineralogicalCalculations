package com.tsoft.app;

import com.tsoft.calculation.Interpreter;
import com.tsoft.model.ContainerOpitsModel;

import java.io.IOException;

/**
 * Created by tania on 12.02.17.
 */
public class App {
    public static void main(String[] args) {
        String path = "/home/tania/Documents/Радиоэк/opit/y7-02-13/13-02-2017.dat";
        try {
            ContainerOpitsModel.getInstance().loadOpitsFromFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Interpreter interpreter = new Interpreter();
        interpreter.executeFromFile();
    }
}
