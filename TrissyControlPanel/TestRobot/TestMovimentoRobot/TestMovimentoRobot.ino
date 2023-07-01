#include <Servo.h>
Servo base;
Servo braccio;
Servo avambraccio;

const int sPosBase=90;
const int sPosBraccio=80;
const int sPosAvambraccio=60;
const int pinMagnete=9;


int posPedineX[5][3]={
  {137,30,133},
  {137,27,135},
  {137,24,138},
  {137,21,142},
  {137,17,145}
};


int trisSlot[9][4]{
  {42,18,165,0},
  {80,15,175,0},
  {116,18,165,0},
  {53,16,140,1},
  {81,15,145,1},
  {105,12,140,1},
  {60,5,120,2},
  {81,7,125,2},
  {99,5,120,2}
};


//Prototipi
void moveBase(int start, int end, int time);
void moveArm(int startB,int endB, int startAva, int endAva, int time);
void getToken(int posBase, int posBraccio, int posAvambraccio,int token[], int time);
void setToken(int posBase, int posBraccio, int posAvambraccio,int token[], int time);
bool check_slot(long int slot_occupati,int num_slot_occupati,long int i);



void setup() {
  // put your setup code here, to run once:
  // put your setup code here, to run once:
  Serial.begin(9600);

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


const int time=20;
long slot_occupati[9];
int num_slot_occupati=0;
long randNumber;


void loop() {
  for(int i=0;i<=4;i++){
    getToken(sPosBase,sPosBraccio,sPosAvambraccio,posPedineX[i],time);

    do{
    randNumber = random(9);
    }while(!check_slot(slot_occupati,num_slot_occupati,randNumber));

    slot_occupati[num_slot_occupati]=randNumber;
    num_slot_occupati++;

    setToken(sPosBase,sPosBraccio,sPosAvambraccio,trisSlot[randNumber],time);
    delay(1000);
  }
  
}


bool check_slot(long int slot_occupati[],int num_slot_occupati,long int i){

    for (int j=0;j<num_slot_occupati;j++){
      if (slot_occupati[j]==i)
        return false;
    }
  return true;
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
