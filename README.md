Trailers App is a Spring Boot–based movie trailer platform that integrates with The Movie Database (TMDB) API to provide users with up-to-date movie trailers, ratings, and metadata.
The application supports user accounts, personalized language preferences, trailer saving, and automated weekly updates from TMDB.

Features
- User Management
- User registration and authentication (JWT-based security)
- User profiles with:
   Username
   Favourite genres
   Preferred language (used for TMDB responses)
- Role-based authorization (ROLE_USER, ROLE_ADMIN)

Trailers
- View trailers by:
- Release date (year, month, or both)
- Rating (e.g. top-rated movies ≥ 7.0)
- Trending movies (weekly)
- Save and unsave trailers to a personal watchlist
- Retrieve saved trailers in the user’s preferred language

TMDB Integration
- Fetches movie data from TMDB, including:
- Titles
- Ratings
- Release dates
- Trailers
- Supports localization using TMDB’s language query parameter
- Uses tmdbId to uniquely identify movies and avoid duplicates

Automated Updates
- Scheduled weekly import of trending movies from TMDB
- Update-or-insert logic ensures:
    Existing trailers are updated
    New trailers are added without duplication
- Admin-only endpoint for manual imports

Tech Stack
- Java 25
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA / Hibernate
- PostgreSQL (or any JPA-compatible database)
- WebClient (for TMDB API calls)
- Scheduled Tasks (@Scheduled)
- Lombok

com.example.Trailers
├── admin
├── auth
├── config
├── exception
├── integration
├── jwt
├── profile
├── roles
├── trailers
├── user
└── TrailersApplication

License
-This project is for educational and portfolio purposes.
