Set objArgs = WScript.Arguments
msg = objArgs(0)
sender = "Message from: "+objArgs(1)
MsgBox msg,0,sender