package com.example.vortexcar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatWithAdminActivity extends AppCompatActivity {
    private RecyclerView recyclerViewChat;
    private EditText editTextMessage;
    private Button buttonSend;
    private ChatAdapter chatAdapter;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_admin);

        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(chatAdapter);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String text = editTextMessage.getText().toString().trim();
        if (!TextUtils.isEmpty(text)) {
            String sender = "User"; // Replace with actual user identification logic
            Message message = new Message(sender, text);
            messageList.add(message);
            chatAdapter.notifyDataSetChanged();
            editTextMessage.setText("");
            recyclerViewChat.scrollToPosition(messageList.size() - 1);
        } else {
            Toast.makeText(ChatWithAdminActivity.this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
}