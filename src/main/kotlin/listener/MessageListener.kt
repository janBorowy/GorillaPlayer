package listener

import BotProperties
import ServerCommandPalette
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object MessageListener : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {

        when {
            event.isFromType(ChannelType.PRIVATE) -> onPrivateMessage(event)
            else -> onServerMessage(event)
        }

    }

    private fun onPrivateMessage(event: MessageReceivedEvent) {
        logPrivateMessage(event)
    }

    private fun onServerMessage(event: MessageReceivedEvent) {
        logServerMessage(event)
        val content = event.message.contentDisplay
        if( content.startsWith(BotProperties.prefix) ) {
            val args = content.split(' ')
            val command = args[0].substring(1)
            if( command in ServerCommandPalette ) {
                ServerCommandPalette[command]!!(event)
            }
        }
    }

    private fun logPrivateMessage(event: MessageReceivedEvent) {
        if(event.message.author.id != BotProperties.id)
            println("[PM][${event.message.timeCreated}] ${event.author.name}: ${event.message.contentDisplay}")
    }

    private fun logServerMessage(event: MessageReceivedEvent) {
        if(event.message.author.id != BotProperties.id)
            println("[SM][${event.message.timeCreated}] [${event.guild.name}@${event.channel.name}] ${event.author.name}: ${event.message.contentDisplay}")
    }

}