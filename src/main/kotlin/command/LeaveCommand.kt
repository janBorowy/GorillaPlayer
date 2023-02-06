package command

import MessageHelper
import audio.GuildMusicManager
import audio.PlayerManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object LeaveCommand : BotCommand{
    override val commandId: String = "leave"

    override fun onCommand(event: MessageReceivedEvent) {
        // Attempt to leave a channel
        // 1. Fail if bot is idle
        // 2. Fail if sender is not on your channel

        val manager = PlayerManager.getMusicManager(event.guild)
        if(manager.state == GuildMusicManager.STATE.IDLE) {
            MessageHelper.sendGenericMessage(event.channel, "I'm not on any channel")
            return
        }

        val channel = PlayerManager.getMusicManager(event.guild).botChannel()
        if(event.member?.voiceState?.channel == null || event.member?.voiceState?.channel?.id != channel?.id) {
            MessageHelper.sendGenericMessage(event.channel, "You're not on my channel")
            return
        }

        // TODO: clear queue
        MessageHelper.sendGenericMessage(event.channel, "Bye")
        manager.leaveChannel()
    }
}