package com.release.indeepen;

/**
 * Created by lyo on 2015-11-11.
 */
public interface DefineNetwork {

    String CHARACTER_SET = "UTF-8";

    String METHOD_GET = "GET";
    String METHOD_POST = "POST";
    String METHOD_PUT = "PUT";
    String METHOD_DELETE = "DELETE";

    //POST ART IMAGE



    String POST_FILE = "file";
    String POST_IMAGE_ARTTYPE = "workType";
    String POST_IMAGE_EMOTION = "emotion";
    String POST_IMAGE_BLOG_ID = "blogId";
    String POST_IMAGE_TEXT = "content";

    String RESULT_SUCCESS = "Success";
    String RESULT_FAIL = "Fail";

    String DELETE_CONTENT = "http://54.64.26.200/posts/%s";

    String CULTURE_LIST = "http://54.64.26.200/showPosts?isStart=true";
    String CULTURE_LIST_MORE = "http://54.64.26.200/showPosts";

    String FAN_LIST = "http://54.64.26.200/posts?isStart=true";
    String FNA_LIST_MORE = "http://54.64.26.200/posts";
    String FNA_LIST_FILTER = "http://54.64.26.200/posts?isStart=true&emotion=%s&field=%s";
    String FNA_LIST_FILTER_MORE = "http://54.64.26.200/posts?emotion=%s&field=%s";

    String COMMENT_LIST = "http://54.64.26.200/posts/*/comments?isStart=true";
    String COMMENT_LIST_MORE = "http://54.64.26.200/posts/*/comments?";

    String ART_CONTENT = "http://54.64.26.200/workPosts/";
    String CULTURE_CONTENT = "http://54.64.26.200/showPosts/";


    String MY_BLOG_PROFILE = "http://54.64.26.200/artistBlogs/%s/profile";
    String MY_BLOG_INFO = "http://54.64.26.200/blogs/";

    String MY_BLOG_ART_SINGLE = "http://54.64.26.200/blogs/%s/MyWorks?type=1";
    String MY_BLOG_ART_SINGLE_MORE = "http://54.64.26.200/blogs/%s/MyWorks?type=2";

    String MY_BLOG_ART_TRIPLE = "http://54.64.26.200/blogs/%s/MyWorks?isStart=true&type=0";
    String MY_BLOG_ART_TRIPLE_MORE = "http://54.64.26.200/blogs/%s/MyWorks?type=0";

    String MY_BLOG_FAVORITE_ART_SINGLE = "http://54.64.26.200/blogs/%s/MyLikes?type=1";
    String MY_BLOG_FAVORITE_ART_SINGLE_MORE = "http://54.64.26.200/blogs/%s/MyLikes?type=2";

    String MY_BLOG_FAVORITE_ART_TRIPLE = "http://54.64.26.200/blogs/%s/MyLikes?isStart=true&type=0";
    String MY_BLOG_FAVORITE_ART_TRIPLE_MORE = "http://54.64.26.200/blogs/%s/MyLikes?type=0";

    String USER_LIST_REQUEST = "userListRequest";
    int USER_LIST_TYPE_MY_FAN = 0;
    int USER_LIST_TYPE_MY_ARTIST = 1;
    int USER_LIST_TYPE_IMISSU = 2;


    String MY_BLOG_FAN_LIST = "http://54.64.26.200/blogs/%s/myFans?isStart=true";
    String MY_BLOG_FAN_LIST_MORE = "http://54.64.26.200/blogs/%s/myFans?";

    String MY_BLOG_ARTIST_LIST = "http://54.64.26.200/blogs/%s/myArtists?isStart=true";
    String MY_BLOG_ARTIST_LIST_MORE = "http://54.64.26.200/blogs/%s/myArtists?";

    String MY_BLOG_IMISSU_LIST = "http://54.64.26.200/blogs/%s/iMissYous?isStart=true";
    String MY_BLOG_IMISSU_LIST_MORE = "http://54.64.26.200/blogs/%s/iMissYous?";


    String PROFILE_IMG = "http://54.64.26.200/artistBlogs/%s/profilePhoto";
    String BACKGROUD_IMG = "http://54.64.26.200/blogs/%s/bg";

    String MY_CULTURE_LIST = "http://54.64.26.200/blogs/%s/myShows?isStart=true&type=0";
    String MY_CULTURE_LIST_MORE = "http://54.64.26.200/blogs/%s/myShows?type=0";

    String MY_FAVORITE_CULTURE_LIT = "http://54.64.26.200/blogs/%s/myShows?isStart=true&type=1";
    String MY_FAVORITE_CULTURE_LIT_MORE = "http://54.64.26.200/blogs/%s/myShows?type=2";

    String HASHTAG_TRIPLE_LIST = "http://54.64.26.200/workPosts/hashTag?isStart=true&key=%s&type=0";
    String HASHTAG_TRIPLE_LIST_MORE = "http://54.64.26.200/workPosts/hashTag?key=%s&type=0";
    String HASHTAG_SINGLE_LIST = "http://54.64.26.200/workPosts/hashTag?key=%s&type=1";
    String HASHTAG_SINGLE_LIST_MORE = "http://54.64.26.200/workPosts/hashTag?key=%s&type=2";

    String RECOMMEND_TRIPLE_LIST = "http://54.64.26.200/workPosts/recommend?isStart=true&type=0";
    String RECOMMEND_TRIPLE_LIST_MORE = "http://54.64.26.200/workPosts/recommend?type=0";
    String RECOMMEND_SINGLE_LIST = "http://54.64.26.200/workPosts/recommend?type=1";
    String RECOMMEND_SINGLE_LIST_MORE = "http://54.64.26.200/workPosts/recommend?type=2";

    String SEARCH_ALL = "http://54.64.26.200/search?key=%s";
    String SEARCH_HASHTAG = "http://54.64.26.200/search/hashTag?key=%s";
    String SEARCH_ARTIST = "http://54.64.26.200/search/artist?key=%s";
    String SEARCH_SPACE = "http://54.64.26.200/search/space?key=%s";

    String ADD_COMMENT = "http://54.64.26.200/posts/%s/comments";

    String REQUEST_URL = "REQUEST_URL";
    String REQUEST_URL_MORE = "REQUEST_URL_MORE";

    String REQUEST_TYPE = "REQUEST_TYPE";

    String LOGIN = "http://54.64.26.200/login";

    String LIKE = "http://54.64.26.200/posts/%s/%s";

    String INDEEPEN_SIGNUP = "http://54.64.26.200/users/users";
    String CHECK_EMAIL = "http://54.64.26.200/users/emailCheck";

    int MY_CULTURE = 0;
    int MY_FAVORITE_CULTURE = 1;

    int TYPE_SEARCH_ALL = 2;
    int TYPE_SEARCH_HASHTAG = 3;
    int TYPE_SEARCH_ARTIST = 4;
    int TYPE_SEARCH_SPACE = 5;

    String LIST_POSITION = "LIST_POSITION";

    String CONTENT_KEY = "contentKey";
    String BLOG_KEY = "blogKey";



    String TEST_USER = "564aa1d94287c23c068b95dd";
    String TEST_COMMENT_USER = "564aa1d94287c23c068b95dc";
}
