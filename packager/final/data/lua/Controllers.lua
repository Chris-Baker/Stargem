controllers = {}

function controllers.minebot(entity, delta)

  local runSpeed = em:getComponent(entity, RunSpeed)
  local physics = em:getComponent(entity, Physics)
  local controller = em:getComponent(entity, Controller)
  
  local character = physicsManager:getCharacter(physics.bodyIndex)

  local moveForward  = controller.moveForward
  local moveBackward = controller.moveBackward
  local moveLeft     = controller.moveLeft
  local moveRight    = controller.moveRight
  local jump         = controller.isJumping
  
  character:move(runSpeed.speed, moveForward, moveBackward, moveLeft, moveRight, jump);

end

function controllers.localPlayer(entity, delta)
  
  -- get the players components
  local camera     = em:getComponent(entity, ThirdPersonCamera)
  local runSpeed   = em:getComponent(entity, RunSpeed)
  local physics    = em:getComponent(entity, Physics)
  local controller = em:getComponent(entity, Controller)
  
  -- return early if a component is missing
  if camera == nil or runSpeed == nil or physics == nil or controller == nil then
    error("A player entity with ID " .. entity:getId() .. " is missing a component for its controller")
    return
  end
  
  -- make sure we have a character physics object
  if physics.type ~= PhysicsManager.CHARACTER then
    return
  end
  
  -- get the character physics object
  local character = physicsManager:getCharacter(physics.bodyIndex)
  
  -- debug player position
--  local position = luajava.newInstance("com.badlogic.gdx.math.Vector3")
--  character:getTranslation(position)
--  debug(position:toString())
  
  -- get the keyboard input
  local isJumping    = controller.isJumping
  local moveForward  = controller.moveForward
  local moveBackward = controller.moveBackward
  local moveLeft     = controller.moveLeft
  local moveRight    = controller.moveRight
  local runSpeed     = runSpeed.speed  
  
  local skills = em:getComponent(entity, SkillModifiers)
  if skills ~= nil then
    runSpeed = runSpeed + skills.speedIncrease
  end  
  
  -- moving and jumping
  character:move(runSpeed, moveForward, moveBackward, moveLeft, moveRight, isJumping);
  
  script:setField(camera, "deltaPitch", Input:getDeltaY() * Preferences.MOUSE_SENSITIVITY)
  
  -- pitch the camera if left or right buttons are active
--  if Input:isRightButtonPressed() or Input:isLeftButtonPressed() then       
--    -- catch cursor and reset the cursor location to the middle of the screen
--    Input:setCursorCatched(true)
--    script:setField(camera, "deltaPitch", Input:getDeltaY() * Preferences.MOUSE_SENSITIVITY)
--  else
--    Input:setCursorCatched(false)
--    script:setField(camera, "deltaPitch", 0)
--  end
  
  -- yaw the camera only when left button pressed     
--  if Input:isLeftButtonPressed() then      
--    script:setField(camera, "deltaYaw", Input:getDeltaX() * Preferences.MOUSE_SENSITIVITY)
--  else
--    script:setField(camera, "deltaYaw", 0)
--  end
  
  -- rotate the player only when right button pressed
--  if Input:isRightButtonPressed() then
--    if camera.yaw ~= 0 then
--      character:snapRotateTo(camera.yaw)
--      script:setField(camera, "yaw", 0)
--    end
--    character:rotate(Input:getDeltaX() * Preferences.MOUSE_SENSITIVITY)
--  else
--    character:rotate(0);
--  end
  
   if camera.yaw ~= 0 then
     character:snapRotateTo(camera.yaw)
     script:setField(camera, "yaw", 0)
   end
   character:rotate(Input:getDeltaX() * Preferences.MOUSE_SENSITIVITY)
  
  -- take care of player animation
  controllers.characterAnimation(entity,delta)
  
end

function controllers.characterAnimation(entity, delta) 

  local controller = em:getComponent(entity, Controller)
  local skinned    = em:getComponent(entity, RenderableSkinned);
  local animation  = representationManager:getAnimationController(skinned.modelIndex)
  
  local isJumping    = controller.isJumping
  local moveForward  = controller.moveForward
  local moveBackward = controller.moveBackward
  local moveLeft     = controller.moveLeft
  local moveRight    = controller.moveRight
  
  if animation.current == nil then
      animation:setAnimation("idle", -1);
  end
  
  if not moveForward and not moveBackward and not moveLeft and not moveRight and not isJumping then
    animation:setAnimation("idle", -1)
  end
  
  if moveForward and not moveLeft and not moveRight then
    animation:setAnimation("run", -1);
  end
  
  if moveRight and not moveLeft then
    animation:setAnimation("strafe_right", -1);
  end
  
  if not moveRight and moveLeft then
    animation:setAnimation("strafe_left", -1);
  end

end