-- these two lines enable importing other Lua files
ScriptManager = luajava.bindClass("com.stargem.scripting.ScriptManager")
script = ScriptManager:getInstance()
script:require("Init")

triggers = {}

triggers.powerCores = 0
triggers.enemiesKilledSinceLastPowerCore = 0
triggers.exitGateEntityID = 0

triggers.transform = luajava.newInstance("com.badlogic.gdx.math.Matrix4")
triggers.from = luajava.newInstance("com.badlogic.gdx.math.Vector3")
triggers.to = luajava.newInstance("com.badlogic.gdx.math.Vector3")
triggers.rayTestCB = luajava.newInstance("com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback", triggers.from, triggers.to)
triggers.hitLocation = luajava.newInstance("com.badlogic.gdx.math.Vector3")
triggers.radius = 90

function triggers.enemyDied()

  -- spawn a new enemy
  -- log how many enemies have died since the last power core dropped
  -- increase chance of dropping

end

--function triggers.buildWorld()
--  triggers.exitGateEntityID = triggers.northGate()
--end

--function triggers.northGate()
--  -- add two gates to the world get the transforms by raycasting from above and below the world  
--  triggers.from:set(0, triggers.radius * 3, 0)
--  triggers.to:set(0, 0, 0)
--  physicsManager:rayTest(triggers.from, triggers.to, triggers.rayTestCB)
--  
--  -- we know direction of this cast is (0, -1, 0) so we scale that by the fraction to get
--  -- point of impact and place the gate there
--  triggers.hitLocation:set(0, -1, 0):scl(rayTestCB:getClosestHitFraction())
--  
--  triggers.transform:idt()
--  triggers.transform:trn(triggers.hitLocation)
--  
--  return entities.gate(triggers.transform)
--end

function triggers.onPickupPowerCore()
  triggers.powerCores = triggers.powerCores + 1
  if triggers.powerCores == 3 then
    -- get the exit gate entity
    local entityID = gateManager:getExitGateID()
    local entity = em:getEntityByID(entityID)
    
    -- activate the gate
    
    -- change the model
    em:removeComponent(entity, RenderableStatic)
    em:addComponent(entity, ComponentFactory:renderableskinned(entity, 0, "data/models/GateActive.g3dj", "Take 001"))
        
    -- add a trigger to exit
    em:addComponent(entity, ComponentFactory:trigger(entity, "changeWorld"))
    
    -- set the animation, animation is broken because UV changes are not saved in file format
    --local animation = representationManager:getAnimationController(entityID)
    --animation:setAnimation("Take 001", -1)
    
  end
end

function triggers.changeWorld()
  
  gameManager:changeWorld("Stargem", "World_01")
  gameManager:loadGame()
  --debug("change levels")
  
end

 -- remove the entity
function triggers.remove(entity)
  em:recycle(entity)
end