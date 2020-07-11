# FoodRecipes App
Application with MVVM architecture and Retrofit2. We use [api](http://recipesapi.herokuapp.com/) to fetch data. User can see food recipes and data even will save to the CACHE. So if user want to see recipe, but he does not have enternet connection, app will check the recipe in the cache and show it

## 1. Architecture
This uses Google company's architecture. This architecture allows developer to observe any change that happend with the object. Google suggest to use this one in youre projects.
Check out the [guide](https://developer.android.com/jetpack/guide#recommended-app-arch) for this architecture
![alt text](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png "MVVM Architecture")

## 2. Retrofit2 
A type-safe HTTP client for Android and Java, Kotlin. Check out this [website](https://square.github.io/retrofit/) to see more. Using this library we will get data from the Rest Api

## 3. Cacheing using Room
The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite. Check out this [website](https://developer.android.com/topic/libraries/architecture/room) to see more. Using this library we will CREATE, READ, UPDATE and DELETE (CRUD) our data to device cache
