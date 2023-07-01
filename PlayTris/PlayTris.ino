#include <Servo.h>
Servo base;
Servo braccio;
Servo avambraccio;


//Prototipi
bool isDraw(char tavolo[][3]);
bool isWinner(char tavolo[][3],char value);
int detectMossa(char tavolo[][3]);
int equalsMatrix(char matrix1[][3],char matrix2[][3]);
bool checkSlot0();
bool checkSlot1();
bool checkSlot2();
bool checkSlot3();
bool checkSlot4();
bool checkSlot5();
bool checkSlot6();
bool checkSlot7();
bool checkSlot8();
void updateTavolo(char tavolo1[][3],char tavolo2[][3],char value);
void moveBase(int start, int end, int time);
void moveArm(int startB,int endB, int startAva, int endAva, int time);
void getToken(int posBase, int posBraccio, int posAvambraccio,int token[], int time);
void setToken(int posBase, int posBraccio, int posAvambraccio,int token[], int time);
int generoMossa(char board[][3]);
void printTavolo(char tavolo[][3]);
bool tableIsClean();
int minimax(char board[][3], int depth, bool isMaximizingPlayer);
bool isMovesLeft(char board[][3]);
int evaluate(char b[][3]);


const int sPosBase=90;
const int sPosBraccio=80;
const int sPosAvambraccio=60;
const int pinMagnete=9;



char tavolo[3][3]={
  {' ',' ',' '},
  {' ',' ',' '},
  {' ',' ',' '}
};




const int s0=10;
const int s1=11;
const int s2=12;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  pinMode(s0, OUTPUT); 
  pinMode(s1, OUTPUT); 
  pinMode(s2, OUTPUT); 
  delay(100);
  digitalWrite(s0, LOW);
  digitalWrite(s1, LOW);
  digitalWrite(s2, LOW);
  delay(100);

  base.attach(5);
  braccio.attach(3);
  avambraccio.attach(6);
  pinMode(pinMagnete,OUTPUT);

  delay(100);

  base.write(sPosBase);
  braccio.write(sPosBraccio);
  avambraccio.write(sPosAvambraccio);

  randomSeed(42);
  delay(1000);
}


int turno=0;
int mossa=-1;
int progressivoPedina=0;

void loop() {
  // put your main code here, to run repeatedly:
  Serial.println(isWinner(tavolo,'X'));
  Serial.println(isWinner(tavolo,'O'));
  Serial.println(isDraw(tavolo));

  while(!isWinner(tavolo,'X') && !isWinner(tavolo,'O') && !isDraw(tavolo)){
    if(turno==0){
      while(mossa==-1){
        Serial.println("In attesa della mossa del giocatore");
        delay(500);
        mossa=detectMossa(tavolo);
      }

      Serial.print("Pedina posizionata nella posizione: ");
      Serial.println(mossa);

      updateTavolo(tavolo,mossa,'O');
      printTavolo(tavolo);
      turno=1;
      mossa=-1;
      delay(1000);
    }else{
      //MOSSA ROBOT
      Serial.print("Genero la mossa del robot: ");
      mossa=generoMossa(tavolo);
      Serial.println(mossa);

      doMossaRobot(mossa, progressivoPedina);
      progressivoPedina=progressivoPedina+1;
      delay(500);
      updateTavolo(tavolo,mossa,'X');
      printTavolo(tavolo);
      turno=0;
      mossa=-1;
      delay(1000);
    }
  }

  if(isWinner(tavolo,'X')) Serial.println("Robot Winner");
  if(isWinner(tavolo,'O')) Serial.println("Player Winner");
  if(isDraw(tavolo)) Serial.println("Draw");

  delay(5000);
  while(!tableIsClean()){
    Serial.println("Pulisci il tavolo");
    delay(2000);
  }
  turno=0;
  mossa=-1;
  progressivoPedina=0;
  for(int i=0;i<3;i++)
    for(int j=0;j<3;j++)
      tavolo[i][j]=' ';

}




bool isDraw(char tavolo[][3]){

  for (int i=0;i<3;i++)
    for (int j=0;j<3;j++)
      if (tavolo[i][j]==' ') return false;

  return true;
}


bool isWinner(char tavolo[][3],char value){
  //Righe
  if(tavolo[0][0]==value && tavolo[0][1]==value && tavolo[0][2]==value) return true;
  if(tavolo[1][0]==value && tavolo[1][1]==value && tavolo[1][2]==value) return true;
  if(tavolo[2][0]==value && tavolo[2][1]==value && tavolo[2][2]==value) return true;
  //Colonne
  if(tavolo[0][0]==value && tavolo[1][0]==value && tavolo[2][0]==value) return true;
  if(tavolo[0][1]==value && tavolo[1][1]==value && tavolo[2][1]==value) return true;
  if(tavolo[0][2]==value && tavolo[1][2]==value && tavolo[2][2]==value) return true;
  //Diagonale
  if(tavolo[0][0]==value && tavolo[1][1]==value && tavolo[2][2]==value) return true;
  if(tavolo[0][2]==value && tavolo[1][1]==value && tavolo[2][0]==value) return true;

  return false;
}

