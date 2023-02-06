package command

import net.dv8tion.jda.api.events.message.MessageReceivedEvent

interface BotCommand {

    val commandId: String
    fun onCommand(event: MessageReceivedEvent)

}