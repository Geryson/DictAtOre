package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class NyelvValaszto1Activity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nyelv_valaszto1);

        idView = (TextView) findViewById(R.id.TudastesztButton);
        //LanguagesBox = (EditText) findViewById(R.id.SzotarakButton);
        ListView listView = (ListView) findViewById(R.id.listView);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
}
