Find Running process : netstat -o -n -a| findstr 0.0:8080
	eg. TCP    0.0.0.0:8080           0.0.0.0:0              LISTENING       4468
Kill process: taskkill /F /PID 4468