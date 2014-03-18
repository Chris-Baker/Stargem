-- these two lines enable importing other Lua files
ScriptManager = luajava.bindClass("com.stargem.scripting.ScriptManager")
script = ScriptManager:getInstance()

-- imports
script:require("Log")
script:require("Entities")
script:require("Components")
script:require("Physics")

--  Other functions that need to be created in Java
--   - Change music
--   - Change the current world script
--   - ...
--

triggers = {}

function triggers.trigger_01(entity)

  -- remove the entity
  -- em:recycle(entity)

end

function triggers.trigger_02(entity)
	
	debug("trigger 02 called")
	
end