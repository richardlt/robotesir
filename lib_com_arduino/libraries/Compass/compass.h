/*
 * compass.h
 *
 *  Created on: May 2, 2013
 *      Author: Yasser
 */

#ifndef COMPASS_H_
#define COMPASS_H_

#include <Wire.h>
#include <HardwareSerial.h>

class Compass {


public:
	Compass();
	void setAddress(int address);
	void begin();
	void sendOrder(char order);
	int RetrieveValueNumeric();
	char RetrieveValueAlpha();
	void setNormalizeValue(int value);
	void send(int data);
private :
	int a_address;
	int normalize_value;
	char lastPosition;
};

#endif /* COMPASS_H_ */
