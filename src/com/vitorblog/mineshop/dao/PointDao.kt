package com.vitorblog.mineshop.dao

import com.vitorblog.mineshop.command.MainCommand
import com.vitorblog.mineshop.model.Command
import com.vitorblog.mineshop.model.shop.Category
import org.bukkit.entity.Player

class PointDao  {companion object{

    val POINTS = hashMapOf<String, Int>()

    fun add(string: String, int: Int) {
        POINTS.put(string, int)
    }

    fun get(name:String):Int? {
        return POINTS.get(name)
    }

}}