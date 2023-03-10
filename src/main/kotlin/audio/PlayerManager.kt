package audio

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import youtubeApi.YoutubeApi

object PlayerManager {

    private val managersMap: HashMap<Long, GuildMusicManager> = hashMapOf()
    private val audioPlayerManager: AudioPlayerManager

    init {
        audioPlayerManager = DefaultAudioPlayerManager()

        AudioSourceManagers.registerRemoteSources(audioPlayerManager)
        AudioSourceManagers.registerLocalSource(audioPlayerManager)
    }

    fun getMusicManager(guild: Guild): GuildMusicManager {
        return managersMap[guild.id.toLong()] ?: run {
            val newManager = GuildMusicManager(audioPlayerManager, guild)
            managersMap[guild.id.toLong()] = newManager

            guild.audioManager.sendingHandler = newManager.sendHandler
            newManager
        }
    }

    fun loadAndPlay(channel: MessageChannelUnion, trackURL: String) {
        val guild = channel.asGuildMessageChannel().guild
        val musicManager = getMusicManager(guild)

        QueueManager.add(guild, YoutubeApi.getVideoById(trackURL))

        audioPlayerManager.loadItemOrdered(musicManager, trackURL, AudioLoadResultHandlerImpl(musicManager, channel))
    }

    fun skipTrack(guild: Guild) {
        val musicManager = getMusicManager(guild)

        QueueManager.poll(guild)

        musicManager.scheduler.skip()
    }

    fun pause(guild: Guild) {
        val musicManager = getMusicManager(guild)

        musicManager.scheduler.pause()
    }

    fun resume(guild: Guild) {
        val musicManager = getMusicManager(guild)

        musicManager.scheduler.resume()
    }
}