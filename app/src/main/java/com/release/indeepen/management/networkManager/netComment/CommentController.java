package com.release.indeepen.management.networkManager.netComment;

import com.release.indeepen.management.networkManager.NetworkManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netComment.data.CommentResult;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by lyo on 2015-11-18.
 */
public class CommentController {

    private static CommentController instance;
    private ThreadPoolExecutor mExecutor;

    public static CommentController getInstance() {
        if (instance == null) {
            instance = new CommentController();
        }
        return instance;
    }

    private CommentController() {
        mExecutor = NetworkManager.getInstance().getExecutor();
    }

    public void getCommentList(NetworkRequest<CommentResult> request, NetworkProcess.OnResultListener<CommentResult> listener) {
        mExecutor.execute(new NetworkProcess<CommentResult>(request, listener));
    }

    public void postComment(NetworkRequest<String> request, NetworkProcess.OnResultListener<String> listener) {
        mExecutor.execute(new NetworkProcess<String>(request, listener));
    }


}
