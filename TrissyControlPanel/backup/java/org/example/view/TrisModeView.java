package org.example.view;

import org.example.action.SerialComunication;
import org.example.tris.TicTacToe;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrisModeView {

    private JPanel panel;
    private TrisPanel tris;
    private JLabel label;
    private JButton button;
    public TrisModeView(SerialComunication serialComunication){
        this.panel=new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel,BoxLayout.Y_AXIS));
        this.panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.panel.setPreferredSize(new Dimension(600, 490));
        this.panel.setMaximumSize(new Dimension(600, 490));
        TitledBorder title=BorderFactory.createTitledBorder("TrisMode");
        title.setTitleJustification(TitledBorder.RIGHT);
        this.panel.setBorder(title);

        this.tris=new TrisPanel();

        this.panel.add(Box.createRigidArea(new Dimension(0,30)));
        this.label=new JLabel("Gioca La Tua Mossa!!!");
        Font newLabelFont=new Font(this.label.getFont().getName(),Font.BOLD,20);
        this.label.setFont(newLabelFont);
        this.label.setForeground(Color.RED);
        this.label.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.panel.add(this.label);

        this.panel.add(Box.createRigidArea(new Dimension(0,20)));

        this.panel.add(this.tris.getPanel());

        this.panel.add(Box.createRigidArea(new Dimension(0,20)));
        this.button=new JButton("Rigioca");
        this.button.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.panel.add(button);
        setActionEvent();
        button.setVisible(false);
        this.tris.setActionEvent(serialComunication,label,button);
    }

    public JPanel getPanel(){return this.panel;}


    public void setActionEvent(){
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tris.clearTable();
                label.setText("Gioca La Tua Mossa!!!");
                button.setVisible(false);
            }
        });
    }

    public void clear(){
        this.label.setText("Gioca La Tua Mossa!!!");
        this.button.setVisible(false);
        this.tris.clearTable();
    }



}
