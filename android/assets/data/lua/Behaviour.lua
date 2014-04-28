behaviour = {}

function behaviour.minebot(entity, brain)
  
  -- set up a series of behaviours by implementing the behaviour interface
  local behaviour = {}
  local behaviourProxy = luajava.createProxy("com.stargem.behaviour.BehaviourStrategy", behaviour)
  behaviourManager:addBehaviour(entity:getId(), behaviourProxy)
  
  -- push an initial task to the action list of the brain
  local idleTask = luajava.newInstance("com.stargem.ai.tasks.IdleTask", behaviourProxy, brain, entity, nil)
  brain:addTask(idleTask)
  
  -- behaviour implementations  
  behaviour.onIdle = function()
    -- push a blocking animation task for the idle animation   
    local animation = "idle"
    local blocking = true
    local mask = 0
    mask = script:bitwiseOr(mask, AbstractTask.MASK_ANIMATION)
    mask = script:bitwiseOr(mask, AbstractTask.MASK_BEHAVIOUR)
    local animationTask = luajava.newInstance("com.stargem.ai.tasks.AnimationTask", behaviourProxy, brain, entity, idleTask, animation, blocking, mask)
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
    local animationTask = luajava.newInstance("com.stargem.ai.tasks.AnimationTask", behaviourProxy, brain, entity, idleTask, animation, blocking, mask)
    brain:addTask(animationTask)
  end
  
  -- push an attack task
  behaviour.onCombatStarted = function()
    local attackTask = luajava.newInstance("com.stargem.ai.tasks.AttackTask", behaviourProxy, brain, entity, idleTask)
    brain:addTask(attackTask)   
  end
  
  -- repeat play the forward animation and set moving forward to true
  behaviour.onMoveForward = function()
    local skinned = em:getComponent(entity, RenderableSkinned);
    local animation = representationManager:getAnimationController(skinned.modelIndex)
    animation:setAnimation("forward", -1);
    components.set(animation, "paused", false)
    
    local controller = em:getComponent(entity, Controller);
    components.set(controller, "moveForward", true)
    components.set(controller, "moveBackward", false)
  end
  
  -- repeat play the bakward animation
  behaviour.onMoveBackward = function()
    local skinned = em:getComponent(entity, RenderableSkinned);
    local animation = representationManager:getAnimationController(skinned.modelIndex)
    animation:setAnimation("backward", -1);
    components.set(animation, "paused", false)
    
    local controller = em:getComponent(entity, Controller);
    components.set(controller, "moveForward", false)
    components.set(controller, "moveBackward", true)
  end
  
  -- repeat play the left animation
  behaviour.onMoveLeft = function()
    local skinned = em:getComponent(entity, RenderableSkinned);
    local animation = representationManager:getAnimationController(skinned.modelIndex)
    animation:setAnimation("left", -1);
    components.set(animation, "paused", false)
    
    local controller = em:getComponent(entity, Controller);
    components.set(controller, "moveLeft", true)
    components.set(controller, "moveRight", false)
  end
  
  -- repeat play the right animation
  behaviour.onMoveRight = function()
    local skinned = em:getComponent(entity, RenderableSkinned);
    local animation = representationManager:getAnimationController(skinned.modelIndex)
    animation:setAnimation("right", -1);
    components.set(animation, "paused", false)
    
    local controller = em:getComponent(entity, Controller);
    components.set(controller, "moveLeft", false)
    components.set(controller, "moveRight", true)
  end
  
  -- push a blocking animation task for the jump animation
  behaviour.onJump = function()
    local animation = "jump"
    local blocking = true
    local mask = 0
    mask = script:bitwiseOr(mask, AbstractTask.MASK_ANIMATION)
    mask = script:bitwiseOr(mask, AbstractTask.MASK_BEHAVIOUR)
    local animationTask = luajava.newInstance("com.stargem.ai.tasks.AnimationTask", behaviour, brain, entity, idleTask, animation, blocking, mask)
    brain:addTask(animationTask)
  end
  
  -- set and pause the animation
  behaviour.onStopMoving = function()
    local skinned = em:getComponent(entity, RenderableSkinned);
    local animation = representationManager:getAnimationController(skinned.modelIndex)
    animation:setAnimation("forward", -1);
    components.set(animation, "paused", true)
    
    local controller = em:getComponent(entity, Controller);
    components.set(controller, "moveForward", false)
    components.set(controller, "moveBackward", false)
    components.set(controller, "moveLeft", false)
    components.set(controller, "moveRight", false)
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
end

function behaviour.jenny(entity)

  local behaviour = {}
  local behaviourProxy = luajava.createProxy("com.stargem.behaviour.BehaviourStrategy", behaviour)
  behaviourManager:setBehaviour(entity:getId(), behaviourProxy)

end
