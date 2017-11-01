package com.shreyanshvit.social;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.shreyanshvit.social.utils.DataAttributes;
import com.shreyanshvit.social.utils.Storage;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Calendar;

import io.victoralbertos.breadcumbs_view.BreadcrumbsView;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class HomeActivity extends AppCompatActivity {

    private Integer cacheCurrentStep;
    private BreadcrumbsView breadcrumbsView;
    // variables to store extracted xml data
    String uid,name,gender,yearOfBirth,careOf,villageTehsil,postOffice,district,state,postCode,product_id,product_name,product_cat,product_mrp,email,mobile,address,age;

    // UI Elements
    TextView tv_sd_uid,tv_sd_name,tv_sd_gender,tv_sd_yob,tv_sd_co,tv_sd_vtc,tv_sd_po,tv_sd_dist,
            tv_sd_state,tv_sd_pc,tv_cancel_action,tv_welcome_message,prd_id,prd_name,prd_cat,prd_mrp,dialog_uid,dialog_name,dialog_mobile,dialog_address,dialog_email,dialog_product,dialog_total;
    LinearLayout ll_action_button_wrapper;
    CardView ll_scanned_data_wrapper,ll_data_wrapper, ll_product_wrapper,ll_detail_wrapper,ll_final_wrapper;
    Button scan,scan_back,next,detail_btn,confrim,discard;

    EditText mEmail, mMobile;

    Dialog dialog;

    // Storage
    Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide the default action bar

        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Aadhaar Scan");
        overrideFonts(HomeActivity.this, getWindow().getDecorView().getRootView());

        // init the UI Elements
        tv_sd_uid = (TextView)findViewById(R.id.tv_sd_uid);
        tv_sd_name = (TextView)findViewById(R.id.tv_sd_name);
        tv_sd_gender = (TextView)findViewById(R.id.tv_sd_gender);
        tv_sd_yob = (TextView)findViewById(R.id.tv_sd_yob);
        tv_sd_co = (TextView)findViewById(R.id.tv_sd_co);
        tv_sd_vtc = (TextView)findViewById(R.id.tv_sd_vtc);
        tv_sd_po = (TextView)findViewById(R.id.tv_sd_po);
        tv_sd_dist = (TextView)findViewById(R.id.tv_sd_dist);
        tv_sd_state = (TextView)findViewById(R.id.tv_sd_state);
        tv_sd_pc = (TextView)findViewById(R.id.tv_sd_pc);
        prd_id = (TextView) findViewById(R.id.tv_sd_prd_id);
        prd_name = (TextView) findViewById(R.id.tv_sd_prd_name);
        prd_cat = (TextView) findViewById(R.id.tv_sd_prd_cat);
        prd_mrp = (TextView) findViewById(R.id.tv_sd_prd_mrp);

        dialog_name = (TextView) findViewById(R.id.dialog_name);
        dialog_mobile = (TextView) findViewById(R.id.dialog_mobile);
        dialog_email = (TextView) findViewById(R.id.dialog_email);
        dialog_address = (TextView) findViewById(R.id.dialog_address);
        dialog_product = (TextView) findViewById(R.id.dialog_prd);
        dialog_total = (TextView) findViewById(R.id.dialog_total);
        dialog_uid = (TextView) findViewById(R.id.dialog_uid);
        confrim = (Button) findViewById(R.id.confrim);
        discard = (Button) findViewById(R.id.discard);


        mEmail = (EditText) findViewById(R.id.email);
        mMobile = (EditText) findViewById(R.id.mobileNo);

        scan = (Button) findViewById(R.id.scan);
        scan_back = (Button) findViewById(R.id.scan_back);
        next = (Button) findViewById(R.id.product_btn);
        detail_btn = (Button) findViewById(R.id.detail_btn);

        ll_scanned_data_wrapper = (CardView)findViewById(R.id.ll_scanned_data_wrapper);
        ll_data_wrapper = (CardView) findViewById(R.id.ll_data_wrapper);
        ll_product_wrapper = (CardView) findViewById(R.id.ll_product_wrapper);
        ll_detail_wrapper = (CardView) findViewById(R.id.ll_detail_wrapper);
        ll_final_wrapper = (CardView) findViewById(R.id.ll_final_wrapper);
        ll_action_button_wrapper = (LinearLayout)findViewById(R.id.ll_action_button_wrapper);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom);
        dialog.setCanceledOnTouchOutside(false);
        TextView text = (TextView) dialog.findViewById(R.id.custom_text);
        text.setText("Please wait...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        if (getLastCustomNonConfigurationInstance() == null) {
            cacheCurrentStep = 0;
        } else {
            cacheCurrentStep = (Integer) getLastCustomNonConfigurationInstance();
        }

        breadcrumbsView = (BreadcrumbsView) findViewById(R.id.breadcrumbs);
        breadcrumbsView.setCurrentStep(cacheCurrentStep);
        breadcrumbsView.setCurrentStep(0);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanNow(v);
            }
        });

        scan_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_data_wrapper.setVisibility(View.GONE);
                ll_scanned_data_wrapper.setVisibility(View.GONE);
                ll_action_button_wrapper.setVisibility(View.GONE);
                scanNow(v);
            }
        });

    }

    /**
     * onclick handler for scan new card
     * @param view
     */
    public void scanNow( View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES
        );
        integrator.setPrompt("Scan a Aadharcard QR Code");
        integrator.setResultDisplayDuration(500);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }

    /**
     * function handle scan result
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            // process received data
            if(scanContent != null && !scanContent.isEmpty()){
                if(scanContent.length() > 10){
                    processScannedData(scanContent);
                }
                else {
                    dialog.show();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://hotelgirnar.ml/social/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    final RequestInterface1 request = retrofit.create(RequestInterface1.class);
                    Call<Result> call = request.getResult(scanContent);
                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            response.body();
                            product_mrp = response.body().getproduct_mrp();
                            product_id = response.body().getproduct_id();
                            product_name = response.body().getproduct_name();
                            product_cat = response.body().getproduct_cat();
                            prd_id.setText(product_id);
                            prd_name.setText(product_name);
                            prd_cat.setText(product_cat);
                            prd_mrp.setText(product_mrp);
                            breadcrumbsView.nextStep();
                            dialog.dismiss();
                            ll_product_wrapper.setVisibility(View.VISIBLE);
                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ll_product_wrapper.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(),product_name, Toast.LENGTH_SHORT);
                                    breadcrumbsView.nextStep();
                                    ll_detail_wrapper.setVisibility(View.VISIBLE);
                                    detail_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            email = mEmail.getText().toString();
                                            mobile = mMobile.getText().toString();
                                            if(!TextUtils.isEmpty(mobile)){
                                                ll_detail_wrapper.setVisibility(View.GONE);
                                                breadcrumbsView.nextStep();
                                                dialog_uid.setText(uid);
                                                dialog_name.setText(name);
                                                dialog_mobile.setText(mobile);
                                                if(TextUtils.isEmpty(email)){
                                                    dialog_email.setText("none");
                                                }
                                                else {
                                                    dialog_email.setText(email);
                                                }
                                                dialog_address.setText(address);
                                                dialog_product.setText(product_name);
                                                dialog_total.setText(product_mrp+"/-");
                                                ll_final_wrapper.setVisibility(View.VISIBLE);
                                                confrim.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        dialog.show();
                                                        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://hotelgirnar.ml/social/").build();
                                                        Billinvoice serviceApi = restAdapter.create(Billinvoice.class);
                                                        serviceApi.senBill(
                                                                email,
                                                                mobile,
                                                                uid,
                                                                name,
                                                                villageTehsil,
                                                                district,
                                                                state,
                                                                address,
                                                                gender,
                                                                calAge(yearOfBirth),
                                                                product_name,
                                                                product_mrp,
                                                                product_cat,
                                                                new retrofit.Callback<retrofit.client.Response>() {
                                                                    @Override
                                                                    public void success(retrofit.client.Response response, retrofit.client.Response response2) {
                                                                        BufferedReader reader = null;
                                                                        String result = "";

                                                                        try {
                                                                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                                                                            result = reader.readLine();
                                                                        } catch (Exception ex) {
                                                                            ex.printStackTrace();
                                                                        }
                                                                        dialog.dismiss();
                                                                        Toast.makeText(HomeActivity.this, result, Toast.LENGTH_LONG).show();
                                                                    }

                                                                    @Override
                                                                    public void failure(RetrofitError error) {
                                                                        dialog.dismiss();
                                                                        Toast.makeText(HomeActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                                                                    }
                                                                }

                                                        );
                                                    }
                                                });
                                                discard.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                    }
                                                });
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(),"Empty fields not allowed!",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {

                        }
                    });
                }

            }else{
                Toast toast = Toast.makeText(getApplicationContext(),"Scan Cancelled", Toast.LENGTH_SHORT);
                toast.show();
            }

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private String calAge(String i) {
        int dob = Integer.parseInt(i);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year-dob);
    }

    /**
     * process xml string received from aadhaar card QR code
     * @param scanData
     */
    protected void processScannedData(String scanData){
        Log.d("Rajdeol",scanData);
        XmlPullParserFactory pullParserFactory;

        try {
            // init the parserfactory
            pullParserFactory = XmlPullParserFactory.newInstance();
            // get the parser
            XmlPullParser parser = pullParserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(scanData));

            // parse the XML
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("Rajdeol","Start document");
                } else if(eventType == XmlPullParser.START_TAG && DataAttributes.AADHAAR_DATA_TAG.equals(parser.getName())) {
                    // extract data from tag
                    //uid
                    uid = parser.getAttributeValue(null,DataAttributes.AADHAR_UID_ATTR);
                    //name
                    name = parser.getAttributeValue(null,DataAttributes.AADHAR_NAME_ATTR);
                    //gender
                    gender = parser.getAttributeValue(null,DataAttributes.AADHAR_GENDER_ATTR);
                    // year of birth
                    yearOfBirth = parser.getAttributeValue(null,DataAttributes.AADHAR_YOB_ATTR);
                    // care of
                    careOf = parser.getAttributeValue(null,DataAttributes.AADHAR_CO_ATTR);
                    // village Tehsil
                    villageTehsil = parser.getAttributeValue(null,DataAttributes.AADHAR_VTC_ATTR);
                    // Post Office
                    postOffice = parser.getAttributeValue(null,DataAttributes.AADHAR_PO_ATTR);
                    // district
                    district = parser.getAttributeValue(null,DataAttributes.AADHAR_DIST_ATTR);
                    // state
                    state = parser.getAttributeValue(null,DataAttributes.AADHAR_STATE_ATTR);
                    // Post Code
                    postCode = parser.getAttributeValue(null,DataAttributes.AADHAR_PC_ATTR);

                } else if(eventType == XmlPullParser.END_TAG) {
                    Log.d("Rajdeol","End tag "+parser.getName());

                } else if(eventType == XmlPullParser.TEXT) {
                    Log.d("Rajdeol","Text "+parser.getText());

                }
                // update eventType
                eventType = parser.next();
            }

            // display the data on screen
            displayScannedData();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }// EO function

    /**
     * show scanned information
     */
    public void displayScannedData(){
        ll_data_wrapper.setVisibility(View.GONE);
        ll_scanned_data_wrapper.setVisibility(View.VISIBLE);
        ll_action_button_wrapper.setVisibility(View.VISIBLE);

        // clear old values if any
        tv_sd_uid.setText("");
        tv_sd_name.setText("");
        tv_sd_gender.setText("");
        tv_sd_yob.setText("");
        tv_sd_co.setText("");
        tv_sd_vtc.setText("");
        tv_sd_po.setText("");
        tv_sd_dist.setText("");
        tv_sd_state.setText("");
        tv_sd_pc.setText("");

        // update UI Elements
        tv_sd_uid.setText(uid);
        tv_sd_name.setText(name);
        tv_sd_gender.setText(gender);
        tv_sd_yob.setText(yearOfBirth);
        tv_sd_co.setText(careOf);
        tv_sd_vtc.setText(villageTehsil);
        tv_sd_po.setText(postOffice);
        tv_sd_dist.setText(district);
        tv_sd_state.setText(state);
        tv_sd_pc.setText(postCode);
        address = villageTehsil+", "+district+", "+state;
    }
    public static void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "ClearSans-Regular.ttff"));
            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "ClearSans-Regular.ttf"));
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "ClearSans-Regular.ttf"));
            }
        } catch (Exception e) {
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(HomeActivity.this, MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }


}// EO class

