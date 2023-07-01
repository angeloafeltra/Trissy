package org.example.view.component;

import org.example.action.SerialComunication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RecorderPanel {


    private JPanel panel;
    private JButton buttonRec;
    private JButton playRec;
    private int clickButton1=0;


    public RecorderPanel(){
        this.panel=new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel,BoxLayout.X_AXIS));
        this.panel.setPreferredSize(new Dimension(550, 30));
        this.panel.setMaximumSize(new Dimension(550, 30));

        this.buttonRec=new JButton();
        this.buttonRec.setBorderPainted(false);
        this.buttonRec.setContentAreaFilled(false);
        this.buttonRec.setFocusPainted(false);
        this.buttonRec.setOpaque(false);
        this.buttonRec.setPreferredSize(new Dimension(30, 30));
        this.buttonRec.setMaximumSize(new Dimension(30, 30));
        this.setImageRec(this.clickButton1);

        this.playRec=new JButton();
        this.playRec.setBorderPainted(false);
        this.playRec.setContentAreaFilled(false);
        this.playRec.setFocusPainted(false);
        this.playRec.setOpaque(false);
        this.playRec.setPreferredSize(new Dimension(30, 30));
        this.playRec.setMaximumSize(new Dimension(30, 30));
        this.setImagePlay(0);

        this.panel.add(buttonRec);
        this.panel.add(playRec);

    }

    private void setImageRec(int click){
        ImageIcon imageIcon;
        Image newimg;
        if (click==0) {
            imageIcon = new ImageIcon("src/main/resources/start_rec.png"); // load the image to a imageIcon
            Image image = imageIcon.getImage(); // transform it
            newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        }else{
            imageIcon = new ImageIcon("src/main/resources/stop_rec.png"); // load the image to a imageIcon
            Image image = imageIcon.getImage(); // transform it
            newimg = image.getScaledInstance(27, 27,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        }
        imageIcon = new ImageIcon(newimg);  // transform it back
        this.buttonRec.setIcon(imageIcon);

    }

    private void setImagePlay(int click){
        ImageIcon imageIcon;
        Image newimg;
        if (click==0) {
            imageIcon = new ImageIcon("src/main/resources/play.png"); // load the image to a imageIcon
            Image image = imageIcon.getImage(); // transform it
            newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        }else{
            imageIcon = new ImageIcon("src/main/resources/stop.png"); // load the image to a imageIcon
            Image image = imageIcon.getImage(); // transform it
            newimg = image.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        }
        imageIcon = new ImageIcon(newimg);  // transform it back
        this.playRec.setIcon(imageIcon);
    }

    public JPanel getPanel(){return this.panel;}

    public void setActionEvent(SerialComunication serialComunication){
        this.buttonRec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clickButton1==0){
                    System.out.println("Attivo Registrazione");
                    serialComunication.startRec();
                    clickButton1=1;
                    setImageRec(clickButton1);
                }else{
                    System.out.println("Fermo Registrazione");
                    serialComunication.stopRec();
                    clickButton1=0;
                    setImageRec(clickButton1);
                }
            }
        });

        this.playRec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Play");
                setImagePlay(1);
                try {
                    serialComunication.playAutoMode();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                setImagePlay(0);
            }
        });
    }

}
