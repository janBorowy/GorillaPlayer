package command

import audio.BotAudio
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.exceptions.PermissionException

object JoinCommand : BotCommand {

    override val commandId: String = "join"

    override fun onCommand(event: MessageReceivedEvent) {
        // Attempt to join a channel of sender
        // 1. Fail if bot is already on some channel
        // 2. Fail if sender is not on any channel
        // 3. Fail if no permission to join a channel

        // 1
        if(BotAudio.state != BotAudio.STATE.IDLE) {
            MessageHelper.sendGenericMessage(event.channel, "I'm already on a channel")
            return
        }

        // 2
        val channel = event.member?.voiceState?.channel
        if (channel == null) {
            MessageHelper.sendGenericMessage(event.channel, "You aren't in any voice channel")
            return
        }

        // 3
        try {
            BotAudio.joinChannel(event.guild, channel)
        } catch (e: PermissionException) {
            MessageHelper.sendGenericMessage(event.channel, "I don't have permission to join this channel")
        }
    }
}