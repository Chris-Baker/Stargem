weapons = {}

function weapons.register()

  weapons.selfDestruct(1)
  weapons.welderPistol(2)
  weapons.minebotGun(32)
  
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
  weapon.rateOfFire = 60
  
  weapon.shoot = function(entity, weaponComponent, from, forward) 
    if weaponComponent.isReady then      
      script:setField(weaponComponent, "isShooting", true)
      
      local physics = physicsManager:getCharacter(entity:getId())
      local transform = luajava.newInstance("com.badlogic.gdx.math.Matrix4")
      physics:getMotionState():getWorldTransform(transform)
      
      -- spawn an explosion effect
      debug("explosion")
      
      -- spawn a trigger which hurts stuff near it
      entities.damageZone(transform)
      
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

  weapon.getRateOfFire = function()
    return weapon.rateOfFire
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
  weapon.rateOfFire = 0
  
  weapon.shoot = function(entity, weaponComponent, from, to, targetEntity) 
    if weaponComponent.isReady then      
      script:setField(weaponComponent, "isShooting", true)
      
      -- spawn an explosion effect
      -- debug("beam heat " .. weaponComponent.currentHeat)
      
      -- spawn an effect going from and to
      
      -- damage the target
      if targetEntity ~= nil then
        -- get the health component and behaviour implementation
        local health = em:getComponent(targetEntity, Health)        
        
        if health ~= nil then        
          local behaviour = behaviourManager:getBehaviour(targetEntity:getId())
          local brain = aiManager:getBrain(targetEntity:getId())
          
          local damage = weapon.damage
          
          local skills = em:getComponent(entity, SkillModifiers)
          if skills ~= nil then
            damage = damage + skills.damageIncrease
          end
          
          -- hurt the entity
          script:setField(health, "currentHealth", health.currentHealth - (damage * Config.FIXED_TIME_STEP))
          behaviour:onDamaged()
          
          -- add threat
          if brain ~= nil then
            brain:increaseThreat(entity, damage * Config.FIXED_TIME_STEP)
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
  
  weapon.getRateOfFire = function()
    return weapon.rateOfFire
  end
  
end

function weapons.spanner(id)

end

function weapons.minebotGun(id)

  local weapon = {}
  local weaponProxy = luajava.createProxy("com.stargem.weapons.RaycastWeaponStrategy", weapon)
  weaponManager:registerWeapon(id, weaponProxy)
  
  weapon.damage = 0
  weapon.range = 100
  weapon.maxHeat = 100
  weapon.heatRate = 0
  weapon.coolRate = 0
  weapon.overHeatingPenalty = 3
  weapon.rateOfFire = 1
  
  weapon.shoot = function(entity, weaponComponent, from, to, targetEntity) 
    if weaponComponent.isReady then      
      script:setField(weaponComponent, "isShooting", true)
      
      -- spawn a mine bot at the point of shooting
      local transform = luajava.newInstance("com.badlogic.gdx.math.Matrix4")
      transform:idt()
      transform:trn(to)      
      entities.minebot(transform, 2, 0, 1)
      --entities.gate(transform)
      debug(transform:toString())
      
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
  
  weapon.getRateOfFire = function()
    return weapon.rateOfFire
  end

end
