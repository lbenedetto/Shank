# Shank
Sure, a Dagger would be nice, but a Shank will do the trick

Why learn complicated new things when you can just use constructors like you're used to?
This library requires that you use MVP pattern, and even sort of enforces some aspects of it.

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

```gradle
dependencies {
    implementation 'com.github.lbenedetto:Shank:1.1.1'
}
```
