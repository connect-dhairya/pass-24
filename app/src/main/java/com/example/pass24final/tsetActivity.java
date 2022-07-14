package com.example.pass24final;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class tsetActivity extends AppCompatActivity {
    private static final int CREATE_FILE = 1;
    String name = "Dhairya" , source = "Ahmendabad" , destination="Nadiad" ,price ="300";
    String entryDate = "MAY 6 2022" , expriyDate = "JUN 6 2022";
    Button download;
    PdfDocument document;
    int pdfWidth = 720;
    int pdfHeight = 1080;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tset);
        download = findViewById(R.id.download_pdf);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    createPDF(name, source, destination, price, entryDate, expriyDate);
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void createPDF(String name, String source, String destination, String price, String entryDate, String expriyDate) throws FileNotFoundException {
        document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pdfWidth,pdfHeight,1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paintText = new Paint();
        paintText.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD,Typeface.NORMAL));
        paintText.setTextSize(25);
        paintText.setColor(ContextCompat.getColor(this,R.color.black));
        paintText.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("( PDF )",396,50,paintText);


        paintText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        paintText.setColor(ContextCompat.getColor(this,R.color.grey));
        paintText.setTextSize(17);
        paintText.setTextAlign(Paint.Align.CENTER);
//
//        canvas.drawText("User",50,100,paintText);
//        canvas.drawText(name,100,100,paintText);
//        canvas.drawText("Source",50,150,paintText);
//        canvas.drawText(source,100,150,paintText);
//        canvas.drawText("Destination",50,200,paintText);
//        canvas.drawText(destination,100,200,paintText);
//        document.finishPage(page);
        createFile();
    }

    private void createFile() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE,"pass.pdf");
        startActivityForResult(intent,CREATE_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData){
        super.onActivityResult(requestCode, resultCode, resultData);
        if(requestCode == CREATE_FILE && resultCode == Activity.RESULT_OK){
            Uri uri = null;
            if(resultData !=null){
                uri = resultData.getData();
                if(document != null){
                    ParcelFileDescriptor pfd = null;
                    try{
                        pfd = getContentResolver().openFileDescriptor(uri,"w");
                        FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
                        document.writeTo(fileOutputStream);
                        document.close();
                        Toast.makeText(this, "PDF saved", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
