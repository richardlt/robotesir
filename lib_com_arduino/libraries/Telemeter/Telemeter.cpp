/*
 * Telemeter.cpp
 *
 *  Created on: May 17, 2013
 *      Author: Yasser
 */

#include "Telemeter.h"

Telemeter::Telemeter(int analogPin) :
		p_analogPin(analogPin), p_output(-1) {

}

void Telemeter::setPin(int analogPin) {
	p_analogPin = analogPin;
}

float Telemeter::retreiveValue() {

	p_output = analogRead(p_analogPin);

	if (p_output < 3)
		return -1; // invalid value

	return (6787.0 / (p_output - 3.0)) - 4.0;
}
