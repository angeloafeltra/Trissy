package org.example.tris;

import java.util.ArrayList;

public class TicTacToe {

    private int[][] matrix;

    public TicTacToe(){
        this.matrix = new int[][] {
                { -1, -1, -1},
                { -1, -1, -1},
                { -1, -1, -1}
        };
    }

    public int[][] getMatrix(){return this.matrix;}

    public void setMove(int n,String symbol){
        int value=-1;
        if(symbol.equals("O"))
            value=0;
        if(symbol.equals("X"))
            value=1;

        switch (n){
            case 0:
                matrix[0][0]=value;
                break;
            case 1:
                matrix[0][1]=value;
                break;
            case 2:
                matrix[0][2]=value;
                break;
            case 3:
                matrix[1][0]=value;
                break;
            case 4:
                matrix[1][1]=value;
                break;
            case 5:
                matrix[1][2]=value;
                break;
            case 6:
                matrix[2][0]=value;
                break;
            case 7:
                matrix[2][1]=value;
                break;
            case 8:
                matrix[2][2]=value;
                break;
        }

    }

    public void clearTable(){
        this.matrix = new int[][] {
                { -1, -1, -1},
                { -1, -1, -1},
                { -1, -1, -1}
        };
    }

    public boolean checkWinner(){

        for (int i=0;i<=1;i++){
            //Righe
            if(matrix[0][0]==i && matrix[0][1]==i && matrix[0][2]==i)
                return true;
            if(matrix[1][0]==i && matrix[1][1]==i && matrix[1][2]==i)
                return true;
            if(matrix[2][0]==i && matrix[2][1]==i && matrix[2][2]==i)
                return true;
            //Colonne
            if(matrix[0][0]==i && matrix[1][0]==i && matrix[2][0]==i)
                return true;
            if(matrix[0][1]==i && matrix[1][1]==i && matrix[2][i]==i)
                return true;
            if(matrix[0][2]==i && matrix[1][2]==i && matrix[2][2]==i)
                return true;
            //Diagonale
            if(matrix[0][0]==i && matrix[1][1]==i && matrix[2][2]==i)
                return true;
            if(matrix[0][2]==i && matrix[1][1]==i && matrix[2][0]==i)
                return true;
        }
        return false;
    }

    public void printMatrix(){
        for(int i=0;i<3;i++){
            System.out.print("| ");
            for(int j=0;j<3;j++){
                if(this.matrix[i][j]!=-1)
                    System.out.print(this.matrix[i][j]);
                else
                    System.out.print(" ");
                System.out.print(" | ");
            }
            System.out.println();
        }
    }

    public ArrayList<Integer> getMosseLibere(){
        ArrayList<Integer> mosseLibere=new ArrayList<>();
        if(matrix[0][0]==-1) mosseLibere.add(0);
        if(matrix[0][1]==-1) mosseLibere.add(1);
        if(matrix[0][2]==-1) mosseLibere.add(2);
        if(matrix[1][0]==-1) mosseLibere.add(3);
        if(matrix[1][1]==-1) mosseLibere.add(4);
        if(matrix[1][2]==-1) mosseLibere.add(5);
        if(matrix[2][0]==-1) mosseLibere.add(6);
        if(matrix[2][1]==-1) mosseLibere.add(7);
        if(matrix[2][2]==-1) mosseLibere.add(8);
        return mosseLibere;
    }

}
