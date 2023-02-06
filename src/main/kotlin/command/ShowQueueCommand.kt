package command

import MessageHelper
import audio.QueueManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import parseDurationString

object ShowQueueCommand : BotCommand {
    override val commandId: String
        get() = "queue"

    override fun onCommand(event: MessageReceivedEvent) {
        val songs = QueueManager.toArray(event.guild)
        val builder: StringBuilder = StringBuilder()
        for(song in songs) {
            builder.append(song.snippet.title)
                .append(" - ")
                .append(parseDurationString(song.contentDetails.duration))
                .append(", ")
        }
        val result = builder.toString()

        MessageHelper.sendGenericMessage(event.channel, result)
    }
}