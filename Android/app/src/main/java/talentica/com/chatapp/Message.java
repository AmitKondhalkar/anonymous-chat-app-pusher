package talentica.com.chatapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amitk on 19/01/17.
 */

public class Message implements Parcelable{
    @SerializedName("user_name")
    String user_name;
    @SerializedName("chat_message")
    String chat_message;

    protected Message(Parcel in) {
        this.user_name = in.readString();
        this.chat_message = in.readString();
    }

    protected Message() {
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_name);
        parcel.writeString(chat_message);
    }
}
