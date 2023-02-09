package command

import MessageHelper
import audio.PlayerManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object PauseCommand : BotCommand {
    override val commandId: String = "pause"

    override fun onCommand(event: MessageReceivedEvent) {
        MessageHelper.sendGenericMessage(event.channel, "Pausing...")

        PlayerManager.pause(event.guild)
    }
}