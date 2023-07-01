package org.example.tris;



public class MinMaxAlgorithm {
    private static char player = 'X';
    private static char opponent = 'O';
    public MinMaxAlgorithm(){
        //Costruttore
    }

    public int[] findBestMove(char[][] board) {
        int bestMoveVal =-1000;
        int[] bestMove={-1,-1};

        for(int row=0;row<3;row++){
            for(int col=0;col<3;col++){
                if(board[row][col]==' '){
                    board[row][col]=player;
                    int moveVal = minimax(board, 0, false);
                    board[row][col]=' ';
                    if(moveVal>bestMoveVal){
                        bestMoveVal=moveVal;
                        bestMove[0]=row;
                        bestMove[1]=col;
                    }
                }

            }
        }

        return bestMove;
    }



    private int minimax(char[][] board, int depth, boolean isMaximizingPlayer){

        int score = evaluate(board);
        if(score==10 || score==-10) return score; //Con 10 il player vince con -10 il player perde
        if(Boolean.FALSE.equals(isMovesLeft(board))) return 0; //Pareggio


        if (isMaximizingPlayer){
            int best=-1000;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(board[i][j]==' '){
                        board[i][j]=player;
                        best=Math.max(best,minimax(board,depth+1,!isMaximizingPlayer));
                        board[i][j]=' ';
                    }
                }
            }
            return best;
        }else{
            int best=1000;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(board[i][j]==' '){
                        board[i][j]=opponent;
                        best=Math.min(best,minimax(board,depth+1,!isMaximizingPlayer));
                        board[i][j]=' ';
                    }
                }
            }
            return  best;
        }
    }


    private int evaluate(char[][] b){

        // Verifico se ci sono righe con X o O vincitrici
        for (int row = 0; row < 3; row++)
        {
            if (b[row][0] == b[row][1] && b[row][1] == b[row][2])
            {
                if (b[row][0] == player)
                    return +10;
                else if (b[row][0] == opponent)
                    return -10;
            }
        }

        // Verifico se ci sono colonne con X o O vincenti
        for (int col = 0; col < 3; col++)
        {
            if (b[0][col] == b[1][col] && b[1][col] == b[2][col])
            {
                if (b[0][col] == player)
                    return +10;
                else if (b[0][col] == opponent)
                    return -10;
            }
        }

        // Verifico se ci sono diagonali vincenti con X o O
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2])
        {
            if (b[0][0] == player)
                return +10;
            else if (b[0][0] == opponent)
                return -10;
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0])
        {
            if (b[0][2] == player)
                return +10;
            else if (b[0][2] == opponent)
                return -10;
        }

        // Pareggio
        return 0;

    }

    private Boolean isMovesLeft(char[][] board)
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ')
                    return true;
        return false;
    }

}
