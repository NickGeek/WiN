//Add features people should work on below:
//TODO: Turn the client part of this app into a background service
//TODO: Notifications on message received

package nz.net.win;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;

public class MainActivity extends Activity {
	EditText usernameTxt;
	EditText targetTxt;
	EditText msgTxt;
	Button startBtn;
	Button sendBtn;
	static String formattedMessage;
	SharedPreferences sprefs;
	static String username;
	static String[] message = new String[3];
	/*Handler mHandler = new Handler();
	MulticastSocket socket;
	DatagramPacket packet;
	InetAddress group;
	byte[] buffer;
	String msg = "";*/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Index 0 is if the message is new, index 1 is the sender and index 2 is the message
		message[0] = "False";
		message[1] = "";
		message[2] = "";
		
		/*/Set some multicast variables
		try {
			socket = new MulticastSocket(8946);
			group = InetAddress.getByName("224.168.2.9");
			socket.joinGroup(group);
			buffer = new byte[256];
			byte b = '>'; //This character can NEVER be in a WiN message. This is a bad fix but it'll have to do for now.
			Arrays.fill(buffer,b);
			packet = new DatagramPacket(buffer, buffer.length);
		} catch (IOException e1) {
		}*/
		//Set variables for layout elements
		usernameTxt = (EditText)findViewById(R.id.usernameTxt);
		targetTxt = (EditText)findViewById(R.id.targetTxt);
		msgTxt = (EditText)findViewById(R.id.msgTxt);
		startBtn = (Button)findViewById(R.id.startBtn);
		sendBtn = (Button)findViewById(R.id.sendBtn);
		
		//Fill out username
		sprefs = this.getSharedPreferences("nz.net.win", Context.MODE_PRIVATE);
		username = sprefs.getString("username", null);
		
		if (username != null) {
			usernameTxt.setText(username);
		}
		
		//Grab a mcast lock... mmm, delicious
		WifiManager wifi = (WifiManager)getSystemService( Context.WIFI_SERVICE );
		if(wifi != null)
		{
			WifiManager.MulticastLock lock = wifi.createMulticastLock("WifiDevices");
			lock.acquire();
		}
		
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		        //try {
		        	//int loop = 1;
					//while (loop == 1) {
						//if (message[0].equalsIgnoreCase("True")) {
							Log.i("M2K", "OMG");
							//Toast.makeText(getApplicationContext(), "New message!", Toast.LENGTH_SHORT).show();
							//displayToast();
							//message[0] = "False";
						//}
					//}
		        //} catch (Exception e) {
		        //}
		    }
		});
		
		thread.start();
		
		//Client
		startBtn.setOnClickListener(
			new View.OnClickListener() {
				public void onClick(View v) {
					if (!usernameTxt.getText().toString().isEmpty()) {
						username = usernameTxt.getText().toString();
						sprefs.edit().putString("username", username).commit();
						
						//Start client
						Thread startMulticast = new Thread(new WiNClient());
						startMulticast.start();
					}
					else {
						Toast.makeText(getApplicationContext(), "Please fill out the username field.", Toast.LENGTH_SHORT).show();
					}
				}
			}
		);
		
		sendBtn.setOnClickListener(
			new View.OnClickListener() {
				public void onClick(View view) {
					if (!targetTxt.getText().toString().isEmpty() && !msgTxt.getText().toString().isEmpty() && !usernameTxt.getText().toString().isEmpty()) {
						//Save username for later use
						sprefs.edit().putString("username", usernameTxt.getText().toString()).commit();
						
						//Messages are encoded like so "senderProgramVx.x##target##sender##message"
						//Example: "linuxV1.8##person87##NickGeek##Hey mate! What do you think of this WiN thing?"
						formattedMessage = "androidVpre.release##"+targetTxt.getText().toString()+"##"+usernameTxt.getText().toString()+"##"+msgTxt.getText().toString();
						
						//Clear mesage textbox
						msgTxt.setText("");
						
						//Start Multicast
						Thread sendMulticast = new Thread(new WiNServer());
						sendMulticast.start();
					}
					else {
						Toast.makeText(getApplicationContext(), "Please fill out all the fields.", Toast.LENGTH_SHORT).show();
					}
				}
			});
	}
	
	/*public void onDestroy() {
		try {
			socket.leaveGroup(group);
			socket.close();
		} catch (IOException e) {
		}
	}*/

	public static String formattedMessage() {
		return formattedMessage;
	}
	
	public static String username() {
		return username;
	}
	
	public static void newMessage() {
		message[0] = "True";
		return;
	}
	
	public void displayToast() {
		Toast.makeText(getApplicationContext(), "New message!!", Toast.LENGTH_SHORT).show();
	}
}
