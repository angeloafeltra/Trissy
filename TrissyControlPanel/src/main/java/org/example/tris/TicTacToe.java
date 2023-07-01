package org.example.tris;


import org.example.comunication.SerialComunication;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicTacToe {

    private char[][] matrix;
    private Logger logger;

    public TicTacToe() {
        this.matrix = new char[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
        logger = Logger.getLogger(SerialComunication.class.getName());
    }

    public char[][] getMatrix() {
        return this.matrix;
    }

    public void setMove(int n, char symbol) {

        switch (n) {
            case 0:
                matrix[0][0] = symbol;
                break;
            case 1:
                matrix[0][1] = symbol;
                break;
            case 2:
                matrix[0][2] = symbol;
                break;
            case 3:
                matrix[1][0] = symbol;
                break;
            case 4:
                matrix[1][1] = symbol;
                break;
            case 5:
                matrix[1][2] = symbol;
                break;
            case 6:
                matrix[2][0] = symbol;
                break;
            case 7:
                matrix[2][1] = symbol;
                break;
            case 8:
                matrix[2][2] = symbol;
                break;
            default:
        }

    }

    public void clearTable() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                this.matrix[i][j] = ' ';
    }

    public boolean playerWinner() {

        int[][][] combinazioni = {{{0, 0}, {0, 1}, {0, 2}},
                {{1, 0}, {1, 1}, {1, 2}},
                {{2, 0}, {2, 1}, {2, 2}},
                {{0, 0}, {1, 0}, {2, 0}},
                {{0, 1}, {1, 1}, {2, 1}},
                {{0, 2}, {1, 2}, {2, 2}},
                {{0, 0}, {1, 1}, {2, 2}},
                {{0, 2}, {1, 1}, {2, 0}}
        };

        for (int i = 0; i < 8; i++) {
            int[][] combinazione = combinazioni[i];
            if (this.matrix[combinazione[0][0]][combinazione[0][1]] == 'O' &&
                    this.matrix[combinazione[1][0]][combinazione[1][1]] == 'O' &&
                    this.matrix[combinazione[2][0]][combinazione[2][1]] == 'O')
                return true;
        }
        return false;
    }

    public boolean robotWinner() {

        int[][][] combinazioni = {{{0, 0}, {0, 1}, {0, 2}},
                {{1, 0}, {1, 1}, {1, 2}},
                {{2, 0}, {2, 1}, {2, 2}},
                {{0, 0}, {1, 0}, {2, 0}},
                {{0, 1}, {1, 1}, {2, 1}},
                {{0, 2}, {1, 2}, {2, 2}},
                {{0, 0}, {1, 1}, {2, 2}},
                {{0, 2}, {1, 1}, {2, 0}}
        };

        for (int i = 0; i < 8; i++) {
            int[][] combinazione = combinazioni[i];
            if (this.matrix[combinazione[0][0]][combinazione[0][1]] == 'X' &&
                    this.matrix[combinazione[1][0]][combinazione[1][1]] == 'X' &&
                    this.matrix[combinazione[2][0]][combinazione[2][1]] == 'X')
                return true;
        }
        return false;


    }

    public boolean isDraw() {

        if (!this.playerWinner() && !this.robotWinner()) {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (this.matrix[i][j] == ' ') return false;
            return true;
        } else {
            return false;
        }

    }

    public int convertMossa(int[] pos) {
        int[][] converter = {{0, 0, 0},
                {0, 1, 1},
                {0, 2, 2},
                {1, 0, 3},
                {1, 1, 4},
                {1, 2, 5},
                {2, 0, 6},
                {2, 1, 7},
                {2, 2, 8}
        };

        for (int[] combo : converter) {
            if (pos[0] == combo[0] && pos[1] == combo[1]) return combo[2];
        }

        return -1;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Tavolo: \n");
        for (int i = 0; i < 3; i++) {
            str.append(this.matrix[i][0]);
            str.append(" | ");
            str.append(this.matrix[i][1]);
            str.append(" | ");
            str.append(this.matrix[i][2]);
            str.append("\n");
        }

        return str.toString();
    }


    public String play(SerialComunication serialComunication, MinMaxAlgorithm minMaxAlgorithm) throws IOException, InterruptedException {

        serialComunication.sendMessage("Tris Mode"); //Invio un messaggio ad arduino per iniziare la partita a tris
        String mossaPlayer;
        String response;
        int mossaRobot;

        while (!this.isDraw() && !this.robotWinner() && !this.playerWinner()) {
            this.logger.log(Level.INFO, "Attendo la mossa del giocatore");
            do {
                mossaPlayer = serialComunication.readResponse();
            } while (mossaPlayer == null); //Ricevo la mossa eseguita dal giocatore
            this.logger.log(Level.INFO, "Mossa eseguita dal player: {0}", mossaPlayer);


            this.setMove(Integer.parseInt(mossaPlayer), 'O');

            if(this.isDraw() && !this.robotWinner() && !this.playerWinner()) break;//Verifico se si vince o pareggia

            mossaRobot = convertMossa(minMaxAlgorithm.findBestMove(this.getMatrix())); //Genero la mossa del robot

            this.setMove(mossaRobot, 'X');
            serialComunication.sendMessage("Tris:" + mossaRobot); //Invio la mossa del robot ad arduino

            Thread.sleep(2000);
            this.logger.log(Level.INFO, "Attendo che il robot esegue la mossa");
            do {
                response = serialComunication.readResponse();
                if(response==null)
                    response="";
            } while (!response.equals("1"));//Attendo che il robot ha eseguito la mossa

            this.logger.log(Level.INFO, "Mossa eseguita dal robot");
        }

        serialComunication.sendMessage("Clear");


        if (this.isDraw()) {
            this.logger.log(Level.INFO, "Pareggio");
            this.clearTable();
            return "Pareggio";
        }
        if (this.robotWinner()) {
            this.logger.log(Level.INFO, "Hai Perso, il robot ha vinto");
            this.clearTable();
            return "Hai Perso";
        }
        if (this.playerWinner()) {
            this.logger.log(Level.INFO, "Hai vinto, il robot ha perso");
            this.clearTable();
            return "Hai Vinto";
        }

        return "";
    }

}



