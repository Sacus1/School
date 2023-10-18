if [ $# -gt 0 ]
then
	echo "Nous somme le `date +%d-%B`,il est $1" 
else
	echo "usage: horloge2.sh <heure>"
fi
