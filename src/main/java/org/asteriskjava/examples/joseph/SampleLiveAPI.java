package org.asteriskjava.examples.joseph;


import org.asteriskjava.live.AsteriskChannel;
import org.asteriskjava.live.internal.AsteriskServerImpl;

import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;

import org.asteriskjava.manager.event.PeerEntryEvent;

import java.util.List;


import java.io.IOException;
import java.util.List;

public class SampleLiveAPI {

	public static void main(String[] args) {

		ManagerConnectionFactory factory = new ManagerConnectionFactory("10.4.14.119","myconnection","agoodpassword");

		//Retrieve the connection from the factory
		ManagerConnection managerconnection = factory.createManagerConnection();



		try {

			managerconnection.login();
			AsteriskServerImpl asteriskServer = new AsteriskServerImpl(managerconnection);

			// Retrieve the list of configured peers
			List <PeerEntryEvent> peerEntries = asteriskServer.getPeerEntries();
			PeerEntryEvent peerToCall = null;
			// Iterate over peers
			for (PeerEntryEvent peerEntry : peerEntries) {
				String status = peerEntry.getStatus();
				// Select the first one connected and exit the loop
				System.out.println("SIP : " + peerEntry.getObjectName());
				System.out.println("No of entry  " + peerEntries.size() );
				System.out.println("Status " + status);
				if (status != null && status.contains("OK")) {
					peerToCall = peerEntry;
					break;
				}
			}
			// If no peer is connected exit from method
			if (peerToCall == null) {
				return;
			}
			// Build the channel name
			String channelName = peerToCall.getChannelType();
			channelName += "/" + peerToCall.getObjectName();

			System.out.println("CallingchannelName");
			AsteriskChannel asteriskChannel = asteriskServer.originateToExtension(channelName, "phones",   "100", 1, 100000);
			Thread.sleep(10000);
			System.out.println("Playingon " + channelName);
			asteriskChannel.playDtmf("1");

		} catch ( Exception e) {
			/* Manage exception */
			e.printStackTrace();
		}
	}

}
