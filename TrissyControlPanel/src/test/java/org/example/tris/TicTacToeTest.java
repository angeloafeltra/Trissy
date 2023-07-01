package org.example.tris;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTest {


    @org.junit.jupiter.api.Test
    void setMove() {
        TicTacToe board=new TicTacToe();
        for(int i=0;i<9;i++){
            board.setMove(i,'X');
        }

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                assertEquals('X',
                                board.getMatrix()[i][j],
                                "Inserimento in posizione ("+i+","+j+") non riuscito");
            }
        }
    }

    @org.junit.jupiter.api.Test
    void clearTable() {
        TicTacToe board=new TicTacToe();
        for(int i=0;i<9;i++){
            board.setMove(i,'X');
        }

        board.clearTable();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                assertEquals(' ',
                        board.getMatrix()[i][j],
                        "Cella ("+i+","+j+") Non Puita");
            }
        }
    }

    @org.junit.jupiter.api.Test
    void playerWinner() {

        int [][] combinazioni={ {0,1,2},
                                {3,4,5},
                                {6,7,8},
                                {0,3,6},
                                {1,4,7},
                                {2,5,8},
                                {0,4,8},
                                {2,4,6}};

        TicTacToe board=new TicTacToe();
        for (int i=0;i<8;i++){
            board.setMove(combinazioni[i][0],'O');
            board.setMove(combinazioni[i][1],'O');
            board.setMove(combinazioni[i][2],'O');
            assertEquals(true,board.playerWinner(),"Combinazione vincente ("+
                    combinazioni[i][0]+","+combinazioni[i][1]+","+combinazioni[i][2]+") non rilevata");
            board.clearTable();
        }


        board.setMove(0,'O');
        board.setMove(3,'O');
        board.setMove(5,'O');
        assertEquals(false,board.playerWinner());
    }

    @org.junit.jupiter.api.Test
    void robotWinner() {
        int [][] combinazioni={ {0,1,2},
                {3,4,5},
                {6,7,8},
                {0,3,6},
                {1,4,7},
                {2,5,8},
                {0,4,8},
                {2,4,6}};

        TicTacToe board=new TicTacToe();
        for (int i=0;i<8;i++){
            board.setMove(combinazioni[i][0],'X');
            board.setMove(combinazioni[i][1],'X');
            board.setMove(combinazioni[i][2],'X');
            assertEquals(true,board.robotWinner(),"Combinazione vincente ("+
                    combinazioni[i][0]+","+combinazioni[i][1]+","+combinazioni[i][2]+") non rilevata");
            board.clearTable();
        }


        board.setMove(0,'X');
        board.setMove(3,'X');
        board.setMove(5,'X');
        assertEquals(false,board.playerWinner());
    }

    @org.junit.jupiter.api.Test
    void isDraw() {
        TicTacToe board=new TicTacToe();
        board.setMove(0,'X');
        board.setMove(1,'X');
        board.setMove(2,'X');
        assertEquals(false,board.isDraw(),"Mossa vincente robot non rilevata");
        board.setMove(0,'O');
        board.setMove(1,'O');
        board.setMove(2,'O');
        assertEquals(false,board.isDraw(),"Mossa vincente player non rilevata");
        board.setMove(0,'X');
        board.setMove(1,'O');
        board.setMove(2,'X');
        board.setMove(3,'O');
        board.setMove(4,'X');
        board.setMove(5,'O');
        board.setMove(6,'O');
        board.setMove(7,'X');
        board.setMove(8,'O');
        assertEquals(true,board.isDraw(),"Draw non rilevto");

    }

}