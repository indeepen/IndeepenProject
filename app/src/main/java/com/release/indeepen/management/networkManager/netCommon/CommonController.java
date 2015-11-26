package com.release.indeepen.management.networkManager.netCommon;

import com.release.indeepen.management.networkManager.NetworkManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netLogin.data.IndeepenLoginResult;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by lyo on 2015-11-10.
 */
public class CommonController {

    private static CommonController instance;
    private ThreadPoolExecutor mExecutor;

    public static CommonController getInstance() {
        if (instance == null) {
            instance = new CommonController();
        }
        return instance;
    }

    private CommonController() {
        mExecutor = NetworkManager.getInstance().getExecutor();
    }


    public void like(NetworkRequest<String> request, NetworkProcess.OnResultListener<String> listener) {
        mExecutor.execute(new NetworkProcess<String>(request, listener));
    }

    public void deleteContent(NetworkRequest<String> request, NetworkProcess.OnResultListener<String> listener) {
        mExecutor.execute(new NetworkProcess<String>(request, listener));
    }

}
