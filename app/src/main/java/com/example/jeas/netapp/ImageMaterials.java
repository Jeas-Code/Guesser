package com.example.jeas.netapp;

import android.content.Intent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jeas on 2021/5/24.
 */

public class ImageMaterials {

    public Map<String, Integer> imagemap= new HashMap<>();
    public List<String> img_name_list = new ArrayList<>();
    public List<Integer> img_resource_list = new ArrayList<>();

    ImageMaterials(){
        imagemap.put("葫芦娃", R.drawable.gourd_brothers);
        imagemap.put("长城", R.drawable.great_wall);
        imagemap.put("胡歌", R.drawable.huge);
        imagemap.put("故宫", R.drawable.imperial_palace);
        imagemap.put("iPhone", R.drawable.iphone);
        imagemap.put("灯笼", R.drawable.lantern);
        imagemap.put("笔记本电脑", R.drawable.laptop);
        imagemap.put("闪电", R.drawable.lightning);
        imagemap.put("平板电脑", R.drawable.matepad);
        imagemap.put("年兽", R.drawable.monster_nian);
        imagemap.put("月饼", R.drawable.mooncake);
        imagemap.put("奥特曼", R.drawable.ultraman);
        imagemap.put("许嵩", R.drawable.xusong);
        imagemap.put("粽子", R.drawable.zongzi);
    }


    public List<String> getImg_name_list(Map<String, Integer> imagemap) {
        Set<String> set = imagemap.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()){
            img_name_list.add(it.next());
        }
        return img_name_list;
    }

    public List<Integer> getImg_resource_list(Map<String, Integer> imagemap) {
        Collection<Integer> collection = imagemap.values();
        Iterator<Integer> it = collection.iterator();
        while (it.hasNext()){
            img_resource_list.add(it.next());
        }
        return img_resource_list;
    }

    public void setImagemap(Map<String, Integer> imagemap) {
        imagemap = this.imagemap;
    }

    public Map<String, Integer> getImagemap() {
        return imagemap;
    }


}
