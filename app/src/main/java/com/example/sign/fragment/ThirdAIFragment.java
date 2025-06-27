package com.example.sign.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sign.R;
import com.example.sign.adapter.TalkListAdapter;
import com.example.sign.bean.Msg;
import com.example.sign.net.OnBack;
import com.example.sign.util.CallBack;
import com.example.sign.util.NetUitl;
import com.example.sign.util.OnClick;
import com.example.sign.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ThirdAIFragment extends Fragment {
    private RecyclerView chatRecyclerView;
    private EditText inputEditText;
    private ImageButton sendButton;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages = new ArrayList<>();
    private OkHttpClient client = new OkHttpClient();
    private static final String API_URL = "https://api.deepseek.com/chat/completions";
    private static final String API_KEY = "sk-4b1207a4875f4595b4623c508a976e24"; // 替换为你的实际API密钥

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third_ai, container, false);
        initViews(view);
        setupChat();
        return view;
    }

    private void initViews(View view) {
        chatRecyclerView = view.findViewById(R.id.chat_recycler_view);
        inputEditText = view.findViewById(R.id.input_edit_text);
        sendButton = view.findViewById(R.id.send_button);

        sendButton.setOnClickListener(v -> {
            String userInput = inputEditText.getText().toString().trim();
            if (!userInput.isEmpty()) {
                addMessage(userInput, "user");
                inputEditText.setText("");
                sendMessageToDeepSeek(userInput);
            }
        });
    }

    private void setupChat() {
        chatAdapter = new ChatAdapter(chatMessages);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatRecyclerView.setAdapter(chatAdapter);

        // 可选：添加系统初始消息
        addMessage("You are a helpful assistant", "system");
    }

    private void addMessage(String content, String role) {
        ChatMessage message = new ChatMessage(content, role);
        chatMessages.add(message);
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
        chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
    }

    private void sendMessageToDeepSeek(String userMessage) {
        // 构建消息历史（包含之前的所有对话）
        JSONArray messagesArray = new JSONArray();
        for (ChatMessage msg : chatMessages) {
            JSONObject msgObj = new JSONObject();
            try {
                msgObj.put("role", msg.getRole());
                msgObj.put("content", msg.getContent());
                messagesArray.put(msgObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // 添加最新的用户消息
        JSONObject userMsg = new JSONObject();
        try {
            userMsg.put("role", "user");
            userMsg.put("content", userMessage);
            messagesArray.put(userMsg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 构建请求体
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("messages", messagesArray);
            requestBody.put("model", "deepseek-chat");
            requestBody.put("max_tokens", 2048);
            requestBody.put("temperature", 1);
            requestBody.put("stream", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        RequestBody body = RequestBody.create(
//                requestBody.toString(),
//                MediaType.parse("application/json")
//        );
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody.toString());


        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "请求失败: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        JSONArray choices = jsonResponse.getJSONArray("choices");
                        if (choices.length() > 0) {
                            String assistantReply = choices.getJSONObject(0)
                                    .getJSONObject("message")
                                    .getString("content");

                            requireActivity().runOnUiThread(() ->
                                    addMessage(assistantReply, "assistant")
                            );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "请求错误: " + response.code(), Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }

    // 聊天消息数据类
    private static class ChatMessage {
        private String content;
        private String role;

        public ChatMessage(String content, String role) {
            this.content = content;
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public String getRole() {
            return role;
        }
    }

    // 简单的RecyclerView适配器
    private class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
        private List<ChatMessage> messages;

        public ChatAdapter(List<ChatMessage> messages) {
            this.messages = messages;
        }

        @Override
        public int getItemViewType(int position) {
            return messages.get(position).getRole().equals("user") ? 0 : 1;
        }

        @NonNull
        @Override
        public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            int layoutId = viewType == 0 ? R.layout.item_user_message : R.layout.item_assistant_message;
            View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new ChatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
            holder.messageText.setText(messages.get(position).getContent());
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        class ChatViewHolder extends RecyclerView.ViewHolder {
            TextView messageText;

            public ChatViewHolder(@NonNull View itemView) {
                super(itemView);
                messageText = itemView.findViewById(R.id.message_text);
            }
        }
    }
}