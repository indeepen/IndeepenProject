package com.release.indeepen.management.networkManager.netLogin;

import com.release.indeepen.management.networkManager.NetworkManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netLogin.data.IndeepenLoginResult;
import com.release.indeepen.management.networkManager.netLogin.data.LoginData;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by lyo on 2015-11-10.
 */
public class LoginController {

    private static LoginController instance;
    private ThreadPoolExecutor mExecutor;

    public static LoginController getInstance() {
        if (instance == null) {
            instance = new LoginController();
        }
        return instance;
    }

    private LoginController() {
        mExecutor = NetworkManager.getInstance().getExecutor();
    }


    public void login(NetworkRequest<IndeepenLoginResult> request, NetworkProcess.OnResultListener<IndeepenLoginResult> listener) {
        mExecutor.execute(new NetworkProcess<IndeepenLoginResult>(request, listener));
    }

    public void signUp(NetworkRequest<String> request, NetworkProcess.OnResultListener<String> listener) {
        mExecutor.execute(new NetworkProcess<String>(request, listener));
    }

}
