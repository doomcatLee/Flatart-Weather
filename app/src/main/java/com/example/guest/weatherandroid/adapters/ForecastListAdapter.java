package com.example.guest.weatherandroid.adapters;

import android.content.Context;
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

        public ForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindWeather(Weather weather) {
            mDay.setText((weather.getTime()));
            mTemp.setText(service.formatTemp(weather.getTemp()));
        }
    }
}