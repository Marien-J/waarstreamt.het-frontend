# Claude Code Instructions — WaarStreamt.Het Frontend

## Project overview
Android app (Jetpack Compose + Kotlin) that lets users search for movies/series and see which streaming services carry them.

## Git remote
```
https://github.com/Marien-J/waarstreamt.het-frontend
```

## One-time setup tasks for Claude Code

### 1. Initialize git and push
```bash
cd <project-root>
git init
git add .
git commit -m "feat: initial frontend — home + detail screens with mock data"
git branch -M main
git remote add origin https://github.com/Marien-J/waarstreamt.het-frontend.git
git push -u origin main
```

### 2. Download font files
Download from Google Fonts and place in `app/src/main/res/font/`:
- Bebas Neue Regular → `bebas_neue_regular.ttf`
- DM Sans Light → `dm_sans_light.ttf`
- DM Sans Regular → `dm_sans_regular.ttf`
- DM Sans Medium → `dm_sans_medium.ttf`
- DM Sans SemiBold → `dm_sans_semibold.ttf`

### 3. Add launcher icons
Place a 512×512 icon PNG in the mipmap dirs (or generate with Android Studio's Image Asset tool).

## Key files
| File | Purpose |
|------|---------|
| `data/model/Models.kt` | All data classes + MockData |
| `data/repository/ContentRepository.kt` | Flip `useMockData` here |
| `ui/screens/HomeScreen.kt` | Search + autocomplete |
| `ui/screens/DetailScreen.kt` | Cover · services · summary · ratings |
| `ui/theme/Theme.kt` | All colours & typography |
| `BuildConfig.BASE_URL` | API base URL (set in app/build.gradle.kts) |

## Demo mode
Debug builds automatically use MockData — no backend required.
Try searching: Squid Game, The Bear, Oppenheimer, Dune, Breaking Bad, Succession, Stranger Things, The Substance.

## Coding conventions
- Kotlin idioms, no Java
- All composables in `ui/screens/` or `ui/components/`
- State hoisted to ViewModel; composables are stateless
- Use `Teal` for all primary accent (see Theme.kt)
