package audio

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion

object BotAudio {

    enum class STATE {
        IDLE,
        ON_CHANNEL
    }

    var state = STATE.IDLE

    fun joinChannel(guild: Guild, channel: AudioChannelUnion) {
        guild.audioManager.openAudioConnection(channel)
        state = STATE.ON_CHANNEL
    }

    fun leaveChannel(guild: Guild) {
        guild.audioManager.closeAudioConnection()
        state = STATE.IDLE
    }

    fun botChannel(guild: Guild) : AudioChannelUnion? = guild.audioManager.connectedChannel
}