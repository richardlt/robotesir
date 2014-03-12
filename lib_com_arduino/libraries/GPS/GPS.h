/*
 * GPS.h
 *
 *  Created on: May 3, 2013
 *      Author: Yasser
 */

#ifndef GPS_H_
#define GPS_H_
#include <Wire.h>
#include <HardwareSerial.h>

typedef unsigned char byte; 

class GPS {

public:
	GPS(int address);
	void begin();
	void retrieveTime();
	void retrieveDate();
	void retrieveHeading();
	void retrieveSpeed();
	void retrieveLatitude();
	void retrieveLongitude();
	void retrieveLatLong();
	int getState(); 
	/**
	 * runs all the retrieve methods
	 */
	void retrieveAllatOnce();
	//send 
	void send(int type);
	void sendAll();
private:
	int getDouble(int address);
	int getSingle(int address);
	int getLetter(int address);
// attributes

	int a_address;

public:
	long a_time;
	long a_date;
	long a_heading;
	long a_speed;
	long a_latitude;
	long a_longitude;
	String output;
};

#endif /* GPS_H_ */
