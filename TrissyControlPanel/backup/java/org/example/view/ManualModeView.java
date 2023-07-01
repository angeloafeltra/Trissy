package org.example.view;

import org.example.action.SerialComunication;
import org.example.view.component.ConnectionPanel;
import org.example.view.component.MagnetPanel;
import org.example.view.component.ServoController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ManualModeView {


    private JPanel panel;
    private RecorderPanel recorderPanel;
    private ServoController baseController,armController,forearmController;
    private MagnetPanel magnetPanel;

    public ManualModeView(SerialComunication serialComunication){
        this.panel=new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel,BoxLayout.Y_AXIS));
        this.panel.setPreferredSize(new Dimension(600, 490));
        this.panel.setMaximumSize(new Dimension(600, 490));
        TitledBorder title=BorderFactory.createTitledBorder("ManualMode");
        title.setTitleJustification(TitledBorder.RIGHT);
        this.panel.setBorder(title);


        this.baseController=new ServoController("Controller Base",0,180,90);
        this.armController=new ServoController("Controller Braccio",0,80,80);
        this.forearmController=new ServoController("Controller Avambraccio",60,180,60);
        this.magnetPanel=new MagnetPanel();
        this.recorderPanel=new RecorderPanel();

        this.baseController.setActionEvent(serialComunication);
        this.armController.setActionEvent(serialComunication);
        this.forearmController.setActionEvent(serialComunication);
        this.magnetPanel.setActionEvent(serialComunication);
        this.recorderPanel.setActionEvent(serialComunication);

        this.panel.add(recorderPanel.getPanel());
        this.panel.add(baseController.getPanel());
        this.panel.add(Box.createRigidArea(new Dimension(0,20)));
        this.panel.add(armController.getPanel());
        this.panel.add(Box.createRigidArea(new Dimension(0,20)));
        this.panel.add(forearmController.getPanel());
        this.panel.add(Box.createRigidArea(new Dimension(0,20)));
        this.panel.add(magnetPanel.getPanel());
    }

    public JPanel getPanel(){return this.panel;}

}
