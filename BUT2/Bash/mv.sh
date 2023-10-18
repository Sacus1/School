move(){
	
}
if [[ $# -eq 1 ]]
then
	move < $1 $2
else 
	if [[ $# -eq 0 ]]
	then 
		move < $0 "/tmp";
	fi
fi

move;
