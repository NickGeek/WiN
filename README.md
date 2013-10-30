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
Run "python client.py myusername"

###What do I need to run this?
Currently WiN is only avalible on GNU/Linux. It requires Python 2.7 and Zenity to run.

###Frequent problems
**I'm not getting messages!**
Check you are on the same WiFi network *and* the same subnet. Also check that your firewall is not blocking port 8946 and 1501.
**I get _____ error!**
Leave a bug report on this GitHub page.