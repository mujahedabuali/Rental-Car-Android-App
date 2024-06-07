package com.example.vortexcar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class ChatActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private EditText messageInput;
    private Button sendButton;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    private String currentUserId;
    private String adminId = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        messageList = new ArrayList<>();
//        currentUserId = mAuth.getCurrentUser().getUid();
//        messageAdapter = new MessageAdapter(messageList, "123");
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(messageAdapter);
//
//        sendButton.setOnClickListener(v -> sendMessage());
//
//        listenForMessages();
    }

    private void sendMessage() {
        String messageText = messageInput.getText().toString();
        if (TextUtils.isEmpty(messageText)) {
            return;
        }

        Map<String, Object> message = new HashMap<>();
        message.put("text", messageText);
        message.put("senderId", currentUserId);
        message.put("receiverId", adminId);
        message.put("timestamp", System.currentTimeMillis());

        db.collection("messages").add(message)
                .addOnSuccessListener(documentReference -> messageInput.setText(""));
    }

    private void listenForMessages() {
        db.collection("messages")
                .orderBy("timestamp")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        return;
                    }

                    messageList.clear();
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            Message message = dc.getDocument().toObject(Message.class);
                            if ((message.getSenderId().equals(currentUserId) && message.getReceiverId().equals(adminId)) ||
                                    (message.getSenderId().equals(adminId) && message.getReceiverId().equals(currentUserId))) {
                                messageList.add(message);
                            }
                        }
                    }
                    messageAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(messageList.size() - 1);
                });
    }
}
