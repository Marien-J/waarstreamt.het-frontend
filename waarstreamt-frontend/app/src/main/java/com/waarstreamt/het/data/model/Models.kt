package com.waarstreamt.het.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val id: String,
    val title: String,
    val year: Int?,
    val type: ContentType,
    @SerialName("poster_url") val posterUrl: String?,
)

@Serializable
data class ContentDetail(
    val id: String,
    val title: String,
    val year: Int?,
    val type: ContentType,
    @SerialName("poster_url") val posterUrl: String?,
    @SerialName("backdrop_url") val backdropUrl: String?,
    val summary: String,
    @SerialName("streaming_services") val streamingServices: List<StreamingService>,
    @SerialName("imdb_rating") val imdbRating: Double?,
    @SerialName("rt_score") val rtScore: Int?,
)

@Serializable
data class StreamingService(
    val id: String,
    val name: String,
    @SerialName("logo_url") val logoUrl: String?,
    @SerialName("brand_color") val brandColor: String,
    @SerialName("watch_url") val watchUrl: String?,
)

@Serializable
enum class ContentType {
    @SerialName("movie") MOVIE,
    @SerialName("series") SERIES,
}

// ── Mock data for demo mode ────────────────────────────────────────────────
object MockData {
    val searchResults = listOf(
        SearchResult("1", "Squid Game", 2021, ContentType.SERIES, null),
        SearchResult("2", "The Bear", 2022, ContentType.SERIES, null),
        SearchResult("3", "Oppenheimer", 2023, ContentType.MOVIE, null),
        SearchResult("4", "Stranger Things", 2016, ContentType.SERIES, null),
        SearchResult("5", "Dune: Part Two", 2024, ContentType.MOVIE, null),
        SearchResult("6", "Breaking Bad", 2008, ContentType.SERIES, null),
        SearchResult("7", "Succession", 2018, ContentType.SERIES, null),
        SearchResult("8", "The Substance", 2024, ContentType.MOVIE, null),
    )

    val details = mapOf(
        "1" to ContentDetail(
            id = "1", title = "Squid Game", year = 2021, type = ContentType.SERIES,
            posterUrl = null, backdropUrl = null,
            summary = "Driehonderd wanhopige mensen nemen deel aan een mysterieus overlevingsspel met dodelijke kinderspelletjes. De inzet? 45,6 miljard won. De prijs? Je leven.",
            streamingServices = listOf(StreamingService("netflix", "Netflix", null, "#E50914", null)),
            imdbRating = 8.0, rtScore = 95,
        ),
        "2" to ContentDetail(
            id = "2", title = "The Bear", year = 2022, type = ContentType.SERIES,
            posterUrl = null, backdropUrl = null,
            summary = "Een gelauwerd chef-kok keert terug naar Chicago om zijn familierestaurant te runnen na een persoonlijke tragedie. Een intense blik achter de schermen van een chaotische keuken.",
            streamingServices = listOf(
                StreamingService("disney", "Disney+", null, "#0063E5", null),
                StreamingService("star", "Star+", null, "#E03A3C", null),
            ),
            imdbRating = 8.8, rtScore = 98,
        ),
        "3" to ContentDetail(
            id = "3", title = "Oppenheimer", year = 2023, type = ContentType.MOVIE,
            posterUrl = null, backdropUrl = null,
            summary = "Het verhaal van J. Robert Oppenheimer en de race om de eerste atoombom te ontwikkelen tijdens de Tweede Wereldoorlog. Een meesterwerk van Christopher Nolan.",
            streamingServices = listOf(
                StreamingService("peacock", "Peacock", null, "#000000", null),
                StreamingService("prime", "Prime Video", null, "#00A8E0", null),
            ),
            imdbRating = 8.9, rtScore = 93,
        ),
        "4" to ContentDetail(
            id = "4", title = "Stranger Things", year = 2016, type = ContentType.SERIES,
            posterUrl = null, backdropUrl = null,
            summary = "In een kleine Amerikaanse stad verdwijnt een jongen mysterieus. Zijn vrienden ontdekken bovennatuurlijke mysteries, geheime experimenten en een vreemd meisje uit een andere dimensie.",
            streamingServices = listOf(StreamingService("netflix", "Netflix", null, "#E50914", null)),
            imdbRating = 8.7, rtScore = 93,
        ),
        "5" to ContentDetail(
            id = "5", title = "Dune: Part Two", year = 2024, type = ContentType.MOVIE,
            posterUrl = null, backdropUrl = null,
            summary = "Paul Atreides sluit een alliantie met Chani en de Fremen terwijl hij een heilige oorlog voorbereidt. Een episch sci-fi spektakel op een verzengende woestijnplaneet.",
            streamingServices = listOf(
                StreamingService("max", "Max", null, "#002BE0", null),
                StreamingService("apple", "Apple TV+", null, "#1C1C1E", null),
            ),
            imdbRating = 8.5, rtScore = 92,
        ),
        "6" to ContentDetail(
            id = "6", title = "Breaking Bad", year = 2008, type = ContentType.SERIES,
            posterUrl = null, backdropUrl = null,
            summary = "Een scheikundeleraar met kanker begint met het produceren van crystal meth om zijn familie financieel veilig te stellen. Zijn onomkeerbare val in criminaliteit.",
            streamingServices = listOf(StreamingService("netflix", "Netflix", null, "#E50914", null)),
            imdbRating = 9.5, rtScore = 96,
        ),
        "7" to ContentDetail(
            id = "7", title = "Succession", year = 2018, type = ContentType.SERIES,
            posterUrl = null, backdropUrl = null,
            summary = "De disfunctionele Roy-familie vecht om de controle over een mediakeizer­rijk terwijl de patriarch zijn erfopvolging regelt. Scherpe satire op macht en hebzucht.",
            streamingServices = listOf(StreamingService("max", "Max", null, "#002BE0", null)),
            imdbRating = 8.9, rtScore = 99,
        ),
        "8" to ContentDetail(
            id = "8", title = "The Substance", year = 2024, type = ContentType.MOVIE,
            posterUrl = null, backdropUrl = null,
            summary = "Een verouderende fitnessster gebruikt een experimenteel stof dat een jongere versie van zichzelf creëert. Een radicale body-horror over schoonheidsidealen en zelfvernietiging.",
            streamingServices = listOf(
                StreamingService("mubi", "MUBI", null, "#1C1C1E", null),
                StreamingService("prime", "Prime Video", null, "#00A8E0", null),
            ),
            imdbRating = 7.3, rtScore = 88,
        ),
    )
}
