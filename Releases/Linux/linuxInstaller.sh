#Installer for WiN 0.1a (Linux)
clear
echo "Setting up..."
cd
rm -rf .WiN 2> /dev/null
rm ~/Desktop/WiN.desktop 2> /dev/null
rm ~/.local/share/applications/WiN.desktop 2> /dev/null
rm ~/.config/autostart/winclient.desktop 2> /dev/null
sudo rm /usr/share/pixmaps/WiN.png 2> /dev/null
sudo rm /usr/share/applications/WiN.desktop 2> /dev/null
mkdir .WiN
cd .WiN
echo "Downloading..."
wget -q http://win.net.nz/content/linux0-1-1b.tar.gz
tar -xzf linux0-1-1b.tar.gz
rm linux0-1a.tar.gz
chmod +x WiN
chmod +x Client
chmod +x WiN.desktop
chmod +x winclient.desktop
echo "Creating launchers..."
sudo cp WiN.png /usr/share/pixmaps/WiN.png
sudo cp WiN.desktop /usr/share/applications/WiN.desktop
cp WiN.desktop ~/Desktop/WiN.desktop
cp WiN.desktop ~/.local/share/applications/WiN.desktop
cp winclient.desktop ~/.config/autostart/winclient.desktop
echo "Done!"
nohup ./Client&

#Run send.py
if which python2.7 >/dev/null; then
	python2.7 send.py
elif which python2 >/dev/null; then
	python2 send.py
elif which python >/dev/null; then
	python send.py
else:
	zenity --error --title="WiN" --text="Error: WiN requires Python 2.7 to work!"
fi