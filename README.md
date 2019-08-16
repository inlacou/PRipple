# PRipple

[![](https://jitpack.io/v/inlacou/PRipple.svg)](https://jitpack.io/#inlacou/PRipple)

This library was created to make clickable buttons (or other button-like surfaces) easier, avoiding the use of drawables.

Features various (could add more) views with ripple effects. They are like the base view but add some extra params:

* *app:normal* -> normal unclicked button color
* *app:ripple* -> ripple effect color
* *app:cornerTopLeft* -> top left corner radius
* *app:cornerTopRight* -> top right corner radius
* *app:cornerBottomLeft* -> bottom left corner radius
* *app:cornerBottomRight* -> bottom right corner radius
* *app:corners* -> corner radius (overrides individual ones)
* *app:strokeColor* -> stroke color
* *app:strokeWidth* -> stroke width

## Examples
![pripple_showcase1](https://github.com/inlacou/PRipple/blob/master/pripple_showcase1.gif)

(Two Textview, two LinearLayout, and the last one is a button made with drawables!)

All this examples use the same theme `@style/ButtonTheme`:
```xml
    <style name="ButtonTheme" parent="@android:style/Widget.TextView">
        <item name="android:paddingRight">@dimen/general_all</item>
        <item name="android:paddingLeft">@dimen/general_all</item>
        <item name="android:paddingTop">@dimen/general_all_half</item>
        <item name="android:paddingBottom">@dimen/general_all_half</item>
        <item name="android:textColor">@color/basic_black</item>
        <item name="android:textStyle">normal</item>
        <item name="android:gravity">center</item>
        <item name="android:fontFamily">sans-serif</item>
        <item name="android:textSize">18sp</item>
        <item name="android:lineSpacingExtra">4sp</item>
        <item name="android:clickable">true</item>
    </style>
```
Note 1: `@dimen/general_all` is 16dp and `@dimen/general_all_half` is 8dp

Note 2: `android:clickable` to `true` is critical

### Textview
```kt
    <com.inlacou.pripple.RippleButton
            android:id="@+id/rippleButton"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:normal="@color/colorPrimary"
            app:ripple="@color/colorAccent"
            android:drawablePadding="@dimen/general_all"
            app:corners="4dp"
            android:drawableStart="@drawable/cancel"
            android:drawableLeft="@drawable/cancel"
            android:theme="@style/ButtonTheme"
            android:text="@string/Lorem_ipsum"/>
```

### LinearLayout
```kt
    <com.inlacou.pripple.RippleLinearLayout
            android:id="@+id/rippleLayout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:normal="@color/basic_white"
            app:ripple="@color/basic_grey"
            app:corners="4dp"
            android:paddingTop="0dp"
            android:paddingBottom="0dp"
            android:gravity="center"
            android:theme="@style/ButtonTheme">
        <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/Lorem_ipsum"/>
    </com.inlacou.pripple.RippleLinearLayout>
```

### RelativeLayout
```kt
    <com.inlacou.pripple.RippleRelativeLayout
            android:id="@+id/rippleLayout2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:normal="@color/basic_white"
            app:ripple="@color/basic_grey"
            app:corners="4dp"
            app:strokeColor="@color/basic_black"
            app:strokeWidth="2dp"
            android:paddingTop="0dp"
            android:paddingBottom="0dp"
            android:gravity="center"
            android:theme="@style/ButtonTheme">
        <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:drawableStart="@drawable/cancel"
                android:drawableLeft="@drawable/cancel"
                android:drawablePadding="@dimen/general_all"
                android:text="@string/Lorem_ipsum"/>
    </com.inlacou.pripple.RippleLinearLayout>
```
