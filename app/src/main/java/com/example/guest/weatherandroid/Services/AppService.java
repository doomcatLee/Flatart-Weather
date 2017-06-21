package com.example.guest.weatherandroid.Services;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.guest.weatherandroid.Model.Weather;
import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.adapters.ForecastListAdapter;

import java.text.DecimalFormat;

/**
 * Created by Gun Lee
 * App Service has multiple helper functions that are used through the application.
 * These methods are reusable as they are abstract methods
 *
 * METHODS:
 * 1. applyShakeAnimation()
 * 2. crossFade()
 * 3. isEmailValid()
 * 4. isPasswordValid()
 * 5. isZipcodeValid()
 * 6. formatTemp()
 * 7. setImageDynamic()
 * 8. setFonts()
 * 9. isFormEmpty()
 */

public class AppService extends WeatherService{

    public AppService(){

    }
    /**
     * Apply Shake Animation from daimajia/androidviewanimation
     * Args: View
     * Returns: none
     */

    public void applyShakeAnimation(View view){
        YoYo.with(Techniques.Shake)
                .duration(700)
                .repeat(1)
                .playOn(view);
    }
    /**
     * Apply cross fade between two views in the same activity
     * Args: View before and View after
     * Returns: none
     */

    private void crossfade(final View before, View after) {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        after.setAlpha(0f);
        after.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        after.animate()
                .alpha(1f)
                .setDuration(10)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        before.animate()
                .alpha(0f)
                .setDuration(10)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        before.setVisibility(View.GONE);
                    }
                });
    }

    /**
     * Validates user input form
     * Args: (EditText) password, (EditText) passwordConfirm, (EditText) email and Activity
     * Returns: (Boolean) true or false
     */

    public Boolean isEmailValid(String e, Activity activity){
        if (e.contains("@")){
            return true;
        }else{
            Toast.makeText(activity, "Email must contain @", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * Check form password validation, both must be more than length of 5 and both must match
     * Args: View before and View after
     * Returns: Boolean
     */

    public Boolean isPasswordValid(String p1,String p2, Activity activity){
        if (p1.length() > 5 && p2.length() > 5){
            if (p1.equals(p2)){
                return true;
            }
            Toast.makeText(activity, "Both passwords must match", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(activity, "Password must be longer than " + p1.length() + " digits", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Check if zipcode has a length of 5
     * Args: String zipcode
     * Returns: Boolean
     */

    public Boolean isZipcodeValid(String z, Activity activity){
        while(z.length() != 5){
            Toast.makeText(activity, "Zipcode must be at a length of 5", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Formats temperature from Kelvin to Fahrenheit with concatenated string
     * Args: (double) Kelvin
     * Returns: (String) Fahrenheit
     */
    public String formatTemp(double num){
        DecimalFormat df = new DecimalFormat("#");
        String str = df.format(num) + " °F";
        return str;
    }

    /**
     * Based on the iconID retrieved, change view to its background
     * Args: ImageView and (String) iconID
     * Returns: nothing
     */
    public void setImageDynamic(ImageView view, String iconID){

        if (iconID.equals("01d") || iconID.equals("01n")){
            view.setImageResource(R.drawable.sunny);
        }else if(iconID.equals("10d") || iconID.equals("10n") || iconID.equals("09n") || iconID.equals("09d")){
            view.setImageResource(R.drawable.rain);
        }else if(iconID.equals("02d") || iconID.equals("02n")){
            view.setImageResource(R.drawable.few_clouds);
        }else if(iconID.equals("04d") || iconID.equals("04n") || iconID.equals("03n") || iconID.equals("03d")){
            view.setImageResource(R.drawable.more_clouds);
        }else if(iconID.equals("13d") || iconID.equals("13n")){
            view.setImageResource(R.drawable.snow);
        }else if(iconID.equals("11d") || iconID.equals("11n")){
            view.setImageResource(R.drawable.heavy_rain);
        }else if(iconID.equals("50d") || iconID.equals("50n")){
            view.setImageResource(R.drawable.mist);
        }else{
            System.out.println("uhhhhhhhhhhhhhhhh");
        }
    }

    /**
     * Set Typeface for all TextView in TextView ArrayList
     * Args: (TextView[]) ArrayList of TextViews
     * Returns: nothing
     */
    public void setFonts(TextView[] views, Typeface font){
        for (TextView i: views){
            i.setTypeface(font);
        }
    }

    public Boolean isFormEmpty(EditText et){
        if (et.getText().toString().equals("")){
            return false;
        }else{
            return true;
        }
    }
}
