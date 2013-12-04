//Add features people should work on below:
//TODO: Turn the client part of this app into a background service or do something to stop android killing it so quickly

package nz.net.win;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText usernameTxt;
	EditText targetTxt;
	EditText msgTxt;
	Button startBtn;
	Button sendBtn;
	static String formattedMessage;
	SharedPreferences sprefs;
	static String username;
	static String[] message = new String[2];
	static Boolean newMessage = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
		
		//Fill out target
		String target = sprefs.getString("target", null);
		
		if (target != null) {
			targetTxt.setText(target);
			sprefs.edit().putString("target", "").commit();
		}
		
		//Grab a mcast lock... mmm, delicious
		WifiManager wifi = (WifiManager)getSystemService( Context.WIFI_SERVICE );
		if(wifi != null)
		{
			WifiManager.MulticastLock lock = wifi.createMulticastLock("WifiDevices");
			lock.acquire();
		}
		
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
						formattedMessage = "androidV0.1##"+targetTxt.getText().toString()+"##"+usernameTxt.getText().toString()+"##"+msgTxt.getText().toString();
						
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
		
		//Notifications
		Runnable notificationLoop = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
					while (newMessage) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
						}
						//Switch over to the UI thread to show the notification
						runOnUiThread(new Runnable() {
							public void run() {
								//Toast.makeText(getApplicationContext(), message[0] + " -" + message[1], Toast.LENGTH_LONG).show();
								showNotification();
								sprefs.edit().putString("target", message[0]).commit();
							}
						});
						
						newMessage = false;
					}
				}
			}
		};
		
		//Run the notification thread
		Thread notificationThread = new Thread(notificationLoop);
		notificationThread.start();
	}

	public static String formattedMessage() {
		return formattedMessage;
	}
	
	public static String username() {
		return username;
	}
	
	public static void newMessage(String sender, String msg) {
		//Index 0 is the sender and index 1 is the message
		message[0] = sender;
		message[1] = msg;
		
		//Clear out the variables to avoid issues
		sender = null;
		msg = null;
		
		newMessage = true;
		return;
	}
	
	public void showNotification() {
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle(message[0])
		        .setContentText(message[1]);
		Intent resultIntent = new Intent(this, MainActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		//I'm setting this to false for now because there is currently not a setup to display the message once the notification is closed
		mBuilder.setAutoCancel(false);
		
		//Sound and vibration
		Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		mBuilder.setSound(sound);
		mBuilder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
		
		//Expandable notification
		mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message[1]));
		
		mNotificationManager.notify(1, mBuilder.build());
	}
}
