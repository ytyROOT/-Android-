package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
private int[] btnum_ids={R.id.Num_0,R.id.Num_1,R.id.Num_2,R.id.Num_3,R.id.Num_4,R.id.Num_5,R.id.Num_6,R.id.Num_7,R.id.Num_8,R.id.Num_9,R.id.Num_d};
private Button[] btnums=new Button[btnum_ids.length];

private int[] btop_ids={R.id.top0,R.id.top5,R.id.top4,R.id.top3,R.id.top2,R.id.top1,R.id.top6,R.id.top7,R.id.top8,R.id.top9,R.id.topD};
private Button[] btops=new Button[btop_ids.length];

private  int status=0;
private String first="";
private String second="";
private String topnum="";
private String preop="";


long downTime =0;//BUTTON_C被按下的时间
long thisTime=0;//while 每次循环的时间


private String[] nums={"0","1","2","3","4","5","6","7","8","9","."};

private final Runnable mTimeRefresher =new Runnable() {
    @Override
    public void run() {
        Date d=new Date(System.currentTimeMillis());
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        TextView texTime=(TextView)findViewById(R.id.TimeText);
        texTime.setText(sdf.format(d));
        handler.postDelayed(this,1000);
    }
};

private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler.postDelayed(mTimeRefresher,1000);

        for (int i=0;i<btnums.length;i++)
        {
            btnums[i]=(Button)findViewById(btnum_ids[i]);
            btnums[i].setOnClickListener(new ButtonOnclick(btnums[i].getText().toString(),btnum_ids[i]));
        }

        for (int i=0;i<btops.length;i++)
        {
            btops[i]=(Button)findViewById(btop_ids[i]);
            btops[i].setOnClickListener(new ButtonOnclick(btops[i].getText().toString(),btop_ids[i]));
        }

    }

class ButtonOnclick implements View.OnClickListener
{
    private String buttontitle="";
    private int id=0;
    ButtonOnclick(String str,int m_id)
    {
        buttontitle=str;
        id=m_id;
    }

    String calc() {
        float result = 0;
        float t=1;
        float s=0;
        double j=0;

        String retstr = "";
        if (preop.equals("+")) {
            result = Float.valueOf(first) + Float.valueOf(second);
            retstr = Float.toString(result);
        }
        if (preop.equals("-")) {
            result = Float.valueOf(first) - Float.valueOf(second);
            retstr = Float.toString(result);

        }
        if (preop.equals("X")) {
            result = Float.valueOf(first)*Float.valueOf(second);
            retstr = Float.toString(result);
        }
        if (preop.equals("/")) {
            result = Float.valueOf(first)/Float.valueOf(second);
            retstr = Float.toString(result);
        }
        if (preop.equals("%")) {
            result = Float.valueOf(first)%Float.valueOf(second);
            retstr = Float.toString(result);
        }

        if (preop.equals("Xⁿ")) {
            t=Float.valueOf(first);
            s=Float.valueOf(second);
            if (s>=0){
                     result=1;

            if (s>0){
                while (s > 0) {
                    result*= t;
                    s--;
                }
            }

                retstr = Float.toString(result);
            }
            else {
                retstr="waring";
            }

        }
        if (preop.equals("∫X")) {
            s = Float.valueOf(first);
            j=(double)s;
            j=Math.sqrt(j);
            result=(float)j;

            retstr = Float.toString(result);
        }

        if (preop.equals("X!")) {
            s=Float.valueOf(first);
            result=1;
            while (t<=s){
                result*=t;
                t++;
            }
            retstr = Float.toString(result);
        }

        return  retstr;


    }



    void  showResult(String str){
        TextView tv=(TextView)findViewById(R.id.NumText);
        tv.setText(str);
    }

    void  showAdsult(String str){
        TextView ev=(TextView)findViewById(R.id.EditText);
        ev.setText(str);
    }



