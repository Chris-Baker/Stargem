entities = {}

--
-- Load all entities from the current database
--
function entities.load()
	
	-- get the number of entities in the world
	numEntities = persistence:beginLoading()
	
	debug("loading entities")
	
	-- iterate creating the entities and populating them with their components
	for i = 1, numEntities do
		
		-- load an entity and get a reference to it
		e = persistence:loadEntity()
				
		-- if the entity is not null then store it
		if e == nil then
		  debug("Throwing away one null player entity reference because the player associated with it hasn't joined the game")
		else
		  entities[e:getId()] = e
		end
	
	end
	
	debug("loaded all entities")
	
end