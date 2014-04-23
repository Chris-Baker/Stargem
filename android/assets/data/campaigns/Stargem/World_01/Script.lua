-- these two lines enable importing other Lua files
ScriptManager = luajava.bindClass("com.stargem.scripting.ScriptManager")
script = ScriptManager:getInstance()
script:require("Init")

triggers = {}

function triggers.trigger_01(entity)

  -- remove the entity
  -- em:recycle(entity)

end

function triggers.trigger_02(entity)
	
	debug("trigger 02 called")
	
end