    @Override
    public void  onClick(View v)
    {
        for (int i=0;i<nums.length;i++) {
        if (buttontitle.equals(nums[i])){
            if (status==0)
            {
                if(buttontitle.equals(".")){
                if (first.lastIndexOf(".")==-1&&first!=""){
                    first+=buttontitle;
                    topnum+=buttontitle;
                }
                else if (first.lastIndexOf(".")==-1&&first==""){
                    first+=0;
                    topnum+=0;
                     first+=buttontitle;
                    topnum+=buttontitle;

                }
            }
                 else if (buttontitle.equals("0")){
                    if (first!="") {
                        first += buttontitle;
                        topnum+=buttontitle;

                    }
                }
                else{
                first+=buttontitle;
                topnum+=buttontitle;
                }

                //Log.i(TAG,first);
//                if (first!=""){
                showResult(first);
                showAdsult(topnum);
//                }
            }
            if (status==1)
            {
                if(buttontitle.equals(".")){
                    if (second.lastIndexOf(".")==-1&&second!=""){
                        second+=buttontitle;
                        topnum+=buttontitle;

                    }
                    else if (second.lastIndexOf(".")==-1&&second==""){
                        second+=0;
                        topnum+=0;
                        second+=buttontitle;
                        topnum+=buttontitle;

                    }
                }
                else if (buttontitle.equals("0")){
                    if (first!="") {
                        first += buttontitle;
                        topnum+=buttontitle;

                    }
                }
                else {
                    topnum += buttontitle;
                    second += buttontitle;
                }
                    showResult(second);
                    showAdsult(topnum);

            }
        }
        }
        if (buttontitle.equals("C")||id==R.id.top0){
            this.showAdsult("");
            this.showResult("0");
            status=0;
            topnum="";
            first="";
            second="";

        }

        if (buttontitle.equals("←")||id==R.id.topD){

                if (status==0){
                    if (first!=""){
                    first=first.substring(0,first.length()-1);
                    topnum=topnum.substring(0,topnum.length()-1);
                    showResult(first);
                    showAdsult(topnum);
                    }
                }
                else {
                    if (second!=""){
                    second=second.substring(0,second.length()-1);
                    topnum=topnum.substring(0,topnum.length()-1);
                    showResult(second);
                    showAdsult(topnum);
                    }
                }
//            }

        }
//        if (first==""){
//        if(buttontitle.equals("-")){
//
//                first=first+buttontitle;
//                topnum+=buttontitle;
//                showResult(first);
//                showAdsult(topnum);
//            }
//
//        }
        if (first!="") {
        if (buttontitle.equals("+") ||buttontitle.equals("-") || buttontitle.equals("X") || buttontitle.equals("/")||buttontitle.equals("%")) {
            //status = 1; //当前状态值发生变化
            if (second != "") {  //实现复杂运算
                first = calc();
                this.showResult(first);

            }
            second = "";
            preop = buttontitle;

            topnum += buttontitle;
            this.showAdsult(topnum);

        }

            if (buttontitle.equals("+")) {
                status = 1;
                preop = buttontitle;
            }
            if (buttontitle.equals("-")) {
//                if (status==1){
//                if (second==""){
//                    second+=buttontitle;
//                    topnum+=buttontitle;
//                    showAdsult(topnum);
//                }
//            }
                    status = 1;
                    preop = buttontitle;
            }
            if (buttontitle.equals("X")) {
                status = 1;
                preop = buttontitle;
            }
            if (buttontitle.equals("/")) {
                status = 1;
                preop = buttontitle;
            }
            if (buttontitle.equals("%")) {
                status = 1;
                preop = buttontitle;
            }
            if (buttontitle.equals("Xⁿ")) {
                status = 1;
                preop = buttontitle;
                topnum += "^";
                this.showAdsult(topnum);

            }

            if (buttontitle.equals("∫X")) {
                status = 1;
                preop = buttontitle;
                topnum += "∫";
                this.showAdsult(topnum);
                this.showResult(calc());

            }

            if (buttontitle.equals("X!")) {
                status = 1;
                preop = buttontitle;
                topnum += "!";
                this.showAdsult(topnum);
                this.showResult(calc());

            }
            if (buttontitle.equals("=")) {
                this.showResult(calc());
                topnum += buttontitle;
                this.showAdsult(topnum);
                status = 0;
                first = "";
                second = "";
                topnum = "";
            }
        }
    }
}

}
