package com.vitorblog.mineshop.handler

import com.vitorblog.mineshop.Main
import com.vitorblog.mineshop.dao.PointDao
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer

class PAPIHandler : PlaceholderExpansion() {

    override
    fun canRegister(): Boolean {
        return true
    }

    override
    fun getVersion(): String {
        return Main.instance!!.lastVersion
    }

    override
    fun getAuthor(): String {
        return "vitorblog"
    }

    override
    fun getIdentifier(): String {
        return "blogpoints_points"
    }

    override
    fun onRequest(p: OfflinePlayer, identifier: String): String? {
        if (identifier.equals("blogpoints_points")){
            var points = PointDao.get(p.name)
            if (points == null) {
                points = 0
            }

            return points.toString()
        }
        return null
    }

}