package org.asteriskjava.examples.joseph;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.asteriskjava.live.*;
import org.asteriskjava.live.internal.AsteriskAgentImpl;

public class LiveListenerTest extends AbstractAsteriskServerListener implements PropertyChangeListener {

	public LiveListenerTest() {
		AsteriskServer asteriskServer = new DefaultAsteriskServer("10.4.14.119","myconnection","agoodpassword");

		asteriskServer.addAsteriskServerListener(this);
	}

	public void onNewAsteriskChannel(AsteriskChannel channelParam) {
		System.out.println("onNewAsteriskChannel : => " + channelParam);
		channelParam.addPropertyChangeListener(this);
	}

	public void onNewMeetMeUser(MeetMeUser userParam) {
		System.out.println("onNewMeetMeUser :      => " + userParam);

		userParam.addPropertyChangeListener(this);
	}

	@Override
	public void onNewAgent(AsteriskAgent agent) {
		System.out.println("onNewAgent :           => " + agent);

	}

	public void onNewAgent(AsteriskAgentImpl agentParam) {

		System.out.println("onNewAgent :           => " +  agentParam);

		agentParam.addPropertyChangeListener(this);
	}

	public void onNewQueueEntry(AsteriskQueueEntry entryParam) {
		System.out.println("onNewQueueEntry :      => " + entryParam);
		entryParam.addPropertyChangeListener(this);
	}

	public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
		System.out.println("propertyChange :       => " + propertyChangeEvent);
	}

	public static void main(String[] args) {
		new LiveListenerTest();
		while (true) {

		}
	}
}