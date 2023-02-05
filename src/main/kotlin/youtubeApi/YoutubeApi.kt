package youtubeApi

import BotProperties
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.Video

object YoutubeApi {

    private val JSON_FACTORY = JacksonFactory.getDefaultInstance()
    private const val APP_NAME = "DiscordBot"

    private val service: YouTube

    init {
        service = getService()
    }

    fun getVideo(videoId: String) : Video {
        val request = service.videos().list("snippet,contentDetails,statistics")
        val response = request.setKey(BotProperties.apiToken).setId(videoId).execute()
        if(response.items.size == 0) {
            throw VideoIdException("Video with id $videoId doesn't exist")
        }
        return response.items[0]
    }

    private fun getService(): YouTube {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val credential = HttpRequestInitializer { }
        return YouTube.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APP_NAME)
            .build()
    }
}