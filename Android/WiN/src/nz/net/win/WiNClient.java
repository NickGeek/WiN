package nz.net.win;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

import android.os.Looper;

public class WiNClient implements Runnable {
	public WiNClient() {
		
	}

	@Override
	public void run() {
		Looper.prepare();
		MulticastSocket socket;
		try {
			socket = new MulticastSocket(8946);
			InetAddress group = InetAddress.getByName("224.168.2.9");
			socket.joinGroup(group);
			DatagramPacket packet;
			String prevMessage = ">";
			
			while(true)
			{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				byte[] buffer = new byte[256];
				byte  b = '>'; //This character can NEVER be in a WiN message. This is a bad fix but it'll have to do for now.
				Arrays.fill(buffer,b);
				packet = new DatagramPacket(buffer, buffer.length);
				String msg = "";
				String myusername = MainActivity.username();
				
				socket.receive(packet);
				msg = new String(packet.getData());
				msg = msg.substring(0, msg.indexOf('>'));
				String[] messageList = msg.split("##");
				String username = messageList[1];
				String sender = messageList[2];
				String message = messageList[3];
				
				if (username.equals(myusername) && !message.equals(prevMessage)) {
					//Tell the UI thread to display a notification
					MainActivity.newMessage(sender, message);
					
					//Stop other identical messages from being logged.
					prevMessage = message;
					
					//Clear the variables to stop merging of messages "Goodare you?"
					packet = null;
					msg = null;
					messageList = null;
					username = null;
					sender = null;
					message = null;
				}
			}
		} catch (IOException e) {
		}
	}
}