# PinnedHeaderLetterListView
PinnedHeaderLetterListView提供一个类似通讯录列表的组件，具备两个主要功能点，1、滑动时首字母会定在顶端，2、右侧有个字母列表，滑动这个列表时，显示选中的字母，同时将列表定位到相应的位置上

##项目简介
**lib**包为最终提供的公共库<br>
**test**包为测试用例<br>
其中lib里面引用了一个开源库相关代码[PinnedHeaderListView](https://github.com/JimiSmith/PinnedHeaderListView "PinnedHeaderListView"),这个开源库已经实现将列表title定在顶端的功能，具体介绍可前往查阅

##主要类介绍
###PinnedHeaderLetterListView.java
最终提供的组件，继承了PinnedHeaderListView，本质上就是一个ListView，只不过是在ListView的基础上添加了一下操作，在具体使用的时候，跟ListView基本一致<br>
   ```java
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.pin_letter_list_view);
        mAlphaTextSize = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_alphaTextSize, 16);
        mAlphaTextColor = typedArray.getColor(R.styleable.pin_letter_list_view_alphaTextColor, Color.BLACK);
        mAlphaBackgroudNormalColor = typedArray.getColor(R.styleable.pin_letter_list_view_alphaBackgroundNormalColor, Color.TRANSPARENT);
        mAlphaBackgroudPressedColor = typedArray.getColor(R.styleable.pin_letter_list_view_alphaBackgroundPressedColor, Color.GRAY);
        mAlphaBackgroudColor = mAlphaBackgroudNormalColor;
        mAlphaPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_alphaPaddingLeft, 0);
        mAlphaPaddingRight = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_alphaPaddingRight, 0);
        mAlphaPaddingTop = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_alphaPaddingTop, 5);
        mAlphaPaddingBottom = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_alphaPaddingBottom, 5);
        String alphaStr = typedArray.getString(R.styleable.pin_letter_list_view_alphaArrays);
        initAlphaList(alphaStr);

        mOverlayTextColor = typedArray.getColor(R.styleable.pin_letter_list_view_overlayTextColor, Color.WHITE);
        mOverlayTextSize = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_overlayTextSize, 40);
        mOverlaySize = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_overlaySize, 80);
        mOverlayColor = typedArray.getColor(R.styleable.pin_letter_list_view_overlayColor, Color.BLACK);
        mOverlayRadius = typedArray.getDimensionPixelSize(R.styleable.pin_letter_list_view_overlayRadius, 10);

        typedArray.recycle();

    }
```
提供了一些可定制项，例如，选中后提示的字体大小、颜色、弹框的大小、颜色、右侧字母表的相关的定制，对于字母表的数据，用户可直接用alphaArrays指定，缺省值为Adapter里面获取
###PinLetterBaseAdapter.java
继承了SectionedBaseAdapter,扩展了两个方法，用于获取用户滑动字母表时对应的字母在ListView中的postion，使用的时候不需要理会，只要继承这个PinLetterBaseAdapter实现自己的Adapter就可以了
   ```java
   /**
     * for locate the position in the whole listview,
     * this index doesn't include with headViewCount
     *
     * @param section
     * @return
     */
    public int getWholePosition(int section) {
        if (section <= 0) {
            return section;
        }
        int index = 0;
        for (int i = 0; i < section; i++) {
            index += getCountForSection(i) + 1;
        }
        return index;
    }

    public int getWholePosition(String letter) {
        if (TextUtils.isEmpty(letter)) {
            return -1;
        }
        int section = -1;
        for (int i = 0, size = list.size(); i < size; i++) {
            if (letter.equals(list.get(i).getLetter())) {
                section = i;
                break;
            }
        }
        return getWholePosition(section);
    }
```
###SectionAdapter.java
这个类是属于PinnedHeaderListView开源项目的，在BaseAdapter的基础上进行了扩展，将顶部和具体内容的itemView分离，让用户自行去实现相应的View
###entity
这里对放进ListView的数据结构做了一些约束<br>
####PinLetterBaseEntity.java
所有的item的最外层数据继承该类
####PinLetterBaseItemEntity.java
所有letter里面的子item数据继承该类

##使用方式
####在values.xml中新建att.xml
   ```java
   <?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="pin_letter_list_view">
        <attr name="alphaTextColor" format="color"/>
        <attr name="alphaTextSize" format="dimension"/>
        <attr name="alphaBackgroundNormalColor" format="color"/>
        <attr name="alphaBackgroundPressedColor" format="color"/>
        <attr name="alphaPaddingLeft" format="dimension"/>
        <attr name="alphaPaddingRight" format="dimension"/>
        <attr name="alphaPaddingTop" format="dimension"/>
        <attr name="alphaPaddingBottom" format="dimension"/>
        <attr name="overlaySize" format="dimension"/>
        <attr name="overlayColor" format="color"/>
        <attr name="overlayTextSize" format="dimension"/>
        <attr name="overlayTextColor" format="color"/>
        <attr name="overlayRadius" format="dimension"/>
        <attr name="alphaArrays" format="string"/>
    </declare-styleable>
</resources>
```
####在layout.xml文件中
   ```java
   <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:pinletter="http://schemas.android.com/apk/res/com.ulex.apps.pinnedheaderletterlistview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    >

    <com.ulex.apps.pinnedheaderletterlistview.lib.PinnedHeaderLetterListView
        android:id="@+id/pinned_indicated_listView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:listSelector="@null"
        android:cacheColorHint="@android:color/transparent"
        pinletter:overlayTextColor="@android:color/holo_red_dark"
        pinletter:overlayColor="@android:color/holo_blue_bright"
        pinletter:alphaArrays="#ABCDEFGHIJKLMNOPQRSTUVWXYZ"/>

</RelativeLayout>
```
####继承PinLetterBaseEntity.java、PinLetterBaseItemEntity.java实现相应的实体类
####继承PinnedHeaderLetterListView实现Adapter类
   ```java
   public class ContactsListAdapter extends SectionedBaseAdapter {
    private List<ContactsEntity> list;

    public ContactsListAdapter(List<ContactsEntity> list) {
        this.list = list;
    }

    @Override
    public Object getItem(int section, int position) {
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }

    @Override
    public int getSectionCount() {
        return list.size();
    }

    @Override
    public int getCountForSection(int section) {
        return list.get(section).getContactsItemEntityList().size();
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        ItemViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ItemViewHolder(parent);
            convertView = viewHolder.getView();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder) convertView.getTag();
        }
        ContactsItemEntity entity = list.get(section).getContactsItemEntityList().get(position);
        viewHolder.render(entity.getName());
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        HeaderViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new HeaderViewHolder(parent);
            convertView = viewHolder.getView();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HeaderViewHolder) convertView.getTag();
        }
        ContactsEntity entity = list.get(section);
        viewHolder.render(entity.getTitle());
        return convertView;
    }
}
```
 ####最后
    ```java
     List<ContactsPinLetterEntity> list = ContactsPinLetterModel.getContactsList();
        ContactsPinLetterListAdapter listAdapter = new ContactsPinLetterListAdapter(list);
        TextView textView = new TextView(this);
        textView.setText("This is a headerView...");
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        textView.setLayoutParams(params);
        listView.addHeaderView(textView);
        listView.setAdapter(listAdapter);
  ```
 


