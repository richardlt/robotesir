//#include <Servo.h> 
#include <stdio.h> 

// declaration & initialisation des variables
//TX envoie, RX recoit
//TX
//int numPinLeft(4),numPinRight(7);
//int SpeedForward(0);
//int SpeedBackward(180);
//int SpeedStop(90);

// create servo object to control a servo 
//Servo servoAngle;  
//Servo servoSpeed;

// initialisation de la bete
/*void setup() {
  Serial.begin(9600);
  servoAngle.attach(numPinLeft);  //the pin for the servo control 
  servoSpeed.attach(numPinRight);
}

void loop () {

  Serial.println("avance tout droit pendant une seconde");
  moveForward(1.0);
  delay(1000);
  Serial.println("recule tout droit pendant une seconde");
  moveBackward(1.0);
  delay(1000);
  Serial.println("avance vers la droite pendant une seconde");
  turnRight(90);
  moveForward(1.0);
  delay(1000);
  Serial.println("avance vers la gauche pendant une seconde");
  turnLeft(90);
  moveForward(1.0);
  delay(1000);
exit();
}*/
/* envoie la commande avancer/reculer
* @ param 	vitesseDegres compris en 0 et 180
* 			0 = Avant , 90 = arret, 180 = marche arriere
*			
*/
/*void move (int vitesseDegres){
	ServoSpeed.write(vitesseDegres);
}*/

/*
* @param vitessePourcent compris 0.0 et 1.0 selon la puissance que l'on veut
* 90 = arret, 180 
* 
*/
void moveBackward(int vitessePourcent){
//	move(90+90*vitessePourcent);
	//move(135);
}

void moveForward(int vitessePourcent){
//	move(90-90*vitessePourcent);
	//move(45);
	}

/*
* fait varier langle des roues, 0=gauche,90=nul,180=droite
*/
/*void turn int angleArduino){
	ServoAngle.write(angleDegres);
}*/
/* 
* tourner a gauche a 90degres selon mavlink, fait ecrire la valeur zero
*/
void turnLeft(int angleMavlink){
//	turn(90-angleMavlink);
	//turn(45);
}
/* 
* tourner a droite a 90degres selon mavlink, fait ecrire la valeur 180
*/
turnRight(int angleMavlink){
//	turn(90+angleMavlink);
	//turn(135);
}

/* appele par BLOCK
* bloque les roues, et reinitialise leurs angles
*/
void stop(){
  printf("STOP");
//	ServoAngle.write(90);
//	ServoSpeed.write(90);
}

/*void block(){
	return (getStateSpeed() == 90 );
}*/

/*
* retourne l'etat courant de la vitesse des roues
* 0=MaxAvant,90=Stop,180=MaxArr
*/
int getStateSpeed(){
  return 0;
//	return (servoSpeed.read());
}

/* appeler par SENDANGLE
* retourne l'etat courant de la vitesse des roues
* 0=MaxAvant,90=Stop,180=MaxArr
*/
/*int getStateAngle(){
	return (servoAngle.read());
*/