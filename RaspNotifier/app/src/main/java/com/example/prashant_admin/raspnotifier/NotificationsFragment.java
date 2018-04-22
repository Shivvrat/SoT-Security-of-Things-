package com.example.prashant_admin.raspnotifier;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsFragment extends Fragment {

    @BindView(R.id.notifications_root)
    LinearLayout root;
    Context myContext;

    public NotificationsFragment() {
    }

    public static NotificationsFragment newInstance() {
        NotificationsFragment fragment = new NotificationsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, rootView);
        setNotificationData(getActivity().getIntent().getExtras());
        return rootView;
    }

    private void setNotificationData(Bundle extras) {
        if (extras == null)
            return;
        StringBuilder text = new StringBuilder("");
        text.append("\n");
        text.append("\n");
        if (extras.containsKey("title")) {
            text.append("Title: ");
            text.append(extras.get("title"));
        }
        text.append("\n");
        if (extras.containsKey("message")) {
            text.append("Message: ");
            text.append(extras.get("message"));
        }
        myContext = this.getActivity().getApplicationContext();
        LinearLayout myLayout = (LinearLayout) root.findViewById(R.id.notifications_root);

        TextView Notification = new TextView(getActivity());
        Notification.setText(text);
        myLayout.addView(Notification);
    }
}
