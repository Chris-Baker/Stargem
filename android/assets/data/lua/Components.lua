components = {}

-- function to set a component field using reflection
-- this is required because Lua lacks write access to public fields
-- because of a syntax clash
function components.set(instance, fieldName, value)

  script:setField(instance, fieldName, value)

end