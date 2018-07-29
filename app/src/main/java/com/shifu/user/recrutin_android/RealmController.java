package com.shifu.user.recrutin_android;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.shifu.user.recrutin_android.json.Job;
import com.shifu.user.recrutin_android.json.JobsResponse;
import com.shifu.user.recrutin_android.realm.Jobs;

import java.lang.reflect.Field;
import java.util.List;
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

    public void addJobs(final JobsResponse data, final String search, final Handler h) {
        final String TAG = CLASS_TAG+"addJobs";
        realm.executeTransactionAsync(realm -> {
            realm.where(Jobs.class).findAll().deleteAllFromRealm();
            for (Job obj : data.getJobs()) {
                Jobs item = realm.createObject(Jobs.class, UUID.randomUUID().toString());
                item.setSearch(search);
                item.setTitle(obj.getTitle());
                item.setCompany(obj.getCompany());
                item.setDescription(obj.getAbout());
                item.setSalary(obj.getSalary());
                item.setUrl(obj.getUrl());
                item.setUpdated(obj.getDate());
            }
            h.sendMessage(Message.obtain(h, 1, TAG));
        });
    }

    public void addJobs(final List<Job> data, final String search, final Handler h) {
        final String TAG = CLASS_TAG+"addJobs";
        realm.executeTransactionAsync(realm -> {
            realm.where(Jobs.class).findAll().deleteAllFromRealm();
            for (Job obj : data) {
                Jobs item = realm.createObject(Jobs.class, UUID.randomUUID().toString());
                item.setSearch(search);
                item.setTitle(obj.getTitle());
                item.setCompany(obj.getCompany());
                item.setDescription(obj.getAbout());
                if (obj.getSalary() == null) {
                    item.setSalary("З/П не указана");
                } else {
                    item.setSalary(obj.getSalary());
                }
                item.setUrl(obj.getUrl());
                item.setUpdated(obj.getDate());
            }
            h.sendMessage(Message.obtain(h, 1, TAG));
        });
    }

//    public void addJoobleJobs(final JoobleJsonResponse data, final String search, final Handler h) {
//        final String TAG = CLASS_TAG+"addJoobleJobs";
//        realm.executeTransactionAsync(realm -> {
//
//            for (Job obj : data.getJobs()) {
//                Jobs item = realm.createObject(Jobs.class, UUID.randomUUID().toString());
//                item.setSearch(search);
//                item.setTitle(obj.getTitle());
//                item.setCompany(obj.getCompany());
//                item.setDescription(obj.getSnippet());
//                item.setSalary(obj.getSalary());
//                item.setUrl(obj.getLink());
//                item.setUpdated(obj.getUpdated());
//            }
//            Log.d(TAG, "Finished. DB: "+realm.where(Jobs.class).count());
//            h.sendMessage(Message.obtain(h, 1, TAG));
//        });
//    }

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
            base = realm.where(objClass).findAll().sort(sortField);
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

    public <T extends RealmObject> T getItem(Class<T> objClass, String field, Object value){
        if (objClass == null) return null;
        boolean has = false;
        if (field != null && value != null)
        {
            for (Field f: objClass.getDeclaredFields()) {
                if (f.getName().equals(field)){
                    has = true;
                    break;
                }
            }
        }
        T item;
        if (has){
            if (value instanceof String) {
                item = realm.where(objClass).equalTo(field, (String) value).findFirst();
            } else if (value instanceof Long) {
                item = realm.where(objClass).equalTo(field, (Long) value).findFirst();
            } else if (value instanceof Integer) {
                item = realm.where(objClass).equalTo(field, (Integer) value).findFirst();
            } else if (value instanceof Boolean) {
                item = realm.where(objClass).equalTo(field, (Boolean) value).findFirst();
            } else {
                item = null;
            }
        } else {
            item = realm.where(objClass).findFirst();
        }
        return item;
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

    public void removeItemById(final String id, final Handler h) {
        final String TAG = "RC.removeItemById";
        realm.executeTransactionAsync(realm -> {
            try {
                Jobs item = realm.where(Jobs.class).equalTo((String)Jobs.class.getField("FIELD_ID").get(null), id).findFirst();
                if (item != null) {
                    item.deleteFromRealm();
                    MainActivity.getRA().notifyDataSetChanged();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                h.sendMessage(Message.obtain(h, 0, TAG));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                h.sendMessage(Message.obtain(h, 0, TAG));
            }
        });
    }

}