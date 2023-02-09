package youtubeApi

import BotProperties
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import youtubeModel.VideoData
import youtubeModel.YoutubeModel

object YoutubeApi {

    private val JSON_FACTORY = JacksonFactory.getDefaultInstance()
    private const val APP_NAME = "DiscordBot"

    private val service: YouTube

    init {
        service = getService()
    }

    fun getVideoById(videoId: String): VideoData {
        val request = service.videos().list("snippet,contentDetails")
        val response = request.setKey(BotProperties.apiToken).setId(videoId).execute()
        if (response.items.size == 0) {
            throw VideoIdException("Video with id $videoId doesn't exist")
        }
        return YoutubeModel.buildVideoData(response.items[0])
    }

    fun searchVideo(title: String, searchSize: Long): List<VideoData> {
        val request = service.search().list("snippet")
        val response = request.setKey(BotProperties.apiToken)
            .setMaxResults(searchSize)
            .setQ(title)
            .execute()

        val results = response.items
        val videoResults = response.items.filter { it.id.kind == "youtube#video" }

        return videoResults.map { getVideoById(it.id.videoId) }
    }

    private fun getService(): YouTube {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val credential = HttpRequestInitializer { }
        return YouTube.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APP_NAME)
            .build()
    }
}