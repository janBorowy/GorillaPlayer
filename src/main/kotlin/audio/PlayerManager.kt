package audio

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion

object PlayerManager {

    private val managersMap: HashMap<Long, GuildMusicManager> = hashMapOf()
    private val audioPlayerManager: AudioPlayerManager

    init {
        audioPlayerManager = DefaultAudioPlayerManager()

        AudioSourceManagers.registerRemoteSources(audioPlayerManager)
        AudioSourceManagers.registerLocalSource(audioPlayerManager)
    }

    private fun getMusicManager(guild: Guild): GuildMusicManager {
        return managersMap[guild.id.toLong()] ?: run {
            val newManager = GuildMusicManager(audioPlayerManager)
            managersMap[guild.id.toLong()] = newManager

            guild.audioManager.sendingHandler = newManager.sendHandler
            newManager
        }
    }

    fun loadAndPlay(channel: MessageChannelUnion, trackURL: String) {
        val musicManager = getMusicManager(channel.asGuildMessageChannel().guild)

        audioPlayerManager.loadItemOrdered(musicManager, trackURL, AudioLoadResultHandlerImpl(musicManager, channel))
    }
}