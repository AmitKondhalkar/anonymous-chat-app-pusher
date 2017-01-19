package talentica.com.chatapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import talentica.com.androidstarterkit.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static String userName;
    Button btnSendMessage;
    EditText etMessageInput;
    ListView messagesView;
    List<Message> messageList;
    MessageAdapter messageAdapter;

    private final String BASE_URL = "Enter server ip and port";  // node server running on
    private final String PUSHER_APP_KEY = "Enter key from pusher dashboard"; //https://dashboard.pusher.com/

    private final String SERVER_ENDPOINT = BASE_URL+"/pusher_message";
    private final String PUSHER_PUBLIC_CHANNEL = "public-chat-message";
    private final String PUSHER_EVENT_CHAT_MESSAGE = "event-chat-message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.chat_room_title);

        etMessageInput = (EditText) findViewById(R.id.et_message_input);
        btnSendMessage = (Button) findViewById(R.id.btn_send_message);
        btnSendMessage.setOnClickListener(this);
        messagesView = (ListView) findViewById(R.id.messages_view);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(MainActivity.this, messageList);
        messagesView.setAdapter(messageAdapter);

        setUserName();
        subscribeToPublicChannel();
    }

    private void setUserName(){
        userName = SharedPref.getUserFromSharedPref(MainActivity.this);
    }

    private void subscribeToPublicChannel(){
        Pusher pusher = new Pusher(PUSHER_APP_KEY);
        Channel channel = pusher.subscribe(PUSHER_PUBLIC_CHANNEL);

        channel.bind(PUSHER_EVENT_CHAT_MESSAGE, new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        Log.d(TAG, data);
                        Message message = gson.fromJson(data, Message.class);
                        messageAdapter.add(message);
                        // have the ListView scroll down to the new message
                        messagesView.smoothScrollToPosition(messageAdapter.getCount() - 1);
                    }

                });
            }
        });
        pusher.connect();
    }

    private void publishToPublicChannel(){
        String message = etMessageInput.getText().toString();
        if(message.trim().equals("")){
            return;
        }

        //send message
        RequestParams params = new RequestParams();

        // set our JSON object
        params.put("user_name", userName);
        params.put("chat_message", message);

        // create our HTTP client
        AsyncHttpClient client = new AsyncHttpClient();

        client.post(SERVER_ENDPOINT, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(
//                                getApplicationContext(),
//                                "message sent..",
//                                Toast.LENGTH_LONG
//                        ).show();
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(
                        getApplicationContext(),
                        "Something went wrong :(",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
        etMessageInput.setText("");
    }

    @Override
    public void onClick(View view) {
        publishToPublicChannel();
    }
}
