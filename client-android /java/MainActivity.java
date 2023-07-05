package gamil.abbas51813.com.arabicsigntranslator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int START_CAMERA_APP = 0;
    private static final int PICK_IMAGE = 100;
    private ImageView photoCamptureImageVie;
    static SimpleClient myconnector;
    public static String predicted="error";
    private Button predictButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        photoCamptureImageVie = (ImageView) findViewById(R.id.photoImageView);
        predictButton = (Button) findViewById(R.id.predictButton);
        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap img = null;
                myconnector = new SimpleClient(img,2);
                myconnector.execute();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 5 seconds
                        setImage(predicted);
                    }
                }, 5000);




            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callCameraApp = new Intent();
                callCameraApp.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(callCameraApp,START_CAMERA_APP);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, help.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            openGallary();
        }
         else if (id == R.id.nav_share) {
       Toast.makeText(this,"not available",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if(requestCode==START_CAMERA_APP && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap photoCapturedBitmap = (Bitmap) extras.get("data");
            photoCamptureImageVie.setImageBitmap(photoCapturedBitmap);

            makeconnection(photoCapturedBitmap);
          //  photoCamptureImageVie.setImageResource(R.drawable.alef);
            //Toast.makeText(this,"picture taken",Toast.LENGTH_SHORT).show();
        }
        else if(requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            Uri imageUri = data.getData();
            Bitmap bitmap=null;
            try {
                 bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            }catch (IOException e) {
                e.printStackTrace();
            }
            photoCamptureImageVie.setImageBitmap(bitmap);
            makeconnection(bitmap);
        }
    }
    public void makeconnection(Bitmap img) {
        myconnector = new SimpleClient(img,1);
        myconnector.execute();
        }

    public void setImage(String tag){
        Toast.makeText(this, tag,Toast.LENGTH_SHORT).show();

            if(tag.contains("Alef")) {
                photoCamptureImageVie.setImageResource(R.drawable.alef);
            }
            else if (tag.contains("Ba")) {
                photoCamptureImageVie.setImageResource(R.drawable.ba);
            }
            else if (tag.contains("Ta")) {
                photoCamptureImageVie.setImageResource(R.drawable.ta);
            }
            else if (tag.contains("Kha")) {
                photoCamptureImageVie.setImageResource(R.drawable.kha);
            }
            else if (tag.contains("Dal")) {
                photoCamptureImageVie.setImageResource(R.drawable.dal);
            }
            else if (tag.contains("Dhad")) {
                photoCamptureImageVie.setImageResource(R.drawable.dhad);
            }
            else if (tag.contains("Lam")) {
                photoCamptureImageVie.setImageResource(R.drawable.lam);
            }
            else if (tag.contains("La")) {
                photoCamptureImageVie.setImageResource(R.drawable.la);
            }
            else if (tag.contains("Ghayn")) {
                photoCamptureImageVie.setImageResource(R.drawable.ghayn);
            }
            else if (tag.contains("Thah")) {
                photoCamptureImageVie.setImageResource(R.drawable.thah);
            }


            else {
                photoCamptureImageVie.setImageResource(R.drawable.error);
            }

        }

        private void openGallary(){
        Intent gallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallary,PICK_IMAGE);
        }

    }



