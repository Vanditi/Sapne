package ngo.sapne.intents.sapne;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentJoinUs extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

        EditText etAddress, etName, etPhoneNumber, etEmail, etAadhar, etOffice;
        Button btnSubmit;
        Spinner spnJoin;
        RadioGroup rgSex;
        EditText tt;
        TextView terms;
        ProgressDialog progressDialog;
        GoogleApiClient mLocationClient;
        Location mLastLocation;

// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentJoinUs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentJoinUs.
     */

    // TODO: Rename and change types and number of parameters
    public static FragmentJoinUs newInstance(String param1, String param2) {
        FragmentJoinUs fragment = new FragmentJoinUs();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_join_us, container, false);

        //View v parameter is taken because no return statement can be written.

        Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(getActivity());
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        mLocationClient = builder.build();

//        terms=(TextView)findViewById(R.id.terms);
        etName= (EditText)v.findViewById(R.id.etName);
        etPhoneNumber= (EditText) v.findViewById(R.id.etPhoneNumber);
        etEmail= (EditText) v.findViewById(R.id.etEmail);
        etAddress= (EditText) v.findViewById(R.id.etAddress);
        etAadhar= (EditText) v.findViewById(R.id.etAadhar);
        etOffice= (EditText) v.findViewById(R.id.etOffice);
        btnSubmit= (Button) v.findViewById(R.id.btnSubmit);
        spnJoin= (Spinner) v.findViewById(R.id.spnJoin);
        progressDialog = new ProgressDialog(getActivity());
        rgSex= (RadioGroup) v.findViewById(R.id.rgSex);
        //        terms.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {

//                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.activity_main);

        // inflate the layout of the popup window
//                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                View popupView = inflater.inflate(R.layout.activity_terms, null);

        // create the popup window
//                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                boolean focusable = true; // lets taps outside the popup also dismiss it
//                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
//                popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
//                popupView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        popupWindow.dismiss();
//                        return true;
//                    }
//                });
//            }

//        });

        final ArrayList<String> joinusas=new ArrayList<>();
        joinusas.add("Intern");
        joinusas.add("Volunteer");
        joinusas.add("Donor");
        joinusas.add("Supporter");


        ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,joinusas);

        spnJoin.setAdapter(adapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String name = etName.getText().toString();
                String phone = etPhoneNumber.getText().toString();
                final String email = etEmail.getText().toString();
                String gender = rgSex.getCheckedRadioButtonId() == R.id.rbMale ? "Male" : "Female";
                String sub = joinusas.get(spnJoin.getSelectedItemPosition());
                String aadhar = etAadhar.getText().toString();
                String address = etAddress.getText().toString();
                String office = etOffice.getText().toString();
                if (name.length() == 0) {
                    Toast.makeText(getActivity(), "Name Empty", Toast.LENGTH_LONG).show();
                    etName.requestFocus();
                    return;
                }
                if (phone.length() != 10) {
                    Toast.makeText(getActivity(), "Phone Number Empty", Toast.LENGTH_SHORT).show();
                    etPhoneNumber.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_LONG).show();
                    etEmail.requestFocus();
                    return;
                }
                if (address.length() == 0) {
                    Toast.makeText(getActivity(), "Please Enter Address", Toast.LENGTH_SHORT).show();
                    etAddress.requestFocus();
                    return;
                }

                if (office.length() == 0) {
                    Toast.makeText(getActivity(), "Please Enter Office/Institute", Toast.LENGTH_SHORT).show();
                    etOffice.requestFocus();
                    return;
                }
                if (aadhar.length() == 0) {
                    Toast.makeText(getActivity(), "Aadhar No. Empty", Toast.LENGTH_SHORT).show();
                    etAadhar.requestFocus();
                    return;
                }


                final String msg = "Name:" + name + "\nPhone Number:" + phone + "\nEmail:" + email + "\nGender:" + gender + "\nAddress:" + address + "\nOffice/Institute:" + office + "\nAadhar No.:" + aadhar + "\nJoining As:" + sub;

                progressDialog.setMessage("Registering Please Wait...");
                progressDialog.show();


                new Thread(new Runnable() {

                    public void run() {

                        try {

                            Gmailsender sender = new Gmailsender(

                                    "sapneapp@gmail.com",

                                    "sapne@delhi");


                            // sender.addAttachment(Environment.getExternalStorageDirectory().getPath()+"/image.jpg");

                            sender.sendMail("Joined form details", msg,

                                    email,

                                    "sapneapp@gmail.com");



                            //  Toast.makeText(getApplicationContext(),"Submitted Successfully...",Toast.LENGTH_LONG).show();
                            getActivity().finish();




                        } catch (Exception e) {

                            Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();



                        }

                    }

                }).start();

            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mLocationClient != null) {
            mLocationClient.connect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {
                        android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.INTERNET
                },10);
            }
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
        if(mLastLocation!=null)
        {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();


            Geocoder geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
            try
            {
                List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
                if (addresses!=null)
                {

                    android.location.Address fetchedAddress=addresses.get(0);
                    etAddress.setText(fetchedAddress.getFeatureName()+","+fetchedAddress.getSubLocality()+","+fetchedAddress.getLocality()+"-"+fetchedAddress.getPostalCode()+","+fetchedAddress.getAdminArea()+","+fetchedAddress.getCountryName());

                }
                else
                    etAddress.setText("No Location Found");


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(),"Connection Failed",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getActivity(),"Connection Suspended",Toast.LENGTH_LONG).show();
    }
}
