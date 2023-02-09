package command

import audio.GuildMusicManager
import audio.PlayerManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.exceptions.PermissionException

object CommandUtils {

    enum class JOIN_STATUS {
        SUCCESS,
        ON_ANOTHER_CHANNEL,
        MEMBER_NOT_ON_CHANNEL,
        PERMISSION_ISSUE
    }

    fun attemptToJoinAChannel(event: MessageReceivedEvent) : JOIN_STATUS {
        // Attempt to join a channel of sender
        // 1. Fail if bot is already on some channel
        // 2. Fail if sender is not on any channel
        // 3. Fail if no permission to join a channel

        // 1
        val manager = PlayerManager.getMusicManager(event.guild)
        if(manager.state != GuildMusicManager.STATE.IDLE) {
            return JOIN_STATUS.ON_ANOTHER_CHANNEL
        }

        // 2
        val channel = event.member?.voiceState?.channel ?: return JOIN_STATUS.MEMBER_NOT_ON_CHANNEL

        // 3
        try {
            manager.joinChannel(channel)
        } catch (e: PermissionException) {
            return JOIN_STATUS.PERMISSION_ISSUE
        }

        return JOIN_STATUS.SUCCESS
    }

}