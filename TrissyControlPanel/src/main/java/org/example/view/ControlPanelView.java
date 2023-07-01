package org.example.view;

import org.example.audio.AudioPlayer;
import org.example.comunication.SerialComunication;
import org.example.tris.MinMaxAlgorithm;
import org.example.tris.TicTacToe;
import org.example.view.component.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControlPanelView {

    private JFrame frame;
    private ConnectionPanel connectPanel;
    private ManualModeView manualModeView;
    private SerialComunication serialComunication;
    private TicTacToe ticTacToe;
    private MinMaxAlgorithm minMaxAlgorithm;
    private JPanel trisPanel;
    private JButton play;
    private JLabel label;
    private AudioPlayer audioPlayer;


    public ControlPanelView(){
        this.frame=new JFrame();
        this.serialComunication=new SerialComunication();
        this.connectPanel=new ConnectionPanel(this.serialComunication.getListPort());
        this.manualModeView=new ManualModeView(serialComunication);
        this.connectPanel.setActionEvente(serialComunication);
        this.genereteView();
        this.ticTacToe=new TicTacToe();
        this.minMaxAlgorithm=new MinMaxAlgorithm();
        this.audioPlayer=new AudioPlayer();
        setActionEvent();
    }


    private void genereteView(){
        frame.setTitle("Trissy ControlPanel");
        frame.setLocationRelativeTo(null);
        frame.setSize(630,680);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));

        frame.add(connectPanel.getPanel());

        this.trisPanel=new JPanel();
        this.trisPanel.setLayout(new BoxLayout(this.trisPanel,BoxLayout.X_AXIS));
        this.trisPanel.setPreferredSize(new Dimension(550, 30));
        this.trisPanel.setMaximumSize(new Dimension(550, 30));

        this.play=new JButton();
        this.play.setBorderPainted(false);
        this.play.setContentAreaFilled(false);
        this.play.setFocusPainted(false);
        this.play.setOpaque(false);
        this.play.setPreferredSize(new Dimension(30, 30));
        this.play.setMaximumSize(new Dimension(30, 30));
        setImage();

        this.label=new JLabel("Play Tris");

        this.trisPanel.add(label);
        this.trisPanel.add(play);


        frame.add(this.trisPanel);
        frame.add(manualModeView.getPanel());
        frame.setVisible(false);
    }

    public void showView(){
        frame.setVisible(true);
    }


    private void setImage(){
        ImageIcon imageIcon = new ImageIcon("src/main/resources/play.png");
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(30, 30,  Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back
        this.play.setIcon(imageIcon);
    }


    private void setActionEvent(){
        this.play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    audioPlayer.play("src/main/resources/audio/info.wav");
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
                int result=JOptionPane.showConfirmDialog(frame,"Prima di iniziare a giocare assicurati che il tavolo da gioco\n" +
                        "sia pulito è tutte le pedine siano al loro posto.\n" +
                        "\n Premi Yes per iniziare la partite ed esegui la tua mossa!!!");

                if(result==JOptionPane.OK_OPTION) {
                    audioPlayer.stop();
                    manualModeView.disable();
                    String resultTris;
                    try {
                        resultTris=ticTacToe.play(serialComunication,minMaxAlgorithm);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(ex);
                    }

                    if(resultTris.equals("Pareggio")) {
                        try {
                            audioPlayer.play("src/main/resources/audio/pareggio.wav");
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if(resultTris.equals("Hai Perso")) {
                        try {
                            audioPlayer.play("src/main/resources/audio/perso.wav");
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if(resultTris.equals("Hai Vinto")) {
                        try {
                            audioPlayer.play("src/main/resources/audio/vittoria.wav");
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    
                    JOptionPane.showMessageDialog(frame, resultTris);
                    manualModeView.enable();
                }


                refreshFrame();
            }
        });
    }

    private void refreshFrame(){
        frame.invalidate();
        frame.validate();
        frame.repaint();
    }

}
