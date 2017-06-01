package com.example.guest.weatherandroid.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guest.weatherandroid.Model.Weather;
import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.Services.AppService;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.ForecastViewHolder> {
    AppService service = new AppService();

    private ArrayList<Weather> mWeatherList = new ArrayList<>();
    private Context mContext;

    public ForecastListAdapter(Context context, ArrayList<Weather> weather) {
        mContext = context;
        mWeatherList = weather;
    }

    @Override
    public ForecastListAdapter.ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list_item, parent, false);
        ForecastViewHolder viewHolder = new ForecastViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ForecastListAdapter.ForecastViewHolder holder, int position) {

        holder.bindWeather(mWeatherList.get(position+1));
        String iconID = mWeatherList.get(position).getIconID();

        if (iconID.equals("10d") || iconID.equals("10n") || iconID.equals("09n") || iconID.equals("09d")){
            Drawable d = holder.mCardView.getResources().getDrawable(R.drawable.rain);
            holder.mCardView.setBackground(d);
        }else if (iconID.equals("01d") || iconID.equals("01n")){
            Drawable d = holder.mCardView.getResources().getDrawable(R.drawable.sunny);
            holder.mCardView.setBackground(d);
        }else if (iconID.equals("02d") || iconID.equals("02n")){
            Drawable d = holder.mCardView.getResources().getDrawable(R.drawable.few_clouds);
            holder.mCardView.setBackground(d);
        }else if (iconID.equals("04d") || iconID.equals("04n") || iconID.equals("03n") || iconID.equals("03d")){
            Drawable d = holder.mCardView.getResources().getDrawable(R.drawable.more_clouds);
            holder.mCardView.setBackground(d);
        }else if (iconID.equals("13d") || iconID.equals("13n")){
            Drawable d = holder.mCardView.getResources().getDrawable(R.drawable.snow);
            holder.mCardView.setBackground(d);
        }else if (iconID.equals("11d") || iconID.equals("11n")){
            Drawable d = holder.mCardView.getResources().getDrawable(R.drawable.heavy_rain);
            holder.mCardView.setBackground(d);
        }else if (iconID.equals("50d") || iconID.equals("50n")){
            Drawable d = holder.mCardView.getResources().getDrawable(R.drawable.mist);
            holder.mCardView.setBackground(d);
        }else{

        }

    }

    @Override
    public int getItemCount() {
        return (mWeatherList.size() - 1);
    }


    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tempTextView)
        TextView mTemp;
        @Bind(R.id.dayTextView) TextView mDay;

        private Context mContext;
        private CardView mCardView;


        public ForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            mCardView = (CardView) itemView.findViewById(R.id.card_view);

        }

        public void bindWeather(Weather weather) {
            mDay.setText((weather.getTime()));
            mTemp.setText(service.formatTemp(weather.getTemp()));
        }
    }
}