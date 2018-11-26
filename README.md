# Shank
Sure, a Dagger would be nice, but a Shank will do the trick

Shank is a library that allows you to pass your arguments directly to your presenter, which it can then use to set up your view in your activity.

Its as simple as extending the provided ShankPresenter and ConstructableActivity, and using this to start your activity instead of doing it the usual way.
MyPresenter(myObject).start(context, MyActivity::class.java)

This library requires that you use MVP pattern, and even sort of enforces some aspects of it.

Its called Shank because it serves a similar purpose to Dagger, but its probably not as good, and kinda seems like something someone made out of some garbage they found.

See the example project to see how to implement.

A stub of a presenter would look like this. You can pass whatever objects you want your activity to use into the presenters constructor

```Kotlin
class MyPresenter() : ShankPresenter<TestActivity, MyContract.View>(), MyContract.Presenter {
    private lateinit var view: TestContract.View

    override fun onReady() {
        view = getView()!!
    }
}
```
    
A stub of an Activity would look like this

```Kotlin
class MyActivity : ConstructableActivity<MyContract.View>() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Note the use of setView instead of setContentView
        //Treat it like the same thing, it is calling it beind the scenes
        setView(TestView(this))
    }
    
}
```

Your view doesn't need to know anything about this library, just treat it like normal

And you would start your Activity/Presenter thing by doing  
```Kotlin
MyPresenter().start(context, MyActivity::class.java)
```


# Installation
Add jitpack to your root build.gradle at the end of repositories:
```gradle
repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Add dependency
```gradle
dependencies {
    implementation 'com.github.lbenedetto:Shank:1.1.2'
}
```
