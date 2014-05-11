behaviour = {}

function behaviour.jenny(entity)
  
  local behaviour = {}
  local behaviourProxy = luajava.createProxy("com.stargem.behaviour.BehaviourStrategy", behaviour)
  behaviourManager:addBehaviour(entity:getId(), behaviourProxy)

  --  behaviour.onDamaged = function()
  --    -- play a sound
  --  end
  
  behaviour.onDeath = function()
    
    -- cash penalty for death
    local inventory = em:getComponent(entity, Inventory)
    if inventory ~= nil then
    
      script:setField(inventory, "gems", inventory.gems - 20)
    
      if inventory.gems < 0 then
        script:setField(inventory, "gems", 0)
      end
    end
    
    -- reset health to max
    local health = em:getComponent(entity, Health)
    if health ~= nil then    
      local maxHealth = health.maxHealth
      
      local skill = em:getComponent(entity, SkillModifiers)
      if skill ~= nil then
        maxHealth = maxHealth + skill.healthIncrease
      end    
      script:setField(health, "currentHealth", maxHealth)
    end
    
    -- move the player to the entrance gate
    local gateID = gateManager:getEntranceGateID()
    local gateEntity = em:getEntityByID(gateID)
--    
    local gate = physicsManager:getRigidBody(gateEntity:getId())
    local character = physicsManager:getCharacter(entity:getId())
--    
    character:teleportTo(gate)
    
  end

end

function behaviour.minebot(entity, brain)
  
  -- set up a series of behaviours by implementing the behaviour interface
  local behaviour = {}
  local behaviourProxy = luajava.createProxy("com.stargem.behaviour.BehaviourStrategy", behaviour)
  behaviourManager:addBehaviour(entity:getId(), behaviourProxy)
  
  -- push an initial task to the action list of the brain
  local idleTask = IdleTask:newInstance(behaviourProxy, brain, entity, nil)
  brain:addTask(idleTask)
  
  -- behaviour implementations  
  behaviour.onIdle = function()
    -- push a blocking animation task for the idle animation   
    local animation = "idle"
    local blocking = true
    local mask = 0
    mask = script:bitwiseOr(mask, AbstractTask.MASK_ANIMATION)
    mask = script:bitwiseOr(mask, AbstractTask.MASK_BEHAVIOUR)
    local animationTask = AnimationTask:newInstance(behaviourProxy, brain, entity, idleTask, animation, blocking, mask)
    brain:addTask(animationTask)
  end
  
  -- push a blocking animation task for the sleep animation
  behaviour.onSleep = function()
