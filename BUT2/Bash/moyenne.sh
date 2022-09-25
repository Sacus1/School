calc(){
	while read L
	do
		set $L
		somme=0
		for var in "${@:3}"
		do
			somme=$((somme + var));
		done
		echo "$1 $2 $((somme/($#-2)))";
	done 
}
if test -f $1 ;
then 
	calc < $1
fi
