package command

import MessageHelper
import audio.PlayerManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object SkipCommand : BotCommand {

    override val commandId: String = "skip"

    override fun onCommand(event: MessageReceivedEvent) {
        PlayerManager.skipTrack(event.guild)
        MessageHelper.sendGenericMessage(event.channel, "Skipping current song...")
    }
}