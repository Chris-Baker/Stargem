entities = {}
factory = {}

--
-- Load all entities from the current database
--
function entities.load()
	
	-- get the number of entities in the world
	numEntities = persistenceManager:beginLoading()
	
	debug("loading entities")
	
	-- iterate creating the entities and populating them with their components
	for i = 1, numEntities do
		
		-- load an entity and get a reference to it
		e = persistenceManager:loadEntity()
				
		-- if the entity is not null then store it
		if e == nil then
		  debug("Throwing away one null player entity reference because the player associated with it hasn't joined the game")
		else
		  entities[e:getId()] = e
		end
	
	end
	
	debug("loaded all entities")
	
end

-- spawn a small gem at the given location
function factory.smallGem(transform)

end

-- spawn a large gem at the given location
function factory.largeGem(transform)

end

-- spawn a special power at the given location
function factory.specialPower(transform)

end

-- spawn a power core at the given location
function factory.powerCore(transform)

end

-- spawn a minebot at the given location
function factory.minebot(transform)

end

-- spawn a damage zone at the given location
function factory.damageZone(transform)

end
