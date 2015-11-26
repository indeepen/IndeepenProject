package com.release.indeepen.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.release.indeepen.DefineNetwork;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netLogin.LoginController;
import com.release.indeepen.management.networkManager.netLogin.POSTCheckEmailRequest;
import com.release.indeepen.management.networkManager.netLogin.POSTSignUpRequest;
import com.release.indeepen.user.UserData;


public class SignupFragment extends Fragment {
    public SignupFragment() {
        // Required empty public constructor
    }

    ImageButton btn_signup;
    EditText email, pw, pw_check, name, nickname;
    boolean isFill = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        email = (EditText) view.findViewById(R.id.text_email);
        pw = (EditText) view.findViewById(R.id.text_pw);
        pw_check = (EditText) view.findViewById(R.id.text_pw_check);
        name = (EditText) view.findViewById(R.id.text_username);
        nickname = (EditText) view.findViewById(R.id.text_nickname);


        btn_signup = (ImageButton) view.findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UserData mData = new UserData();
                mData.sEmail = email.getText().toString();
                mData.sArtist =  nickname.getText().toString();
                mData.sName =  name.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                if (TextUtils.isEmpty(mData.sEmail)) {
                    isFill = false;
                    builder.setMessage("이메일를 입력해 주세요.");
                } else if (TextUtils.isEmpty(pw.getText().toString())) {
                    isFill = false;
                    builder.setMessage("비밀번호를 입력해 주세요.");
                } else if (TextUtils.isEmpty(pw_check.getText().toString())) {
                    isFill = false;
                    builder.setMessage("비밀번호를 재입력 입력해 주세요.");
                } else if (TextUtils.isEmpty(mData.sName)) {
                    isFill = false;
                    builder.setMessage("사용자 이름를 입력해 주세요.");
                } else if (TextUtils.isEmpty(mData.sArtist)) {
                    isFill = false;
                    builder.setMessage("닉네임를 입력해 주세요.");
                }
                if(!pw.getText().toString().equals(pw_check.getText().toString())){
                    isFill = false;
                    builder.setMessage("비밀번호가 일치하지 않습니다.");
                }
                if(false == isFill) {
                    builder.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.setCancelable(false);
                    builder.create().show();
                }else {
                    POSTCheckEmailRequest request = new POSTCheckEmailRequest();
                    request.setData(mData.sEmail);
                    request.setURL(DefineNetwork.CHECK_EMAIL);
                    LoginController.getInstance().signUp(request, new NetworkProcess.OnResultListener<String>() {
                        @Override
                        public void onSuccess(NetworkRequest<String> request, String result) {
                            POSTSignUpRequest signUpRequest = new POSTSignUpRequest();
                            signUpRequest.setURL(DefineNetwork.INDEEPEN_SIGNUP);
                            signUpRequest.setData(mData, pw.getText().toString());
                            Toast.makeText(getContext(), "사용 할 수 있는 이메일 입니다.", Toast.LENGTH_SHORT).show();
                            LoginController.getInstance().signUp(signUpRequest, new NetworkProcess.OnResultListener<String>() {
                                @Override
                                public void onSuccess(NetworkRequest<String> request, String result) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("회원가입이 완료 되었습니다.");
                                    builder.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            email.setSelectAllOnFocus(true);
                                            email.setCursorVisible(true);
                                        }
                                    });
                                    builder.setCancelable(false);
                                    builder.create().show();
                                    startActivity(new Intent(getContext(), MainActivity.class));
                                    getActivity().finish();
                                }

                                @Override
                                public void onFail(NetworkRequest<String> request, int code) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("잠시 후 다시 시도해 주세요." + code);
                                    builder.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            email.setSelectAllOnFocus(true);
                                            email.setCursorVisible(true);
                                        }
                                    });
                                    builder.setCancelable(false);
                                    builder.create().show();
                                }
                            });
                        }
                        @Override
                        public void onFail(NetworkRequest<String> request, int code) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("이미 사용중인 이메일 입니다." + code);

                            builder.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    email.setSelectAllOnFocus(true);
                                    email.setCursorVisible(true);
                                }
                            });
                            builder.setCancelable(false);
                            builder.create().show();
                        }
                    });
                }
                /*startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();*/
            }
        });
        return view;
    }


}