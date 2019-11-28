package ru.systempla.weatherapp.ui.com;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import ru.systempla.weatherapp.R;


public class SMFragment extends Fragment {

    private TextView messageField;
    private TextView emailField;
    private Button sendButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.sm_layout, container, false);

        initViews(layout);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toastText = "<"+emailField.getText().toString()+">"+" : "
                        +messageField.getText().toString()+" "+getString(R.string.is_sent);
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), toastText,
                Toast.LENGTH_LONG).show();
                clearForms();
            }
        });

        return layout;

    }

    private void clearForms() {
        messageField.setText("");
        emailField.setText("");
    }

    private void initViews(View layout) {
        sendButton = layout.findViewById(R.id.send_button);
        messageField = layout.findViewById(R.id.mes_text);
        emailField = layout.findViewById(R.id.email_text);
    }
}