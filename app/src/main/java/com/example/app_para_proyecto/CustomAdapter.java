package com.example.app_para_proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private String[] options;
    private String[] infoTexts;
    private boolean[] isInfoVisible;

    public CustomAdapter(Context context, String[] options, String[] infoTexts) {
        this.context = context;
        this.options = options;
        this.infoTexts = infoTexts;
        this.isInfoVisible = new boolean[options.length]; // Controla la visibilidad de la información
    }

    @Override
    public int getCount() {
        return options.length;
    }

    @Override
    public Object getItem(int position) {
        return options[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_with_info, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView infoTextView = convertView.findViewById(R.id.infoTextView);

        // Configura los textos
        titleTextView.setText(options[position]);
        infoTextView.setText(infoTexts[position]);

        // Controla la visibilidad del texto de información
        if (isInfoVisible[position]) {
            infoTextView.setVisibility(View.VISIBLE);
        } else {
            infoTextView.setVisibility(View.GONE);
        }

        // Maneja clics para alternar la visibilidad del texto de información
        convertView.setOnClickListener(v -> {
            isInfoVisible[position] = !isInfoVisible[position];
            notifyDataSetChanged(); // Actualiza el ListView
        });

        return convertView;
    }
}