--    local animation = "sleep"
--    local blocking = true
--    local mask = 0
--    mask = script:bitwiseOr(mask, AbstractTask.MASK_ANIMATION)
--    mask = script:bitwiseOr(mask, AbstractTask.MASK_BEHAVIOUR)
--    local animationTask = luajava.newInstance("com.stargem.ai.tasks.AnimationTask", behaviourProxy, brain, entity, idleTask, animation, blocking, mask)
--    brain:addTask(animationTask)
  end
  
  -- push a blocking animation task for the wake animation
  behaviour.onWakeUp = function()
    local animation = "awake"
    local blocking = true
    local mask = 0
    mask = script:bitwiseOr(mask, AbstractTask.MASK_ANIMATION)
    mask = script:bitwiseOr(mask, AbstractTask.MASK_BEHAVIOUR)
    local animationTask = AnimationTask:newInstance(behaviourProxy, brain, entity, idleTask, animation, blocking, mask)
    brain:addTask(animationTask)
  end
  
  -- push an attack task
  behaviour.onCombatStarted = function()
    local attackTask = AttackTask:newInstance(behaviourProxy, brain, entity, idleTask)
    brain:addTask(attackTask)   
  end
  
  -- repeat play the forward animation and set moving forward to true
  behaviour.onMoveForward = function()
    local skinned = em:getComponent(entity, RenderableSkinned);
    local animation = representationManager:getAnimationController(skinned.modelIndex)
    animation:setAnimation("forward", -1);
    script:setField(animation, "paused", false)
    
    local controller = em:getComponent(entity, Controller);
    script:setField(controller, "moveForward", true)
    script:setField(controller, "moveBackward", false)
  end
  
  -- repeat play the bakward animation
  behaviour.onMoveBackward = function()
    local skinned = em:getComponent(entity, RenderableSkinned);
    local animation = representationManager:getAnimationController(skinned.modelIndex)
    animation:setAnimation("backward", -1);
    script:setField(animation, "paused", false)
    
    local controller = em:getComponent(entity, Controller);
    script:setField(controller, "moveForward", false)
    script:setField(controller, "moveBackward", true)
  end
  
  -- repeat play the left animation
  behaviour.onMoveLeft = function()
    local skinned = em:getComponent(entity, RenderableSkinned);
    local animation = representationManager:getAnimationController(skinned.modelIndex)
    animation:setAnimation("left", -1);
    script:setField(animation, "paused", false)
    
    local controller = em:getComponent(entity, Controller);
    script:setField(controller, "moveLeft", true)
    script:setField(controller, "moveRight", false)
  end
  
  -- repeat play the right animation
  behaviour.onMoveRight = function()
    local skinned = em:getComponent(entity, RenderableSkinned);
    local animation = representationManager:getAnimationController(skinned.modelIndex)
    animation:setAnimation("right", -1);
    script:setField(animation, "paused", false)
    
    local controller = em:getComponent(entity, Controller);
    script:setField(controller, "moveLeft", false)
    script:setField(controller, "moveRight", true)
  end
  
  -- push a blocking animation task for the jump animation
  behaviour.onJump = function()
    local animation = "jump"
    local blocking = true
    local mask = 0
    mask = script:bitwiseOr(mask, AbstractTask.MASK_ANIMATION)
    mask = script:bitwiseOr(mask, AbstractTask.MASK_BEHAVIOUR)
    local animationTask = AnimationTask:newInstance(behaviour, brain, entity, idleTask, animation, blocking, mask)
    brain:addTask(animationTask)
  end
  
  -- set and pause the animation
  behaviour.onStopMoving = function()
    local skinned = em:getComponent(entity, RenderableSkinned);
    local animation = representationManager:getAnimationController(skinned.modelIndex)
    animation:setAnimation("forward", -1);
    script:setField(animation, "paused", true)
    
    local controller = em:getComponent(entity, Controller);
    script:setField(controller, "moveForward", false)
    script:setField(controller, "moveBackward", false)
    script:setField(controller, "moveLeft", false)
    script:setField(controller, "moveRight", false)
  end
  
  behaviour.onDamaged = function()
    
  end
  
  behaviour.onAttack = function()
    
    -- get the weapon component
    local weapon = em:getComponent(entity, Weapon)
    
    if weapon ~= nil and weapon.isReady then
      
      -- check the weapon type here and do something different with raycast weapon
      -- then this method could actually apply to all entities
      
      local physics = em:getComponent(entity, Physics)
      local character = physicsManager:getCharacter(physics.bodyIndex)
      
      local forward = luajava.newInstance("com.badlogic.gdx.math.Vector3")
      local from = luajava.newInstance("com.badlogic.gdx.math.Vector3")
    
      character:getForward(forward)
      character:getTranslation(from)

      weaponManager:shoot(entity, weapon, from, forward)
    end
  end
  
  behaviour.onDeath = function()
    
    -- empty the inventory of the robot
    local inventory = em:getComponent(entity, Inventory) 
    local physics = physicsManager:getCharacter(entity:getId())
    local transform = luajava.newInstance("com.badlogic.gdx.math.Matrix4")
    physics:getMotionState():getWorldTransform(transform)
    
    if inventory ~= nil then
      for i = 1, inventory.cores  do
         debug("added a core")
         entities.powerCore(transform)
      end
      
      for i = 1, inventory.specials  do
         debug("added a special power")
         entities.specialPower(transform)
      end
      
      for i = 1, inventory.gems  do
         debug("added a gem")
         entities.smallGem(transform)
      end
      
    end
  
    triggers.enemyDied()
    em:recycle(entity)
    
  end
  
end
