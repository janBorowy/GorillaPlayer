package audio

import com.google.api.services.youtube.model.Video
import net.dv8tion.jda.api.entities.Guild

object QueueManager {

    private val managersMap: HashMap<Long, GuildQueueManager> = hashMapOf()

    private fun getQueueManager(guild: Guild) : GuildQueueManager {
        return QueueManager.managersMap[guild.id.toLong()] ?: run {
            val newManager = GuildQueueManager(guild)
            QueueManager.managersMap[guild.id.toLong()] = newManager
            newManager
        }
    }

    fun add(guild: Guild, video: Video) {
        val manager = getQueueManager(guild)
        manager.add(video)
    }

    fun poll(guild: Guild): Video? {
        val manager = getQueueManager(guild)
        return manager.poll()
    }

    fun peek(guild: Guild): Video? {
        val manager = getQueueManager(guild)
        return manager.peek()
    }

    fun toArray(guild: Guild): List<Video> {
        val manager = getQueueManager(guild)
        return manager.toList()
    }
}