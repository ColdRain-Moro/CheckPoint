package ink.rainbowbridge.checkpoint

import io.izzel.taboolib.cronus.CronusParser
import io.izzel.taboolib.cronus.CronusUtils
import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.command.lite.CommandBuilder
import io.izzel.taboolib.module.db.local.LocalPlayer
import io.izzel.taboolib.module.inject.TInject
import org.bukkit.ChatColor
import org.bukkit.entity.Player

/**
 * ink.rainbowbridge.checkpoint.CheckPoint
 * CheckPoint
 *
 * @author coldrain
 * @since 2021/6/20
 **/
object CheckPoint : Plugin() {
    @TInject
    val save = CommandBuilder.create("savepoint",plugin)
            .permission("checkpoint.save")
            .execute { sender, args ->
                if (sender !is Player){
                    sender.sendMessage(ChatColor.DARK_RED.toString()+"不允许在控制台中使用")
                    return@execute
                }
                LocalPlayer.get(sender).set("checkpoint","${sender.world.name},${sender.location.x},${sender.location.y},${sender.location.z}")
                sender.sendMessage(ChatColor.GRAY.toString()+"已保存")
            }.build()
    @TInject
    val d = CommandBuilder.create("d",plugin)
            .permission("checkpoint.d")
            .execute { sender, args ->
                if (sender !is Player){
                    sender.sendMessage(ChatColor.DARK_RED.toString()+"不允许在控制台中使用")
                    return@execute
                }
                if(LocalPlayer.get(sender).getString("checkpoint") == null){
                    sender.sendMessage(ChatColor.GRAY.toString()+"你没有在任何检查点存过档")
                    return@execute
                }
                sender.teleport(CronusParser.toBukkitLocation(LocalPlayer.get(sender).getString("checkpoint")))
                sender.sendMessage(ChatColor.GRAY.toString()+"已回到检查点")
            }.build()

}