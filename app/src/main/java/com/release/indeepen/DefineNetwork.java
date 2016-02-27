package com.release.indeepen;

/**
 * Created by lyo on 2015-11-11.
 */
public interface DefineNetwork {
    
    //String URL = "http://54.64.26.200";
    String URL = "http://52.192.126.247";
    String CHARACTER_SET = "UTF-8";

    String METHOD_GET = "GET";
    String METHOD_POST = "POST";
    String METHOD_PUT = "PUT";
    String METHOD_DELETE = "DELETE";

    //POST ART IMAGE


 
    String POST_FILE = "file";
    String POST_IMAGE_ARTTYPE = "workType";
    String POST_IMAGE_EMOTION = "emotion";
    String POST_BLOG_ID = "blogId";
    String POST_TEXT = "content";
    String POST_YOUTUBE = "youTube";


    String POST_CULTURE_TYPE = "showType";
    String POST_CULTURE_TITLE = "title";
    String POST_CULTURE_TAGS = "tags";
    String POST_CULTURE_START_DATE = "startDate";
    String POST_CULTURE_END_DATE = "endDate";
    String POST_CULTURE_START_TIME = "startTime";
    String POST_CULTURE_END_TIME = "endTime";
    String POST_CULTURE_FEE = "fee";
    String POST_CULTURE_ADDRESS = "address";
    String POST_CULTURE_LATITUDE = "latitude";
    String POST_CULTURE_LONGITUDE = "longitude";



    String RESULT_SUCCESS = "Success";
    String RESULT_FAIL = "Fail";

    String CONTENT = URL + "/workPosts";

    String DELETE_CONTENT = URL + "/posts/%s";

    String CULTURE_LIST = URL + "/showPosts?isStart=true";
    String CULTURE_LIST_MORE = URL + "/showPosts";

    String CULTURE_LIST_FILTER = URL + "/showPosts?region=%s&startDate=%s&endDate=%s&field=%s&isStart=true";
    String CULTURE_LIST_MORE_FILTER_MORE = URL + "/showPosts?region=%s&startDate=%s&endDate=%s&field=%s";

    String FAN_LIST = URL + "/posts?isStart=true";
    String FNA_LIST_MORE = URL + "/posts";
    String FNA_LIST_FILTER = URL + "/posts?isStart=true&emotion=%s&field=%s";
    String FNA_LIST_FILTER_MORE = URL + "/posts?emotion=%s&field=%s";

    String COMMENT_LIST = URL + "/posts/%s/comments?isStart=true";
    String COMMENT_LIST_MORE = URL + "/posts/%s/comments?";

    String ART_CONTENT = URL + "/workPosts/";
    String CULTURE_CONTENT = URL + "/showPosts/";


    String MY_BLOG_PROFILE = URL + "/artistBlogs/%s/profile";
    String MY_BLOG_INFO = URL + "/blogs/";

    String MY_BLOG_ART_SINGLE = URL + "/blogs/%s/MyWorks?type=1";
    String MY_BLOG_ART_SINGLE_MORE = URL + "/blogs/%s/MyWorks?type=2";

    String MY_BLOG_ART_TRIPLE = URL + "/blogs/%s/MyWorks?isStart=true&type=0";
    String MY_BLOG_ART_TRIPLE_MORE = URL + "/blogs/%s/MyWorks?type=0";

    String MY_BLOG_FAVORITE_ART_SINGLE = URL + "/blogs/%s/MyLikes?type=1";
    String MY_BLOG_FAVORITE_ART_SINGLE_MORE = URL + "/blogs/%s/MyLikes?type=2";

    String MY_BLOG_FAVORITE_ART_TRIPLE = URL + "/blogs/%s/MyLikes?isStart=true&type=0";
    String MY_BLOG_FAVORITE_ART_TRIPLE_MORE = URL + "/blogs/%s/MyLikes?type=0";

    String USER_LIST_REQUEST = "userListRequest";
    int USER_LIST_TYPE_MY_FAN = 0;
    int USER_LIST_TYPE_MY_ARTIST = 1;
    int USER_LIST_TYPE_IMISSU = 2;


