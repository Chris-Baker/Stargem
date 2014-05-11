entities = {}

entities.tmp1 = luajava.newInstance("com.badlogic.gdx.math.Vector3")
entities.tmp2 = luajava.newInstance("com.badlogic.gdx.math.Vector3")
entities.tmp3 = luajava.newInstance("com.badlogic.gdx.math.Vector3")

function entities.orient(transform)    
  transform:getTranslation(entities.tmp1);
  transform:getTranslation(entities.tmp2);
  entities.tmp3:set(0, 1, 0)
  entities.tmp1:nor() -- this is up vector
  transform:setToRotation(entities.tmp3, entities.tmp1) -- defaultUp, up
  transform:trn(entities.tmp2); -- position
  return transform
end

-- spawn a health pack at the given location
function entities.healthPack(transform)
  transform = entities.orient(transform)

  local entity = em:createEntity()  

  local bodyType = physicsManager.RIGID_BODY
  local collisionGroup = 5
  local collidesWith = 35
  local shape = physicsManager.SHAPE_BOX
  local width = 0.2
  local height = 0.2
  local depth = 0.2
  local mass = 2
  local restitution = 0
  local contactGroup = 4096 + 8
  local contactWith = 264
  
  em:addComponent(entity, ComponentFactory:physicsSimple(entity, bodyType, collisionGroup, collidesWith, transform, shape, width, height, depth, mass, restitution, contactGroup, contactWith));
  em:addComponent(entity, ComponentFactory:renderablestatic(entity, 0, "data/models/mine_bot.g3dj"))

end

-- spawn a small gem at the given location
function entities.smallGem(transform)
  transform = entities.orient(transform)

  local entity = em:createEntity()  

  debug("" .. entity:getId())

  local bodyType = physicsManager.RIGID_BODY
  local collisionGroup = 5
  local collidesWith = 35
  local shape = physicsManager.SHAPE_BOX
  local width = 0.2
  local height = 0.2
  local depth = 0.2
  local mass = 1
  local restitution = 0
  local contactGroup = 16384 + 8
  local contactWith = 264
  
  em:addComponent(entity, ComponentFactory:physicsSimple(entity, bodyType, collisionGroup, collidesWith, transform, shape, width, height, depth, mass, restitution, contactGroup, contactWith));
  em:addComponent(entity, ComponentFactory:renderablestatic(entity, 0, "data/models/mine_bot.g3dj"))

end

-- spawn a large gem at the given location
-- 32768
function entities.largeGem(transform)
  transform = entities.orient(transform)

  local entity = em:createEntity()  

  debug("" .. entity:getId())

  local bodyType = physicsManager.RIGID_BODY
  local collisionGroup = 5
  local collidesWith = 35
  local shape = physicsManager.SHAPE_BOX
  local width = 0.2
  local height = 0.2
  local depth = 0.2
  local mass = 1
  local restitution = 0
  local contactGroup = 32768 + 8
  local contactWith = 264
  
  em:addComponent(entity, ComponentFactory:physicsSimple(entity, bodyType, collisionGroup, collidesWith, transform, shape, width, height, depth, mass, restitution, contactGroup, contactWith));
  em:addComponent(entity, ComponentFactory:renderablestatic(entity, 0, "data/models/mine_bot.g3dj"))

end

-- spawn a special power at the given location
function entities.specialPower(transform)
transform = entities.orient(transform)

  local entity = em:createEntity()  

  debug("" .. entity:getId())
  
  local bodyType = physicsManager.RIGID_BODY
  local collisionGroup = 5
  local collidesWith = 35
  local shape = physicsManager.SHAPE_BOX
  local width = 0.2
  local height = 0.2
  local depth = 0.2
  local mass = 1
  local restitution = 0
  local contactGroup = 131072 + 8
  local contactWith = 264
  
  em:addComponent(entity, ComponentFactory:physicsSimple(entity, bodyType, collisionGroup, collidesWith, transform, shape, width, height, depth, mass, restitution, contactGroup, contactWith));
  em:addComponent(entity, ComponentFactory:renderablestatic(entity, 0, "data/models/mine_bot.g3dj"))

end

