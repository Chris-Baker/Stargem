controllers = {}

function controllers.minebot(entity, delta)

  runSpeed = em:getComponent(entity, RunSpeed)
  physics = em:getComponent(entity, Physics)
  character = physicsManager:getCharacter(physics.bodyIndex)

  character:move(runSpeed.speed, true, false, false, false);

  -- animation controls
  skinned = em:getComponent(entity, RenderableSkinned);
  animation = representationManager:getAnimationController(skinned.modelIndex)
  
  if animation.current == nil then
      animation:setAnimation("forward", -1);
  end

  animation:update(delta);

end

function controllers.localPlayer(entity, delta)
  
  -- get the players components
  camera     = em:getComponent(entity, ThirdPersonCamera)
  runSpeed   = em:getComponent(entity, RunSpeed)
  physics    = em:getComponent(entity, Physics)
  controller = em:getComponent(entity, Controller)
  
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
  character = physicsManager:getCharacter(physics.bodyIndex)
  
  -- get the keyboard input
  isJumping    = controller.isJumping
  moveForward  = controller.moveForward
  moveBackward = controller.moveBackward
  moveLeft     = controller.moveLeft
  moveRight    = controller.moveRight
  
  -- jumping
  if isJumping then
    character:jump()
  end
  
  -- moving
  character:move(runSpeed.speed, moveForward, moveBackward, moveLeft, moveRight);
  
  -- pitch the camera if left or right buttons are active
  if Input:isRightButtonPressed() or Input:isLeftButtonPressed() then       
    -- catch cursor and reset the cursor location to the middle of the screen
    Input:setCursorCatched(true)
    components.set(camera, "deltaPitch", Input:getDeltaY() * Preferences.MOUSE_SENSITIVITY)
  else
    Input:setCursorCatched(false)
    components.set(camera, "deltaPitch", 0)
  end
  
  -- yaw the camera only when left button pressed     
  if Input:isLeftButtonPressed() then      
    components.set(camera, "deltaYaw", Input:getDeltaX() * Preferences.MOUSE_SENSITIVITY)
  else
    components.set(camera, "deltaYaw", 0)
  end
  
  -- rotate the player only when right button pressed
  if Input:isRightButtonPressed() then
    if camera.yaw ~= 0 then
      character:snapRotateTo(camera.yaw)
      components.set(camera, "yaw", 0)
    end
    character:rotate(Input:getDeltaX() * Preferences.MOUSE_SENSITIVITY)
  else
    character:rotate(0);
  end
  
  -- take care of player animation
  controllers.characterAnimation(entity,delta)
  
end

function controllers.characterAnimation(entity, delta) 

  controller = em:getComponent(entity, Controller)
  skinned    = em:getComponent(entity, RenderableSkinned);
  animation  = representationManager:getAnimationController(skinned.modelIndex)
  
  isJumping    = controller.isJumping
  moveForward  = controller.moveForward
  moveBackward = controller.moveBackward
  moveLeft     = controller.moveLeft
  moveRight    = controller.moveRight
  
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
  
  animation:update(delta);

end