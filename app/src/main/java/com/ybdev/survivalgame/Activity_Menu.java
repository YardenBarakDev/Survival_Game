package com.ybdev.survivalgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class Activity_Menu extends AppCompatActivity {

    private TextInputEditText menu_EDT_id;
    private MaterialButton menu_BTN_start;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        initViews();
    }

    private void initViews() {
        this.menu_BTN_start.setOnClickListener(v -> makeServerCall());
    }

    public static String getJSON(String url) {
        String data = "";
        try {
            HttpsURLConnection con2 = (HttpsURLConnection) new URL(url).openConnection();
            con2.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(con2.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = br.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine).append("\n");
            }
            br.close();
            data = sb.toString();
            try {
                con2.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex2) {
            ex2.printStackTrace();
        }
        return data;
    }

    private void makeServerCall() {
        new Thread() {
            public void run() {
                String data = getJSON(getString(R.string.url));
                if (data != null) {
                    startGame(menu_EDT_id.getText().toString(), data);
                }
            }
        }.start();
    }

    private void startGame(String id, String data) {
        if (id.length() == 9) {
            String state = data.split(",")[Integer.parseInt(String.valueOf(id.charAt(7)))];
            Intent intent = new Intent(getBaseContext(), Activity_Game.class);
            intent.putExtra(Activity_Game.EXTRA_ID, id);
            intent.putExtra(Activity_Game.EXTRA_STATE, state);
            startActivity(intent);
        } else {
            runOnUiThread(() -> Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show());
        }
    }

    private void findViews() {
        this.menu_BTN_start = findViewById(R.id.menu_BTN_start);
        this.menu_EDT_id = findViewById(R.id.menu_EDT_id);
    }
}