int detectMossa(char tavolo[][3]){

  char matrix[3][3]={
    {' ',' ',' '},
    {' ',' ',' '},
    {' ',' ',' '}
  };

  if(checkSlot0()) matrix[0][0]='-';
  if(checkSlot1()) matrix[0][1]='-';
  if(checkSlot2()) matrix[0][2]='-';
  if(checkSlot3()) matrix[1][0]='-';
  if(checkSlot4()) matrix[1][1]='-';
  if(checkSlot5()) matrix[1][2]='-';
  if(checkSlot6()) matrix[2][0]='-';
  if(checkSlot7()) matrix[2][1]='-';
  if(checkSlot8()) matrix[2][2]='-';

  

  int isEquals=equalsMatrix(matrix,tavolo);

  return isEquals;
}



int equalsMatrix(char matrix1[][3],char matrix2[][3]){
  printTavolo(matrix1);
  printTavolo(matrix2);
  int pos=0;  
  for(int i=0;i<3;i++)
    for(int j=0;j<3;j++)
      if(matrix1[i][j]=='-' && matrix2[i][j]==' ') {return pos;} else {pos=pos+1;}

  return -1;
}

bool checkSlot0(){
  digitalWrite(s0,LOW);
  digitalWrite(s1,HIGH);
  digitalWrite(s2,HIGH);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  if(value>15) return false; else return true;
}

bool checkSlot1(){
  digitalWrite(s0,HIGH);
  digitalWrite(s1,LOW);
  digitalWrite(s2,HIGH);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  if(value>15) return false; else return true;

}
bool checkSlot2(){
  digitalWrite(s0,LOW);
  digitalWrite(s1,HIGH);
  digitalWrite(s2,LOW);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  if(value>15) return false; else return true;
}
bool checkSlot3(){
  int value=analogRead(A1);
  value=map(value,0,1024,0,200);
  if(value>15) return false; else return true;
}
bool checkSlot4(){
  digitalWrite(s0,LOW);
  digitalWrite(s1,LOW);
  digitalWrite(s2,HIGH);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  if(value>15) return false; else return true;
}
bool checkSlot5(){
  digitalWrite(s0,HIGH);
  digitalWrite(s1,LOW);
  digitalWrite(s2,LOW);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  if(value>15) return false; else return true;
}

bool checkSlot6(){
  digitalWrite(s0,HIGH);
  digitalWrite(s1,HIGH);
  digitalWrite(s2,HIGH);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  if(value>15) return false; else return true;
}
bool checkSlot7(){
  digitalWrite(s0,HIGH);
  digitalWrite(s1,HIGH);
  digitalWrite(s2,LOW);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  if(value>15) return false; else return true;
}
bool checkSlot8(){
  digitalWrite(s0,LOW);
  digitalWrite(s1,LOW);
  digitalWrite(s2,LOW);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  if(value>15) return false; else return true;
}

void updateTavolo(char tavolo[][3],int mossa,char value){
  switch(mossa){
        case 0:
          tavolo[0][0]=value;
          break;
        case 1:
          tavolo[0][1]=value;
          break;
        case 2:
          tavolo[0][2]=value;
          break;
        case 3:
          tavolo[1][0]=value;
          break;
        case 4:
          tavolo[1][1]=value;
          break;
        case 5:
          tavolo[1][2]=value;
          break;
        case 6:
          tavolo[2][0]=value;
          break;
        case 7:
          tavolo[2][1]=value;
          break;
        case 8:
          tavolo[2][2]=value;
          break;
      }

}

void setToken(int posBase, int posBraccio, int posAvambraccio,int token[], int time){
  int area=token[3];
  moveBase(posBase,token[0],time);
  if(area==0){
    moveArm(posBraccio,40,posAvambraccio,token[2],time);
    moveArm(40,token[1],token[2],token[2],time);
  }
  if(area==1){
    moveArm(posBraccio,30,posAvambraccio,token[2],time);
    moveArm(30,token[1],token[2],token[2],time);
  }
  if(area==2){
    moveArm(posBraccio,20,posAvambraccio,token[2],time);
    moveArm(20,token[1],token[2],token[2],time);
  }
  
  delay(100);
  digitalWrite(pinMagnete,LOW);
  delay(100);
  moveArm(token[1],posBraccio,token[2],posAvambraccio,time);
  moveBase(token[0],posBase,time);

}

void getToken(int posBase, int posBraccio, int posAvambraccio,int token[], int time){

  moveBase(posBase,token[0],time);
  moveArm(posBraccio,token[1],posAvambraccio,token[2],time);
  delay(100);
  digitalWrite(pinMagnete,HIGH);
  delay(100);
  moveArm(token[1],posBraccio,token[2],posAvambraccio,time);
  moveBase(token[0],posBase,time);

}



