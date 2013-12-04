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

sender = subprocess.check_output("echo $USER", shell=True)
sender = sender.rstrip()
target = subprocess.check_output("echo `zenity --entry --title='WiN' --text='Recipient:' --ok-label='Next'`", shell=True)
target = target.rstrip('\r\n')

#Did the user cancel?
if target == "":
	exit()

#message = str(raw_input("Message: "))
message = subprocess.check_output("echo `zenity --entry --title='WiN' --text='Message:' --ok-label='Send'`", shell=True)
message = message.rstrip('\r\n')

#Did the user cancel?
if message == "":
	exit()

#Messages are encoded like so "senderProgramVx.x##target##sender##message"
#Example: "linuxV1.8##person87##NickGeek##Hey mate! What do you think of this WiN thing?"
formattedMessage = "linuxV1.0##"+target+"##"+sender+"##"+message

#Send message
send(formattedMessage)