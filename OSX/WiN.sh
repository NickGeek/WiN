#!/bin/bash
nohup sh Client.sh&

if which python2.7 >/dev/null; then
	python2.7 send.py
elif which python2 >/dev/null; then
	python2 send.py
elif which python >/dev/null; then
	python send.py
else:
	./CocoaDialog.app/Contents/MacOS/CocoaDialog ok-msgbox --title "WiN" --text Error: 'WiN requires Python 2.7 to work!' --no-cancel
fi