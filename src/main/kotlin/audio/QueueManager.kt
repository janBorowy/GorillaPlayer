package audio

import net.dv8tion.jda.api.entities.Guild
import youtubeModel.VideoData

object QueueManager {

    private val managersMap: HashMap<Long, GuildQueueManager> = hashMapOf()

    private fun getQueueManager(guild: Guild) : GuildQueueManager {
        return QueueManager.managersMap[guild.id.toLong()] ?: run {
            val newManager = GuildQueueManager(guild)
            QueueManager.managersMap[guild.id.toLong()] = newManager
            newManager
        }
    }

    fun add(guild: Guild, snippet: VideoData) {
        val manager = getQueueManager(guild)
        manager.add(snippet)
    }

    fun poll(guild: Guild): VideoData? {
        val manager = getQueueManager(guild)
        return manager.poll()
    }

    fun peek(guild: Guild): VideoData? {
        val manager = getQueueManager(guild)
        return manager.peek()
    }

    fun toArray(guild: Guild): List<VideoData> {
        val manager = getQueueManager(guild)
        return manager.toList()
    }
}