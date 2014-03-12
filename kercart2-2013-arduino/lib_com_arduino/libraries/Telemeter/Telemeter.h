/*
 * Telemeter.h
 *
 *  Created on: May 17, 2013
 *      Author: Yasser
 */


#ifndef TELEMETER_H_
#define TELEMETER_H_
#include <Arduino.h>

class Telemeter {

public:
	Telemeter(int analogPin);
	void setPin(int analogPin);
	float retreiveValue();

private:
	int p_analogPin;
	float p_output;
};

#endif /* TELEMETER_H_ */
