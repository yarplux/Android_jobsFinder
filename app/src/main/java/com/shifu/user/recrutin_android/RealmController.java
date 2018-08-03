package com.shifu.user.recrutin_android;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.shifu.user.recrutin_android.json.Job;
import com.shifu.user.recrutin_android.json.JobsResponse;
import com.shifu.user.recrutin_android.realm.Jobs;

import java.lang.reflect.Field;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmController {

    private final static String CLASS_TAG = "RC.";
    private Realm realm;
    RealmController(Context context) {
        Realm.init(context);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        realm = Realm.getInstance(config);
    }



    /**
     * CREATE DATA FUNCTIONS _______________________________________________________________________
    */

    public void addJobs(final JobsResponse data, final Handler h) {
        final String TAG = CLASS_TAG+"addJobs";
        realm.executeTransactionAsync(realm -> {
            for (Job obj : data.getJobs()) {
                Jobs item = realm.createObject(Jobs.class, UUID.randomUUID().toString());
                item.setTitle(obj.getTitle());
                item.setCompany(obj.getCompany());
                item.setDescription(obj.getAbout());
                item.setSalary(obj.getSalary());
                item.setUrl(obj.getUrl());
                item.setUpdated(obj.getDate().substring(0,11));
            }
            h.sendMessage(Message.obtain(h, 1, TAG));
        });
    }

    /**
     * READ DATA FUNCTIONS _________________________________________________________________________
     */

    public <T extends RealmObject> Long getSize (Class<T> objClass) {
        if (objClass == null) return null;
        return realm.where(objClass).count();
    }

    public <T extends RealmObject> RealmResults<T> getBase(Class<T> objClass, String sortField){
        RealmResults<T> base;

        boolean sort = exist(objClass, sortField);
        if (sort){
            base = realm.where(objClass).sort(sortField).findAll();
        } else {
            base = realm.where(objClass).findAll();
        }
        return base;
    }

    private <T extends RealmObject> boolean exist(Class<T> objClass, String checkField) {
        boolean check = false;
        if (checkField != null)
        {
            for (Field f: objClass.getDeclaredFields()) {
                if (f.getName().equals(checkField)){
                    check = true;
                    break;
                }
            }
        }
        return check;
    }



    /**
     * DELETE DATA FUNCTIONS _______________________________________________________________________
     */

    public void clear (final Handler h) {
        realm.executeTransactionAsync(realm -> {
            realm.where(Jobs.class).findAll().deleteAllFromRealm();
            h.sendMessage(Message.obtain(h, 1, "RC.clear"));
        });
    }

}