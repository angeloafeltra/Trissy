package org.example.view;

import org.example.comunication.SerialComunication;
import org.example.view.component.MagnetPanel;
import org.example.view.component.ServoControllerPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ManualModeView {


    private JPanel panel;
    private ServoControllerPanel baseController,armController,forearmController;
    private MagnetPanel magnetPanel;

    public ManualModeView(SerialComunication serialComunication){
        this.panel=new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel,BoxLayout.Y_AXIS));
        this.panel.setPreferredSize(new Dimension(600, 490));
        this.panel.setMaximumSize(new Dimension(600, 490));
        TitledBorder title=BorderFactory.createTitledBorder("ManualMode");
        title.setTitleJustification(TitledBorder.RIGHT);
        this.panel.setBorder(title);


        this.baseController=new ServoControllerPanel("Controller Base",0,180,90);
        this.armController=new ServoControllerPanel("Controller Braccio",0,80,80);
        this.forearmController=new ServoControllerPanel("Controller Avambraccio",60,180,60);
        this.magnetPanel=new MagnetPanel();


        this.baseController.setActionEvent(serialComunication);
        this.armController.setActionEvent(serialComunication);
        this.forearmController.setActionEvent(serialComunication);
        this.magnetPanel.setActionEvent(serialComunication);

        this.panel.add(baseController.getPanel());
        this.panel.add(Box.createRigidArea(new Dimension(0,20)));
        this.panel.add(armController.getPanel());
        this.panel.add(Box.createRigidArea(new Dimension(0,20)));
        this.panel.add(forearmController.getPanel());
        this.panel.add(Box.createRigidArea(new Dimension(0,20)));
        this.panel.add(magnetPanel.getPanel());
    }

    public JPanel getPanel(){return this.panel;}

    public void disable(){
        this.baseController.disable();
        this.armController.disable();
        this.forearmController.disable();
        this.magnetPanel.disable();
    }

    public void enable(){
        this.baseController.enable();
        this.armController.enable();
        this.forearmController.enable();
        this.magnetPanel.enable();
    }

}
