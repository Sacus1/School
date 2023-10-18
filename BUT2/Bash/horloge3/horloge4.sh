for i in *.TZ
do
	echo "Nous somme le `date +%d-%B`,il est $(cat $i) a" ${i%.TZ} 
done
