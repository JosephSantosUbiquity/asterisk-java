package org.asteriskjava.examples.joseph;

import org.asteriskjava.pbx.*;
import org.asteriskjava.pbx.activities.DialActivity;
import org.asteriskjava.pbx.internal.core.AsteriskPBX;

public class FromSample {

	public static void main(String[] args) throws Exception {
		/**
		 * Initialise the PBX Factory. You need to implement your own AsteriskSettings class.
		 */
		PBXFactory.init(new MyAsteriskSettings());

		/**
		 * Activities utilise an agi entry point in your dial plan.
		 * You can create your own entry point in dialplan or have
		 * asterisk-java add it automatically as we do here.
		 */
		AsteriskPBX asteriskPbx = (AsteriskPBX) PBXFactory.getActivePBX();
		asteriskPbx.createAgiEntryPoint();

		try
		{
			PBX pbx = PBXFactory.getActivePBX();

			// We are going to dial from extension 100 to 5551234

			// The trunk MUST match the section header (e.g. [default]) that appears
			// in your /etc/asterisk/sip.d file (assuming you are using a SIP trunk).
			// The trunk is used to select which SIP trunk to dial through.
			//Trunk trunk = pbx.buildTrunk("default");
			Trunk trunk = pbx.buildTrunk("phones");

			// We are going to dial from extension 100
			//EndPoint from = pbx.buildEndPoint(TechType.SIP, "100");
			EndPoint from = pbx.buildEndPoint(TechType.SIP, "mathias");
			// Provide confirmation to the agent which no. we are dialing by
			// showing it on their handset.
			//CallerID fromCallerID = pbx.buildCallerID("5551234", "Dialing");
			CallerID fromCallerID = pbx.buildCallerID("200", "Dailing");

			// The caller ID to display on the called parties phone.
			// On most systems the caller id name part won't display
			//CallerID toCallerID = pbx.buildCallerID("5558100", "Asterisk Java is calling");
			CallerID toCallerID = pbx.buildCallerID("100", "James is calling");
			// The party we are going to call.
			//EndPoint to = pbx.buildEndPoint(TechType.SIP, trunk, "5551234");
			EndPoint to = pbx.buildEndPoint(TechType.SIP, trunk, "200");

			// Now dial. This method won't return until the call is answered
			// or the dial timeout is reached.
			//DialActivity dial = pbx.dial(null, from, fromCallerID, to, toCallerID);
			DialActivity dial = pbx.dial( from, fromCallerID, to, toCallerID);

			// We should have a live call here.
			Call call = dial.getNewCall();

			Thread.sleep(2000000);

			// Bit tired now so time to hangup.
			pbx.hangup(call);
		}
		catch (PBXException | InterruptedException e)
		{
			System.out.println(e);
		}


	}
}
