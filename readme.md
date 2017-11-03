## Tide Places

Technical project for the position of Android developer at [Tide.co](http://www.tide.co).

## Description


## Screenshots

![List screen](screenshots/shot1.png)
![Map screen](screenshots/shot2.png)

## Architecture
The project architecture is based on the MVP pattern. More specifically, the code structure is separated in three layers :

* **Model:** This layer is responsible for managing data. In this app this layer communicates/interacts with external resources (Network, databases etc) to serve the requested data. 
* **Presenter:** This layer is responsible to interacts to all views requests, querying the Model layer and updating the corresponding view, according to the data provided by model
* **View:** This layer is responsible to present the data as decided by the presenter and querying the presenter when it needs to be updated.

More info about this architecture may be found [here](https://antonioleiva.com/mvp-android/)

## Libraries used
* **Dagger 2**
* **Retrofit**
* **Picasso**
* **ButterKnife**
* **RxJava 2**
* **RxAndroid**
* **Mockito**

## IDE
Developed in Android studio 3.0 using Gradle 3.0.0

## Developed by
Antonis Latas (latas.ceid@gmail.com) 