package org.example.tris;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinMaxAlgorithmTest {

    @Test
    void findBestMove() {
        TicTacToe board=new TicTacToe();
        board.setMove(0,'X');
        board.setMove(1,'O');
        board.setMove(2,'X');
        board.setMove(3,'O');
        board.setMove(4,'O');
        board.setMove(5,'X');
        MinMaxAlgorithm minmax=new MinMaxAlgorithm();
        int bestMove= board.convertMossa(minmax.findBestMove(board.getMatrix()));
        assertEquals(8,bestMove);
    }


}