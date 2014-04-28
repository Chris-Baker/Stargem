weapons = {}

function weapons.register()

  weapons.selfDestruct(1)
  weapons.welderPistol(2)
  
end

function weapons.selfDestruct(id)

  local weapon = {}
  local weaponProxy = luajava.createProxy("com.stargem.weapons.ProjectileWeaponStrategy", weapon)
  weaponManager:registerWeapon(id, weaponProxy)
  
  weapon.damage = 20
  weapon.range = 1
  weapon.maxHeat = 100
  weapon.heatRate = 0
  weapon.coolRate = 0
  weapon.overHeatingPenalty = 0
  
  weapon.shoot = function(entity, weaponComponent, from, forward) 
    if weaponComponent.isReady then      
      components.set(weaponComponent, "isShooting", true)
      
      -- spawn an explosion effect
      debug("explosion")
      
      -- spawn a trigger which hurts stuff near it
      
      -- remove the entity that exploded
      em:recycle(entity)
      
    end
  end

  weapon.getDamage = function()
    return weapon.damage
  end

  weapon.getRange = function()
    return weapon.range
  end
  
  weapon.getMaxHeat = function()
    return weapon.maxHeat
  end
  
  weapon.getHeatRate = function()
    return weapon.heatRate
  end
  
  weapon.getCoolRate = function()
    return weapon.coolRate
  end
  
  weapon.getOverHeatingPenalty = function()
    return weapon.overHeatingPenalty
  end

end

function weapons.welderPistol(id)
    
  local weapon = {}
  local weaponProxy = luajava.createProxy("com.stargem.weapons.RaycastWeaponStrategy", weapon)
  weaponManager:registerWeapon(id, weaponProxy)
  
  weapon.damage = 20
  weapon.range = 100
  weapon.maxHeat = 100
  weapon.heatRate = 40
  weapon.coolRate = 30
  weapon.overHeatingPenalty = 3
  
  weapon.shoot = function(entity, weaponComponent, from, to, targetEntity) 
    if weaponComponent.isReady then      
      components.set(weaponComponent, "isShooting", true)
      
      -- spawn an explosion effect
      debug("beam heat " .. weaponComponent.currentHeat)
      
      -- spawn an effect going from and to
      
      -- damage the target
      if targetEntity ~= nil then
        -- get the health component and behaviour implementation
        local health = em:getComponent(targetEntity, Health)        
        
        if health ~= nil then        
          local behaviour = behaviourManager:getBehaviour(targetEntity:getId())
          
          -- hurt the entity
          components.set(health, "currentHealth", health.currentHealth - (weapon.damage * Config.FIXED_TIME_STEP))
          
          -- check if it is dead and initiate the appropriate behaviour response
          if health.currentHealth < 0 then
            components.set(health, "currentHealth", health.currentHealth - (weapon.damage * Config.FIXED_TIME_STEP))
            debug("dead")
            behaviour:onDeath()
          else
            debug("currentHealth " .. health.currentHealth)
            behaviour:onDamaged()
          end
        end
      end      
    end
  end

  weapon.getDamage = function()
    return weapon.damage
  end

  weapon.getRange = function()
    return weapon.range
  end
  
  weapon.getMaxHeat = function()
    return weapon.maxHeat
  end
  
  weapon.getHeatRate = function()
    return weapon.heatRate
  end
  
  weapon.getCoolRate = function()
    return weapon.coolRate
  end
  
  weapon.getOverHeatingPenalty = function()
    return weapon.overHeatingPenalty
  end
  
end

function weapons.spanner(id)

end