-- spawn a power core at the given location
function entities.powerCore(transform)
  transform = entities.orient(transform)

  local entity = em:createEntity()  

  debug("" .. entity:getId())

  local bodyType = physicsManager.RIGID_BODY
  local collisionGroup = 5
  local collidesWith = 35
  local shape = physicsManager.SHAPE_BOX
  local width = 0.2
  local height = 0.2
  local depth = 0.2
  local mass = 1
  local restitution = 0
  local contactGroup = 8192 + 8
  local contactWith = 264
  debug("middle")
  em:addComponent(entity, ComponentFactory:physicsSimple(entity, bodyType, collisionGroup, collidesWith, transform, shape, width, height, depth, mass, restitution, contactGroup, contactWith));
  em:addComponent(entity, ComponentFactory:renderablestatic(entity, 0, "data/models/mine_bot.g3dj"))
  debug("bottom")
end

-- spawn a minebot at the given location
function entities.minebot(transform, gems, specials, cores)
  transform = entities.orient(transform)
  local entity = em:createEntity()  
  
  local bodyType = physicsManager.CHARACTER
  local collisionGroup = 36
  local collidesWith = 3
  local shape = physicsManager.SHAPE_CAPSULE
  local width = 0.4
  local height = 0.4
  local depth = 0.4
  local mass = 0
  local restitution = 0
  local contactGroup = 520
  local contactWith = 8  
  
  em:addComponent(entity, ComponentFactory:physicsSimple(entity, bodyType, collisionGroup, collidesWith, transform, shape, width, height, depth, mass, restitution, contactGroup, contactWith));
  em:addComponent(entity, ComponentFactory:controller(entity, 0, 3, "minebot", "minebot", false, false, false, false, false));
  em:addComponent(entity, ComponentFactory:renderableskinned(entity, 0, "data/models/mine_bot.g3dj", "idle"))
  em:addComponent(entity, ComponentFactory:health(entity, 100, 100));
  em:addComponent(entity, ComponentFactory:runspeed(entity, 3));
  em:addComponent(entity, ComponentFactory:weapon(entity, 1, 1, false, true, 0, 0, 0, 0, 0, 0, 0, 0));
  em:addComponent(entity, ComponentFactory:inventory(entity, cores, specials, gems));
  -- parent a sensor to this entity
  entities.aiSensor(entity,5,transform)
  
end

function entities.aiSensor(parent, radius, transform)
  transform = entities.orient(transform)
  local entity = em:createEntity()
  
  local bodyType = physicsManager.RIGID_BODY
  local collisionGroup = 5
  local collidesWith = 37
  local shape = physicsManager.SHAPE_SPHERE
  local width = radius
  local height = radius
  local depth = radius
  local mass = 0
  local restitution = 0
  local contactGroup = 65536
  local contactWith = 264
  
  em:addComponent(entity, ComponentFactory:physicsSimple(entity, bodyType, collisionGroup, collidesWith, transform, shape, width, height, depth, mass, restitution, contactGroup, contactWith));
  em:addComponent(entity, ComponentFactory:parent(entity, parent:getId()))

end

-- spawn a damage zone at the given location
-- 1048576
function entities.damageZone(transform)
  transform = entities.orient(transform)

  local entity = em:createEntity()  

  local bodyType = physicsManager.RIGID_BODY
  local collisionGroup = 5
  local collidesWith = 37
  local shape = physicsManager.SHAPE_SPHERE
  local width = 2
  local height = 2
  local depth = 2
  local mass = 0
  local restitution = 0
  local contactGroup = 1048576 + 8
  local contactWith = 264
  
  em:addComponent(entity, ComponentFactory:physicsSimple(entity, bodyType, collisionGroup, collidesWith, transform, shape, width, height, depth, mass, restitution, contactGroup, contactWith));
  em:addComponent(entity, ComponentFactory:timer(entity, 0.01))
  em:addComponent(entity, ComponentFactory:trigger(entity, "remove"))
end

-- spawn an inactive at the given location
function entities.gate(transform)
  transform = entities.orient(transform)

  local entity = em:createEntity()  

  local bodyType = physicsManager.RIGID_BODY
  local collisionGroup = 5
  local collidesWith = 37
  local shape = physicsManager.SHAPE_BOX
  local width = 2.5
  local height = 3.5
  local depth = 0.25
  local mass = 0
  local restitution = 0
  local contactGroup = 128 + 8
  local contactWith = 264
  
  em:addComponent(entity, ComponentFactory:physicsSimple(entity, bodyType, collisionGroup, collidesWith, transform, shape, width, height, depth, mass, restitution, contactGroup, contactWith));
  em:addComponent(entity, ComponentFactory:renderablestatic(entity, 0, "data/models/Gate.g3dj"))
  
  return entity:getId()
end