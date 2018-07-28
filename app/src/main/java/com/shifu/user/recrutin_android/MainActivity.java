package com.shifu.user.recrutin_android;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.shifu.user.recrutin_android.realm.Jobs;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationHost{

    private static RealmRVAdapter ra;
    private static RealmController rc;

    static RealmController getRC() {
        return rc;
    }
    static RealmRVAdapter getRA() {
        return ra;
    }
    static void setRA(RealmRVAdapter ra) {
        MainActivity.ra = ra;
    }

    final Handler h = new Handler(new Handler.Callback() {
        String TAG = "H.Main";

        @Override
        public boolean handleMessage(android.os.Message msg) {
            Log.d(TAG, "Event type:" + Integer.toString(msg.what));
            Log.d(TAG, "Event:" + msg.obj);
            if (msg.what == 1) {
                switch ((String) msg.obj) {
                    case "RC.clear":
                        ra =  new RealmRVAdapter(rc.getBase(Jobs.class, null, null, "title"), h);
                        break;
                    case "RA":
                        getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.container, new ListFragment(), "START")
                                .commit();
                        break;
                }
            }
            if (msg.what == 2) {
                WebFragment fragment = new WebFragment();
                Bundle args = new Bundle();
                args.putString("url", (String) msg.obj);
                fragment.setArguments(args);
                navigateTo(fragment, true);
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MainActivity activity = this;
        rc = new RealmController(getApplicationContext());
        rc.clear(h);
    }

    /**
     * Navigate to the given fragment.
     *
     * @param fragment       Fragment to navigate to.
     * @param addToBackstack Whether or not the current fragment should be added to the backstack.
     */

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment f : fragments){
            if(f != null && f instanceof WebFragment) {
                ((WebFragment) f).onBackPressed();
                break;
            } else {
                super.onBackPressed();
            }
        }
    }


}
