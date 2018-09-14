# CYStickyNavLayout
横向列表滑动释放查看更多

![](https://github.com/zhxhcoder/CYStickyNavLayout/blob/master/screenshots/img1.png)

![](https://github.com/zhxhcoder/CYStickyNavLayout/blob/master/screenshots/img2.png)

动态效果图：


![](https://github.com/zhxhcoder/CYStickyNavLayout/blob/master/screenshots/CYStickyNavLayout.gif)



# 最新状况
现已加入XComponent豪华套餐

https://github.com/zhxhcoder/XComponent

#### 使用方法

引用   implementation 'com.zhxh:xcomponentlib:2.8'


        <com.zhxh.xcomponentlib.xstickyhorizon.XStickyNavContainer
            android:id="@+id/xStickynavlayout"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:paddingBottom="15dp">

            <android.support.v7.widget.RecyclerView
                android:id="@+id/xRecyclerview"
                android:layout_width="fill_parent"
                android:layout_height="160dp"
                android:layout_gravity="center_vertical"
                android:background="@color/color_white"
                android:overScrollMode="never"
                android:scrollbars="none" />

        </com.zhxh.xcomponentlib.xstickyhorizon.XStickyNavContainer>
        
        
      xStickynavlayout.setOnStartActivity(() -> RequestManager.toQuantTacticsReverse(strategyId, pageTitle));




