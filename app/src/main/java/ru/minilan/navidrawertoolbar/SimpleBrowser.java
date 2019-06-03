package ru.minilan.navidrawertoolbar;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class SimpleBrowser extends AppCompatActivity {
    private EditText editTextURL;
    private Button buttonDownload;
    private WebView webView;
    private Handler handler;
    private StringBuilder buffer;
    private String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_browser);

        initGUI();
        initListeners();
        handler = new Handler();

    }

    private void initGUI() {
        editTextURL = findViewById(R.id.editTextURL);
        buttonDownload = findViewById(R.id.buttonDownload);
        webView = findViewById(R.id.webView);
    }

    private void initListeners() {
        buttonDownload.setOnClickListener(onButtonDownloadClick);
    }

    View.OnClickListener onButtonDownloadClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String urlString = editTextURL.getText().toString();
            if (urlString.equals("")) {
                Toast.makeText(SimpleBrowser.this, getResources().getString(R.string.empty_url), LENGTH_SHORT).show();
            } else {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        //// HTTPS CONNECTION

//                        HttpsURLConnection connection = null;
//                        try {
//                            URL url = new URL(urlString);
//                            connection = (HttpsURLConnection) url.openConnection();
//                            connection.setRequestMethod("GET");
//                            connection.setReadTimeout(1000);
//                            connection.connect();
//                            buffer = new StringBuilder();
//                            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                            String line;
//                            while ((line = input.readLine()) != null) {
//                                buffer.append(line);
//                                buffer.append("\n");
//                            }
//                            page = buffer.toString();
//
//                        } catch (Exception e) {
//                            Log.e("MyTAG", e.getMessage());
//                        } finally {
//                            if (connection != null) {
//                                connection.disconnect();
//                            }
//                        }
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                webView.loadData(page, "text/html; charset=utf-8", "utf-8");
//                            }
//                        });

                        //// OKHTTP

                        OkHttpClient okHttpClient = new OkHttpClient();
                        try {
                            Request request = new Request.Builder()
                                    .url(urlString)
                                    .build();
                            Call call = okHttpClient.newCall(request);
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    final String page = response.body().string();
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            webView.loadData(page, "text/html; charset=utf-8", "utf-8");
                                        }
                                    });
                                }
                            });

                        } catch (Exception e) {
                            Log.e("MyTAG",e.getMessage());
                        }




                    }
                }).start();

            }
        }
    };
}
