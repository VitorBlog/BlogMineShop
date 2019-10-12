package com.vitorblog.mineshop.dao

import com.vitorblog.mineshop.command.MainCommand
import com.vitorblog.mineshop.command.PointCommand
import com.vitorblog.mineshop.command.ShopCommand
import com.vitorblog.mineshop.model.Command

class CommandDao  {companion object{

    val COMMANDS = hashMapOf<String, Command>("mineshop" to MainCommand(), "shop" to ShopCommand(), "points" to PointCommand())

    fun get(name:String):Command? {
        if (COMMANDS.containsKey(name)) return COMMANDS.get(name)!!
        return null
    }

}}