package com.vitorblog.mineshop.dao

import com.vitorblog.mineshop.command.MainCommand
import com.vitorblog.mineshop.model.Command

class CommandDao  {companion object{

    val COMMANDS = hashMapOf<String, Command>("mineshop" to MainCommand())

    fun get(name:String):Command? {
        if (COMMANDS.containsKey(name)) return COMMANDS.get(name)!!
        return null
    }

}}