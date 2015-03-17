package com.togocms.pns;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class AndroidNotification extends AbstractNotification {

	public List<String> send() throws Exception {
		Sender sender = new Sender(getSecretKey());
		Message message = new Message.Builder().addData("TogoMessage", getTitle()).addData("TogoInfo", getPayload()).build();
		MulticastResult multicastResult = sender.send(message, getTokens(), 0);
		List<String> badTokens = new ArrayList<String>();
		int i = 0;
		for (Result result : multicastResult.getResults()) {
			if (result.getErrorCodeName() != null) {
				badTokens.add(getTokens().get(i));
			}
			i++;
		}
		return badTokens;
	}
}
