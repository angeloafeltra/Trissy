const int s0=10;
const int s1=11;
const int s2=12;


//Prototipi
bool checkSlot0();
bool checkSlot1();
bool checkSlot2();
bool checkSlot3();
bool checkSlot4();
bool checkSlot5();
bool checkSlot6();
bool checkSlot7();
bool checkSlot8();


void setup() {
  // put your setup code here, to run once:
  // put your setup code here, to run once:
  Serial.begin(9600);

  delay(100);
  pinMode(s0, OUTPUT); 
  pinMode(s1, OUTPUT); 
  pinMode(s2, OUTPUT); 

  digitalWrite(s0, LOW);
  digitalWrite(s1, LOW);
  digitalWrite(s2, LOW);


  randomSeed(42);
  delay(1000);
}





void loop() {
  

  Serial.print("Slot 0 occupato?: ");
  if(checkSlot0()) Serial.println("SI"); else Serial.println("NO");
  delay(100);
  Serial.print("Slot 1 occupato?: ");
  if(checkSlot1()) Serial.println("SI"); else Serial.println("NO");
  delay(100);
  Serial.print("Slot 2 occupato?: ");
  if(checkSlot2()) Serial.println("SI"); else Serial.println("NO");
  delay(100);
  Serial.print("Slot 3 occupato?: ");
  if(checkSlot3()) Serial.println("SI"); else Serial.println("NO");
  delay(100);
  Serial.print("Slot 4 occupato?: ");
  if(checkSlot4()) Serial.println("SI"); else Serial.println("NO");
  delay(100);
  Serial.print("Slot 5 occupato?: ");
  if(checkSlot5()) Serial.println("SI"); else Serial.println("NO");
  delay(100);
  Serial.print("Slot 6 occupato?: ");
  if(checkSlot6()) Serial.println("SI"); else Serial.println("NO");
  delay(100);
  Serial.print("Slot 7 occupato?: ");
  if(checkSlot7()) Serial.println("SI"); else Serial.println("NO");
  delay(100);
  delay(100);
  Serial.print("Slot 8 occupato?: ");
  if(checkSlot8()) Serial.println("SI"); else Serial.println("NO");
  delay(100);


  Serial.println();
  delay(1000);
  



}

bool checkSlot0(){
  digitalWrite(s0,LOW);
  digitalWrite(s1,HIGH);
  digitalWrite(s2,HIGH);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  Serial.print(value);
  Serial.print(" ");
  if(value>10) return false; else return true;
}

bool checkSlot1(){
  digitalWrite(s0,HIGH);
  digitalWrite(s1,LOW);
  digitalWrite(s2,HIGH);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  Serial.print(value);
  Serial.print(" ");
  if(value>10) return false; else return true;

}
bool checkSlot2(){
  digitalWrite(s0,LOW);
  digitalWrite(s1,HIGH);
  digitalWrite(s2,LOW);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  Serial.print(value);
  Serial.print(" ");
  if(value>10) return false; else return true;
}
bool checkSlot3(){
  int value=analogRead(A1);
  value=map(value,0,1024,0,200);
  Serial.print(value);
  Serial.print(" ");
  if(value>10) return false; else return true;
}
bool checkSlot4(){
  digitalWrite(s0,LOW);
  digitalWrite(s1,LOW);
  digitalWrite(s2,HIGH);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  Serial.print(value);
  Serial.print(" ");
  if(value>10) return false; else return true;
}
bool checkSlot5(){
  digitalWrite(s0,HIGH);
  digitalWrite(s1,LOW);
  digitalWrite(s2,LOW);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  Serial.print(value);
  Serial.print(" ");
  if(value>10) return false; else return true;
}

bool checkSlot6(){
  digitalWrite(s0,HIGH);
  digitalWrite(s1,HIGH);
  digitalWrite(s2,HIGH);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  Serial.print(value);
  Serial.print(" ");
  if(value>10) return false; else return true;
}
bool checkSlot7(){
  digitalWrite(s0,HIGH);
  digitalWrite(s1,HIGH);
  digitalWrite(s2,LOW);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  Serial.print(value);
  Serial.print(" ");
  if(value>10) return false; else return true;
}
bool checkSlot8(){
  digitalWrite(s0,LOW);
  digitalWrite(s1,LOW);
  digitalWrite(s2,LOW);
  int value=analogRead(A0);
  value=map(value,0,1024,0,200);
  Serial.print(value);
  Serial.print(" ");
  if(value>10) return false; else return true;
}

