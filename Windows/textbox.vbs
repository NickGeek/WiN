Set objArgs = WScript.Arguments
sender = objArgs(0)
message = objArgs(1)
reply = InputBox(message, sender)
Wscript.Echo reply