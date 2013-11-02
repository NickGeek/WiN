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

				#Clear up special characters in msg to avoid error. Feel free to add escape codes to more special characters here. If the character refuses to be escaped feel free to remove the character from the message.
				msg = msg.replace('~', '\~')
				msg = msg.replace('`', '\`')
				msg = msg.replace('!', '\!')
				msg = msg.replace('@', '\@')
				msg = msg.replace('#', '\#')
				msg = msg.replace('$', '\$')
				msg = msg.replace('%', '\%')
				msg = msg.replace('^', '\^')
				msg = msg.replace('&', '\&amp;')
				msg = msg.replace('*', '\*')
				msg = msg.replace('(', '\(')
				msg = msg.replace(')', '\)')
				msg = msg.replace('-', '\-')
				msg = msg.replace('_', '\_')
				msg = msg.replace('{', '\{')
				msg = msg.replace('[', '\[')
				msg = msg.replace('}', '\}')
				msg = msg.replace(']', '\]')
				msg = msg.replace('|', '\|')
				msg = msg.replace(':', '\:')
				msg = msg.replace(';', '\;')
				msg = msg.replace('<', '')
				msg = msg.replace('>', '')
				msg = msg.replace('?', '\?')
				msg = msg.replace('/', '\/')
				msg = msg.replace('+', '\+')
				msg = msg.replace('=', '\=')
				msg = msg.replace("'", '')
				msg = msg.replace('"', '')

				os.system('zenity --info --title="Message from: '+str(sender)+'" --text="'+str(msg)+'"')
				reply = subprocess.check_output("echo `zenity --entry --title='Reply to "+sender+"' --text='Enter your reply:' --ok-label='Send'`", shell=True)
				if reply != "\n":
					reply = reply.rstrip("\r\n")

					#Messages are encoded like so "senderProgramVx.x##target##sender##message"
					#Example: "linuxV1.8##person87##NickGeek##Hey mate! What do you think of this WiN thing?"
					formattedMessage = "linuxVpre.release##"+sender+"##"+username+"##"+reply

					#Write to file
					messageFile = open('msg.txt', 'w+')
					messageFile.write(formattedMessage)
					messageFile.close()

					os.system("python server.py")