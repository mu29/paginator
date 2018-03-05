# paginator
The simplest way to make your recycler view paginable.

It will automatically detect if content loading is complete or not. You don't need to implement a method like `hasFinishLoading`!

## Example

<img src="etc/example.gif" width="400" />

See full example [here](https://github.com/mu29/paginator/tree/master/example).

# Installation

Your top-level `build.gradle`:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

In module-level `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.mu29:paginator:1.0'
}
```

Because this library uses support library 27.1.0, you may get errors if you are already using a different version. In that case, exclude support library:

```gradle
dependencies {
    implementation ('com.github.mu29:paginator:1.0') {
        exclude group: "com.android.support"
    }
}
```

# Usage

### 1. Define view in your layout

```xml
<net.yeoubi.paginator.PagingRecyclerView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_marginTop="45dp"
    app:progressColor="@color/gray_30"
    app:innerClipToPadding="false"
    app:innerPaddingTop="24dp"/>
```

> The `progressColor` attribute must exist and is reference type. You can't define it just like "#FFF".



### 2. Implement OnPaginateListener

```java
class MyActivity extends AppCompatActivity implements PagingRecyclerView.OnPaginateListener {

    private PagingRecyclerView itemList;
    private YourCustomAdapter adapter;

    private void setRecyclerView() {
        // Adapter must be set before the OnPaginateListener.
        itemList.setAdapter(adapter);
        itemList.setOnPaginateListener(this);
    }
    
    @Override
    public void onPaginate(int page) {
        // ...fetch data from database, network, etc...
        adapter.notifyDataSetChanged();
    }
}
```

> As mentioned above, you must set the adapter before the OnPaginateListener.

## Author

InJung Chung / [@mu29](http://mu29.github.io/)

## License

[MIT](https://github.com/mu29/paginator/blob/master/LICENSE)