    String MY_BLOG_FAN_LIST = URL + "/blogs/%s/myFans?isStart=true";
    String MY_BLOG_FAN_LIST_MORE = URL + "/blogs/%s/myFans?";

    String FAN = URL + "/blogs/%s/fan";  ///blogs/{blogId}/{fanStatus}  PUT
    String UNFAN = URL + "/blogs/%s/unfan";  ///blogs/{blogId}/{fanStatus}  PUT

    String MY_BLOG_ARTIST_LIST = URL + "/blogs/%s/myArtists?isStart=true";
    String MY_BLOG_ARTIST_LIST_MORE = URL + "/blogs/%s/myArtists?";

    String MY_BLOG_IMISSU_LIST = URL + "/blogs/%s/iMissYous?isStart=true";
    String MY_BLOG_IMISSU_LIST_MORE = URL + "/blogs/%s/iMissYous?";

    String IMISSU =  URL + "/blogs/%s/iMissYous";  ///blogs/{blogId}/iMissYous POST



    String PROFILE_IMG = URL + "/artistBlogs/%s/profilePhoto";
    String BACKGROUD_IMG = URL + "/blogs/%s/bg";

    String MY_CULTURE_LIST = URL + "/blogs/%s/myShows?isStart=true&type=0";
    String MY_CULTURE_LIST_MORE = URL + "/blogs/%s/myShows?type=0";

    String MY_FAVORITE_CULTURE_LIT = URL + "/blogs/%s/myShows?isStart=true&type=1";
    String MY_FAVORITE_CULTURE_LIT_MORE = URL + "/blogs/%s/myShows?type=2";

    String HASHTAG_TRIPLE_LIST = URL + "/workPosts/hashTag?isStart=true&key=%s&type=0";
    String HASHTAG_TRIPLE_LIST_MORE = URL + "/workPosts/hashTag?key=%s&type=0";

    String HASHTAG_SINGLE_LIST = URL + "/workPosts/hashTag?key=%s&type=1";
    String HASHTAG_SINGLE_LIST_MORE = URL + "/workPosts/hashTag?key=%s&type=2";

    String RECOMMEND_TRIPLE_LIST = URL + "/workPosts/recommend?isStart=true&type=0";
    String RECOMMEND_TRIPLE_LIST_MORE = URL + "/workPosts/recommend?type=0";
    String RECOMMEND_SINGLE_LIST = URL + "/workPosts/recommend?type=1";
    String RECOMMEND_SINGLE_LIST_MORE = URL + "/workPosts/recommend?type=2";

    String SEARCH_ALL = URL + "/search?key=%s";
    String SEARCH_HASHTAG = URL + "/search/hashTag?key=%s";
    String SEARCH_ARTIST = URL + "/search/artist?key=%s";
    String SEARCH_SPACE = URL + "/search/space?key=%s";

    String ADD_COMMENT = URL + "/posts/%s/comments";

    String REQUEST_URL = "REQUEST_URL";
    String REQUEST_URL_MORE = "REQUEST_URL_MORE";

    String REQUEST_TYPE = "REQUEST_TYPE";

    String LOGIN = URL + "/login";

    String LIKE = URL + "/posts/%s/%s";

    String INDEEPEN_SIGNUP = URL + "/users";
    String CHECK_EMAIL = URL + "/users/emailCheck";

    String MY_BLOG_INFO_LIST =  URL + "/users/info"; //

    String CHANGE_BLOG =   URL + "/users/activityMode"; //PUT

    int MY_CULTURE = 0;
    int MY_FAVORITE_CULTURE = 1;

    int TYPE_SEARCH_ALL = 2;
    int TYPE_SEARCH_HASHTAG = 3;
    int TYPE_SEARCH_ARTIST = 4;
    int TYPE_SEARCH_SPACE = 5;

    String LIST_POSITION = "LIST_POSITION";

    String CONTENT_KEY = "contentKey";
    String BLOG_KEY = "blogKey";
    String CONTENT_DATA= "contentData";
    String CONTENT_FRAGMENT= "fragment";


    String TEST_USER = "564aa1d94287c23c068b95dd";
    String TEST_COMMENT_USER = "564aa1d94287c23c068b95dc";
}
