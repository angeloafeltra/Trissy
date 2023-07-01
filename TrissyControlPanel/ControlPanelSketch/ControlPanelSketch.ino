
#include <Servo.h>
Servo base;
Servo braccio;
Servo avambraccio;

const int startPosBase=90;
const int startPosBraccio=80;
const int startPosAvambraccio=60;


const int rangeBase[2]={0,180};
const int rangeAvambraccio[2]={60,180};
const int rangeBraccio[2]={80,0};
const int pinMagnete=9;

char tavolo[3][3]={
  {' ',' ',' '},
  {' ',' ',' '},
  {' ',' ',' '}
};




const int s0=10;
const int s1=11;
const int s2=12;

//Prototipi
String readMessage();
void sendMessage(String msg);
void moveBase(int angle,int time);
void moveBraccio(int angle,int time);
void moveAvambraccio(int angle,int time);
int detectMossa(char tavolo[][3]);
int equalsMatrix(char matrix1[][3],char matrix2[][3]);
void updateTavolo(char tavolo[][3],int mossa,char value);
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
void moveArm(int endB, int endAva, int time);
void getToken(int token[], int time);
void setToken(int token[], int time);
bool isWinner(char tavolo[][3],char value);
bool isDraw(char tavolo[][3]);
void clearTris(char table[][3],int progressivoPedine);
void sendMossaPlayer(char tavolo[][3]);
void getMossaRobot(char tavolo[][3], int progressivoPedina);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  base.attach(5);
  braccio.attach(3);
  avambraccio.attach(6);
  pinMode(pinMagnete,OUTPUT);

  delay(100);

  base.write(startPosBase);
  braccio.write(startPosBraccio);
  avambraccio.write(startPosAvambraccio);

  delay(100);

  pinMode(s0, OUTPUT); 
  pinMode(s1, OUTPUT); 
  pinMode(s2, OUTPUT); 
  delay(100);
  digitalWrite(s0, LOW);
  digitalWrite(s1, LOW);
  digitalWrite(s2, LOW);
  delay(100);


}


String incomingByte = "";
int progressivoPedina=0;
int mossa=-1;

void loop() {
  
  incomingByte=readMessage();
  
  if(incomingByte!=""){
    if(incomingByte=="RichiedoConnessione") sendMessage("OK");
    else if(1000<=incomingByte.toInt() && incomingByte.toInt()<2000) moveBase(incomingByte.toInt()-1000,20); 
    else if(2000<=incomingByte.toInt() && incomingByte.toInt()<3000) moveBraccio(incomingByte.toInt()-2000,20); 
    else if(3000<=incomingByte.toInt() && incomingByte.toInt()<4000) moveAvambraccio(incomingByte.toInt()-3000,20); 
    else if(incomingByte=="011") digitalWrite(pinMagnete,HIGH);
    else if(incomingByte=="010") digitalWrite(pinMagnete,LOW);
    else if(incomingByte=="000") clearTris(tavolo,progressivoPedina);
    else if(incomingByte=="111") sendMossaPlayer(tavolo);
    else if(0<=incomingByte.toInt() && incomingByte.toInt()<=8) getMossaRobot(tavolo, progressivoPedina);
  }


  incomingByte="";
}

String readMessage(){
  String incomingByte="";
  while(Serial.available()){
      incomingByte=Serial.readStringUntil('\n');
  }
  return incomingByte;
}

void sendMessage(String msg){
  Serial.print(msg);
}

void moveBase(int angle,int time){
  int currentPosition=base.read();
  if(currentPosition!=angle){
    for(int i=currentPosition;i!=angle;){
        if(i<angle)
          i=i+1;
        else
          i=i-1;
        base.write(i);
        delay(time);
    }
  }
}

void moveBraccio(int angle,int time){
  int currentPosition=braccio.read();
  if(currentPosition!=angle){
    for(int i=currentPosition;i!=angle;){
        if(i<angle)
          i=i+1;
        else
          i=i-1;
        braccio.write(i);
        delay(time);
    }
  }
}

void moveAvambraccio(int angle,int time){
  int currentPosition=avambraccio.read();
  if(currentPosition!=angle){
    for(int i=currentPosition;i!=angle;){
        if(i<angle)
          i=i+1;
        else
          i=i-1;
        avambraccio.write(i);
        delay(time);
    }
  }
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

void setToken(int token[], int time){
  int area=token[3];
  moveBase(token[0],time);
  if(area==0){
    moveArm(40,token[2],time);
    moveArm(token[1],token[2],time);
  }
  if(area==1){
    moveArm(30,token[2],time);
    moveArm(token[1],token[2],time);
  }
  if(area==2){
    moveArm(20,token[2],time);
    moveArm(token[1],token[2],time);
  }
  
  delay(100);
  digitalWrite(pinMagnete,LOW);
  delay(100);
  moveArm(startPosBraccio,startPosAvambraccio,time);
  moveBase(startPosBase,time);

}

void getToken(int token[], int time){

  moveBase(token[0],time);
  moveArm(token[1],token[2],time);
  delay(100);
  digitalWrite(pinMagnete,HIGH);
  delay(100);
  moveArm(startPosBraccio,startPosAvambraccio,time);
  moveBase(startPosBase,time);

}

void moveArm(int endB, int endAva, int time){

    int startB=braccio.read();
    int startAva=avambraccio.read();
  
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

  getToken(posPedineX[progressivoPedina],time);
  delay(100);
  setToken(trisSlot[posizione],time);

  return;
}

void clearTris(char table[][3],int progressivoPedine){
  for (int i=0;i<3;i++)
    for (int j=0;j<3;j++)
      table[i][j]=' ';
  
  progressivoPedine=0;
}

void sendMossaPlayer(char tavolo[][3]){
  int mossa=-1;
  while(mossa==-1){
    delay(500);
    mossa=detectMossa(tavolo);
  }
  updateTavolo(tavolo,mossa,'O');
  sendMessage(String(mossa));
}

void getMossaRobot(char tavolo[][3], int progressivoPedina){
  doMossaRobot(incomingByte.toInt(), progressivoPedina);
  progressivoPedina=progressivoPedina+1;
  updateTavolo(tavolo,incomingByte.toInt(),'X');
  sendMessage("1");

  delay(100);
  if(!isDraw(tavolo) && !isWinner(tavolo,'X')){
    sendMossaPlayer(tavolo);
  }

}








