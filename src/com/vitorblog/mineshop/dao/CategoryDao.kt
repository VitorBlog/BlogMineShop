package com.vitorblog.mineshop.dao

import com.vitorblog.mineshop.command.MainCommand
import com.vitorblog.mineshop.model.Command
import com.vitorblog.mineshop.model.shop.Category

class CategoryDao  {companion object{

    val CATEGORIES = hashMapOf<String, Category>()

    fun add(category: Category) {
        CATEGORIES.put(category.id, category)
    }

    fun get(name:String):Category? {
        return CATEGORIES.get(name)!!
    }

}}