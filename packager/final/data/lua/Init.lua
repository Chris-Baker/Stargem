Gdx = luajava.bindClass("com.badlogic.gdx.Gdx")
Input = luajava.bindClass("com.stargem.utils.InputAdapter")
Preferences = luajava.bindClass("com.stargem.Preferences");
AbstractTask = luajava.bindClass("com.stargem.ai.tasks.AbstractTask");
Config = luajava.bindClass("com.stargem.Config");

-- import all the component classes
Controller = luajava.bindClass("com.stargem.entity.components.Controller")
Health = luajava.bindClass("com.stargem.entity.components.Health")
Inventory = luajava.bindClass("com.stargem.entity.components.Inventory")
Parent = luajava.bindClass("com.stargem.entity.components.Parent")
Physics = luajava.bindClass("com.stargem.entity.components.Physics")
RenderableSkinned = luajava.bindClass("com.stargem.entity.components.RenderableSkinned")
RenderableStatic = luajava.bindClass("com.stargem.entity.components.RenderableStatic")
RunSpeed = luajava.bindClass("com.stargem.entity.components.RunSpeed")
SkillModifiers = luajava.bindClass("com.stargem.entity.components.SkillModifiers")
ThirdPersonCamera = luajava.bindClass("com.stargem.entity.components.ThirdPersonCamera")
Timer = luajava.bindClass("com.stargem.entity.components.Timer")
Trigger = luajava.bindClass("com.stargem.entity.components.Trigger")
Weapon = luajava.bindClass("com.stargem.entity.components.Weapon")

-- import managers
EntityManager = luajava.bindClass("com.stargem.entity.EntityManager")
EntityPersistence = luajava.bindClass("com.stargem.persistence.EntityPersistence")
PersistenceManager = luajava.bindClass("com.stargem.persistence.PersistenceManager")
PhysicsManager = luajava.bindClass("com.stargem.physics.PhysicsManager")
ContactCallbackFlags = luajava.bindClass("com.stargem.physics.ContactCallbackFlags")
RepresentationManager = luajava.bindClass("com.stargem.graphics.RepresentationManager")
AIManager = luajava.bindClass("com.stargem.ai.AIManager")
BehaviourManager = luajava.bindClass("com.stargem.behaviour.BehaviourManager")
WeaponManager = luajava.bindClass("com.stargem.weapons.WeaponManager")
GameManager = luajava.bindClass("com.stargem.GameManager")
GateManager = luajava.bindClass("com.stargem.GateManager")
ComponentFactory = luajava.bindClass("com.stargem.entity.ComponentFactory")

-- get global instances of required managers
em = EntityManager:getInstance()
persistenceManager = PersistenceManager:getInstance():getEntityPersistence()
physicsManager = PhysicsManager:getInstance()
representationManager = RepresentationManager:getInstance()
aiManager = AIManager:getInstance()
behaviourManager = BehaviourManager:getInstance()
weaponManager = WeaponManager:getInstance()
gameManager = GameManager:getInstance()
gateManager = GateManager:getInstance()

-- import AI Tasks
AnimationTask = luajava.bindClass("com.stargem.ai.tasks.AnimationTask")
AttackTask = luajava.bindClass("com.stargem.ai.tasks.AttackTask")
GetInRangeTask = luajava.bindClass("com.stargem.ai.tasks.GetInRangeTask")
IdleTask = luajava.bindClass("com.stargem.ai.tasks.IdleTask")
MoveToTask = luajava.bindClass("com.stargem.ai.tasks.MoveToTask")
TurnToFaceTask = luajava.bindClass("com.stargem.ai.tasks.TurnToFaceTask")

-- import all the other scripts for stargem
script:require("Behaviour")
script:require("Controllers")
script:require("Entities")
script:require("Log")
script:require("Physics")
script:require("Weapons")

-- register the weapons
weapons.register()
