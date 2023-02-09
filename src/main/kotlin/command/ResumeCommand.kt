package command

import MessageHelper
import audio.PlayerManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object ResumeCommand : BotCommand {
    override val commandId: String = "resume"

    override fun onCommand(event: MessageReceivedEvent) {
        MessageHelper.sendGenericMessage(event.channel, "Resuming...")

        PlayerManager.resume(event.guild)
    }
}