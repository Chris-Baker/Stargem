/**
 * 
 */
package com.stargem;

import com.badlogic.gdx.utils.IntMap;
import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;
import com.stargem.utils.Log;

/**
 * PlayersManager.java
 * 
 * The players manager tracks which entities are players.
 * The local localPlayer is stored as a field and a list of all network players
 * is stored which also includes the local localPlayer.
 * 
 * The manager is responsible for unloading entities during a world switch
 * but keeping the players in memory.
 * 
 * Once the new worlds entities have been loaded into the entity manager
 * the players are moved to their starting locations.
 * 
 * 
 *
 * @author 	Chris B
 * @date	1 Feb 2014
 * @version	1.0
 */
public class PlayersManager {
	
	// a counter which tracks the next localPlayer number to be assigned
	// localPlayer numbers are used as keys in the Map which stores localPlayer entities
	private int nextPlayerNum = 1;
	
	// the local localPlayer number (localPlayer 0, localPlayer 1, localPlayer 2, etc...)
	// this is actually the index into the players array.
	// the array is the same across all players.
	private int localPlayer;
	
	// array of localPlayer entities this is used to track network players
	// the key identifies which network connection the localPlayer is?
	private final IntMap<Entity> players;
	
	private static PlayersManager instance;
	public static PlayersManager getInstance() {
		if(instance == null) {
			instance = new PlayersManager();
		}		
		return instance;		
	}
	
	private PlayersManager() {
		this.players = new IntMap<Entity>(2);		
	}

	/**
	 * Set the local localPlayer to the entity provided. The local localPlayer is not always
	 * localPlayer 0. If this is a network game then it is very likely the local localPlayer
	 * will be a different number.
	 * 
	 * @param entity the entity representing the local localPlayer
	 */
	public void setLocalPlayer(int playerNum) {
		this.localPlayer = playerNum;
	}
	
	/**
	 * @return the entity representing the local localPlayer
	 */
	public Entity getLocalPlayer() {
		return players.get(this.localPlayer);
	}
	
	/**
	 * Return the number of players in this game.
	 * 
	 * @return the number of players in this game.
	 */
	public int getNumPlayers() {
		return this.players.size;
	}
	
	/**
	 * Set all the localPlayer entity IDs to 0. This happens when a new world is loaded and
	 * a new set of entities is imported because the new world will have its own set 
	 * of player entity IDs.
	 */
	public void resetPlayerIds() {
		EntityManager em = EntityManager.getInstance();
		for(Entity entity : this.players.values()) {
			if(entity != null) {
				em.setEntityId(entity, 0);
			}
		}
	}

	/**
	 * Check whether or not the given entity is a localPlayer entity
	 * 
	 * @param entity
	 * @return true is the given entity is a localPlayer, false otherwise.
	 */
	public boolean playerExists(Entity entity) {
		return this.players.containsValue(entity, false);
	}

	/**
	 * Update all players in the game with their new entity IDs
	 * 
	 * @param playerIDs the map of player numbers and entity IDs
	 */
	public void setPlayerIDs(IntMap<Integer> playerIDs) {
		EntityManager em = EntityManager.getInstance();
		for(Integer entityId : playerIDs.values()) {
			
			int playerNum = playerIDs.findKey(entityId, false, -1);
			
			if(playerNum == -1) {
				String message = "A player number key could not be found in the map of entity IDs. Whilst setting new player entity IDs.";
				Log.error(Config.ENTITY_ERR, message);
				throw new Error(message);
			}
			
			// if there is a player in the game with the player number then update its entity id.
			if(this.players.containsKey(playerNum)) {				
				Entity e = this.players.get(playerNum);
				
				// the entity can be null if this is the first load of the game
				if(e != null) {
					em.setEntityId(e, entityId);
				}
			}
		}
	}
	
	/**
	 * Join a player to the game. This method issues a player number and stores
	 * it in the map of players. A null value is set as the entity associated with
	 * the player. This will be added by the entity loader which will add an entity
	 * for each player that has joined the game. 
	 * 
	 * @return
	 */
	public int join() {
		int playerNum = this.nextPlayerNum;
		this.players.put(playerNum, null);
		this.nextPlayerNum += 1;
		return playerNum;
	}	
	
	/**
	 * Remove a player from the game. This removes a player number
	 * and the associated entity entry from the map of players
	 * 
	 * @param playerNum
	 */
	public void leave(int playerNum) {
		this.players.remove(playerNum);
	}

	/**
	 * Check if a player has joined and that the player entity is not null.
	 * 
	 * @return true if the player has joined and the entity is not null
	 */
	public boolean playerEntityExists(int playerNum) {
		return (this.players.containsKey(playerNum) && this.players.get(playerNum) != null);
	}

	/**
	 * Returns the player entity associated with the player number given.
	 * 
	 * @param playerNum
	 * @return
	 */
	public Entity getPlayerEntity(int playerNum) {		
		if(playerEntityExists(playerNum)) {
			return this.players.get(playerNum);
		}
		else {
			throw new RuntimeException("Player entity doesn't exist or is null for the given player number " + playerNum);
		}
	}
	
	/**
	 * Add an entity object to an already joined player.
	 * 
	 * @param playerNum
	 * @param e
	 */
	public void addPlayerEntity(int playerNum, Entity e) {
		if(this.players.containsKey(playerNum))	{
			this.players.put(playerNum, e);
		}
		else {
			throw new RuntimeException("Cannot add an entity to a player who hasn't joined the game.");
		}
	}

	/**
	 * Check to see if the player number given has joined the game.
	 * 
	 * @param playerNum
	 * @return true if the player number has joined the game, false otherwise.
	 */
	public boolean hasJoined(int playerNum) {
		return this.players.containsKey(playerNum);
	}
	
}