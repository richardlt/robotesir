#include <Arduino.h>
int numPinLeft(4),numPinRight(7);
int SpeedForward(0);
int SpeedBackward(180);
int SpeedStop(90);

Servo servoAngle;  
Servo servoSpeed;
int forward = 1; //definit le sens de la marche, 1=av,0,=ar

/*
* applique la commande avancer/reculer
* @ param vitesseDegres : compris en 0 et 180
* 			0 = Avant , 90 = arret, 180 = marche arriere
*			
*/
void servoMoteur_move (int vitesseDegres){
	servoSpeed.write(vitesseDegres);
}

/*
* Fait reculer le robot à la vitesse vitessePourcent
* @param 0 < vitessePourcent <= 100
*/
void servoMoteur_moveBackward(int vitessePourcent){
	if(vitessePourcent >= 0 && vitessePourcent <=100)
	{
		servoMoteur_move(90+45*vitessePourcent/100);
		forward = 0;
	}
}

/*
* Fait avancer le robot à la vitesse vitessePourcent
* @param 0 < vitessePourcent <= 100
*/
void servoMoteur_moveForward(int vitessePourcent){
	if(vitessePourcent >= 0 && vitessePourcent <=100)
	{
		servoMoteur_move(90-45*vitessePourcent/100);
		forward = 1;
	}
}

/*
* fait tourner le robot
* @param angleArd : 0=gauche,90=nul,180=droite
*/
void servoMoteur_turn(int angleArd){
	servoAngle.write(angleArd);
	if (forward == 1)
	{
		servoMoteur_moveForward(25);
	}
	else
	{
		servoMoteur_moveBackward(25);
	}
}
/* 
* Fait tourner le robot à gauche de angleRasp degrés
* @param angleRasp : 0 < angleRasp <= 90
*/
void servoMoteur_turnLeft(int angleRasp){
	if(angleRasp > 0 && angleRasp <= 90)
		servoMoteur_turn(90-45*angleRasp/90);
}
/* 
* Fait tourner le robot à droite de angleRasp degrés
* @param angleRasp : 0 < angleRasp <= 90
*/
void servoMoteur_turnRight(int angleRasp){
	if(angleRasp > 0 && angleRasp <= 90)
		servoMoteur_turn(90+45*angleRasp/90);
}

/*
* stop le robot et reinitialise les angles appliqués aux moteurs
*/
void servoMoteur_stop(){
	servoAngle.write(90);
	servoSpeed.write(90);//BUG : doit être 90
}

/************************************************/
/*		Fonctions d'état		*/
/************************************************/

/*
* retourne l'etat courant de la vitesse des roues
* 0=MaxAvant,90=Stop,180=MaxArr
*/
int servoMoteur_getStateSpeed(){
	return (servoSpeed.read());
}

/*
* vérifie si le robot est arrêté
* @return : 1 si arrêté, 0 sinon 
*/
int servoMoteur_isBlock(){
	return (servoMoteur_getStateSpeed() == 90 );
}

/*
* retourne l'etat courant de la vitesse des roues
* @return : 0=gauche,90=nul,180=droite
*/
int servoMoteur_getStateAngle(){
	return (servoAngle.read());
}

void servoMoteur_test()
{
	servoMoteur_stop();
	delay(1000);
	servoMoteur_moveForward(40);
	delay(1000);
	servoMoteur_moveBackward(40);
	delay(1000);
	servoMoteur_turnLeft(40);
	delay(1000);
	servoMoteur_turnRight(40);
	delay(1000);
	servoMoteur_stop();
	delay(1000);
}