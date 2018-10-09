
  package com.pka.anganwadisamay;


        import android.app.DatePickerDialog;
        import android.app.Dialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.res.Resources;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.TextView;
        import android.widget.Toast;
        import java.util.Calendar;
        import android.content.Context;
        import android.content.res.Resources;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;


        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.pka.anganwadisamay.Helper.LocaleHelper;

  public class HomeActivity extends AppCompatActivity {

      RadioButton female;
      RadioButton male;

      TextView bacheKaWajan;
      TextView bacheKiJanmtithi;
      Button startbutton;
      TextView wajanLeneKITithi;
      Button chune;
      Button gadna;
      Button poshanstar;

      Button mToENButton;
      Button mToHIButton;
      static final int DILOG_ID = 0;
      static final int DIALOG_ID = 1;
      Button btn1, btn2, btn3, btn4;
      int year1, month1, day1;
      int year2, month2, day2;
      int resultMonth, resultDay, resultYear;
      float weight1;
      private RadioGroup radioSexGroup;
      private RadioButton radioSexButton;
      private DatabaseReference mDatabase;
      private Button mSendButton;


      private DatePickerDialog.OnDateSetListener dPickerListener =
              new DatePickerDialog.OnDateSetListener() {
                  @Override
                  public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                      year1 = i;
                      month1 = i1 + 1;
                      day1 = i2;
                      displayStartDate(year1, month1, day1);
                  }
              };
      private DatePickerDialog.OnDateSetListener dPickerListener1 =
              new DatePickerDialog.OnDateSetListener() {
                  @Override
                  public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                      year2 = i;
                      month2 = i1 + 1;
                      day2 = i2;
                      displayEndDate(year2, month2, day2);
                  }
              };
      private View view;
      private View view1;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_home);
