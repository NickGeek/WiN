WiN
===

WiN (Wireless Inter-Home Network) is designed to do what crowd-funding did for projects but for mobile networks. Uses of this could be in businesses to replace wired phone extensions or in place of a pager at a hospital. It could even be used to call your son to dinner! It doesn't require an Internet connection or traditional cellular coverage so it can be completely free.

###Where are you in development?
You can see everything and vote/comment at our public Trello here: https://trello.com/b/xTcyUVmY

###How do I use this as of now?
NOTE: This is still in super early development so expect everything to change.

**Sending a message**

To send a message run "python send.py" and fill out your desired username, the target's username and the message.

**Receiving messages**

Run "python client.py *myusername*"

###What do I need to run this?
Currently WiN is only avalible on GNU/Linux. It requires Python 2.7 and Zenity to run.

###Frequent problems
**I'm not getting messages!**

Check you are on the same WiFi network *and* the same subnet. Also check that your firewall is not blocking port 8946 and 1501.

**I get _____ error!**

Leave a bug report on this GitHub page.

###The WiN format
The WiN format is designed for ultimate flexibility.

Messages will be send with the body looking like this:

senderProgramVx.x##target##sender##message

An example from WiN for GNU/Linux is:

linuxV1.8##person87##NickGeek##Hey mate! What do you think of this WiN thing?

The first value is the application that sent the message. This can be used to enable certain features based on the application that sent the message.

You can also extend the WiN message simply by adding any other values after the message value.

Here is an example:
specialappV3.6##person87##NickGeek##Hey there. Here's that picture you wanted.##http://192.168.1.62/specialapp/picture.png

This example has the URL to an image in it. A compatible client could include the image with the message even though this is not officially in the WiN format.

**Special Messages**

Special messages usually contain instructions for (a) client(s) connected to the network.

Here is a list of current special messages:

* "DELIVCONFRM" This message confirms the arrival of a message
* "DELIVFAIL" This message confirms the failure of a message to send
* "TCP" This message is an invitation to join a TCP chat. This is more reliable than UDP.

An example special message body would look like this:

linuxV1.8##person87##NickGeek##SMSG: DELIVCONFRM