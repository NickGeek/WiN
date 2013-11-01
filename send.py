import os

if os.path.exists("account.conf") is False:
	sender = str(raw_input("Your Username: "))
	accountFile = open('account.conf', 'w+')
	accountFile.write(sender)
	accountFile.close()
else:
	accountFile = open('account.conf', 'r')
	sender = accountFile.read()
	accountFile.close()

target = str(raw_input("Target's Username: "))
message = str(raw_input("Message: "))

#Messages are encoded like so "senderProgramVx.x##target##sender##message"
#Example: "linuxV1.8##person87##NickGeek##Hey mate! What do you think of this WiN thing?"
formattedMessage = "linuxVpre.release##"+target+"##"+sender+"##"+message

#Write to file
messageFile = open('msg.txt', 'w+')
messageFile.write(formattedMessage)
messageFile.close()

os.system("python2 server.py")
