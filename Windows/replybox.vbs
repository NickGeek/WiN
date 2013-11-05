Set objArgs = WScript.Arguments
sender = "Reply to "+objArgs(0)
reply = InputBox("Enter your reply:", sender)
Wscript.Echo reply