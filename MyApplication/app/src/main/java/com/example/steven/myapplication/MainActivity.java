package com.example.steven.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import de.timroes.axmlrpc.XMLRPCCallback;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;

import static de.timroes.axmlrpc.XMLRPCClient.FLAGS_DEFAULT_TYPE_STRING;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_PERMISSON_APP = 1;


    public MainActivity() throws MalformedURLException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Button bt = (Button)findViewById(R.id.button123);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProcessClick();

        }}
            );

        //openAblumWithPermissionsCheck();
    }


    private class JsonRPCTask extends AsyncTask<Object,Void,Object> {

        @Override
        protected Object doInBackground(Object[] objects) {
            String method=(String)objects[0];

            try {
                XMLRPCClient client = new XMLRPCClient(new URL("http://192.168.0.198:8000/"));

                Object obj=client.call(method,objects[1]);
                return obj;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XMLRPCException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if(result!=null){
                Log.d("APP",(String)result);
                System.out.println((String)result);
                showMesage((String)result);
            }
        }
    }
    private class JsonRPCTask2 extends AsyncTask<Object,Void,Object> {

        @Override
        protected Object doInBackground(Object[] objects) {
            String method=(String)objects[0];
            Object[] temp=new Object[objects.length-1];
            for( int i =0;i<objects.length-1;i++){
                System.out.printf(String.format("%d",i));
                temp[i]=objects[i+1];
            }
            try {
                XMLRPCClient client = new XMLRPCClient(new URL("http://192.168.0.198:8000/"));

                Object obj=client.call(method,temp);
                return obj;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XMLRPCException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if(result!=null){
                Log.d("APP",(String)result);
                System.out.println((String)result);
                showMesage((String)result);
            }
        }
    }

    private void ProcessClick(){
        openAblumWithPermissionsCheck();

        try {

            //long id =this.client.callAsync(this.rpclistener, "sum",5,4);


            XMLRPCClient client = new XMLRPCClient(new URL("http://192.168.0.198:8000/"),FLAGS_DEFAULT_TYPE_STRING);

            XMLRPCCallback listener = new XMLRPCCallback() {
                Runnable runNotOk = new Runnable() {
                    public void run() {
                        System.out.println( "runNotOk");
                    }
                };

                Runnable runOk = new Runnable() {
                    public void run() {
                        System.out.println( "runOk");
                    }
                };

                public void onResponse(long id, Object result) {
                    System.out.println( "onResponse");
                    System.out.println(result);

                    try {
                        int value=Integer.valueOf ((String)result);
                        String message=String.format("%d",value);
                        System.out.println(message);

                        // showMesage(message);  // this will not work
                    } catch(Exception e){
                        System.out.println(e.toString());
                    }
                }

                public void onError(long id, XMLRPCException error) {
                    System.out.println(  "onError");
                }

                public void onServerError(long id, XMLRPCServerException error) {
                    System.out.println(  "onServerError");
                }
            };

            client.callAsync(listener, "sum",5,4);
            showMesage( "callAsync");


            JsonRPCTask2 task=new JsonRPCTask2();
            task.execute( new Object[]{"sum",5,4});
            JsonRPCTask taskecho=new JsonRPCTask();
            taskecho.execute( new Object[]{"echo","ZZZZZZZZZZZZZ"});
            //showMesage(String.format("%d",id));
            //Object obj=client.call("text");
            //String str=(String)client.call("echo","hello world");
            //showMesage(str);
            //showMesage("str");
            //} catch(XMLRPCServerException ex) {
            //   showMesage(ex.toString());
            // The server throw an error.
            //} catch(XMLRPCException ex) {
            // An error occured in the client.
            //   showMesage(ex.toString());
        } catch(Exception ex) {
            // Any other exception
            showMesage(ex.toString());
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {




        if (requestCode == REQUEST_PERMISSON_APP
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showMesage("OK");
            return;
        }//end if


    }

    private void openAblumWithPermissionsCheck() {
        showMesage("Check permission");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                    REQUEST_PERMISSON_APP);
            showMesage("Request permission");

        }else{
            showMesage("Already have!");
        }

    }
    private void showMesage(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






}
