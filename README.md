WiN
===

WiN (Wireless Inter-Home Network) is designed to do what crowd-funding did for projects but for mobile networks. Uses of this could be in businesses to replace wired phone extensions, communications when cellular towers and the Internet is down or in place of a pager at a hospital. It could even be used to call your son to dinner! It doesn't require an Internet connection or traditional cellular coverage so it can be completely free.

###How can I install this?
To install a release version of WiN click the releases tab near the top of the page and find the version of WiN that will run on your device.

If you want to be on the cutting edge you can download a development version by either cloning this git repo or clicking the "Download ZIP" button on the side-bar. If you are using a development version you will have to run WiN manually. Instructions for that are below.

###Where are you in development?
You can see everything and vote/comment at our public Trello here: https://trello.com/b/xTcyUVmY

###How do I use this as of now?
NOTE: This is still in super early development so expect everything to change.

####Release versions:

**Sending a message**

To send a message click on the WiN icon that would of been added to your application launcher during installation.

**Receiving messages**

On GNU/Linux you don't have to do  anything, you'll get messages automatically. On Mac OS X you just need to open WiN (you don't have to send a message, just hit cancel) and it'll start checking for messages in the background. On Windows you shouldn't have to do anything.

####Development versions:
**Sending a message**

To send a message run "python send.py" (or "python.exe send.py" on Windows) and fill out the target's username and the message.

**Receiving messages**

Run "python client.py" (or "python.exe client.py" on Windows).

###What do I need to run this?
**WiN for GNU/Linux**

WiN for GNU/Linux requires:
* Python 2.7
* Zenity

**WiN for Mac OS X**

WiN for Mac OS X requires:
* Python 2.7

**WiN for Windows**

WiN for Windows requires:
* Windows XP or later
* *Note: No install of python is required. WiN for Windows comes with a standalone version of Python 2.7.5 from http://www.orbitals.com/programs/pyexe.html*

**WiN for Android**

WiN for Android requires:
* A device that supports multicast (most devices released with android 3.0 or higher support this)
* Android 4.0 or higher

###Frequent problems
**I'm not getting messages!**

Check you are on the same WiFi network *and* the same subnet. Also check that your firewall is not blocking port 8946 and 1501. You can also try starting the client again (on Windows go Start > WiN > Win Client, on Mac OS X just open WiN and on GNU/Linux cd into ".WiN" and run "./Client").

**I get _____ error!**

Leave a bug report on this GitHub page.

**Windows tells me I need to allow python.exe to access the network!**

Allow it. This is required for WiN to communicate.

**Are there any viruses in WiN**

Nope and if you don't trust me you can have a look at all the code yourself on this page.

###How can I help
The main thing you can do is spread the word, the more hands on thing you can do is look at the "Issues" page on this website and see if you can fix any of those. Also you could look at our (Trello) and help with some of the tasks on there.
