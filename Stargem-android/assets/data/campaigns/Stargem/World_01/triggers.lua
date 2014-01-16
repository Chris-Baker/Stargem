-- these two lines enable importing other Lua files
LuaScript = luajava.bindClass("com.stargem.scripting.LuaScript")
script = LuaScript:getInstance()

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