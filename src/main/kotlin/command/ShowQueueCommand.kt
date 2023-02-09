package command

import MessageHelper
import audio.QueueManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object ShowQueueCommand : BotCommand {
    override val commandId: String
        get() = "queue"

    override fun onCommand(event: MessageReceivedEvent) {
        val songs = QueueManager.toArray(event.guild)

        MessageHelper.sendQueueInfoMessage(event.channel, songs)
    }
}