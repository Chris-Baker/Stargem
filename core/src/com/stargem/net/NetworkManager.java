/**
 * 
 */
package com.stargem.net;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.stargem.Config;
import com.stargem.net.messages.NewPlayerMessage;
import com.stargem.utils.Log;

/**
 * NetworkManager.java
 * 
 * @author Chris B
 * @date 29 Apr 2014
 * @version 1.0
 */
public class NetworkManager {

	private final static NetworkManager instance = new NetworkManager();

	public static NetworkManager getInstance() {
		return instance;
	}

	private NetworkManager() {
	}

	public void startServer(int TCPPort, int UDPPort) {
		try {
			
			// make a server
			Server server = new Server();
			server.start();
			server.bind(TCPPort, UDPPort);

			// register a server listener which waits for new player messages
			server.addListener(new Listener() {
				@Override
				public void received(Connection connection, Object object) {
					if (object instanceof NewPlayerMessage) {
						NewPlayerMessage message = (NewPlayerMessage) object;
						Log.debug(Config.NET_ERR, message.text);
						
						NewPlayerMessage response = new NewPlayerMessage();
						response.text = "Server Thanks";
						connection.sendTCP(response);
					}
				}
			});
			
			// make a client
			Client client = new Client();
		    client.start();
		    client.connect(5000, "192.168.0.6", 13000, 13001);

		    client.addListener(new Listener() {
				@Override
				public void received(Connection connection, Object object) {
					if (object instanceof NewPlayerMessage) {
						NewPlayerMessage message = (NewPlayerMessage) object;
						Log.debug(Config.NET_ERR, message.text);
						
						NewPlayerMessage response = new NewPlayerMessage();
						response.text = "Client Thanks";
						connection.sendTCP(response);
					}
				}
			});
		    
		    // register some classes with client and server
			Kryo kryo; 
			kryo = server.getKryo();
		    kryo.register(NewPlayerMessage.class);
		    kryo= client.getKryo();
		    kryo.register(NewPlayerMessage.class);
		    
		    // send a message
		    NewPlayerMessage message = new NewPlayerMessage();
		    message.text = "Here is the message";
		    client.sendTCP(message);
		    
		}
		catch (IOException e) {
			Log.error(Config.NET_ERR, e.getMessage());
		}
	}

	// https://github.com/EsotericSoftware/kryonet
	
	// using Kryo for p2p
	// each client has a server
	// each client has a client instance for every other client
	// everyone connects to everyone else's server
	// those servers only push updates about their sphere of influence

	// we need to register all components with Kryo

	// we also need to create a command to update each component type
	// can this be done with reflection? much neater yes easy

	// we need to store a lookup for entity ID consistency
	// when we create an entity we push its creation and ID
	// the client will create it and either agree on the id or send a message to say
	// what its local ID is, we store this in a lookup for that client
	// updates from that client have their ID switched for ones in the lookup

}