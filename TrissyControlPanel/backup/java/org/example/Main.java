package org.example;


import java.io.IOException;


import org.example.view.ControlPanelView;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException{
        ControlPanelView view=new ControlPanelView();
        view.showView();
    }
}