#include <Servo.h>
#include <Wire.h>
#include <HardwareSerial.h>
#include <servoMoteur.h>
#include <compass.h>
#include <GPS.h>
//#include <Telemeter.h>
#include <libComArduino.h>

//#define PIN_TELEMETER_BACK 2
//#define PIN_TELEMETER_FRONT 4
#define SEUIL_ARRET 15

int compassAddress = 0x42 >> 1;
int prems = 0;
//Telemeter frontTelemeter(PIN_TELEMETER_FRONT);
//Telemeter backTelemeter(PIN_TELEMETER_BACK);

void setup() {
  Serial.begin(9600);
  servoAngle.attach(numPinLeft);
  servoSpeed.attach(numPinRight);
  compass.begin();
  compass.setNormalizeValue(5);
  compass.setAddress(compassAddress);
  gps.begin();
}

void loop(){
    int i, distTelemeter;
    if(prems == 0)
    {
        servoMoteur_test();
        prems = 1;
    }
    //on attend que le buffer ait le bon nombre d'octets (taille d'un message)
    if(Serial.available() == NB_OCTETS)
    {
        message msgRecu;
        char* msgAenvoyer;
        msgRecu.id = Serial.read();
        for(i=0;i<4;i++){
            delay(25);
            msgRecu.param1.octets[i] = Serial.read();
        }
        for(i=0;i<4;i++){
            delay(25);
            msgRecu.param2.octets[i] = Serial.read();
        }
        msgAenvoyer = call_order(&msgRecu);
        //s'il y a un message à envoyer :
        if(msgAenvoyer != NULL)
        {
            for(i = 0; i < NB_OCTETS; i++)
            {
              Serial.print(msgAenvoyer[i]);
              delay(10);
            }
            free(msgAenvoyer);
        }
    }
    //Traitement du télémetre
    /*if(forward == 1)
    {
        distTelemeter = frontTelemeter.retreiveValue();
    }
    else
    {
        distTelemeter = backTelemeter.retreiveValue();
    }
    if(distTelemeter < SEUIL_ARRET && servoMoteur_isBlock() == 0)
    {
        servoMoteur_stop();
        isRobotBlock = 1;
    }
    else
    {
        isRobotBlock = 0; 
    }*/
    delay(25);
}
