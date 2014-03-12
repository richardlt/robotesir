#!/bin/bash

if [ -h /dev/ttyUSB0 ]
then
	sudo rm /dev/ttyUSB0
fi

sudo ln -s /dev/ttyACM0 /dev/ttyUSB0
