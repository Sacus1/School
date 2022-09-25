if [ $# -gt 0 ]
then
	if test -f $1 ;
	then
		echo "Nous somme le `date +%d-%B`,il est `cat $1`" 
	else
		echo "fichier « $1 » non trouvé"
	fi
else 
	echo "usage : horloge3.sh <fichier heure>"
fi
