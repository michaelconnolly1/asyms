package com.example.michael.aysms.messages;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.AysmsDate;
import com.example.michael.aysms.model.MessageEntity;

import java.text.SimpleDateFormat;
import java.util.List;
import static com.example.michael.aysms.Utils.Constants.VIEW_TYPE_MESSAGE_RECEIVED;
import static com.example.michael.aysms.Utils.Constants.VIEW_TYPE_MESSAGE_SENT;

/**
 * Created by Michael Connolly on 06/09/2018.
 *
 *  Recycler Adapter to handle list of messages
 */

public class MessageListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MessageEntity> mMessageList;
    private long messageID;

    public MessageListAdapter(Context context, List<MessageEntity> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        MessageEntity message = (MessageEntity) mMessageList.get(position);
        messageID = message.getMessageID();
        Log.d("VIEWTYPE", position + " " + message.getMessageType() + " " + messageID);

        if (message.getMessageType() == VIEW_TYPE_MESSAGE_SENT) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        Log.d("VIEWHOLDER", viewType + " " + messageID);

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageEntity message = (MessageEntity) mMessageList.get(position);

        boolean isNewDay = false;

        // If there is at least one item preceding the current one, check the previous message.
        if (position > 0) {
            MessageEntity prevMessage = mMessageList.get(position - 1);

            // If the date of the previous message is different, display the date before the message,
            SimpleDateFormat ddMMYYYYformat = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = ddMMYYYYformat.format(message.getMessageDate());
            String prevDateString = ddMMYYYYformat.format(prevMessage.getMessageDate());
            if (!dateString.equals(prevDateString)) {
                isNewDay = true;
            }
        } else  {
            isNewDay = true;
        }

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message, isNewDay);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message, isNewDay);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, warning;
        ImageView picture;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            warning = (TextView) itemView.findViewById(R.id.warning);
            picture = (ImageView) itemView.findViewById(R.id.picture);
        }

        void bind(MessageEntity message, boolean isNewDay) {
            messageText.setText(message.getMessageBody());
            if (isNewDay) {
                timeText.setVisibility(View.VISIBLE);
                AysmsDate date = new AysmsDate();
                timeText.setText (date.convertDateToString(message.getMessageDate())) ;
            }
            else
                timeText.setVisibility(View.GONE);

            if (message.getDbMessageID() == -1) {
                warning.setTag(Long.toString(message.getMessageID()));
                Log.d("INFLATE", message.getDbMessageID() + " " + message.getMessageBody() + " " + warning.getTag());
                warning.setVisibility(View.VISIBLE);
            }
            else
                warning.setVisibility(View.GONE);

            if (message.getFileName() != null) {
                picture.setVisibility(View.VISIBLE);
                try {
                    Uri imageURI = Uri.parse(message.getFileName());
                    Log.d("PICTURE", Long.toString(message.getMessageID()) + " " + message.getMessageBody());
                    picture.setImageURI(imageURI);
                } catch (SecurityException e) {
                }
                catch (OutOfMemoryError e) {
                    Toast.makeText(mContext, "Insufficient memory to display all images", Toast.LENGTH_SHORT).show();
                }
            }
            else
                picture.setVisibility(View.GONE);
        }
    }


    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
        }

        void bind(MessageEntity message, boolean isNewDay) {
            messageText.setText(message.getMessageBody());
            nameText.setText(message.getTitle());
            if (isNewDay) {
                timeText.setVisibility(View.VISIBLE);
                AysmsDate date = new AysmsDate();
                timeText.setText (date.convertDateToString(message.getMessageDate())) ;
            }
            else
                timeText.setVisibility(View.GONE);
        }
    }

    public void addRow(MessageEntity message) {
        mMessageList.add(message);
        notifyDataSetChanged();
    }
}
