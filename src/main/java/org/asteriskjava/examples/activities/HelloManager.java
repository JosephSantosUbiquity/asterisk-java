package org.asteriskjava.examples.activities;

import java.io.IOException;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.OriginateAction;
import org.asteriskjava.manager.response.ManagerResponse;

public class HelloManager
{
	private ManagerConnection managerConnection;

	public HelloManager() throws IOException
	{
		ManagerConnectionFactory factory = new ManagerConnectionFactory(
			"10.4.14.119", "myconnection", "agoodpassword");

		this.managerConnection = factory.createManagerConnection();
	}

	public void run() throws IOException, AuthenticationFailedException,
		TimeoutException
	{
		OriginateAction originateAction;
		ManagerResponse originateResponse;

		originateAction = new OriginateAction();
		originateAction.setChannel("SIP/mathias");
		originateAction.setContext("phones");
		originateAction.setExten("100");
		originateAction.setPriority(new Integer(1));

		// connect to Asterisk and log in
		managerConnection.login();

		// send the originate action and wait for a maximum of 30 seconds for Asterisk
		// to send a reply
		originateResponse = managerConnection.sendAction(originateAction, 3000000);

		// print out whether the originate succeeded or not
		System.out.println(originateResponse.getResponse());

		// and finally log off and disconnect
		managerConnection.logoff();
	}

	public static void main(String[] args) throws Exception
	{
		HelloManager helloManager;

		helloManager = new HelloManager();
		helloManager.run();
	}
}