package com.practice;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.gax.rpc.Batch;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;

public class PushNotification {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FirebaseOptions options;
		try {
			options = FirebaseOptions.builder().setCredentials(GoogleCredentials.getApplicationDefault()).build();
			FirebaseApp.initializeApp(options);

			// This registration token comes from the client FCM SDKs.
			String registrationToken = "<user_token>";

			List<String> registrationTokens = Arrays.asList(
					"<user_token1>",
					"<user_token2>");

			// See documentation on defining a message payload.
			Notification notification = Notification.builder().setTitle("Case Assigned")
					.setBody("A case has been assigned to you.").build();

			WebpushNotification webpushNotification = WebpushNotification.builder()
					.setIcon("http://localhost:8081/firebase-logo.png").build();
			WebpushConfig webpushConfig = WebpushConfig.builder().setNotification(webpushNotification).build();

			Message message = Message.builder().setNotification(notification).setWebpushConfig(webpushConfig)
					.setToken(registrationToken).build();

			MulticastMessage multicastMessage = MulticastMessage.builder().setNotification(notification)
					.setWebpushConfig(webpushConfig).addAllTokens(registrationTokens).build();

			// Send a message to the device corresponding to the provided
			// registration token.
			// String response = FirebaseMessaging.getInstance().send(message);
			BatchResponse batchResponse = FirebaseMessaging.getInstance().sendMulticast(multicastMessage);
			// Response is a message ID string.
			// System.out.println("Successfully sent message: " + response);
			System.out.println(batchResponse.getSuccessCount() + " messages were sent successfully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FirebaseMessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
