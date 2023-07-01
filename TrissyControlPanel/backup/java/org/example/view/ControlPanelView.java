package org.example.view;

import org.example.action.SerialComunication;
import org.example.tris.TicTacToe;
import org.example.view.component.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanelView {


    private JFrame frame;
    private JButton mode;
    private ConnectionPanel connectPanel;
    private TrisModeView trisView;
    private ManualModeView manualModeView;
    private SerialComunication serialComunication;

    private int click=0;



    public ControlPanelView(){
        this.frame=new JFrame();
        this.serialComunication=new SerialComunication();

        this.connectPanel=new ConnectionPanel(serialComunication.getListPort());
        this.trisView=new TrisModeView(serialComunication);
        this.manualModeView=new ManualModeView(serialComunication);
        this.connectPanel.setActionEvente(serialComunication);
        this.genereteView();
    }


    private void genereteView(){
        frame.setTitle("Trissy ControlPanel");
        frame.setLocationRelativeTo(null);
        frame.setSize(630,680);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));

        frame.add(connectPanel.getPanel());

        this.mode=new JButton();
        this.mode.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.mode.setPreferredSize(new Dimension(90, 40));
        this.mode.setMaximumSize(new Dimension(90, 40));
        this.mode.setBorderPainted(false);
        this.mode.setContentAreaFilled(false);
        this.mode.setFocusPainted(false);
        this.mode.setOpaque(false);
        setImage(click);
        setActionEvent();


        frame.add(this.mode);
        frame.add(manualModeView.getPanel());

        frame.setVisible(false);
    }

    public void showView(){
        frame.setVisible(true);
    }

    private void setImage(int click){
        ImageIcon imageIcon;
        if (click==0) {
            imageIcon = new ImageIcon("src/main/resources/MANUAL.png"); // load the image to a imageIcon
        }else{
            imageIcon = new ImageIcon("src/main/resources/TRIS.png"); // load the image to a imageIcon
        }
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(90, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back
        this.mode.setIcon(imageIcon);

    }

    private void setActionEvent(){
        this.mode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(click==0){
                    System.out.println("Attivo Tris Mode");
                    click=1;
                    setImage(click);
                    frame.remove(manualModeView.getPanel());
                    frame.add(trisView.getPanel());
                    frame.invalidate();
                    frame.validate();
                    frame.repaint();
                }else{
                    System.out.println("Attivo Manual Mode");
                    click=0;
                    setImage(click);
                    frame.remove(trisView.getPanel());
                    trisView.clear();
                    frame.add(manualModeView.getPanel());
                    frame.invalidate();
                    frame.validate();
                    frame.repaint();
                }
            }
        });
    }



}
