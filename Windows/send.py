import os
import subprocess
import socket

def send(message):
	ANY = '0.0.0.0'
	SENDERPORT=1501
	MCAST_ADDR = '224.168.2.9'
	MCAST_PORT = 8946
	sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
	sock.bind((ANY,SENDERPORT))
	sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, 255)

	#Send 50 times as a temporary fix until TCP is put in
	loop = 0

	while loop <= 50:
		sock.sendto(message, (MCAST_ADDR,MCAST_PORT) )
		loop += 1
	sock.close()

sender = subprocess.check_output('echo %username%', shell=True)
sender = sender.rstrip()
target = str(raw_input("Target's Username: "))
if target.rstrip('\r\n') == "":
	exit()
message = str(raw_input("Message: "))
if message.rstrip('\r\n') == "":
	exit()

#Messages are encoded like so "senderProgramVx.x##target##sender##message"
#Example: "linuxV1.8##person87##NickGeek##Hey mate! What do you think of this WiN thing?"
formattedMessage = "windowsVpre.release##"+target+"##"+sender+"##"+message

#Send message
send(formattedMessage)