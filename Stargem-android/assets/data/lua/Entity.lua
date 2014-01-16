-- Import the entity manager and component factory
EntityManager = luajava.bindClass("com.stargem.entity.EntityManager")
EntityPersistenceManager = luajava.bindClass("com.stargem.sql.EntityPersistenceManager")
ComponentFactory = luajava.bindClass("com.stargem.entity.ComponentFactory")

-- get the entity  manager instance
em = EntityManager:getInstance()
persistence = EntityPersistenceManager:getInstance()

entities = {}

--
-- Load all entities from the current database
--
function entities.load()
	
	numEntities = persistence:beginLoading()
	for i = 1, numEntities do
		e = em:createEntity()
		persistence:loadEntity(e)
		entities[e:getId()] = e
	end
	
	echo("loaded all entities")
	
end