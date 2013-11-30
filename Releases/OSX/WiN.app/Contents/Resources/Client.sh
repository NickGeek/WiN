#!/bin/bash
if which python2.7 >/dev/null; then
	python2.7 client.py
elif which python2 >/dev/null; then
	python2 client.py
elif which python >/dev/null; then
	python client.py
else:
	./CocoaDialog.app/Contents/MacOS/CocoaDialog ok-msgbox --title "WiN" --text Error: 'WiN requires Python 2.7 to work!' --no-cancel
fi