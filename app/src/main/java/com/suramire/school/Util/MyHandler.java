package com.suramire.school.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.suramire.school.R;
import com.suramire.school.haomabaishitong.Child;

import java.util.ArrayList;

/**
 * Created by Suramire on 2017/6/7.
 * 自定义handelr类 用于实现联系人下载
 */

public class MyHandler extends Handler {
    public final static int MYHANDLER_DOWNLOAD = 0x11;
    public final static int MYHANDLER_UPLOAD = 0x12;
    public final static int MYHANDLER_SINGLEDOWNLOAD = 0x13;
    public final static int MYHANDLER_SINGLEDELETE= 0x14;


    private MyDataBase myDataBase;
    private MyContentProvider myContentProvider;
    private ArrayList<Child> children;
    private Context context;

    public MyHandler(MyDataBase myDataBase,MyContentProvider myContentProvider,ArrayList<Child> children,Context context) {
        this.myDataBase = myDataBase;
        this.myContentProvider = myContentProvider;
        this.children = children;
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            //选中下载联系人
            case MYHANDLER_DOWNLOAD:{
                final AlertDialog.Builder builder  = new AlertDialog.Builder(context);
                builder.setTitle("请选中要下载的联系人").setIcon(R.mipmap.ic_launcher);
                if(children.size() <= 0){
                    Looper.prepare();
                    Toast.makeText(context, "请先添加联系人", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else{
                    final String[] names = new String[children.size()];
                    final boolean[] checks = new boolean[children.size()];
                    for(int i=0;i<children.size();i++){
                        Child child = children.get(i);
                        names[i] =child.getName()+"  "+child.getNumber();
                        checks[i] = false;
                    }
                    final ArrayList<Child> mchildren = new ArrayList<Child>();
                    builder.setMultiChoiceItems(names, checks,new DialogInterface.OnMultiChoiceClickListener() {


                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    mchildren.clear();
                                    for (int i = 0; i < checks.length; i++) {
                                        if (checks[i]) {
                                            mchildren.add(children.get(i));
                                        }
                                    }
                                }
                            }
                    );
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try{
                                int mcount = 0;
                                for (Child x: mchildren) {
                                    myContentProvider.write(x.getName(), x.getNumber());
                                    mcount++;
                                }
                                Log.e("tag", "下载:"+mcount );
                                Toast.makeText(context, "成功下载" + mcount + "条联系人信息", Toast.LENGTH_SHORT).show();
                            }catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(context, "下载联系人信息失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
                    builder.setCancelable(false).show();
                }
            }break;
            //上传联系人
            case MYHANDLER_UPLOAD:
                final ArrayList<Child> mchildren = myContentProvider.read();
                int mcount = 0;
                if (mchildren.size() != 0) {

                    final AlertDialog.Builder builder  = new AlertDialog.Builder(context);
                    builder.setTitle("请选中要上传的联系人").setIcon(R.mipmap.ic_launcher);
                        final String[] names = new String[mchildren.size()];
                        final boolean[] checks = new boolean[mchildren.size()];
                        final ArrayList<Child> newChildren = new ArrayList<>();
                        //将系统通讯录联系人存入list显示出来
                        for(int i=0;i<mchildren.size();i++){
                            Child child = mchildren.get(i);
                            child.setPingyin(PinyinUtils.getPingYin(child.getName()));
                            child.setP(PinyinUtils.getFirstSpell(child.getName()));
                            newChildren.add(child);
                            names[i] =child.getName()+"  "+child.getNumber();
                            checks[i] = false;
                        }
                        //存放选中的联系人
                        final ArrayList<Child> m2children = new ArrayList<Child>();
                        builder.setMultiChoiceItems(names, checks,new DialogInterface.OnMultiChoiceClickListener() {


                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        m2children.clear();
                                        for (int i = 0; i < checks.length; i++) {
                                            if (checks[i]) {
                                                m2children.add(newChildren.get(i));
                                            }
                                        }
                                    }
                                }
                        );
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try{
                                    long insert = 0;//代表插入数据库的数量
                                    int m2count = 0;//代表上传数量
                                    for (Child child : m2children) {
                                        if (myDataBase.selectByNameNumber(child.getName(), child.getNumber()).getCount() != 0) {
                                            //当数据库已存在该联系人时(忽略关键字)不添加
                                        } else {
                                            child.setPingyin(PinyinUtils.getPingYin(child.getName()));
                                            child.setP(PinyinUtils.getFirstSpell(child.getName()));
                                            insert = myDataBase.insert(child);
                                            m2count++;
                                        }
                                        if (insert != 0 && m2count != 0) {
                                            Toast.makeText(context, "上传" + m2count + "位联系人信息成功", Toast.LENGTH_SHORT).show();
                                            //发送广播,刷新联系人列表
                                            Intent  intent = new Intent();
                                            intent.setAction("com.suramire.school.refresh");
                                            context.sendBroadcast(intent);
                                        } else {
                                            Toast.makeText(context, "上传联系人信息失败,是否存在重复联系人", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(context, "未读取到本机的联系人信息,请重试", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        });
                        builder.setCancelable(false).show();

                }
                break;
            //单个下载联系人
            case MYHANDLER_SINGLEDOWNLOAD:
                Child child = (Child) msg.obj;
                try {
                    myContentProvider.write(child.getName(), child.getNumber());
                    Toast.makeText(context, "成功下载1条联系人信息", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "下载联系人信息失败 错误:"+e.getCause(), Toast.LENGTH_SHORT).show();
                }break;
            //单个删除联系人
            case  MYHANDLER_SINGLEDELETE:
                final Child child2 = (Child) msg.obj;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示").setMessage("是否删除该联系人信息").setIcon(R.drawable.tip);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String nameString = child2.getName();
                            String phoneString = child2.getNumber();
                            int delete = myDataBase.delete("name=? and number=?", new String[]{nameString, phoneString});
                            if (delete != 0) {
                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                //发送广播,刷新联系人列表
                                Intent  intent = new Intent();
                                intent.setAction("com.suramire.school.refresh");
                                context.sendBroadcast(intent);
                            } else {
                                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){e.printStackTrace();Toast.makeText(context, "删除失败 错误:"+e.getMessage(), Toast.LENGTH_SHORT).show();}

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                builder.setCancelable(false);
                builder.create().show();
                break;
        }
    }
}
