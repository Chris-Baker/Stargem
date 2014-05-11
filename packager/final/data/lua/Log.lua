-- for making log entries from script
Log = luajava.bindClass("com.stargem.utils.Log")
System = luajava.bindClass("java.lang.System")

function debug(message)
	Log:debug("SCRIPT", message)
end

function info(message)
	Log:info("SCRIPT", message)
end

function error(message)
	Log:error("SCRIPT", message)
end

function echo(message)
  System.out:println(message)
end