/*
 * compass.cpp
 *
 *  Created on: May 3, 2013
 *      Author: Yasser
 */

#include "compass.h"

Compass::Compass() :
		normalize_value(1) {
	lastPosition = ' ';
}

/**
 *Function to call on Setup
 */
void Compass::begin() {
	Wire.begin();
}

void Compass::sendOrder(char order) {
	Wire.beginTransmission(a_address);
	Wire.write(order);
	Wire.endTransmission();
}

int Compass::RetrieveValueNumeric() {
	Wire.beginTransmission(a_address);
	Wire.requestFrom(a_address, 2);
	int reading = 0;
	int result = 0;
	for (int i = 0; i < normalize_value; i++) {
		if (Wire.available() >= 2) {
			reading = Wire.read();
			reading = reading << 8;
			reading += Wire.read();
			reading /= 10;
		}
		result += reading;
	}
	return result / normalize_value;
}
/*
 * Might be faulty
 * calls RetrieveValueNumeric
 * return: a pointer to String
 */
char Compass::RetrieveValueAlpha() {
	int result = this->RetrieveValueNumeric();
	if (result >= 315 && result < 45) {
		return 'N';
	}
	if (result >= 45 && result < 135) {
		return 'E';
	}

	if (result >= 135 && result < 225) {
		return 'S';
	}

	else {
		return 'W';
	}
}

void Compass::setAddress(int address) {
	a_address = address;
}
void Compass::setNormalizeValue(int value) {
	if (value == 0) {
		normalize_value = 1;
		return;
	}
	normalize_value = value;
}

void Compass::send(int data) {
	Serial.print(data);
}


