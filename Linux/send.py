import os
import subprocess

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
formattedMessage = "linuxVpre.release##"+target+"##"+sender+"##"+message

#Write to file
messageFile = open('msg.txt', 'w+')
messageFile.write(formattedMessage)
messageFile.close()

pyVersion = subprocess.check_output("sh pycommand.sh", shell=True)
os.system(pyVersion + " server.py")