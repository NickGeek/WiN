import socket
import time
import os
import sys
import subprocess

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

#Listen and act
while 1:
	try:
		data, addr = sock.recvfrom(1024)
	except socket.error, e:
		pass
	else:
		data = data.split("##")
		if data[1] == sys.argv[1]:
			msg = data[3]
			sender = data[2]
			os.system('zenity --info --title="Message from: '+str(sender)+'" --text="'+str(msg)+'"')
			reply = subprocess.check_output("echo `zenity --entry --title='Reply to "+sender+"' --text='Enter your reply:' --ok-label='Send'`", shell=True)
			if reply != "\n":
				reply = reply.rstrip("\r\n")

				#Messages are encoded like so "senderProgramVx.x##target##sender##message"
				#Example: "linuxV1.8##person87##NickGeek##Hey mate! What do you think of this WiN thing?"
				formattedMessage = "linuxVpre.release##"+sender+"##"+sys.argv[1]+"##"+reply

				#Write to file
				messageFile = open('msg.txt', 'w+')
				messageFile.write(formattedMessage)
				messageFile.close()

				os.system("python2 server.py")