package com.vitorblog.mineshop.handler

import com.vitorblog.mineshop.dao.CommandDao
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class CommandHandler : CommandExecutor, TabCompleter {

    override
    fun onCommand(commandSender: CommandSender, cmd: Command, s: String, a: Array<String>): Boolean {
        try {
            val command = CommandDao.get(cmd.name)
            command!!.execute(commandSender, cmd, a)
        } catch (e: Exception) {
            val stack = e.stackTrace[0]
            commandSender.sendMessage(arrayOf("", "§cOcorreu um erro ao executar o comando.", "§cMensagem: ${e.message}", "§cLinha de código: ${stack.className}:${stack.lineNumber}", "§cMétodo: ${stack.methodName}", "§aReporte para vitorblog#0017 (Discord)", ""))
            e.printStackTrace()
        }

        return false
    }

    override
    fun onTabComplete(sender: CommandSender, command: Command, s: String, a: Array<String>): MutableList<String>? {
        val command = CommandDao.get(command.name)
        if (command != null) return command.getTabs(a.size).toMutableList()
        return null
    }
}