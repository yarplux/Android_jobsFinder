package com.shifu.user.recrutin_android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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


    private static ItemRVAdapter adapter;

    public static ItemRVAdapter getAdapter() {
        return adapter;
    }

    public static void setAdapter(ItemRVAdapter adapter) {
        MainActivity.adapter = adapter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rc = new RealmController(getApplicationContext());
        ra =  new RealmRVAdapter(rc.getBase(Jobs.class, "title"));


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new ListFragment())
                    .commit();
        }
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
