1) Redis Database has been used to store the data.
2) Java and Redis have been connected using the Jedis JAR.
3) When you run the program, you need to provide inputs to the command line.
4) Before running the program make sure you turn on the Redis server on local machine. 
5) Run the program and it will ask you for inputs.
	•Give input as 1 if you want to directly execute commands. All the commands mentioned in the Problem Statement document can be executed i.e: SET, GET, DEL, INCR, INCRBY, MULTI, COMPACT and EXEC.

		EG: SET NAME CHINMAY

	•Give input as 2 if you want to directly execute the Cases provided in the Problem Statement document.

6) The code has been made modular and Object Oriented. Most of the operations which need to be performed are put into different functions.

------------------------------------------------------Assumptions made------------------------------------------------------

	i)User may want to manually enter the commands or directly execute the cases. (Though directly executing the cases will restrict the scope of program. Hence manually entering the commands is a better approach.)
	
	ii)Program runs in an infinite loop, hence user can provide any number of commands.
	
	iii)For executing Case 4 of the problem statement the database must be emptied first. And the commands can be executed after that.
	
	iv)The commands which we want to execute are: SET, GET, DEL, INCR, INCRBY, MULTI, COMPACT and EXEC.
	
	v)Program will throw exception and terminate itself if the input from user is not valid.


------------------------------------------------------My approach------------------------------------------------------ 

	i)Making Redis server active (on local) for use is the first and most important step.
	
	ii)Connecting the Java code to Redis server up on local host using the Jedis JAR.
	
	iii)Checking if the server is connected to Java, and if it is connected successfully, printing that it is connected.
	
	iv)Taking inputs from the user.
	
	v)User may try to input several commands at once so using infinite loop for that purpose.
	
	vi)Cross check if our commands run successfully using the Redis-CLI on local.
	
	vii)Code should always look for the Exceptions.
	
	viii)[IMP] If command is {command} {key} / {command} {key} {value}/ {command}, the {command} part case(uppercase/lowercase) should always be case insensitive as user may give command set KEY VALUE or SET KEY VALUE or sEt KEY VALUE or SEt KEY VALUE, but the case of {key} and {value} is case sensitive in Redis.

