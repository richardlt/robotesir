#!/bin/bash

echo "$1" | grep $2 -A 11 | tail -n 1 | awk '{print $1}'
