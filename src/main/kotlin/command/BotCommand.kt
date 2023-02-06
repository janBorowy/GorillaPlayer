package command

import audio.BotAudio
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.exceptions.PermissionException

interface BotCommand {

    val commandId: String
    fun onCommand(event: MessageReceivedEvent)

}