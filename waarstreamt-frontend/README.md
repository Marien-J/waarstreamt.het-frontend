# WaarStreamt.Het — Frontend

Android app to discover which streaming services carry a given movie or TV series.

## Stack
- **Jetpack Compose** — fully declarative UI
- **Hilt** — dependency injection
- **Retrofit + kotlinx.serialization** — network layer
- **Coil** — async image loading
- **Navigation Compose** — single-Activity nav

## Architecture
```
app/
 └─ data/
 │   ├─ api/          Retrofit interface
 │   ├─ model/        Data classes (+ MockData for demo)
 │   └─ repository/   ContentRepository (real ↔ mock toggle)
 └─ ui/
     ├─ screens/      HomeScreen · DetailScreen
     ├─ viewmodel/    SearchViewModel
     ├─ theme/        Colors · Typography · Theme
     └─ NavGraph.kt
```

## Demo mode
In `debug` builds, `ContentRepository` uses **MockData** — no backend required.  
Flip `useMockData = false` in `NetworkModule` to hit the real API.

## Backend API contract (when ready)
```
GET /search?q={query}         → List<SearchResult>
GET /content/{id}             → ContentDetail
```
See `data/api/WaarStreamtApi.kt` for full interface.

## Font assets
Place these in `app/src/main/res/font/`:
- `bebas_neue_regular.ttf`
- `dm_sans_light.ttf`
- `dm_sans_regular.ttf`
- `dm_sans_medium.ttf`
- `dm_sans_semibold.ttf`

Download from [Google Fonts](https://fonts.google.com).

## Backend repo
Coming soon: `waarstreamt.het-backend`
