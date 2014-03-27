-- import all the component classes
Component = luajava.bindClass("com.stargem.entity.components.AbstractComponent")
Health = luajava.bindClass("com.stargem.entity.components.Health")
KeyboardMouseController = luajava.bindClass("com.stargem.entity.components.KeyboardMouseController")
Physics = luajava.bindClass("com.stargem.entity.components.Physics")
PlayerStats = luajava.bindClass("com.stargem.entity.components.PlayerStats")
RenderableSkinned = luajava.bindClass("com.stargem.entity.components.RenderableSkinned")
RenderableStatic = luajava.bindClass("com.stargem.entity.components.RenderableStatic")
RunSpeed = luajava.bindClass("com.stargem.entity.components.RunSpeed")
ThirdPersonCamera = luajava.bindClass("com.stargem.entity.components.ThirdPersonCamera")
Timer = luajava.bindClass("com.stargem.entity.components.Timer")
Trigger = luajava.bindClass("com.stargem.entity.components.Trigger")

components = {}

function components.set(instance, fieldName, value)

  script:setField(instance, fieldName, value)

end