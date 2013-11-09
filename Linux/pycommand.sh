#!/bin/bash
if which python2.7 >/dev/null; then
	printf "python2.7"
elif which python2 >/dev/null; then
	printf "python2"
elif which python >/dev/null; then
	printf "python"
else:
	zenity --error --title="WiN" --text="Error: WiN requires Python 2.7 to work!"
fi