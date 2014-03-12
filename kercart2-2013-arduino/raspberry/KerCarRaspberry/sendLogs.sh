#!/bin/bash

echo "Hep, voilà les logs" | mutt -s "Logs Kercar" -a logs/*.log -- quentin.de.gr@gmail.com

