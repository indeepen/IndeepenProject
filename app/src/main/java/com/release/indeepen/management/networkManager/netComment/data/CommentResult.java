package com.release.indeepen.management.networkManager.netComment.data;

import com.google.gson.annotations.SerializedName;
import com.release.indeepen.management.networkManager.netComment.data.Comments;

import java.util.List;

/**
 * Created by lyo on 2015-11-18.
 */
public class CommentResult {
    @SerializedName("result")
    public List<Comments> arrComments;
}
