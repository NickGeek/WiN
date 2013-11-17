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
wget -q http://win.net.nz/content/linux0-1a.tar.gz
tar -xzf linux0-1a.tar.gz
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