void moveArm(int startB,int endB, int startAva, int endAva, int time){

    if(startB!=endB || startAva!=endAva){
      for(int i=startB, j=startAva; i!=endB || j!=endAva;){
        if(i!=endB){
          if(i<endB)
            i=i+1;
          else
            i=i-1;
        }

        if(j!=endAva){
          if(j<endAva)
            j=j+1;
          else
            j=j-1;
        }

        braccio.write(i);
        avambraccio.write(j);
        delay(time);
      }
    }
}

void moveBase(int start, int end, int time){

  if(start!=end){
    for(int i=start;i!=end;){
        if(i<end)
          i=i+1;
        else
          i=i-1;
        base.write(i);
        delay(time);
    }
  }

}

void doMossaRobot(int posizione, int progressivoPedina){
  

  int posPedineX[5][3]={
  {137,30,133},
  {137,27,135},
  {137,24,138},
  {137,21,142},
  {137,17,145}
  };

  int trisSlot[9][4]{
    {99,5,120,2},
    {81,7,125,2},
    {60,5,120,2},
    {105,12,140,1},
    {81,15,145,1},
    {53,16,140,1},
    {116,18,165,0},
    {80,15,175,0},
    {42,18,165,0}
  };

  int time=30;

  getToken(sPosBase,sPosBraccio,sPosAvambraccio,posPedineX[progressivoPedina],time);
  delay(100);
  setToken(sPosBase,sPosBraccio,sPosAvambraccio,trisSlot[posizione],time);

  return;
}

int generoMossa(char board[][3]){

  int bestMoveVal =-1000;
  int bestMove[2]={-1,-1};

  for(int row=0;row<3;row++){
    for(int col=0;col<3;col++){
      if(board[row][col]==' '){
        board[row][col]='X';
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

  int converter[][3]={ {0,0,0},
                      {0,1,1},
                      {0,2,2},
                      {1,0,3},
                      {1,1,4},
                      {1,2,5},
                      {2,0,6},
                      {2,1,7},
                      {2,2,8}
  };

  for(int i=0;i<9;i++){
      if(bestMove[0]==converter[i][0] && bestMove[1]==converter[i][1]) return converter[i][2];
  }

  return -1;
}


int minimax(char board[][3], int depth, bool isMaximizingPlayer){

  int score = evaluate(board);
  if(score==10 || score==-10) return score; //Con 10 il player vince con -10 il player perde
  if(!isMovesLeft(board)) return 0; //Pareggio

  if (isMaximizingPlayer){
    int best=-1000;
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        if(board[i][j]==' '){
          board[i][j]='X';
          score=minimax(board,depth+1,!isMaximizingPlayer);
          if(score>best)
            best=score;
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
          board[i][j]='O';
          score=minimax(board,depth+1,!isMaximizingPlayer);
          if(score<best)
            best=score;
          board[i][j]=' ';
        }
      }
    }
    return  best;
  }
}


int evaluate(char b[][3]){

  // Verifico se ci sono righe con X o O vincitrici
  for (int row = 0; row < 3; row++)
  {
    if (b[row][0] == b[row][1] && b[row][1] == b[row][2])
    {
      if (b[row][0] == 'X')
        return +10;
      else if (b[row][0] == 'O')
        return -10;
    }
  }

    // Verifico se ci sono colonne con X o O vincenti
  for (int col = 0; col < 3; col++)
  {
    if (b[0][col] == b[1][col] && b[1][col] == b[2][col])
    {
      if (b[0][col] == 'X')
        return +10;
      else if (b[0][col] == 'O')
        return -10;
    }
  }

  // Verifico se ci sono diagonali vincenti con X o O
  if (b[0][0] == b[1][1] && b[1][1] == b[2][2])
  {
    if (b[0][0] == 'X')
      return +10;
    else if (b[0][0] == 'O')
      return -10;
  }

  if (b[0][2] == b[1][1] && b[1][1] == b[2][0])
  {
    if (b[0][2] == 'X')
      return +10;
    else if (b[0][2] == 'O')
      return -10;
  }

  // Pareggio
  return 0;

}

bool isMovesLeft(char board[][3])
{
  for (int i = 0; i < 3; i++)
    for (int j = 0; j < 3; j++)
      if (board[i][j] == ' ')
        return true;
  return false;
}

void printTavolo(char tavolo[][3]){

  Serial.println("Tavolo :");
  for(int i=0;i<3;i++){
      Serial.print(tavolo[i][0]);
      Serial.print(" | ");
      Serial.print(tavolo[i][1]);
      Serial.print(" | ");
      Serial.println(tavolo[i][2]);
  }
  
}

bool tableIsClean(){
  if(checkSlot0()) return false;
  if(checkSlot1()) return false;
  if(checkSlot2()) return false;
  if(checkSlot3()) return false;
  if(checkSlot4()) return false;
  if(checkSlot5()) return false;
  if(checkSlot6()) return false;
  if(checkSlot7()) return false;
  if(checkSlot8()) return false;
  return true;
}


