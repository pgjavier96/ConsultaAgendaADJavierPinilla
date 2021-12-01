package org.izv.aff.consultaagendaad;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.UserDictionary;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.izv.aff.consultaagendaad.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private final int CONTACT_PERMISSION = 1;
    private final String TAG = "izvaff";

    private Button bt_search; // = findViewById(R.id.bt_search);
    private EditText etPhone;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "He entrado al onCreate"); //v de verbose
        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "He entrado al onDestroy"); //v de verbose
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            viewSettings();
            return true;
        }

        /*if (id == R.id.home){
            onBackPressed();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "He entrado al onPause"); //v de verbose
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.v(TAG, "He entrado al onRequestPermissionsResult");
        switch (requestCode) {
            case CONTACT_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Me han dado permiso
                    search();
                } else {
                    // Sin permiso
                }
                break;
        }
        //requestCode
        //permissions
        //grantResults
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "He entrado al onResume"); //v de verbose
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "He entrado al onStart"); //v de verbose
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "He entrado al onStop"); //v de verbose
    }

    private void initialize() {
        bt_search = findViewById(R.id.bt_search);
        etPhone = findViewById(R.id.etPhone);
        tvResult = findViewById(R.id.tvResult);

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchIfPermitted();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void explain() {
        showRationaleDialog(getString(R.string.title),
                getString(R.string.message),
                Manifest.permission.READ_CONTACTS,
                CONTACT_PERMISSION);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, CONTACT_PERMISSION);
    }

    private void search() {
        tvResult.setText("A PELO YA SABES");
        //buscar entre los contactos
        //ContentProvider Proveedor de contenidos
        //ContentResolver Consultor de contenidos
        //url: https://ieszaidinvergeles.org/carpeta/carpeta2/pagina.html?dato=1
        //uri: protocolo://dirección/ruta/recurso
        /*Cursor cursor = getContentResolver().query(
                UserDictionary.Words.CONTENT_URI, //Devuelve información en forma de tabla SELECT
                new String[] {"projection"}, //Filtra segun lo que queremos ver en la tabla *
                "selectionClause", //Condición, como usando WHERE en BDD
                new String[] {"selectionArgs"}, //Con estos parámetros
                "sortOrder"); //Ordenamos por algo como SORT BY

        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String proyeccion[] = new String[] {ContactsContract.Contacts.DISPLAY_NAME};
        String seleccion = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = ? and " +
                ContactsContract.Contacts.HAS_PHONE_NUMBER + "= ?";
        String argumentos[] = new String[]{"1","1"};
        //seleccion = null;
        //argumentos = null;
        String orden = ContactsContract.Contacts.DISPLAY_NAME + " collate localized asc";
        Cursor cursor = getContentResolver().query(uri, proyeccion, seleccion, argumentos, orden);
        String[] columnas = cursor.getColumnNames();
        for(String s: columnas){
            Log.v(TAG, s);
        }
        String displayName;
        int columna = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        while(cursor.moveToNext()){
            displayName = cursor.getString(columna);
            Log.v(TAG, displayName);
        }
        */
        Uri uri2 = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String proyeccion2[] = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
        String seleccion2 = null;//ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
        String argumentos2[] = null;//new String[]{id+""};
        String orden2 = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        Cursor cursor2 = getContentResolver().query(uri2, proyeccion2, seleccion2, argumentos2, orden2);
        String[] columnas2 = cursor2.getColumnNames();
        for (String s : columnas2) {
            Log.v(TAG, s);
        }
        int columnaNombre = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int columnaNumero = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        String nombre, numero;
        while(cursor2.moveToNext()){
            nombre = cursor2.getString(columnaNombre);
            numero = cursor2.getString(columnaNumero);
            //Log.v(TAG,nombre+": "+numero);
            for (String s: columnas2){
                int pos = cursor2.getColumnIndex(s);
                String valor = cursor2.getString(pos);
                numero = numero.replaceAll("[^0-9]", "");
                if(numero.equals(etPhone.getText().toString())){
                    tvResult.setText(nombre);
                    Log.v(TAG, pos + " " + s + " " + valor);
                }


            }
        }
        String[] projeccion = new String[] { ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE };
        String selectionClause = ContactsContract.Data.MIMETYPE + "='" +
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND "
                + ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";
        String sortOrder = ContactsContract.Data.DISPLAY_NAME + " ASC";
        Cursor c = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                projeccion,
                selectionClause,
                null,
                sortOrder);
    }

    private void searchIfPermitted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // La version de android es posterior a la 6 incluida
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_CONTACTS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // Ya tengo el permiso
                search();
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                explain(); //2ª Ejecución
            } else {
                requestPermission(); //1ª Ejecución
            }
        } else {
            // La version de android es anterior a la 6
            // Ya tengo el permiso
            search();
        }
    }

    private void showRationaleDialog(String title, String message, String permission, int requestCode) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Si pulso negativo no quiero hacer nada
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Si pulso positivo quiero pedir los permisos
                        requestPermission();
                    }
                });

        builder.create().show();

    }

    public void viewSettings() {
        //Intent - intención
        //Intenciones explícitas o implícitas
        //Explícita: definir que quiero ir desde el contexto actual a un contexto
        //Que se crea con la clase SettingsActivity
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}