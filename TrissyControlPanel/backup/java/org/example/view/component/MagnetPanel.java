package org.example.view.component;

import org.example.action.SerialComunication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MagnetPanel {


    private JPanel panel;
    private JLabel label;
    private JButton button;


    private int click=0;


    public MagnetPanel(){
        this.panel=new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel,BoxLayout.X_AXIS));
        this.panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.panel.setPreferredSize(new Dimension(140, 70));
        this.panel.setMaximumSize(new Dimension(140, 70));

        this.label=new JLabel("Magnete:");
        Font newLabelFont=new Font(this.label.getFont().getName(),Font.BOLD,this.label.getFont().getSize());
        this.label.setFont(newLabelFont);


        this.button=new JButton();
        this.button.setBorderPainted(false);
        this.button.setContentAreaFilled(false);
        this.button.setFocusPainted(false);
        this.button.setOpaque(false);
        this.setImage(click);

        this.panel.add(this.label);
        this.panel.add(this.button);

    }

    private void setImage(int click){
        ImageIcon imageIcon;
        if (click==0) {
            imageIcon = new ImageIcon("src/main/resources/switch-off.png"); // load the image to a imageIcon
        }else{
            imageIcon = new ImageIcon("src/main/resources/switch-on.png"); // load the image to a imageIcon
        }
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(60, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back
        this.button.setIcon(imageIcon);

    }

    public JPanel getPanel(){return this.panel;}

    public void setActionEvent(SerialComunication serialComunication){
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(click==0){
                    try {
                        serialComunication.sendMessage("Magnete On");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    click=1;
                    setImage(click);
                }else{
                    try {
                        serialComunication.sendMessage("Magnete Off");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    click=0;
                    setImage(click);
                }
            }
        });
    }




}
