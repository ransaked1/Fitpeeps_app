package com.example.helsenaplus.data.model;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import com.example.helsenaplus.R;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;



public class HomeFragment extends Fragment {

    private static String user_name;

    RequestQueue requestQueue;
    String id = "E090FC1A852B70D9F24E2EB568711BCD";
    String url = "https://fitpeeps.ransaked1.repl.co/get/user?id=" + id;


    public int user_age;
    public String user_gender;
    public int normal_steps;

    public int user_food;
    public int user_sleep;
    public int user_steps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        requestQueue = Volley.newRequestQueue(getContext());

        return v;
    }

    private void fetchdata()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                String jsonString = response.toString();
                JSONObject obj = new JSONObject(jsonString);
                user_age = Integer.valueOf(obj.getJSONObject("AboutUser").getString("Age"));
                Log.d("JSON:", String.valueOf(user_age));
                user_food = Math.round(Float.valueOf(obj.getJSONObject("Percentages").getString("Food")));
                Log.d("JSON:", String.valueOf(user_food));
                user_sleep = Math.round(Float.valueOf(obj.getJSONObject("Percentages").getString("Sleep")));
                if (user_sleep > 100)
                    user_sleep = 100;
                Log.d("JSON:", String.valueOf(user_sleep));
                user_steps = Math.round(Float.valueOf(obj.getJSONObject("Percentages").getString("Steps")));
                if (user_steps > 100)
                    user_steps = 100;
                Log.d("JSON:", String.valueOf(user_steps));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            // Do something when error occurred
            Log.d("JSON:", error.getMessage());
        }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView date = (TextView) getView().findViewById(R.id.date);
        TextView user = (TextView) getView().findViewById(R.id.user_name);

        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMMM d");//formating according to my need
        String dateString = formatter.format(today);

        date.setText(dateString);
        user.setText("Hello, " + user_name + "!");

        ImageButton btn_activity = (ImageButton) getView().findViewById(R.id.activity);
        ImageButton btn_wellness = (ImageButton) getView().findViewById(R.id.wellness);
        ImageButton btn_sleep = (ImageButton) getView().findViewById(R.id.sleep);
        ImageButton btn_social = (ImageButton) getView().findViewById(R.id.social);
        ImageButton btn_mindful = (ImageButton) getView().findViewById(R.id.mindful);
        ImageButton btn_eating = (ImageButton) getView().findViewById(R.id.eating);
        ImageButton btn_health = (ImageButton) getView().findViewById(R.id.health);

        fetchdata();

        Toast toast = Toast.makeText(getActivity(), "Data is fetched and bubbles are updated",
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 1000);
        toast.show();

        btn_activity.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255,255, 235,59)));
        btn_wellness.setBackgroundTintList(ColorStateList.valueOf(Color.argb(40,3, 169,244)));
        btn_sleep.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255,139, 195,74)));
        btn_social.setBackgroundTintList(ColorStateList.valueOf(Color.argb(40,195, 73,216)));
        btn_mindful.setBackgroundTintList(ColorStateList.valueOf(Color.argb(40,255, 152,0)));
        btn_eating.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255,241, 92,142)));
        btn_health.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255,244, 67,54)));

        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(1000); // duration
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        final ImageButton btn = (ImageButton) getView().findViewById(R.id.activity);
        btn.startAnimation(animation);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.clearAnimation();
                Toast toast = Toast.makeText(getActivity(), "Video with challenge is played",
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 1000);
                toast.show();
            }
        });

    }

    public static HomeFragment newInstance(String text) {

        HomeFragment f = new HomeFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        user_name = text;

        f.setArguments(b);

        return f;
    }
}


