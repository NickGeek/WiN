@echo off
::This script will install WiN
cls
echo WiN is being installed...
deltree "%appdata%\WiN" >nul 2>&1
cscript Files\download.vbs
Files\7za.exe x -o%appdata%\WiN -y %appdata%\win.zip 2>&1
del %appdata%\win.zip >nul 2>&1

::Copy to start menu/desktop
deltree "%appdata%\Microsoft\Windows\Start Menu\Programs\WiN\" >nul 2>&1
mkdir "%appdata%\Microsoft\Windows\Start Menu\Programs\WiN\"
Files\Shortcut.exe /a:c /f:"%userprofile%\desktop\WiN.lnk" /t:"%appdata%\WiN\WiN.exe"
Files\Shortcut.exe /a:c /f:"%appdata%\Microsoft\Windows\Start Menu\Programs\WiN\WiN.lnk" /t:"%appdata%\WiN\WiN.exe"
Files\Shortcut.exe /a:c /f:"%appdata%\Microsoft\Windows\Start Menu\Programs\WiN\WiN Clinet.lnk" /t:"%appdata%\WiN\client.exe"
Files\Shortcut.exe /a:c /f:"%appdata%\Microsoft\Windows\Start Menu\Programs\Startup\WiN Clinet.lnk" /t:"%appdata%\WiN\client.exe"

::Done!
cscript Files\notify.vbs
cd %APPDATA%\WiN\
start %APPDATA%\WiN\python.exe %APPDATA%\WiN\client.py
start %APPDATA%\WiN\python.exe %APPDATA%\WiN\send.py