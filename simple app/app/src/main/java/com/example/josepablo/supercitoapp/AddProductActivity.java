package com.example.josepablo.supercitoapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josepablo.supercitoapp.database.producto.ProductoCRUD;
import com.example.josepablo.supercitoapp.object.Producto;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddProductActivity extends AppCompatActivity {

    private Producto producto;
    private boolean isUpdate = false;
    private int prodId = -1;
    private EditText etNombre;
    private EditText etPrecio;
    private String imgUrl = "null";
    private ProductoCRUD productoCrud;

    private Button bGaleria;
    private Button bCamara;
    private ImageView ivFoto;

    //Constantes para usar Galería y Cámara
    //TODO: 2.- Definir constantes de permisos
    private static final int SELECT_PHOTO = 100;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    //TODO: 3.- Obtenemos permisos de storage
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //TODO: 9.- Obtenemos permisos de cámara
    private static final int REQUEST_CAMERA = 200;
    private static final String FILE_PROVIDER = "com.example.josepablo.supercitoapp.fileprovider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent1 = getIntent();
        isUpdate = intent1.getBooleanExtra(MainActivity.UPDATE_PROD, false);


        etNombre = (EditText) findViewById(R.id.etNombre);
        etPrecio = (EditText) findViewById(R.id.etPrecio);

        bGaleria = (Button) findViewById(R.id.bGaleria);
        bCamara = (Button) findViewById(R.id.bFoto);
        ivFoto = (ImageView) findViewById(R.id.ivPreview);

        productoCrud = new ProductoCRUD(this);

        if(isUpdate){
            prodId = intent1.getIntExtra(MainActivity.PROD_ID, -1);
            if(prodId != -1){
                producto = productoCrud.getProducto(prodId);
                if(producto != null) {

                    etNombre.setText(producto.getNombre(), TextView.BufferType.EDITABLE);
                    etPrecio.setText(String.format("%.2f",producto.getPrecio()), TextView.BufferType.EDITABLE);
                    imgUrl = producto.getImgUrl();
                    if(!imgUrl.equals("null")){
                        Picasso.with(this).load(imgUrl).error(R.drawable.ic_error_outline_black_24dp).into(ivFoto);
                    }

                } else {
                    isUpdate = false;
                }
            } else {
                isUpdate = false;
            }
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Insert new element
                if(!isUpdate){
                    if(getProductoParameters()){
                        productoCrud.newProducto(producto);

                        Toast.makeText(AddProductActivity.this, "Producto Agregado Correctamente", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AddProductActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } else {
                    if(getProductoParameters()){
                        productoCrud.updateProducto(producto);

                        Toast.makeText(AddProductActivity.this, "Producto Actualizado Correctamente", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AddProductActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }

            }
        });

        bGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarPermisosStorage();
            }
        });

        bCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarPermisosCamara();
            }
        });
    }

    private boolean getProductoParameters(){
        if(etPrecio.getText().toString().isEmpty() || etNombre.getText().toString().isEmpty()){
            Toast.makeText(this, "Algún campo está vacio.", Toast.LENGTH_SHORT).show();
            return false;
        }

        String nombre = etNombre.getText().toString();
        float precio = Float.parseFloat(etPrecio.getText().toString());

        if(!isUpdate) {
            producto = new Producto(nombre, precio, imgUrl);
        } else {
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setImgUrl(imgUrl);
        }

        return true;
    }

    //TODO: 5.- Validamos si el usuario ya tiene permisos, se le negó o no ha pedido
    public void validarPermisosStorage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Debemos mostrar un mensaje?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Mostramos una explicación de que no aceptó dar permiso para acceder a la librería
            } else {
                // Pedimos permiso
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE);
            }
        }else{
            iniciarIntentSeleccionarFotos();
        }
    }

    //TODO: 6.- Llama al intent para seleccionar fotos
    private void iniciarIntentSeleccionarFotos() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    //TODO: 7.- Revisamos si se le dio permiso al usuario o no
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                // Si la petición se cancela se regresa un arreglo vacío
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso concedido
                    iniciarIntentSeleccionarFotos();
                } else {
                    // Permiso negado
                }
                return;
            // TODO 14.- Validamos el permiso para el acceso a la cámara
            case REQUEST_CAMERA:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Se concedió acceso
                    iniciarIntentTomarFoto();
                } else {
                    // permiso negado
                }
                return;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            //TODO: 8.- Si obtuvimos una imagen entonces la procesamos
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri imagenSeleccionada = imageReturnedIntent.getData();
                    //try{
                        imgUrl = imagenSeleccionada.toString();
                        Picasso.with(this).load(imgUrl).error(R.drawable.ic_error_outline_black_24dp).into(ivFoto);
                        //InputStream imagenStream = getContentResolver().openInputStream(imagenSeleccionada);
                        //Bitmap imagen = BitmapFactory.decodeStream(imagenStream);
                        //ivFoto.setImageBitmap(imagen);
                        //imgUrl = imagenSeleccionada.toString();

                    //}catch (FileNotFoundException fnte){
                        //Toast.makeText(this, fnte.getMessage().toString(), Toast.LENGTH_LONG).show();
                    //}
                    return;
                }
                // TODO 15.- Si obtuvimos la imagen y la guardamos la mostramos
            case REQUEST_CAMERA:
                if(resultCode == RESULT_OK){
                    Picasso.with(this).load(imgUrl).error(R.drawable.ic_error_outline_black_24dp).into(ivFoto);
                }
                return;
        }
    }




    //TODO: 11.- Validamos permisos de cámara
    public void validarPermisosCamara() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA);
            }
        }else{
            iniciarIntentTomarFoto();
        }
    }

    //TODO: 12.- Iniciamos intent para tomar foto
    private  void iniciarIntentTomarFoto(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Validamos que hay una actividad de cámara
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Creamos un nuevo objeto para almacenar la foto
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error creando archivo
            }
            // Si salió bien
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, FILE_PROVIDER, photoFile);
                // Mandamos llamar el intent
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
            }
        }
    }

    //TODO: 13.- Función para crear archivo y URL
    private File createImageFile() throws IOException {
        // Creamos el archivo
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreImagen = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                nombreImagen,  /* prefijp */
                ".jpg",         /* sufijo */
                storageDir      /* directorio */
        );

        // Obtenemos la URL
        String urlName = "file://" + image.getAbsolutePath();
        imgUrl = urlName;
        return image;
    }

}
