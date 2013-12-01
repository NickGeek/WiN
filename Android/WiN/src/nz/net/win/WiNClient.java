package nz.net.win;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

import android.os.Looper;
import android.util.Log;

public class WiNClient implements Runnable {
	public WiNClient() {
		
	}

	@Override
	public void run() {
		Looper.prepare();
		MulticastSocket socket;
		try {
			//TODO: Do something about Android killing this thread
			socket = new MulticastSocket(8946);
			InetAddress group = InetAddress.getByName("224.168.2.9");
			socket.joinGroup(group);
			DatagramPacket packet;
			
			byte[] buffer = new byte[256];
			byte  b = '>'; //This character can NEVER be in a WiN message. This is a bad fix but it'll have to do for now.
			Arrays.fill(buffer,b);
			packet = new DatagramPacket(buffer, buffer.length);
			String msg = "";
			String prevMessage = ">";
			
			while(msg != null)
			{
				socket.receive(packet);
				msg = new String(packet.getData());
				msg = msg.substring(0, msg.indexOf('>'));
				String[] messageList = msg.split("##");
				String myusername = MainActivity.username();
				String username = messageList[1];
				String sender = messageList[2];
				String message = messageList[3];
				//Log.i("M2K", message);
				Log.i("M2K", prevMessage);
				
				if (username.equals(myusername) && !message.equals(prevMessage)) {
					Log.i("Message", message + " | From: " + sender);
					//TODO: Stop other identical messages from being logged.
					prevMessage = message;
					
					MainActivity.newMessage();
				}
			}

			socket.leaveGroup(group);
			socket.close();
		} catch (IOException e) {
		}
	}
}