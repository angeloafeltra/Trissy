package org.example.view.component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.example.comunication.SerialComunication;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectionPanel {

    private JPanel mainPanel; //Panel Principale
    private JPanel panel; //Panel di supporto
    private JComboBox<String> comboBoxPort;
    private JComboBox<String> comboBoxBaud;
    private JButton button;
    private JLabel label;
    private JLabel labelPort;

    private int click=0;


    public ConnectionPanel(String[] listPort){

        this.labelPort=new JLabel("Port:"); //Creo la label

        //Creo la prima ComboBox
        this.comboBoxPort=new JComboBox<>(listPort);
        this.comboBoxPort.setPreferredSize(new Dimension(300,50));
        this.comboBoxPort.setMaximumSize( this.comboBoxPort.getPreferredSize() );


        //Creo la seconda ComboBox
        this.comboBoxBaud=new JComboBox<>(new String[]{"9600"});
        this.comboBoxBaud.setPreferredSize(new Dimension(100,50));
        this.comboBoxBaud.setMaximumSize( this.comboBoxBaud.getPreferredSize() );

        //Creo il button
        this.button=new JButton("Connect");


        this.panel=this.setLayoutPanel();
        this.panel.add(this.labelPort);
        this.panel.add(this.comboBoxPort);
        this.panel.add(this.comboBoxBaud);
        this.panel.add(this.button);


        this.label=new JLabel();
        this.label.setForeground(Color.RED);
        this.label.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.mainPanel=new JPanel();
        this.mainPanel.setPreferredSize(new Dimension(530, 100));
        this.mainPanel.setMaximumSize(new Dimension(530, 100));

        this.mainPanel.setLayout(new BoxLayout(this.mainPanel,BoxLayout.Y_AXIS));
        this.mainPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        this.mainPanel.add(this.panel);
        this.mainPanel.add(this.label);
    }


    private JPanel setLayoutPanel(){

        this.panel=new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel,BoxLayout.X_AXIS));
        this.panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.panel.setPreferredSize(new Dimension(530, 50));
        this.panel.setMaximumSize(new Dimension(530, 50));

        return this.panel;
    }


    public JPanel getPanel(){ return this.mainPanel; }



    public void setActionEvente(SerialComunication serial){
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(click==0) {
                    String portDescritpor = comboBoxPort.getSelectedItem().toString();
                    String baud = comboBoxBaud.getSelectedItem().toString();
                    //Comunicazione Seriale
                    try {
                        boolean result=serial.startConnection(portDescritpor, Integer.valueOf(baud));
                        if(result) {
                            label.setText("Connessione Riuscita");
                            click = 1;
                            button.setText("Stop");
                        }else{
                            label.setText("Connessione Non Riuscita");
                        }
                    } catch (IOException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                }else{
                    //Comunicazione Seriale
                    boolean result=serial.disconnect();
                    if(result){
                        label.setText("Porta Chiusa");
                        click=0;
                        button.setText("Connect");
                    }else{
                        label.setText("Errore Disconnessione");
                    }

                }
            }
        });



    }


}
