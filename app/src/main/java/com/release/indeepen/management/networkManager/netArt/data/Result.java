package com.release.indeepen.management.networkManager.netArt.data;

import com.google.gson.annotations.SerializedName;
import com.release.indeepen.management.networkManager.netComment.data.Comments;

import java.util.List;

/**
 * Created by lyo on 2015-11-11.
 */
public class Result {
    @SerializedName("postInfo")
    public Postinfo mPostinfo;

    @SerializedName("commentCnt")
    public int nCommentCnt;

    @SerializedName("comments")
    public List<Comments> arrComments;

}
