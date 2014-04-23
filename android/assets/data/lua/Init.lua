Gdx = luajava.bindClass("com.badlogic.gdx.Gdx")
Input = luajava.bindClass("com.stargem.utils.InputAdapter")
Preferences = luajava.bindClass("com.stargem.Preferences");

-- import all the component classes
Component = luajava.bindClass("com.stargem.entity.components.AbstractComponent")
Health = luajava.bindClass("com.stargem.entity.components.Health")
Controller = luajava.bindClass("com.stargem.entity.components.Controller")
Physics = luajava.bindClass("com.stargem.entity.components.Physics")
PlayerStats = luajava.bindClass("com.stargem.entity.components.PlayerStats")
RenderableSkinned = luajava.bindClass("com.stargem.entity.components.RenderableSkinned")
RenderableStatic = luajava.bindClass("com.stargem.entity.components.RenderableStatic")
RunSpeed = luajava.bindClass("com.stargem.entity.components.RunSpeed")
ThirdPersonCamera = luajava.bindClass("com.stargem.entity.components.ThirdPersonCamera")
Timer = luajava.bindClass("com.stargem.entity.components.Timer")
Trigger = luajava.bindClass("com.stargem.entity.components.Trigger")

-- import managers
EntityManager = luajava.bindClass("com.stargem.entity.EntityManager")
EntityPersistence = luajava.bindClass("com.stargem.persistence.EntityPersistence")
PersistenceManager = luajava.bindClass("com.stargem.persistence.PersistenceManager")
PhysicsManager = luajava.bindClass("com.stargem.physics.PhysicsManager")
ContactCallbackFlags = luajava.bindClass("com.stargem.physics.ContactCallbackFlags")
RepresentationManager = luajava.bindClass("com.stargem.graphics.RepresentationManager")
AIManager = luajava.bindClass("com.stargem.ai.AIManager")

-- get global instances of required managers
em = EntityManager:getInstance()
persistence = PersistenceManager:getInstance():getEntityPersistence()
physicsManager = PhysicsManager:getInstance()
representationManager = RepresentationManager:getInstance()
aiManager = AIManager:getInstance()

-- import all the other scripts for stargem
script:require("AI")
script:require("Components")
script:require("Controllers")
script:require("Entities")
script:require("Log")
script:require("Physics")