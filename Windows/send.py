import os
import subprocess

sender = subprocess.check_output('echo %username%', shell=True)
sender = sender.rstrip()
target = str(raw_input("Target's Username: "))
message = str(raw_input("Message: "))

#Messages are encoded like so "senderProgramVx.x##target##sender##message"
#Example: "linuxV1.8##person87##NickGeek##Hey mate! What do you think of this WiN thing?"
formattedMessage = "windowsVpre.release##"+target+"##"+sender+"##"+message

#Write to file
messageFile = open('msg.txt', 'w+')
messageFile.write(formattedMessage)
messageFile.close()

os.system("python.exe server.py")