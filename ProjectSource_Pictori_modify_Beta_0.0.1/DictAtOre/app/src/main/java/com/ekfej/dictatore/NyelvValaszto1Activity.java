package com.ekfej.dictatore;

<<<<<<< HEAD
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class NyelvValaszto1Activity extends AppCompatActivity {
=======

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NyelvValaszto1Activity extends ActionBarActivity {

    TextView idView;
    EditText LanguagesBox;

    public void LanguagesSelect (View view) {
        DictDatabaseHandler dbHandler = new DictDatabaseHandler(this, null, null, 1);

        Languages languages =
                dbHandler.findLanguages(LanguagesBox.getText().toString());

        if (languages != null) {
            idView.setText(String.valueOf(languages.getID()));


        } else {
            idView.setText("No Match Found");
        }
    }
>>>>>>> 186317e93dded085c22f2b33c3d343473ed7976c

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nyelv_valaszto1);
<<<<<<< HEAD
    }

    @Override
=======

        idView = (TextView) findViewById(R.id.TudastesztButton);
        LanguagesBox = (EditText) findViewById(R.id.SzotarakButton);
    }

    /*@Override
>>>>>>> 186317e93dded085c22f2b33c3d343473ed7976c
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
<<<<<<< HEAD
    }

    @Override
=======
    }*/

    /*@Override
>>>>>>> 186317e93dded085c22f2b33c3d343473ed7976c
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
<<<<<<< HEAD
=======
   */



       /* public void newProduct (View view) {
            MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

            int quantity =
                    Integer.parseInt(quantityBox.getText().toString());

            Product product =
                    new Product(productBox.getText().toString(), quantity);

            dbHandler.addProduct(product);
            productBox.setText("");
            quantityBox.setText("");
        }
*/



>>>>>>> 186317e93dded085c22f2b33c3d343473ed7976c
}
