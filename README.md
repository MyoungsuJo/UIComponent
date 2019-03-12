# UIComponent
자주사용하는 UI 컴포넌트 모음

## 1. Infinity Preview View Pager

1. Loop & Infinity scroll
2. left , right side preview

<table>
<thead>
<tr>
<th align="center">Attribute</th>
<th align="center">Default Value</th>
<th align="center">Action</th>
</tr>
</thead>
<tbody>
<td align="center">autoScroll</td>
<td align="center">false</td>
<td align="center">true (Auto scroll) / false (or not)</td>
</tr>
<tr>
<td align="center">scrollInterval</td>
<td align="center">3000(ms)</td>
<td align="center"> Scroll Interval (if autoScroll is true)</td>
</tr>
<tr>
<td align="center">isShowPreview</td>
<td align="center">fasle</td>
<td align="center">true (left N right side showing preview) / false (or not)</td>
</tr>
<tr>
<td align="center">padding</td>
<td align="center">0</td>
<td align="center">Padding within one page (Works if isShowPreview is true)</td>
</tr>
<tr>
<td align="center">pageMargin</td>
<td align="center">0</td>
<td align="center">Margin with other page (Works if isShowPreview is true)</td>
</tr>
</tbody>
</table>

### Usage



#### Add to Project

use Gradle:

```gradle
allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io" } // add this line
    }
}

dependencies {
  implementation 'com.github.MyoungsuJo:UIComponent:0.0.1'
}
```
    

#### xml

    <com.msjo.uicomponentlib.MSViewPager
            android:id="@+id/viewPager"
            android:layout_width="0dp"
            android:layout_height="0dp"
            app:autoScroll="true"
            app:isShowPreview="true"
            app:scrollInterval="3000"
            app:padding="50"
            app:pageMargin="15"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintBottom_toBottomOf="parent">

    </com.msjo.uicomponentlib.MSViewPager>

#### java

   
    
        MSViewPager msViewPager = (MSViewPager) findViewById(R.id.viewPager);

        imageArrayList.add("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
        imageArrayList.add("https://images.g2a.com/newlayout/323x433/1x1x0/bfde13051dfc/5bb78114ae653a5bd2008af2");
        imageArrayList.add("https://s.yimg.com/ny/api/res/1.2/6P3IyVQjEwFw79YTiAqFmw--~A/YXBwaWQ9aGlnaGxhbmRlcjtzbT0xO3c9ODAw/https://media.zenfs.com/en-GB/the_telegraph_258/8f9924d49e8bc69642e39a38da4b2eed");

        msViewPager.setAdapter(new LoopImageViewPagerAdapter(this,imageArrayList, true));
   
