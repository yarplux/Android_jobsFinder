package com.shifu.user.recrutin_android;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.shifu.user.recrutin_android.json.JsonFake;
import com.shifu.user.recrutin_android.json.JsonJob;
import com.shifu.user.recrutin_android.realm.Jobs;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
        //realm = Realm.getDefaultInstance();
    }



    /**
     * CREATE DATA FUNCTIONS _______________________________________________________________________
    */

    public void addJobs(final List<JsonJob> data, final Handler h) {
        final String TAG = CLASS_TAG+"addMsgs";
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NotNull Realm realm) {
                realm.where(Jobs.class).findAll().deleteAllFromRealm();
                for (JsonJob obj : data) {
                    Jobs item = realm.createObject(Jobs.class, UUID.randomUUID().toString());
                    item.setTitle(obj.getTitle());
                    item.setCompany(obj.getCompany());
                    item.setDescription(obj.getDescription());
                    item.setSalary(obj.getSalary());
                    item.setUrl(obj.getUrl());
                }

                //MainActivity.setRA(new RealmRVAdapter(realm.where(Jobs.class).findAll().sort("title")));
                //MainActivity.getRA().notifyDataSetChanged();
                h.sendMessage(Message.obtain(h, 1, TAG));
            }
        });
    }

    public void setData(final List<JsonFake> data, final Handler h) {
        final String TAG = CLASS_TAG+"setData";
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NotNull Realm realm) {
                realm.where(Jobs.class).findAll().deleteAllFromRealm();
                for (JsonFake obj : data) {
                    Jobs item = realm.createObject(Jobs.class, UUID.randomUUID().toString());
                    item.setTitle(obj.title);
                    item.setCompany(obj.company);
                    item.setDescription(obj.description);
                    item.setSalary(Long.parseLong(obj.salary));
                    item.setUrl(obj.url);
                }

                h.sendMessage(Message.obtain(h, 1, TAG));
            }
        });
    }

//    public void addJobs(final Map<String, JsonMsg> data, final Handler h) {
//        final String TAG = CLASS_TAG+"addMsgs";
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(@NotNull Realm realm) {
//                realm.where(Messages.class).findAll().deleteAllFromRealm();
//                for (String key : data.keySet()) {
//                    JsonMsg obj = data.get(key);
//                    Messages item = realm.createObject(Messages.class, UUID.randomUUID().toString());
//                    item.setText(obj.getText());
//                    Log.d(TAG, "Created text: "+obj.getText());
//                    item.setDate(obj.getDate());
//                    item.setUid(obj.getUid());
//                    item.setFirebase_id(key);
//                }
//
//                RealmResults<Messages> msgs = realm.where(Messages.class).findAll().sort("date");
//                ActivityMain.setRA(new RealmRVAdapter(msgs));
//                ActivityMain.getRA().notifyDataSetChanged();
//                h.sendMessage(Message.obtain(h, 1, TAG));
//            }
//        });
//    }

//    public void addJob(final String text, final Long date, final Handler h) {
//        final String TAG = CLASS_TAG+"addMsg";
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(@NotNull Realm realm) {
//                MessagesAuthor user = realm.where(MessagesAuthor.class).findFirst();
//                Messages item = realm.createObject(Messages.class, UUID.randomUUID().toString());
//                item.setText(text);
//                item.setDate(date);
//                item.setUid(user.getUid());
//                item.setFirebase_id(null);
//                ActivityMain.getRA().notifyDataSetChanged();
//
//                Bundle obj = new Bundle();
//                obj.putString("text", text);
//                obj.putLong("date", date);
//                obj.putString("uuid", item.getID());
//                obj.putString("uid", user.getUid());
//                obj.putString("idToken", user.getIdToken());
//                obj.putString("refreshToken", user.getRefreshToken());
//
//                RestController.pushMsg(obj, h);
//                h.sendMessage(Message.obtain(h, 1, TAG));
//            }
//        });
//    }

    /**
     * READ DATA FUNCTIONS _________________________________________________________________________
     */

    public <T extends RealmObject> Long getSize (Class<T> objClass) {
        if (objClass == null) return null;
        return realm.where(objClass).count();
    }

    public List<JsonFake> getData(String sortField, Handler h){
        RealmResults<Jobs> base;
        final String TAG = CLASS_TAG+"getData";
        boolean sort = false;
        if (sortField != null)
        {
            for (Field f: Jobs.class.getDeclaredFields()) {
                if (f.getName().equals(sortField)){
                    sort = true;
                    break;
                }
            }
        }

        if (sort) {
            base = realm.where(Jobs.class).findAll().sort(sortField);
        } else {
            base = realm.where(Jobs.class).findAll();
        }

        List<JsonFake> list=new ArrayList <>();
        for (Jobs obj : base) {
            list.add(new JsonFake(obj.getTitle(), obj.getDescription(), Long.toString(obj.getSalary()), obj.getCompany(), obj.getUrl()));
        }
        h.sendMessage(Message.obtain(h, 1, TAG));
        return list;
    }

    public <T extends RealmObject> RealmResults<T> getBase(Class<T> objClass, String sortField){
        RealmResults<T> base;
        final String TAG = "RC.getBase";
        boolean sort = false;
        if (sortField != null)
        {
            for (Field f: objClass.getDeclaredFields()) {
                if (f.getName().equals(sortField)){
                    sort = true;
                    break;
                }
            }
        }

        if (sort) {
            base = realm.where(objClass).findAll().sort(sortField);
        } else {
            base = realm.where(objClass).findAll();
        }
        return base;
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
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NotNull Realm realm) {
                realm.where(Jobs.class).findAll().deleteAllFromRealm();
                h.sendMessage(Message.obtain(h, 1, "RC.clear"));

            }
        });
    }

    public void removeItemById(final String id, final Handler h) {
        final String TAG = "RC.removeItemById";
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NotNull Realm realm) {
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
            }
        });
    }

}