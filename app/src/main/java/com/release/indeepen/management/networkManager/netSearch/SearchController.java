package com.release.indeepen.management.networkManager.netSearch;

import com.release.indeepen.management.networkManager.NetworkManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netSearch.data.SearchAllResult;
import com.release.indeepen.management.networkManager.netSearch.data.SearchResult;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by lyo on 2015-11-10.
 */
public class SearchController {

    private static SearchController instance;
    private ThreadPoolExecutor mExecutor;

    public static SearchController getInstance() {
        if (instance == null) {
            instance = new SearchController();
        }
        return instance;
    }

    private SearchController() {
        mExecutor = NetworkManager.getInstance().getExecutor();
    }


    public void getSeachAll(NetworkRequest<SearchResult> request, NetworkProcess.OnResultListener<SearchResult> listener) {
        mExecutor.execute(new NetworkProcess<SearchResult>(request, listener));
    }

    public void getSeachKeyword(NetworkRequest<SearchAllResult> request, NetworkProcess.OnResultListener<SearchAllResult> listener) {
        mExecutor.execute(new NetworkProcess<SearchAllResult>(request, listener));
    }




}
