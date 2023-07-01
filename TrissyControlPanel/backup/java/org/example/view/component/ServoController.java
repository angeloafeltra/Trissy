package org.example.view.component;

import org.example.action.SerialComunication;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;

public class ServoController{


    private JSlider slider;
    private JPanel panel;
    private JLabel label;

    private JLabel labelValue;


    public ServoController(String nameController,int minVal,int maxVal, int startVal){
        this.panel=new JPanel();
        this.panel.setPreferredSize(new Dimension(550, 100));
        this.panel.setMaximumSize(new Dimension(550, 100));
        this.panel.setLayout(new BoxLayout(this.panel,BoxLayout.Y_AXIS));
        this.label=new JLabel(nameController);
        this.label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.labelValue=new JLabel(Integer.toString(startVal));
        this.labelValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        this.slider=new JSlider(JSlider.HORIZONTAL,minVal,maxVal,startVal);
        this.slider.setPreferredSize(new Dimension(300, 40));
        this.slider.setMajorTickSpacing(20);
        this.slider.setMinorTickSpacing(10);
        this.slider.setPaintTicks(true);
        this.slider.setPaintLabels(true);
        this.slider.setFont(new Font("Serif", Font.ITALIC, 15));

        this.panel.add(this.label);
        this.panel.add(Box.createRigidArea(new Dimension(0,5)));
        this.panel.add(this.labelValue);
        this.panel.add(Box.createRigidArea(new Dimension(0,5)));
        this.panel.add(this.slider);

    }

    public JPanel getPanel(){return this.panel;}


    public void setActionEvent(SerialComunication serialComunication){
        this.slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value=slider.getValue();
                labelValue.setText(Integer.toString(value));
                String message=label.getText()+":"+Integer.toString(value);
                System.out.println("Comando da eseguire - "+message);
                try {
                    String txt=serialComunication.sendMessage(message);
                    Thread.sleep(50);
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });


    }

}


