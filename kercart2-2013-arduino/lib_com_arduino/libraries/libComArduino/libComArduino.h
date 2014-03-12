#include <stdlib.h>
#include <stdio.h>

//Variables
#define NB_PARAM 2
#define SIZE_PARAM 4
#define NB_OCTETS (1 + (NB_PARAM * SIZE_PARAM))

//Functions
#define OK 0
#define STOP 1
#define FORWARD 2
#define BACKWARD 3
#define LEFT 4
#define RIGHT 5
#define IS_BLOCK 6
#define GETPOS 7
#define GETANGLE 8
#define GETGPSINFO 9

#define BLOCK 10
#define SENDPOS 11
#define SENDANGLE 12
#define ARRIVED 13
#define SENDGPSINFO 14
#define STOPTURN 16

#define LEFTANGLE 17
#define RIGHTANGLE 18

#define VITESSETOURNER 40
#define MARGEROTATION 8

Compass compass;
GPS gps(0x68);
int isRobotBlock(0);

typedef union
{
	long entier;
	char octets[4];
}longToChar;

typedef struct
{
	char id;
	longToChar param1;
	longToChar param2;
} message;

static inline void turnScrutation(char sens, int angleAtourner)
{
	int orientationCourante, orientationFinale;
	compass.sendOrder('A');
	delay(10);
	orientationCourante = compass.RetrieveValueNumeric();
	//calcul modulo de l'angle que l'on veut obtenir
	if(sens == 'L')
		orientationFinale = (360 +  orientationCourante - angleAtourner) % 360;
	else
		orientationFinale = (orientationCourante + angleAtourner) % 360;
	do{
		if(sens == 'L')
			servoMoteur_turnLeft(VITESSETOURNER);
		else
			servoMoteur_turnRight(VITESSETOURNER);
		delay(100);
		compass.sendOrder('A');
		delay(10);
		orientationCourante = compass.RetrieveValueNumeric();
		if(sens == 'L')
		{
			if(orientationCourante < orientationFinale && orientationCourante > 0)
				orientationCourante += 360;
		}
		else
		{
			if(orientationCourante < 360 && orientationCourante > orientationFinale)
				orientationCourante -= 360;
		}
	}while((orientationCourante > orientationFinale+MARGEROTATION && sens == 'L') || (orientationCourante < orientationFinale-MARGEROTATION && sens == 'R'));
	servoMoteur_stop();
}

/*
* Envoie un message
*/
static inline char* send_order(int id)
{
	char* message;
	longToChar tmp;
	message = (char*)malloc(NB_OCTETS);
	
	//Initialisation du message
	int i, valCompass;
	for(i = 0; i < NB_OCTETS; i++)
		message[i] = 0;
	
	switch(id)
	{
		case OK :
			message[0] = OK;
			break;
		case BLOCK :
			message[0] = BLOCK;
			break;
		case GETPOS :
			message[0] = SENDPOS;
			gps.retrieveLongitude();
			tmp.entier = gps.a_longitude;
	//		tmp.entier = 2147483647;
			for(i=0;i<SIZE_PARAM;i++)
			{
				message[i+1] = (char)tmp.octets[i];
			}
			gps.retrieveLatitude();
	//		tmp.entier = 2147483647;
			tmp.entier = gps.a_latitude;
			for(i=0;i<SIZE_PARAM;i++)
			{
				message[i+1+SIZE_PARAM] = (char)tmp.octets[i];
			}
			break;
		case GETANGLE:
			message[0] = SENDANGLE;
			compass.sendOrder('A');
			delay(10);
			tmp.entier = (long)compass.RetrieveValueNumeric();
			for(i=0;i<SIZE_PARAM;i++)
			{
				message[i+1] = (char)tmp.octets[i];
			}
			break;
		case ARRIVED :
			message[0] = ARRIVED;
			break;
		case GETGPSINFO :
			message[0] = SENDGPSINFO;
			//ToDO : récupérer info gps
			break;
		case IS_BLOCK :
			message[0] = BLOCK;
			tmp.entier = (long)isRobotBlock;
			for(i=0;i<SIZE_PARAM;i++)
			{
				message[i+1] = (char)tmp.octets[i];
			}
			break;
		case STOPTURN:
			message[0] = STOPTURN;
			break;
	}
	return message;
}

/*
* Appelle la fonction correspondant à l'ID
*/
static inline char* call_order(message* msg)
{
	switch(msg->id)
	{
		case STOP : 
			servoMoteur_stop();
			return(send_order(OK));
			break;
		case FORWARD :
			servoMoteur_moveForward((int) (msg->param1.entier));
			return(send_order(OK));
			break;
		case BACKWARD :
			servoMoteur_moveBackward((int) (msg->param1.entier));
			return(send_order(OK));
			break;
		case LEFT :
			servoMoteur_turnLeft(90);
			return(send_order(OK));
			break;
		case RIGHT :
			servoMoteur_turnRight(90);
			return(send_order(OK));
			break;
		case IS_BLOCK :
			return(send_order(IS_BLOCK));
			break;
		case GETPOS :
			return(send_order(msg->id));
			break;
		case GETANGLE :
			return(send_order(msg->id));
			break;
		case GETGPSINFO :
			return(send_order(msg->id));
			break;
		case LEFTANGLE :
			turnScrutation('L', (int) (msg->param1.entier));
			return(send_order(STOPTURN));
			break;
		case RIGHTANGLE :
			turnScrutation('R', (int) (msg->param1.entier));
			return(send_order(STOPTURN));
			break;
			
	}
	return NULL;
}