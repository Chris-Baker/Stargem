-- these two lines enable importing other Lua files
ScriptManager = luajava.bindClass("com.stargem.scripting.ScriptManager")
script = ScriptManager:getInstance()

-- imports
script:require("Log")
script:require("Entity")


--  Other functions that need to be created in Java
--   - Change music
--   - Change the current world script
--   - ...
--

world_01 = {}

function world_01.trigger_01()

end

function world_01.trigger_02()
	
end