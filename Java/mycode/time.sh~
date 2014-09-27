#Shell Script to extract distance and time

#echo "Enter the latitude of origin: "
#read ori_lat
#echo "Enter the longitude of origin: "
#read ori_long

#echo "Enter the latitude of Destination : "
#read dest_lat
#echo "Enter the longitude of Destination : "
#read dest_long

if [ -f extract.txt ] 
then 
	rm extract.txt
fi

curl "http://maps.googleapis.com/maps/api/directions/json?origin=$1,$2&alternatives=false&units=imperial&destination=$3,$4&sensor=false" > extract.txt 

dist=`awk 'NR==18{print $0}' extract.txt | grep '[0-9]*[.]*[0-9]*' -o`
dist=`echo "$dist * 1.609344" | bc`
awk 'NR==22{print $0}' extract.txt | grep '[0-9]*' -o  > temp.txt
part1=`awk 'NR==1{print $0}' temp.txt`
part2=`awk 'NR==2{print $0}' temp.txt`

if [ ! -z "$part2" ]
then
	echo "$part1.$part2"
else
	echo ".$part1"
fi 
 
