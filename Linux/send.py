import os
import subprocess

sender = subprocess.check_output("echo $USER", shell=True)
sender = sender.rstrip()
target = str(raw_input("Target's Username: "))
message = str(raw_input("Message: "))

#Make sure the message isn't blank
if message != "" and sender != "" and target != "":
	#Messages are encoded like so "senderProgramVx.x##target##sender##message"
	#Example: "linuxV1.8##person87##NickGeek##Hey mate! What do you think of this WiN thing?"
	formattedMessage = "linuxVpre.release##"+target+"##"+sender+"##"+message

	#Write to file
	messageFile = open('msg.txt', 'w+')
	messageFile.write(formattedMessage)
	messageFile.close()

	os.system("python server.py")
else:
	print "Error: Please fill out all the fields."