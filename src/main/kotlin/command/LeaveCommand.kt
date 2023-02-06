package command

import audio.BotAudio
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object LeaveCommand : BotCommand{
    override val commandId: String = "leave"

    override fun onCommand(event: MessageReceivedEvent) {
        // Attempt to leave a channel
        // 1. Fail if bot is idle
        // 2. Fail if sender is not on your channel

        if(BotAudio.state == BotAudio.STATE.IDLE) {
            MessageHelper.sendGenericMessage(event.channel, "I'm not on any channel")
            return
        }

        val channel = BotAudio.botChannel(event.guild)
        if(event.member?.voiceState?.channel == null || event.member?.voiceState?.channel?.id != channel?.id) {
            MessageHelper.sendGenericMessage(event.channel, "You're not on my channel")
            return
        }

        BotAudio.leaveChannel(event.guild)
    }
}