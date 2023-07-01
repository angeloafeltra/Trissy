package org.example.view.component;

import org.example.action.SerialComunication;
import org.example.tris.TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class TrisPanel {


    private JPanel panel;
    private JButton b1,b2,b3,b4,b5,b6,b7,b8,b9;

    private TicTacToe ticTacToe;
    private MinMaxAlgorithm minMaxAlgorithm;


    public TrisPanel(){
        this.panel=new JPanel();
        this.panel.setPreferredSize(new Dimension(300, 300));
        this.panel.setMaximumSize(new Dimension(300, 300));
        this.panel.setBackground(Color.BLACK);
        this.panel.setLayout(new GridLayout(3, 3, 5, 5));

        this.b1=new JButton();
        this.b2=new JButton();
        this.b3=new JButton();
        this.b4=new JButton();
        this.b5=new JButton();
        this.b6=new JButton();
        this.b7=new JButton();
        this.b8=new JButton();
        this.b9=new JButton();

        int i=0;
        for(JButton button:new JButton[]{this.b1, this.b2, this.b3, this.b4, this.b5, this.b6, this.b7, this.b8, this.b9}){
            button.setName(Integer.toString(i));
            button.setPreferredSize(new Dimension(96,96));
            button.setMaximumSize(new Dimension(96,96));
            this.panel.add(button);
            i=i+1;
        }

        this.ticTacToe=new TicTacToe();
        this.minMaxAlgorithm=new MinMaxAlgorithm();

    }



    public JPanel getPanel(){
        return this.panel;
    }

    public void setActionEvent(SerialComunication serialComunication,JLabel result,JButton rigioca){
        for(JButton button:new JButton[]{this.b1, this.b2, this.b3, this.b4, this.b5, this.b6, this.b7, this.b8, this.b9}){
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setMove(button,"O",serialComunication);

                    //Verifio se con la mossa generata ho vinto
                    if(!ticTacToe.checkWinner()){
                        //Verifico se ci sono mosse disponibili
                        ArrayList<Integer> mosseDisponibili=ticTacToe.getMosseLibere();
                        if(mosseDisponibili.size()>0){
                            //Genero la mossa del robot
                            int mossa=minMaxAlgorithm.genereteMove(mosseDisponibili);
                            //Setto la mossa
                            setMove(getButtonByName(Integer.toString(mossa)),"X",serialComunication);
                            //Verifico se la mossa generata ha vinto
                            if(ticTacToe.checkWinner()) {
                                System.out.println("Hai perso");
                                result.setText("Hai Perso");
                                rigioca.setVisible(true);
                                disableAllButton();
                            }else {
                                //Verifico se ci sono mosse disponibili
                                mosseDisponibili=ticTacToe.getMosseLibere();
                                if(mosseDisponibili.size()==0) {
                                    System.out.println("Hai Pareggiato");
                                    result.setText("Hai Pareggiato");
                                    rigioca.setVisible(true);
                                    disableAllButton();
                                }
                            }
                        }else{
                            System.out.println("Pareggio");
                            result.setText("Hai Pareggiato");
                            rigioca.setVisible(true);
                            disableAllButton();
                        }
                    }else{
                        System.out.println("Il Giocatore ha vinto");
                        result.setText("Hai vinto");
                        rigioca.setVisible(true);
                        disableAllButton();
                    }

                }
            });
        }

    }

    public void clearTable(){
        ticTacToe.clearTable();
        ticTacToe.printMatrix();
        for(JButton button:new JButton[]{this.b1, this.b2, this.b3, this.b4, this.b5, this.b6, this.b7, this.b8, this.b9}){
            button.setIcon(null);
            button.setEnabled(true);
        }
        System.out.print("Button Clear");
    }


    private void setImage(JButton button,String symbol){
        ImageIcon imageIcon;
        Image newimg;
        if(symbol.equals("X")){
            imageIcon=new ImageIcon("src/main/resources/X.png"); // load the image to a imageIcon
            Image image = imageIcon.getImage(); // transform it
            newimg = image.getScaledInstance(70, 70,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        }else{
            imageIcon=new ImageIcon("src/main/resources/O.png"); // load the image to a imageIcon
            Image image = imageIcon.getImage(); // transform it
            newimg = image.getScaledInstance(110, 110,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        }
        imageIcon = new ImageIcon(newimg);  // transform it back
        button.setIcon(imageIcon);
    }

    private void setMove(JButton button, String symbol,SerialComunication serialComunication){
        //Avendo cliccato il bottone gli assegno l'icona O e lo disabilito
        setImage(button,symbol);
        button.setDisabledIcon(button.getIcon());
        button.setEnabled(false);
        //Setto la mossa anche sul object TicTacToe e la inivio ad arduino
        ticTacToe.setMove(Integer.valueOf(button.getName()),symbol);
        try {
            serialComunication.sendMessage("Tris:"+button.getName());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private JButton getButtonByName(String name){
        for(JButton button: new JButton[]{this.b1, this.b2, this.b3, this.b4, this.b5, this.b6, this.b7, this.b8, this.b9}){
            if(button.getName().equals(name))
                return button;
        }
        return null;
    }

    private void disableAllButton(){
        for(JButton button: new JButton[]{this.b1, this.b2, this.b3, this.b4, this.b5, this.b6, this.b7, this.b8, this.b9}) {
            button.setEnabled(false);
        }
    }

}
