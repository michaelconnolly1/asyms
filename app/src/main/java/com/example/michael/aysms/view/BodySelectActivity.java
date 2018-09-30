package com.example.michael.aysms.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.michael.aysms.App;
import com.example.michael.aysms.R;
import com.example.michael.aysms.Utils.BodyButton;
import com.example.michael.aysms.Utils.Constants;
import com.example.michael.aysms.model.Body;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Connolly on 30/07/2018.
 *
 *  Activity to handle Body select questionnaire page
 */

public class BodySelectActivity extends AppCompatActivity {
    private LinearLayout extraQuestions, llOverall;
    private RadioGroup sickRG, severityRG, botherRG;
    private ImageView myBody;
    private Button touchButton, touchButton1, touchButton2, touchButton3;
    private int myBodyWidth, myBodyHeight, bitmapWidth, bitmapHeight, myBodyStartX, myBodyStartY, centreX, centreY;
    private boolean painFlag;
    private int severityFlag, botherFlag;
    private Bitmap bitmap;
    private Dialog myDialog;
    private int buttonCount;
    private App myApp;
    private Body myBodyModel;
    private boolean restored, buttonClicked;
    private int buttonNoClicked;

    private List<BodyButton> buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_select);

        myApp = (App)getApplicationContext();
        myBodyModel = new Body(myApp.getDaoSession(), myApp.getQuestionnaireStartDate());
        buttons = new ArrayList<BodyButton>();

        myBody = (ImageView)findViewById(R.id.body);
        bitmap = ((BitmapDrawable)myBody.getDrawable()).getBitmap();

        Log.d("IDs", myBody.getId() + " " + bitmap.getGenerationId());
        restored = false;
        buttonClicked = false;
        buttonCount = 0;
        touchButton = (Button)findViewById(R.id.touchButton);
        touchButton.setVisibility(View.INVISIBLE);
        touchButton1 = (Button)findViewById(R.id.touchButton1);
        touchButton1.setVisibility(View.INVISIBLE);
        touchButton2 = (Button)findViewById(R.id.touchButton2);
        touchButton2.setVisibility(View.INVISIBLE);
        touchButton3 = (Button)findViewById(R.id.touchButton3);
        touchButton3.setVisibility(View.INVISIBLE);
        llOverall = (LinearLayout) findViewById(R.id.linearLayout);

        llOverall.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (myApp.getBodySelect() && (!restored))
                    restoreValues();
                }

        });

        myBody.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int touchX = (int) event.getX();
                    int touchY = (int) event.getY();
                    myBodyStartX = (int)myBody.getX();
                    myBodyStartY = (int)myBody.getY();

                    Drawable imgDrawable = ((ImageView) v).getDrawable();
                    //imgDrawable will not be null if you had set src to ImageView, in case of background drawable it will be null
                    Bitmap bitmap = ((BitmapDrawable) imgDrawable).getBitmap();
                    Matrix inverse = new Matrix();
                    ((ImageView) v).getImageMatrix().invert(inverse);
                    float[] touchPoint = new float[]{event.getX(), event.getY()};
                    inverse.mapPoints(touchPoint);
                    int xCoord = (int) touchPoint[0];
                    int yCoord = (int) touchPoint[1];

                    Log.d("Coordinates: ",  xCoord + " " + yCoord + " " + touchX + " " +  touchY + " " + myBody.getX() + " " + myBody.getY() + " " + bitmap.getWidth() + " " + bitmap.getHeight());

                    try {
                        int pixel = bitmap.getPixel(xCoord, yCoord);
                        if (pixel == -1)
                            placeButton(touchX, touchY);
                        else if ((pixel >= -256) && (pixel <= -200 ))
                            Log.d("Coordinate: ", "OUT");
                        else {
                            placeButton(touchX, touchY);
                            int redValue = Color.red(pixel);
                            int blueValue = Color.blue(pixel);
                            int greenValue = Color.green(pixel);
                            Log.d("Colour: ", Integer.toString(pixel) + " " + Integer.toString(redValue) + " " + Integer.toString(blueValue) + " " + Integer.toString(greenValue));
                        }
                    }
                    catch (IllegalArgumentException e){
                        Log.d("Touch event: ", "Illegal");
                    }
                }
                return false;
            }
        });

    }

    private void addButton(int centreX, int centreY, int painRGValue, int severityRGValue, int botherRGValue) {
        painFlag = getSickRGValue(painRGValue);
        severityFlag = getSeverityRGValue(severityRGValue);
        botherFlag = getBotherRGValue(botherRGValue);

        BodyButton newButton = new BodyButton(centreX, centreY, painFlag, severityFlag, botherFlag,
                painRGValue, severityRGValue, botherRGValue);
        buttons.add(newButton);
    }

    private void restoreValues() {

        Log.d("restoreValues", Integer.toString(myBody.getWidth()) + " " + Integer.toString(myBody.getHeight()) + " " + Float.toString(touchButton.getX()) + " " + Float.toString(touchButton.getY()));
        int painRGValue, severityRGValue, botherRGValue;
        int centreX, centreY;
        myBodyWidth = myApp.getSharedPreferencesInt(Constants.BODYSELECT_BODYWIDTH, 0);
        myBodyHeight = myApp.getSharedPreferencesInt(Constants.BODYSELECT_BODYHEIGHT, 0);
        bitmapWidth = myApp.getSharedPreferencesInt(Constants.BODYSELECT_BITMAPWIDTH, 0);
        bitmapHeight = myApp.getSharedPreferencesInt(Constants.BODYSELECT_BITMAPHEIGHT, 0);
        buttonCount = myApp.getSharedPreferencesInt(Constants.BODYSELECT_NBUTTONS, 0);
        myBodyStartX = (int)myBody.getX();
        myBodyStartY = (int)myBody.getY();

        if (buttonCount > 0) {
            centreX = myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON1X, 0);
            centreY = myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON1Y, 0);
            Log.d("Restore1", Integer.toString(centreX) + " " + Integer.toString(centreY));
            touchButton.setVisibility(View.VISIBLE);
            touchButton.setX(centreX);
            touchButton.setY(centreY);

            addButton(centreX, centreY, myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON1PAINRG, -1),
                    myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON1SEVERITYRG, -1),
                    myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON1BOTHERRG, -1));
        }

        if (buttonCount > 1) {
            centreX = myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON2X, 0);
            centreY = myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON2Y, 0);
            Log.d("Restore2", Integer.toString(centreX) + " " + Integer.toString(centreY));
            touchButton1.setVisibility(View.VISIBLE);
            touchButton1.setX(centreX);
            touchButton1.setY(centreY);

            addButton(centreX, centreY, myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON2PAINRG, -1),
                    myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON2SEVERITYRG, -1),
                    myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON2BOTHERRG, -1));

        }
        if (buttonCount > 2) {
            centreX = myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON3X, 0);
            centreY = myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON3Y, 0);
            Log.d("Restore3", Integer.toString(centreX) + " " + Integer.toString(centreY));
            touchButton2.setVisibility(View.VISIBLE);
            touchButton2.setX(centreX);
            touchButton2.setY(centreY);

            addButton(centreX, centreY, myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON3PAINRG, -1),
                    myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON3SEVERITYRG, -1),
                    myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON3BOTHERRG, -1));

        }
        if (buttonCount > 3) {
            centreX = myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON4X, 0);
            centreY = myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON4Y, 0);
            Log.d("Restore4", Integer.toString(centreX) + " " + Integer.toString(centreY));
            touchButton3.setVisibility(View.VISIBLE);
            touchButton3.setX(centreX);
            touchButton3.setY(centreY);

            addButton(centreX, centreY, myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON4PAINRG, -1),
                    myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON4SEVERITYRG, -1),
                    myApp.getSharedPreferencesInt(Constants.BODYSELECT_BUTTON4BOTHERRG, -1));

        }
        restored = true;
    }

    private void placeButton(float X, float Y) {
        int touchX = (int) X;
        int touchY = (int) Y;

        //placing at center of touch
        int viewWidth = touchButton.getWidth();
        int viewHeight = touchButton.getHeight();
        viewWidth = viewWidth / 2;
        viewHeight = viewHeight / 2;
        centreX = touchX - viewWidth;
        centreY = touchY - viewHeight;

        if (buttonCount == 0) {
            touchButton.setVisibility(View.VISIBLE);
            touchButton.setX(centreX);
            touchButton.setY(centreY);
            Log.d("Place", Integer.toString(centreX) + " " + Integer.toString(centreY) + " " + Integer.toString(touchButton.getWidth()));
            touchButton.invalidate();
        }
        else if (buttonCount == 1){
            touchButton1.setVisibility(View.VISIBLE);
            touchButton1.setX(centreX);
            touchButton1.setY(centreY);
            Log.d("Place1", Integer.toString(centreX) + " " + Integer.toString(centreY) + " " + Integer.toString(touchButton.getWidth()));
            touchButton1.invalidate();
        }
        else if (buttonCount == 2){
            Log.d("Place2", Integer.toString(centreX) + " " + Integer.toString(centreY));
            touchButton2.setVisibility(View.VISIBLE);
            touchButton2.setX(centreX);
            touchButton2.setY(centreY);
        }else {
            Log.d("Place3", Integer.toString(centreX) + " " + Integer.toString(centreY));
            touchButton3.setVisibility(View.VISIBLE);
            touchButton3.setX(centreX);
            touchButton3.setY(centreY);
        }

        Log.d("Touch", Integer.toString(touchX) + " " + Integer.toString(touchY) + " " + Integer.toString(viewWidth) + " " + Integer.toString(viewHeight) );

        myDialog = new Dialog(this, R.style.myDialogTheme);
        myDialog.setContentView(R.layout.activity_pain1);
        extraQuestions = (LinearLayout) myDialog.findViewById(R.id.extraquestions);
        myDialog.setTitle("Add Pain Details");
        myDialog.show();
    }

    public void radioClick(View view) {
        switch (view.getId()) {
            case R.id.sickYes:
                extraQuestions.setVisibility(View.VISIBLE);
                break;
            case R.id.sickNo:
                extraQuestions.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public boolean getSickRGValue(int checkedButtonID) {
        if (checkedButtonID == -1)
            return false;
        else if(checkedButtonID == R.id.sickNo)
            return false;
        else return true;
    }

    public int getSeverityRGValue(int checkedButtonID) {
        if (checkedButtonID == -1)
            return 0;
        else if(checkedButtonID == R.id.severityMild)
            return 0;
        else if(checkedButtonID == R.id.severityModerate)
            return 1;
        else return 2;
    }

    public int getBotherRGValue(int checkedButtonID) {
        if (checkedButtonID == -1)
            return 0;
        else if (checkedButtonID == R.id.botherNotAtAll)
            return 0;
        else if (checkedButtonID == R.id.botherALittle)
            return 1;
        else if (checkedButtonID == R.id.botherQuiteABit)
            return 2;
        else return 3;
    }

    public void buttonClick(View view) {
        int painRGValue = -1, severityRGValue = -1, botherRGValue = -1;
        buttonClicked = true;
        switch (view.getId()) {
            case R.id.touchButton:
                Log.d("buttonClick", "first button");
                buttonNoClicked = 0;
                break;
            case R.id.touchButton1:
                Log.d("buttonClick", "second button");
                buttonNoClicked = 1;
                break;
            case R.id.touchButton2:
                Log.d("buttonClick", "third button");
                buttonNoClicked = 2;
                break;
            case R.id.touchButton3:
                Log.d("buttonClick", "fourth button");
                buttonNoClicked = 3;
                break;
        }
        myDialog = new Dialog(this, R.style.myDialogTheme);
        myDialog.setContentView(R.layout.activity_pain1);

        painRGValue = buttons.get(buttonNoClicked).getPainRG();
        severityRGValue = buttons.get(buttonNoClicked).getSeverityRG();
        botherRGValue = buttons.get(buttonNoClicked).getBotherRG();

        setDialogValues(painRGValue, severityRGValue, botherRGValue);

        myDialog.setTitle("Add Pain Details");
        myDialog.show();
    }

    private void setDialogValues(int painRGValue, int severityRGValue, int botherRGValue){
        sickRG = (RadioGroup)myDialog.findViewById(R.id.sickRG);
        botherRG = (RadioGroup)myDialog.findViewById(R.id.botherRG);
        severityRG = (RadioGroup)myDialog.findViewById(R.id.severityRG);
        extraQuestions = (LinearLayout) myDialog.findViewById(R.id.extraquestions);

        if (painRGValue != -1) {
            sickRG.check(painRGValue);
            if (painRGValue == R.id.sickYes)
                extraQuestions.setVisibility(View.VISIBLE);
            else
                extraQuestions.setVisibility(View.INVISIBLE);
        }
        if (severityRGValue != -1)
            severityRG.check(severityRGValue);
        if (botherRGValue != -1)
            botherRG.check(botherRGValue);
    }

    public void continueClick(View view) {
        myDialog.dismiss();
        sickRG = (RadioGroup)myDialog.findViewById(R.id.sickRG);
        botherRG = (RadioGroup)myDialog.findViewById(R.id.botherRG);
        severityRG = (RadioGroup)myDialog.findViewById(R.id.severityRG);
        painFlag = getSickRGValue(sickRG.getCheckedRadioButtonId());
        severityFlag = getSeverityRGValue(severityRG.getCheckedRadioButtonId());
        botherFlag = getBotherRGValue(botherRG.getCheckedRadioButtonId());

        if (buttonClicked) {
            buttons.get(buttonNoClicked).setPainRG(sickRG.getCheckedRadioButtonId());
            buttons.get(buttonNoClicked).setSeverityRG(severityRG.getCheckedRadioButtonId());
            buttons.get(buttonNoClicked).setBotherRG(botherRG.getCheckedRadioButtonId());
        }
        else {
            BodyButton newButton = new BodyButton(centreX, centreY, painFlag, severityFlag, botherFlag,
                    sickRG.getCheckedRadioButtonId(), severityRG.getCheckedRadioButtonId(), botherRG.getCheckedRadioButtonId());
            buttons.add(newButton);
            buttonCount++;
        }
        buttonClicked = false;
    }

    private void saveBodySelect(int bodyWidth, int bodyHeight, int bitmapWidth, int bitmapHeight, int nButtons,
                                int button1X, int button1Y, int button1PainRG, int button1SeverityRG, int button1BotherRG,
                                int button2X, int button2Y, int button2PainRG, int button2SeverityRG, int button2BotherRG,
                                int button3X, int button3Y, int button3PainRG, int button3SeverityRG, int button3BotherRG,
                                int button4X, int button4Y, int button4PainRG, int button4SeverityRG, int button4BotherRG) {
        myApp.setSharedPreferencesBoolean(Constants.BODYSELECT, true);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BODYWIDTH, bodyWidth);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BODYHEIGHT, bodyHeight);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BITMAPWIDTH, bitmapWidth);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BITMAPHEIGHT, bitmapHeight);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_NBUTTONS, nButtons);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON1X, button1X);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON1Y, button1Y);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON1PAINRG, button1PainRG);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON1SEVERITYRG, button1SeverityRG);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON1BOTHERRG, button1BotherRG);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON2X, button2X);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON2Y, button2Y);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON2PAINRG, button2PainRG);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON2SEVERITYRG, button2SeverityRG);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON2BOTHERRG, button2BotherRG);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON3X, button3X);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON3Y, button3Y);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON3PAINRG, button3PainRG);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON3SEVERITYRG, button3SeverityRG);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON3BOTHERRG, button3BotherRG);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON4X, button4X);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON4Y, button4Y);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON4PAINRG, button4PainRG);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON4SEVERITYRG, button4SeverityRG);
        myApp.setSharedPreferencesInt(Constants.BODYSELECT_BUTTON4BOTHERRG, button4BotherRG);
        Log.d("SaveBodySELECT", Integer.toString(nButtons) + " " + Integer.toString(button1X) + " " + Integer.toString(button1Y) + " " + Integer.toString(button1PainRG) + " " + Integer.toString(button1SeverityRG) + " " + Integer.toString(button1BotherRG));
        Log.d("SaveBodySELECT", Integer.toString(nButtons) + " " + Integer.toString(button2X) + " " + Integer.toString(button2Y) + " " + Integer.toString(button2PainRG) + " " + Integer.toString(button2SeverityRG) + " " + Integer.toString(button2BotherRG));
        Log.d("SaveBodySELECT", Integer.toString(nButtons) + " " + Integer.toString(button3X) + " " + Integer.toString(button3Y) + " " + Integer.toString(button3PainRG) + " " + Integer.toString(button3SeverityRG) + " " + Integer.toString(button3BotherRG));
        Log.d("SaveBodySELECT", Integer.toString(nButtons) + " " + Integer.toString(button4X) + " " + Integer.toString(button4Y) + " " + Integer.toString(button4PainRG) + " " + Integer.toString(button4SeverityRG) + " " + Integer.toString(button4BotherRG));
    }

    private void saveModel() {
        myBodyWidth = myBody.getWidth();
        myBodyHeight = myBody.getHeight();
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        Log.d("Size: ", "Start: " + Integer.toString(myBodyStartX) + " " + Integer.toString(myBodyStartY) + " Body: " + Integer.toString(myBodyWidth) + " " + Integer.toString(myBodyHeight) );

        for (int index = buttonCount; index < 4; index++) {
            BodyButton newButton = new BodyButton(0, 0, false, 0, 0, -1, -1, -1);
            buttons.add(newButton);
        }

        Log.d("Count: ", Integer.toString(buttonCount) + " " + Integer.toString(buttons.size()));

        myBodyModel.insertBodyRecord(myBodyWidth, myBodyHeight, myBodyStartX, myBodyStartY, buttonCount,
                buttons.get(0).getX(), buttons.get(0).getY(), buttons.get(0).getPain(), buttons.get(0).getSeverity(), buttons.get(0).getBother(),
                buttons.get(1).getX(), buttons.get(1).getY(), buttons.get(1).getPain(), buttons.get(1).getSeverity(), buttons.get(1).getBother(),
                buttons.get(2).getX(), buttons.get(2).getY(), buttons.get(2).getPain(), buttons.get(2).getSeverity(), buttons.get(2).getBother(),
                buttons.get(3).getX(), buttons.get(3).getY(), buttons.get(3).getPain(), buttons.get(3).getSeverity(), buttons.get(3).getBother());

        saveBodySelect(myBodyWidth, myBodyHeight, myBodyStartX, myBodyStartY, buttonCount,
                buttons.get(0).getX(), buttons.get(0).getY(), buttons.get(0).getPainRG(), buttons.get(0).getSeverityRG(), buttons.get(0).getBotherRG(),
                buttons.get(1).getX(), buttons.get(1).getY(), buttons.get(1).getPainRG(), buttons.get(1).getSeverityRG(), buttons.get(1).getBotherRG(),
                buttons.get(2).getX(), buttons.get(2).getY(), buttons.get(2).getPainRG(), buttons.get(2).getSeverityRG(), buttons.get(2).getBotherRG(),
                buttons.get(3).getX(), buttons.get(3).getY(), buttons.get(3).getPainRG(), buttons.get(3).getSeverityRG(), buttons.get(3).getBotherRG());
    }

    public void nextPage(View view) {
        saveModel();
        Intent i = new Intent(BodySelectActivity.this, HowDoYouFeelActivity.class);
        startActivity(i);
    }

    public void previousPage(View view) {
        saveModel();
        Intent i = new Intent(BodySelectActivity.this, PainActivity.class);
        startActivity(i);
    }
}
