package com.pkg.android.grossary.navigation;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pkg.android.grossary.R;

/**
 * Created by GAURAV on 30-01-2017.
 */

public class AboutUsFragment extends Fragment {
    private TextView SunilSirViewProfileText;
    private TextView KushalViewProfileText;
    private TextView PankajViewProfileText;
    private TextView GogoViewProfileText;


    public AboutUsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        SunilSirViewProfileText = (TextView)rootView.findViewById(R.id.sunil_view_profile);
        SunilSirViewProfileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFB("1778282324");
            }
        });

        KushalViewProfileText = (TextView)rootView.findViewById(R.id.kushal_view_profile);
        KushalViewProfileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFB("100013345935980");
            }
        });


        PankajViewProfileText = (TextView)rootView.findViewById(R.id.pankaj_view_profile);
        PankajViewProfileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFB("100006030339805");
            }
        });

        GogoViewProfileText = (TextView)rootView.findViewById(R.id.gogo_view_profile);
        GogoViewProfileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFB("100002222080895");
            }
        });


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void openFB(String facebookId) {
        try{
            String facebookScheme = "fb://profile/" + facebookId;
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookScheme));
            startActivity(facebookIntent);
        } catch (ActivityNotFoundException e) {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id="+facebookId));
            startActivity(facebookIntent);
        }
    }
}
