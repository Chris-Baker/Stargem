-- Import the entity manager, the persistence manager and the entity persistence layer
EntityManager = luajava.bindClass("com.stargem.entity.EntityManager")
EntityPersistence = luajava.bindClass("com.stargem.persistence.EntityPersistence")
PersistenceManager = luajava.bindClass("com.stargem.persistence.PersistenceManager")

-- get the entity  manager instance
em = EntityManager:getInstance()
persistence = PersistenceManager:getInstance():getEntityPersistence()

entities = {}

--
-- Load all entities from the current database
--
function entities.load()
	
	-- get the number of entities in the world
	numEntities = persistence:beginLoading()
	
	info("loading entities")
	
	-- iterate creating the entities and populating them with their components
	for i = 1, numEntities do
		
		-- load an entity and get a reference to it
		e = persistence:loadEntity()
				
		-- if the entity is not null then store it
		if e == nil then
		  info("Throwing away one null player entity reference because the player associated with it hasn't joined the game")
		else
		  entities[e:getId()] = e
		end
	
	end
	
	info("loaded all entities")
	
end