package org.asteriskjava.examples.activities;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.AgiServerThread;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.asteriskjava.fastagi.ClassNameMappingStrategy;
import org.asteriskjava.fastagi.DefaultAgiServer;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.action.OriginateAction;
import org.asteriskjava.manager.response.ManagerResponse;

public class SimpleAsteriskTest extends BaseAgiScript {

	public void service(AgiRequest request, AgiChannel channel) throws AgiException {
		answer();

		int retCode = 0;

		while (retCode == 0) {
			retCode = exec("Festival", "This is a test of the festival text to speech system.");
			System.out.println ("Read digit "+retCode);
		}
		hangup();

	}

	public static void main (String[] args) throws Exception {
		DefaultAgiServer server = new DefaultAgiServer();
		server.setPort(5060);
		ClassNameMappingStrategy strategy = new ClassNameMappingStrategy();
		server.setMappingStrategy(strategy);
		AgiServerThread thread = new AgiServerThread(server);
		thread.startup();
		System.out.println ("Server started");


		// Do the outbound
		ManagerConnection con = new ManagerConnectionFactory("10.4.14.119", "myconnection", "agoodpassword").createManagerConnection();
		con.login();
		OriginateAction origAction = new OriginateAction();
		origAction.setChannel("SIP/james");
		origAction.setContext("phones");
		origAction.setCallerId("james");
		origAction.setExten("100");
		origAction.setPriority(new Integer(1));
		origAction.setActionId("Calling Mathias");
		origAction.setVariable("ALERT_INFO","myUniqueValue");

		ManagerResponse origResponse = con.sendAction(origAction,3000);
		String alertinfo = origResponse.getAttribute("ALERT_INFO");
		System.out.println("Alert Info : " + alertinfo);
		if (origResponse.getResponse().toLowerCase().indexOf("error") >= 0) {
			System.out.println ("Error: "+origResponse.getMessage());
		}
		System.out.println ("Originate Response = "+origResponse);
		con.logoff();

		while (true) {
			Thread.sleep(100000);
			System.out.println(".");
		}
	}

}