package audio

import com.google.api.services.youtube.model.Video
import net.dv8tion.jda.api.entities.Guild
import java.util.PriorityQueue
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class GuildQueueManager(
    val guild: Guild
) : LinkedBlockingQueue<Video>()