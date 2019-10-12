package com.vitorblog.mineshop.model

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class MySQL {companion object{
    val SQLite = CONFIG.MYSQL_USE.message.equals("false")
    var connection:Connection? = null

    fun connect():Connection {
        this.connection = if (SQLite){
            Class.forName("org.sqlite.JDBC")
            DriverManager.getConnection("jdbc:sqlite:plugins/BlogMineShop/points.db")
        }else{
            DriverManager.getConnection("jdbc:mysql://${CONFIG.MYSQL_IP.message}:3306/${CONFIG.MYSQL_DATABASE.message}", CONFIG.MYSQL_USER.message, CONFIG.MYSQL_PASSWORD.message)
        }

        return connection!!
    }

    fun close(){
        connection!!.close()
    }

    fun createDatabase(){
        update("CREATE TABLE IF NOT EXISTS BlogPoints(player VARCHAR(36), points BIGINT)")
    }

    fun query(query:String):ResultSet {
        val st = connection!!.prepareStatement(query)
        return st.executeQuery()
    }

    fun update(query:String):Int {
        val st = connection!!.prepareStatement(query)
        return st.executeUpdate()
    }

}}