// ids
          female = (RadioButton) findViewById(R.id.female);
          male = (RadioButton) findViewById(R.id.male);
          bacheKaWajan = (TextView) findViewById(R.id.weightchild);
          bacheKiJanmtithi = (TextView) findViewById(R.id.dob);
          startbutton = (Button) findViewById(R.id.startButton);
          wajanLeneKITithi = (TextView) findViewById(R.id.currentdate);
          chune = (Button) findViewById(R.id.endButton);
          gadna = (Button) findViewById(R.id.calculate);
          poshanstar = (Button) findViewById(R.id.tip);

          mToENButton = (Button) findViewById(R.id.toHIButton);
          mToENButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  updateViews("en");
              }
          });

          mToHIButton = (Button) findViewById(R.id.toENButton);
          mToHIButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  updateViews("hi");
              }
          });


          final Calendar cal = Calendar.getInstance();
          year2 = cal.get(Calendar.YEAR);
          month2 = cal.get(Calendar.MONTH);
          day2 = cal.get(Calendar.DAY_OF_MONTH);
          year1 = year2;
          month1 = month2;
          day1 = day2;
          showDialogButtonClick();
          hideNavigationBar();
      }


      @Override
      protected void attachBaseContext(Context base) {
          super.attachBaseContext(LocaleHelper.onAttach(base));
      }

      //language hindi
      private void updateViews(String languageCode) {
          Context context = LocaleHelper.setLocale(this, languageCode);
          Resources resources = context.getResources();
          female.setText(resources.getString(R.string.female));
          male.setText(resources.getString(R.string.male));
          bacheKaWajan.setText(resources.getString(R.string.bacheKaWajan));
          startbutton.setText(resources.getString(R.string.startbutton));
          bacheKiJanmtithi.setText(resources.getString(R.string.bacheKiJanamTithi));
          wajanLeneKITithi.setText(resources.getString(R.string.wajanLeneKITithi));
          chune.setText(resources.getString(R.string.chune));
          gadna.setText(resources.getString(R.string.gadna));
          poshanstar.setText(resources.getString(R.string.poshanstar));

      }

      @Override
      protected void onResume() {
          super.onResume();
          hideNavigationBar();
      }

      private void hideNavigationBar() {
          this.getWindow().getDecorView().
                  setSystemUiVisibility(
                          View.SYSTEM_UI_FLAG_FULLSCREEN |
                                  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                  View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                  View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                  View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                  );
      }

      public void showDialogButtonClick() {
          btn1 = findViewById(R.id.startButton);
          btn2 = findViewById(R.id.endButton);
          btn3 = findViewById(R.id.calculate);
          btn4 = findViewById(R.id.tip);
          radioSexGroup = (RadioGroup) findViewById(R.id.radiogroup);

          btn1.setOnClickListener(
                  new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          showDialog(DILOG_ID);
                      }
                  }
          );
          btn2.setOnClickListener(
                  new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          showDialog(DIALOG_ID);
                      }
                  }
          );
          btn3.setOnClickListener(
                  new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          weight_of_child();
                          TextView EndDate = findViewById(R.id.final_result);
                          check();
                          EndDate.setText(String.valueOf(" आयु : " + resultYear + " वर्ष " + resultMonth + " माह " + resultDay + " दिन\n" + " वजन  : " + weight1 + " किलोग्राम"));
                      }
                  });
          btn4.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  int selectedId = radioSexGroup.getCheckedRadioButtonId();
                  switch (selectedId) {
                      case R.id.male:
                          showTipsBoys(view);
                          break;
                      case R.id.female:
                          showTipsGirls(view1);
                          break;

                  }

              }
          });
      }

      public void weight_of_child() {
          EditText containNumberDecimal = (EditText) findViewById(R.id.weight);
          String weighted = containNumberDecimal.getText().toString();
          if (weighted.isEmpty()) {
              Toast.makeText(this, "Weight Box is Empty", Toast.LENGTH_SHORT).show();
              weight1 = 0;
          } else {
              weight1 = Float.valueOf(weighted);
          }
      }

      @Override
      protected Dialog onCreateDialog(int id) {
          switch (id) {
              case DILOG_ID:
                  return new DatePickerDialog(this, dPickerListener, year1, month1, day1);
              case DIALOG_ID:
                  return new DatePickerDialog(this, dPickerListener1, year2, month2, day2);
          }
          return null;
      }

      public void displayStartDate(int year, int month, int day) {
          TextView startDate = findViewById(R.id.born_date);
          startDate.setText(String.valueOf(day + "/" + month + "/" + year));
      }

      public void displayEndDate(int year, int month, int day) {
          TextView EndDate = findViewById(R.id.end_date);
          EndDate.setText(String.valueOf(day + "/" + month + "/" + year));
      }

      public void check() {
          if (year1 < year2 || month1 < month2) {
              result();
          } else if (year1 == year2 && month1 == month2 && day1 < day2) {
              result();
          } else {
              Toast.makeText(this, "Please pick Right dates", Toast.LENGTH_SHORT).show();
          }
      }

      public void result() {
          calculateDay();
          calculateMonth();
          calculateYear();
      }

      public void calculateYear() {
          resultYear = difference() / 365;
      }

      public void calculateMonth() {
          if (month2 >= month1) {
              if (month1 == month2) {

                  if (day2 >= day1) {
                      resultMonth = 0;
                  } else {
                      resultMonth = month2 - month1;
                      resultMonth = 12 + resultMonth - 1;
                  }
                  resultYear--;
              } else {
                  resultMonth = month2 - month1;
                  if (day1 > day2) {

                      if (resultMonth == 1) {
                          resultMonth = 0;
                      } else {
                          resultMonth = resultMonth - 1;
                      }
                  } else {
                      resultMonth = month2 - month1;
                  }
              }
          } else {
              resultMonth = month2 - month1;
              resultMonth = 12 + resultMonth;
              resultYear--;
          }
      }

      public void calculateDay() {

          if (day2 >= day1) {
              resultDay = day2 - day1;
          } else {
              resultDay = day2 - day1;
              resultDay = 30 + resultDay;
          }
      }

      public int dayReturn(int ayear, int amonth, int aday) {
          amonth = (amonth + 9) % 12;
          ayear = ayear - amonth / 10;
          return 365 * ayear + ayear / 4 - ayear / 100 + ayear / 400 + (amonth * 306 + 5) / 10 + (aday - 1);
      }

      public int difference() {
          return dayReturn(year2, month2, day2) - dayReturn(year1, month1, day1);
      }

      public void showTipsBoys(View view)

      {
          this.view = view;
          if (resultYear == 0 && resultMonth <= 1 && resultDay > 0) {
              if (weight1 >= 2.5) {

                  Intent Intent = new Intent(HomeActivity.this, g0.class);
                  startActivity(Intent);
              } else if (weight1 < 2.5 && weight1 >= 2.1) {

                  Intent intent1 = new Intent(HomeActivity.this, y0.class);
                  startActivity(intent1);
              } else if (weight1 < 2.1) {
                  Intent intent2 = new Intent(HomeActivity.this, r0.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 2 && resultMonth > 1) {
              if (weight1 >= 3.4) {

                  Intent Intent = new Intent(HomeActivity.this, g0.class);
                  startActivity(Intent);
              } else if (weight1 < 3.4 && weight1 >= 2.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y0.class);
                  startActivity(intent1);
              } else if (weight1 < 2.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r0.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 3 && resultMonth > 2) {
              if (weight1 >= 4.3) {

                  Intent Intent = new Intent(HomeActivity.this, g0.class);
                  startActivity(Intent);
              } else if (weight1 < 4.3 && weight1 >= 3.8) {

                  Intent intent1 = new Intent(HomeActivity.this, y0.class);
                  startActivity(intent1);
              } else if (weight1 < 3.8) {
                  Intent intent2 = new Intent(HomeActivity.this, r0.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 4 && resultMonth > 3) {
              if (weight1 >= 5.0) {

                  Intent Intent = new Intent(HomeActivity.this, g0.class);
                  startActivity(Intent);
              } else if (weight1 < 5.0 && weight1 >= 4.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y0.class);
                  startActivity(intent1);
              } else if (weight1 < 4.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r0.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 5 && resultMonth > 4) {
              if (weight1 >= 5.6) {

                  Intent Intent = new Intent(HomeActivity.this, g0.class);
                  startActivity(Intent);
              } else if (weight1 < 5.6 && weight1 >= 4.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y0.class);
                  startActivity(intent1);
              } else if (weight1 < 4.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r0.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 6 && resultMonth > 5) {
              if (weight1 >= 6.0) {

                  Intent Intent = new Intent(HomeActivity.this, g0.class);
                  startActivity(Intent);
              } else if (weight1 < 6.0 && weight1 >= 5.3) {

                  Intent intent1 = new Intent(HomeActivity.this, y0.class);
                  startActivity(intent1);
              } else if (weight1 < 5.3) {
                  Intent intent2 = new Intent(HomeActivity.this, r0.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 7 && resultMonth > 6) {
              if (weight1 >= 6.4) {

                  Intent Intent = new Intent(HomeActivity.this, g0.class);
                  startActivity(Intent);
              } else if (weight1 < 6.4 && weight1 >= 5.7) {

                  Intent intent1 = new Intent(HomeActivity.this, y0.class);
                  startActivity(intent1);
              } else if (weight1 < 5.7) {
                  Intent intent2 = new Intent(HomeActivity.this, r0.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 8 && resultMonth > 7) {
              if (weight1 >= 6.7) {

                  Intent Intent = new Intent(HomeActivity.this, g7.class);
                  startActivity(Intent);
              } else if (weight1 < 6.7 && weight1 >= 5.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y7.class);
                  startActivity(intent1);
              } else if (weight1 < 5.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r7.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 9 && resultMonth > 8) {
              if (weight1 >= 6.9) {

                  Intent Intent = new Intent(HomeActivity.this, g8.class);
                  startActivity(Intent);
              } else if (weight1 < 6.9 && weight1 >= 6.2) {

                  Intent intent1 = new Intent(HomeActivity.this, y8.class);
                  startActivity(intent1);
              } else if (weight1 < 6.2) {
                  Intent intent2 = new Intent(HomeActivity.this, r8.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 10 && resultMonth > 9) {
              if (weight1 >= 7.1) {

                  Intent Intent = new Intent(HomeActivity.this, g9.class);
                  startActivity(Intent);
              } else if (weight1 < 7.1 && weight1 >= 6.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y9.class);
                  startActivity(intent1);
              } else if (weight1 < 6.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r9.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 11 && resultMonth > 10) {
              if (weight1 >= 7.4) {

                  Intent Intent = new Intent(HomeActivity.this, g9.class);
                  startActivity(Intent);
              } else if (weight1 < 7.4 && weight1 >= 6.6) {

                  Intent intent1 = new Intent(HomeActivity.this, y9.class);
                  startActivity(intent1);
              } else if (weight1 < 6.6) {
                  Intent intent2 = new Intent(HomeActivity.this, r9.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 12 && resultMonth > 11) {
              if (weight1 >= 7.6) {

                  Intent Intent = new Intent(HomeActivity.this, g9.class);
                  startActivity(Intent);
              } else if (weight1 < 7.6 && weight1 >= 6.8) {

                  Intent intent1 = new Intent(HomeActivity.this, y9.class);
                  startActivity(intent1);
              } else if (weight1 < 6.8) {
                  Intent intent2 = new Intent(HomeActivity.this, r9.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 1 && resultMonth >= 0) {
              if (weight1 >= 7.7) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 7.7 && weight1 >= 6.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 6.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 2 && resultMonth > 1) {
              if (weight1 >= 7.9) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 7.9 && weight1 >= 7.1) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 7.1) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 3 && resultMonth > 2) {
              if (weight1 >= 8.1) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 8.1 && weight1 >= 7.2) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 7.2) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 4 && resultMonth > 3) {
              if (weight1 >= 8.3) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 8.3 && weight1 >= 7.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 7.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 5 && resultMonth > 4) {
              if (weight1 >= 8.4) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 8.4 && weight1 >= 7.5) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 7.5) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 6 && resultMonth > 5) {
              if (weight1 >= 8.6) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 8.6 && weight1 >= 7.7) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 7.7) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 7 && resultMonth > 6) {
              if (weight1 >= 8.8) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 8.8 && weight1 >= 7.8) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 7.8) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 8 && resultMonth > 7) {
              if (weight1 >= 8.9) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 8.9 && weight1 >= 8.0) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.0) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 9 && resultMonth > 8) {
              if (weight1 >= 9.1) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 9.1 && weight1 >= 8.1) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.1) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 10 && resultMonth > 9) {
              if (weight1 >= 9.2) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 9.2 && weight1 >= 8.2) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.2) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 11 && resultMonth > 10) {
              if (weight1 >= 9.4) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 9.4 && weight1 >= 8.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 12 && resultMonth > 11) {
              if (weight1 >= 9.5) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 9.5 && weight1 >= 8.5) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.5) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 1 && resultMonth >= 0) {
              if (weight1 >= 9.7) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 9.7 && weight1 >= 8.6) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.6) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 2 && resultMonth > 1) {
              if (weight1 >= 9.8) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 9.8 && weight1 >= 8.8) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.8) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 3 && resultMonth > 2) {
              if (weight1 >= 10.0) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10 && weight1 >= 8.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 4 && resultMonth > 3) {
              if (weight1 >= 10.1) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.1 && weight1 >= 9.0) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.0) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 5 && resultMonth > 4) {
              if (weight1 >= 10.2) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.2 && weight1 >= 9.18) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.1) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 6 && resultMonth > 5) {
              if (weight1 >= 10.4) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.4 && weight1 >= 9.2) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.2) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 7 && resultMonth > 6) {
              if (weight1 >= 10.5) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.5 && weight1 >= 9.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 8 && resultMonth > 7) {
              if (weight1 >= 10.7) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.7 && weight1 >= 9.5) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.5) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 9 && resultMonth > 8) {
              if (weight1 >= 10.8) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.8 && weight1 >= 9.6) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.6) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 10 && resultMonth > 9) {
              if (weight1 >= 10.9) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.9 && weight1 >= 9.7) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.7) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 11 && resultMonth > 10) {
              if (weight1 >= 11.0) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.0 && weight1 >= 9.8) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.8) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 12 && resultMonth > 11) {
              if (weight1 >= 11.2) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.2 && weight1 >= 9.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 1 && resultMonth >= 0) {
              if (weight1 >= 11.3) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.3 && weight1 >= 10.0) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.0) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 2 && resultMonth > 1) {
              if (weight1 >= 11.4) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.4 && weight1 >= 10.1) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.1) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 3 && resultMonth > 2) {
              if (weight1 >= 11.5) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.5 && weight1 >= 10.2) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.2) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 4 && resultMonth > 3) {
              if (weight1 >= 11.6) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.6 && weight1 >= 10.3) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.3) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 5 && resultMonth > 4) {
              if (weight1 >= 11.8) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.8 && weight1 >= 10.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 6 && resultMonth > 5) {
              if (weight1 >= 11.9) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.9 && weight1 >= 10.5) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.55) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 7 && resultMonth > 6) {
              if (weight1 >= 12.0) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.0 && weight1 >= 10.6) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.6) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 8 && resultMonth > 7) {
              if (weight1 >= 12.1) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.1 && weight1 >= 10.7) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.7) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 9 && resultMonth > 8) {
              if (weight1 >= 12.2) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.2 && weight1 >= 10.8) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.8) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 10 && resultMonth > 9) {
              if (weight1 >= 12.4) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.4 && weight1 >= 10.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 11 && resultMonth > 10) {
              if (weight1 >= 12.5) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.5 && weight1 >= 11.0) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.0) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 12 && resultMonth > 11) {
              if (weight1 >= 12.6) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.6 && weight1 >= 11.1) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.1) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 1 && resultMonth >= 0) {
              if (weight1 >= 12.7) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.7 && weight1 >= 11.2) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.2) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 2 && resultMonth > 1) {
              if (weight1 >= 12.8) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.8 && weight1 >= 11.3) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.3) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 3 && resultMonth > 2) {
              if (weight1 >= 12.9) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.9 && weight1 >= 11.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 4 && resultMonth > 3) {
              if (weight1 >= 13.1) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.1 && weight1 >= 11.5) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.5) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 5 && resultMonth > 4) {
              if (weight1 >= 13.2) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.2 && weight1 >= 11.6) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.6) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 6 && resultMonth > 5) {
              if (weight1 >= 13.3) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.3 && weight1 >= 11.7) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.7) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 7 && resultMonth > 6) {
              if (weight1 >= 13.4) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.4 && weight1 >= 11.8) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.8) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }

          if (resultYear == 4 && resultMonth <= 8 && resultMonth > 7) {
              if (weight1 >= 13.5) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.5 && weight1 >= 11.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 9 && resultMonth > 8) {
              if (weight1 >= 13.6) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.6 && weight1 >= 12.0) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 12.0) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 10 && resultMonth > 9) {
              if (weight1 >= 13.7) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.7 && weight1 >= 12.1) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 12.1) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 11 && resultMonth > 10) {
              if (weight1 >= 13.8) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.8 && weight1 >= 12.2) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 12.2) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 12 && resultMonth > 11) {
              if (weight1 >= 14.0) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 14.0 && weight1 >= 12.3) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 12.3) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 5 && resultMonth < 1 && resultMonth == 0) {
              if (weight1 >= 14.1) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 14.1 && weight1 >= 12.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 12.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }

          } else if (resultYear >= 5 && resultMonth >= 1 && resultDay >= 0) {
              Toast.makeText(this, "App only for 0-5 year of children", Toast.LENGTH_LONG).show();
          }
      }

      public void showTipsGirls(View view)

      {
          view1 = view;
          if (resultYear == 0 && resultMonth <= 1 && resultDay > 0) {
              if (weight1 >= 2.4) {

                  Intent Intent = new Intent(HomeActivity.this, g0.class);
                  startActivity(Intent);
              } else if (weight1 < 2.4 && weight1 >= 2.0) {

                  Intent intent1 = new Intent(HomeActivity.this, y0.class);
                  startActivity(intent1);
              } else if (weight1 < 2.0) {
                  Intent intent2 = new Intent(HomeActivity.this, r0.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 2 && resultMonth > 1) {
              if (weight1 >= 3.2) {

                  Intent Intent = new Intent(HomeActivity.this, g0.class);
                  startActivity(Intent);
              } else if (weight1 < 3.2 && weight1 >= 2.7) {

                  Intent intent1 = new Intent(HomeActivity.this, y0.class);
                  startActivity(intent1);
              } else if (weight1 < 2.7) {
                  Intent intent2 = new Intent(HomeActivity.this, r0.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 3 && resultMonth > 2) {
              if (weight1 >= 3.9) {

                  Intent Intent = new Intent(HomeActivity.this, g0.class);
                  startActivity(Intent);
              } else if (weight1 < 3.9 && weight1 >= 3.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y0.class);
                  startActivity(intent1);
              } else if (weight1 < 3.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r0.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 4 && resultMonth > 3) {
              if (weight1 >= 4.5) {

                  Intent Intent = new Intent(HomeActivity.this, g0.class);
                  startActivity(Intent);
              } else if (weight1 < 4.5 && weight1 >= 4.0) {

                  Intent intent1 = new Intent(HomeActivity.this, y0.class);
                  startActivity(intent1);
              } else if (weight1 < 4.0) {
                  Intent intent2 = new Intent(HomeActivity.this, r0.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 5 && resultMonth > 4) {
              if (weight1 >= 5.0) {

                  Intent Intent = new Intent(HomeActivity.this, g0.class);
                  startActivity(Intent);
              } else if (weight1 < 5.0 && weight1 >= 4.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y0.class);
                  startActivity(intent1);
              } else if (weight1 < 4.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r0.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 6 && resultMonth > 5) {
              if (weight1 >= 5.4) {

                  Intent Intent = new Intent(HomeActivity.this, g0.class);
                  startActivity(Intent);
              } else if (weight1 < 5.4 && weight1 >= 4.8) {

                  Intent intent1 = new Intent(HomeActivity.this, y0.class);
                  startActivity(intent1);
              } else if (weight1 < 4.8) {
                  Intent intent2 = new Intent(HomeActivity.this, r0.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 7 && resultMonth > 6) {
              if (weight1 >= 5.7) {

                  Intent Intent = new Intent(HomeActivity.this, g0.class);
                  startActivity(Intent);
              } else if (weight1 < 5.7 && weight1 >= 5.1) {

                  Intent intent1 = new Intent(HomeActivity.this, y0.class);
                  startActivity(intent1);
              } else if (weight1 < 5.1) {
                  Intent intent2 = new Intent(HomeActivity.this, r0.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 8 && resultMonth > 7) {
              if (weight1 >= 6.0) {

                  Intent Intent = new Intent(HomeActivity.this, g7.class);
                  startActivity(Intent);
              } else if (weight1 < 6.0 && weight1 >= 5.3) {

                  Intent intent1 = new Intent(HomeActivity.this, y7.class);
                  startActivity(intent1);
              } else if (weight1 < 5.3) {
                  Intent intent2 = new Intent(HomeActivity.this, r7.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 9 && resultMonth > 8) {
              if (weight1 >= 6.3) {

                  Intent Intent = new Intent(HomeActivity.this, g8.class);
                  startActivity(Intent);
              } else if (weight1 < 6.3 && weight1 >= 5.6) {

                  Intent intent1 = new Intent(HomeActivity.this, y8.class);
                  startActivity(intent1);
              } else if (weight1 < 5.6) {
                  Intent intent2 = new Intent(HomeActivity.this, r8.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 10 && resultMonth > 9) {
              if (weight1 >= 6.5) {

                  Intent Intent = new Intent(HomeActivity.this, g9.class);
                  startActivity(Intent);
              } else if (weight1 < 6.5 && weight1 >= 5.8) {

                  Intent intent1 = new Intent(HomeActivity.this, y9.class);
                  startActivity(intent1);
              } else if (weight1 < 5.8) {
                  Intent intent2 = new Intent(HomeActivity.this, r9.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 11 && resultMonth > 10) {
              if (weight1 >= 6.7) {

                  Intent Intent = new Intent(HomeActivity.this, g9.class);
                  startActivity(Intent);
              } else if (weight1 < 6.7 && weight1 >= 5.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y9.class);
                  startActivity(intent1);
              } else if (weight1 < 5.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r9.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 0 && resultMonth <= 12 && resultMonth > 11) {
              if (weight1 >= 6.9) {

                  Intent Intent = new Intent(HomeActivity.this, g9.class);
                  startActivity(Intent);
              } else if (weight1 < 6.9 && weight1 >= 6.1) {

                  Intent intent1 = new Intent(HomeActivity.this, y9.class);
                  startActivity(intent1);
              } else if (weight1 < 6.1) {
                  Intent intent2 = new Intent(HomeActivity.this, r9.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 1 && resultMonth >= 0) {
              if (weight1 >= 7.0) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 7.0 && weight1 >= 6.3) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 6.3) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 2 && resultMonth > 1) {
              if (weight1 >= 7.2) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 7.2 && weight1 >= 6.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 6.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 3 && resultMonth > 2) {
              if (weight1 >= 7.4) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 7.4 && weight1 >= 6.6) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 6.6) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 4 && resultMonth > 3) {
              if (weight1 >= 7.6) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 7.6 && weight1 >= 6.7) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 6.7) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 5 && resultMonth > 4) {
              if (weight1 >= 7.7) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 7.7 && weight1 >= 6.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 6.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 6 && resultMonth > 5) {
              if (weight1 >= 7.9) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 7.9 && weight1 >= 7.0) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 7.0) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 7 && resultMonth > 6) {
              if (weight1 >= 8.1) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 8.1 && weight1 >= 7.2) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 7.2) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 8 && resultMonth > 7) {
              if (weight1 >= 8.2) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 8.2 && weight1 >= 7.3) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 7.3) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 9 && resultMonth > 8) {
              if (weight1 >= 8.4) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 8.4 && weight1 >= 7.5) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 7.5) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 10 && resultMonth > 9) {
              if (weight1 >= 8.6) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 8.6 && weight1 >= 7.6) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 7.6) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 11 && resultMonth > 10) {
              if (weight1 >= 8.7) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 8.7 && weight1 >= 7.8) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 7.8) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 1 && resultMonth <= 12 && resultMonth > 11) {
              if (weight1 >= 8.9) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 8.9 && weight1 >= 7.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 7.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 1 && resultMonth >= 0) {
              if (weight1 >= 9.0) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 9.0 && weight1 >= 8.1) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.1) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 2 && resultMonth > 1) {
              if (weight1 >= 9.2) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 9.2 && weight1 >= 8.2) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.2) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 3 && resultMonth > 2) {
              if (weight1 >= 9.4) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 9.4 && weight1 >= 8.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 4 && resultMonth > 3) {
              if (weight1 >= 9.5) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 9.5 && weight1 >= 8.5) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.5) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 5 && resultMonth > 4) {
              if (weight1 >= 9.7) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 9.7 && weight1 >= 8.6) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.6) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 6 && resultMonth > 5) {
              if (weight1 >= 9.8) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 9.8 && weight1 >= 8.8) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.8) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 7 && resultMonth > 6) {
              if (weight1 >= 10.0) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.0 && weight1 >= 8.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 8.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 8 && resultMonth > 7) {
              if (weight1 >= 10.1) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.1 && weight1 >= 9.0) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.0) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 9 && resultMonth > 8) {
              if (weight1 >= 10.3) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.3 && weight1 >= 9.1) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.1) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 10 && resultMonth > 9) {
              if (weight1 >= 10.4) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.4 && weight1 >= 9.3) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.3) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 11 && resultMonth > 10) {
              if (weight1 >= 10.5) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.5 && weight1 >= 9.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 2 && resultMonth <= 12 && resultMonth > 11) {
              if (weight1 >= 10.7) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.7 && weight1 >= 9.5) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.5) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 1 && resultMonth >= 0) {
              if (weight1 >= 10.8) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.8 && weight1 >= 9.6) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.6) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 2 && resultMonth > 1) {
              if (weight1 >= 10.9) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 10.9 && weight1 >= 9.7) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.7) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 3 && resultMonth > 2) {
              if (weight1 >= 11.1) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.1 && weight1 >= 9.8) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.8) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 4 && resultMonth > 3) {
              if (weight1 >= 11.2) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.2 && weight1 >= 9.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 9.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 5 && resultMonth > 4) {
              if (weight1 >= 11.3) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.3 && weight1 >= 10.1) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.1) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 6 && resultMonth > 5) {
              if (weight1 >= 11.5) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.5 && weight1 >= 10.2) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.2) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 7 && resultMonth > 6) {
              if (weight1 >= 11.6) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.6 && weight1 >= 10.3) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.3) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 8 && resultMonth > 7) {
              if (weight1 >= 11.7) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.7 && weight1 >= 10.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 9 && resultMonth > 8) {
              if (weight1 >= 11.8) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 11.8 && weight1 >= 10.5) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.5) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 10 && resultMonth > 9) {
              if (weight1 >= 12.0) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.0 && weight1 >= 10.6) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.6) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 11 && resultMonth > 10) {
              if (weight1 >= 12.1) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.1 && weight1 >= 10.7) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.7) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 3 && resultMonth <= 12 && resultMonth > 11) {
              if (weight1 >= 12.2) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.2 && weight1 >= 10.8) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.8) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 1 && resultMonth >= 0) {
              if (weight1 >= 12.3) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.3 && weight1 >= 10.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 10.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 2 && resultMonth > 1) {
              if (weight1 >= 12.4) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.4 && weight1 >= 11.0) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.0) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 3 && resultMonth > 2) {
              if (weight1 >= 12.6) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.6 && weight1 >= 11.1) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.1) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 4 && resultMonth > 3) {
              if (weight1 >= 12.7) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.7 && weight1 >= 11.2) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.2) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 5 && resultMonth > 4) {
              if (weight1 >= 12.8) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.8 && weight1 >= 11.3) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.3) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 6 && resultMonth > 5) {
              if (weight1 >= 12.9) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 12.9 && weight1 >= 11.4) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.4) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 7 && resultMonth > 6) {
              if (weight1 >= 13.0) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.0 && weight1 >= 11.5) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.5) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 8 && resultMonth > 7) {
              if (weight1 >= 13.2) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.2 && weight1 >= 11.6) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.6) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 9 && resultMonth > 8) {
              if (weight1 >= 13.3) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.3 && weight1 >= 11.7) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.7) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 10 && resultMonth > 9) {
              if (weight1 >= 13.4) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.4 && weight1 >= 11.8) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.8) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 11 && resultMonth > 10) {
              if (weight1 >= 13.5) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.5 && weight1 >= 11.9) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 11.9) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 4 && resultMonth <= 12 && resultMonth > 11) {
              if (weight1 >= 13.6) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.6 && weight1 >= 13.6) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 12.0) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          }
          if (resultYear == 5 && resultMonth < 1 && resultMonth == 0) {
              if (weight1 >= 13.7) {

                  Intent Intent = new Intent(HomeActivity.this, g12.class);
                  startActivity(Intent);
              } else if (weight1 < 13.7 && weight1 >= 12.1) {

                  Intent intent1 = new Intent(HomeActivity.this, y12.class);
                  startActivity(intent1);
              } else if (weight1 < 12.1) {
                  Intent intent2 = new Intent(HomeActivity.this, r12.class);
                  startActivity(intent2);

              }
          } else if (resultYear >= 5 && resultMonth >= 1 && resultDay >= 0) {
              Toast.makeText(this, "App only for 0-5 year of children.", Toast.LENGTH_LONG).show();
          }

      }

      @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater().inflate(R.menu.main_menu, menu);
          return super.onCreateOptionsMenu(menu);
      }

      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
          if (item.getItemId() == R.id.language_en) {
              updateViews("en");

          }
          else if (item.getItemId() == R.id.language_hindi) {
              updateViews("hi");

          }
          return true;
      }

  }







