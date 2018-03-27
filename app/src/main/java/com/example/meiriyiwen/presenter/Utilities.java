package com.example.meiriyiwen.presenter;

/**
 * Created by 李木白 on 2018/3/24.
 */


import com.example.meiriyiwen.model.Tag;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.util.Base64;
import android.util.Log;

public class Utilities {

    //获取请求返回结果
    public static String requestResources(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document.toString();
    }

    //获取正文部分,返回一个String[],下标1、2分别为标题和作者，3为正文，4为空字符串（adapter中的按钮）
    public static String[] pickUpTextString(String resource) {
        Document document = Jsoup.parse(resource);
        Elements text = document.select("#article_show").select("div.article_text");
        Elements title = document.select("#article_show").select("h1");
        Elements author = document.select("#article_show").select("p");
        String authortext = author.toString().split("\n")[0];
        int l = authortext.indexOf("<span>");
        int r = authortext.indexOf("</span>");
        return new String[] {title.text(),authortext.substring(l+6,r),
                text.toString().replace("</p>","\n").replace("<p>","").replace("<div class=\"article_text\">","").replace("</div>",""),Math.random()+""};
    }


    //提取声音板块全部内容，并返回一个list
    public static List<Tag> pickUpTagList(String resource) {
        Document document = Jsoup.parse(resource);
        List<Tag> list = new ArrayList<Tag>();
        Elements tags = document.select("body").select("div.past_table").select("div");
        String[] ones = tags.toString().split("<div class=\"list_box\"> ");

        //初始化
        for(int i = 1; i <= 13; i++) list.add(new Tag());

        for(int i = 1; i <= 12; i++) {
            String[] temp = ones[i].split("\n");
            //提取书名部分
            int l = temp[4].indexOf(">");
            int r = temp[4].indexOf("</a>");
            list.get(i).setTitle(temp[4].substring(l+1,r));
            //System.out.println(temp[4].substring(l+1,r));

            //提取作者名
            l = temp[7].indexOf("作者：");
            r = temp[7].indexOf("&nbsp");
            list.get(i).setAuthor(temp[7].substring(l+3,r));
            //System.out.println(temp[7].substring(l+3,r));

            //提取主播名
            l = temp[7].indexOf("主播：");
            r = temp[7].length();
            list.get(i).setAnchor(temp[7].substring(l+3,r));
            //System.out.println(temp[7].substring(l+3,r));

            //提取期数
            l = temp[4].indexOf("vid=");
            r = temp[4].indexOf("\" target=");
            list.get(i).setIssuse(Integer.valueOf(temp[4].substring(l+4,r)));
            //System.out.println(temp[4].substring(l+4,r));
            //System.out.println(list.get(i).getIssuse());

            //提取图片URL
            String host = "http://voice.meiriyiwen.com";
            l = temp[2].indexOf("src=");
            r = temp[2].indexOf("\"> </a>");
            list.get(i).setImageURL(host+temp[2].substring(l+5,r));
            //System.out.println(host+temp[2].substring(l+5,r));
        }
        list.remove(0);
        return list;
    }

    //提取书架内容
    public static List<Tag> pickUpBookshelf(String resource) {
        Document document = Jsoup.parse(resource);
        Elements elements = document.select("#container").select("div.content").select("div.list-bg").select("ul");
        String[] ones = elements.toString().split("<li>");
        List<Tag> list = new ArrayList<Tag>();

        //初始化
        for(int i = 1; i <= 13; i++) list.add(new Tag());

        for(int i = 1; i <= 12; i++) {
            String[] temp = ones[i].split("\n");
            //System.out.println(temp[0]);

            //提取标题
            int l = temp[0].indexOf("title=\"");
            int r = temp[0].indexOf("><img");
            list.get(i).setTitle(temp[0].substring(l+7,r-1));
            //System.out.println(temp[0].substring(l+7,r-1));

            //提取图片URL
            String host = "http://book.meiriyiwen.com";
            l = temp[0].indexOf("src=\"");
            r = temp[0].indexOf("></a>");
            list.get(i).setImageURL(host+temp[0].substring(l+5,r-1));
            //System.out.println(host+temp[0].substring(l+5,r-1));

            //提取作者
            list.get(i).setAuthor(temp[5].trim());
            //System.out.println(temp[5].trim());

            //提取bid
            l = temp[2].indexOf("bid=");
            r = temp[2].indexOf("\" title=");
            list.get(i).setBid(temp[2].substring(l+4,r));
            //System.out.println(temp[2].substring(l+4,r));
        }
        //System.out.println(elements.toString());
        list.remove(0);
        return list;
    }

    //提取书架上某本书的目录,第0个为标题，后面的除最后一行为目录,
    public static String[] pickUpCatalog(String resource) {
        Document document = Jsoup.parse(resource);
        Elements elements = document.select("#container").select("div.content").select("div.list-bg").select("ul");
        String[] ones = elements.toString().split("\n");
        elements = document.select("#container").select("div.content").select("div.list-header");
        ones[0] = elements.text();
        ones[ones.length-1] = "end";
        for(int i = 1; i <= ones.length-2; i++) {
            int l = ones[i].indexOf("\">");
            int r = ones[i].indexOf("</a> </li>");
            ones[i] = ones[i].substring(l+2,r);

        }
        return ones;
    }

    //提取声音播放页面
    public static String pickVoice(String resource) {
        Document document = Jsoup.parse(resource);
        System.out.println(resource);
        return null;
    }

    //提取声音文件
    public static String pickUpVoiceFile(String resource) {
        Document document = Jsoup.parse(resource);
        Elements elements = document.select("#voice_show").select("p.p_file");
        resource = elements.toString();
        int l = resource.indexOf("url=");
        int r = resource.indexOf("=&");
        resource = resource.substring(l+4,r);
        try {
            resource = new String(Base64.decode(resource,Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resource;
    }

}


