import socket
import time
import os
import sys
import subprocess

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

#Connect
ANY = '0.0.0.0'
MCAST_ADDR = '224.168.2.9'
MCAST_PORT = 8946
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
sock.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1)
sock.bind((ANY,MCAST_PORT))
sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, 255)
status = sock.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, socket.inet_aton(MCAST_ADDR) + socket.inet_aton(ANY))
sock.setblocking(0)
ts = time.time()
username = subprocess.check_output("echo $USER", shell=True)
username = username.rstrip()

#Listen and act
while 1:
	try:
		data, addr = sock.recvfrom(1024)
	except socket.error, e:
		pass
	else:
		data = data.split("##")

		if os.path.exists('lastmsg.txt'):
			lastMessageFile = open('lastmsg.txt', 'r')
			lastMessage = lastMessageFile.read()
			lastMessageFile.close
		else:
			lastMessage = ""

		if data[1] == username:
			if lastMessage != data[3]:
				msg = data[3]
				lastMessageFile = open('lastmsg.txt', 'w+')
				lastMessageFile.write(msg)
				lastMessageFile.close()
				sender = data[2]

				msg = msg.rstrip()

				os.system('./CocoaDialog.app/Contents/MacOS/CocoaDialog bubble --title "Message from: '+str(sender)+'" --text "'+str(msg)+'" --icon "globe" --timeout 1.5')
				reply = subprocess.check_output('./CocoaDialog.app/Contents/MacOS/CocoaDialog inputbox --title "Reply to '+str(sender)+'" --informative-text "Enter your reply:" --button1 "Send" --button2 "Cancel"', shell=True)
				reply = reply.split('\n')
				if reply[1] != "":
					reply = reply[1]
					reply = reply.rstrip("\r\n")

					#Messages are encoded like so "senderProgramVx.x##target##sender##message"
					#Example: "linuxV1.8##person87##NickGeek##Hey mate! What do you think of this WiN thing?"
					formattedMessage = "osxV0.1##"+sender+"##"+username+"##"+reply

					#Send message
					send(formattedMessage)