package com.isam.appsueldo;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    //Definimos los objetos
    EditText txtDni;
    EditText txtNombres;
    EditText txtFechaNacimiento;
    EditText txtEdad;

    //Select estado civil
    Spinner spEstadoCivil;

    //Default oculto / Se muestra si tiene hijos
    EditText txtCantidadHijos;

    //Agrupación de radio buttons
    RadioGroup rgConviviente;
    RadioButton rbConvivienteSi;
    RadioButton rbConvivienteNo;


    RadioGroup rgHijos;
    RadioButton rbHijosSi;
    RadioButton rbHijosNo;

    RadioGroup rgTurno;
    RadioButton rbManana;
    RadioButton rbNoche;

    EditText txtHorasTrabajadas;
    EditText txtPagoHora;

    Button btnCalcular;
    TextView lblResultado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Terminamos de asociar los objetos (Componenetes)
        txtDni = findViewById(R.id.txtDni);
        txtNombres = findViewById(R.id.txtNombres);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento);
        txtEdad = findViewById(R.id.txtEdad);

        spEstadoCivil = findViewById(R.id.spEstadoCivil);

        rgConviviente = findViewById(R.id.rgConviviente);
        rbConvivienteSi = findViewById(R.id.rbConvivienteSi);
        rbConvivienteNo = findViewById(R.id.rbConvivienteNo);

        rgHijos = findViewById(R.id.rgHijos);
        rbHijosSi = findViewById(R.id.rbHijosSi);
        rbHijosNo = findViewById(R.id.rbHijosNo);

        rgTurno = findViewById(R.id.rgTurno);
        rbManana = findViewById(R.id.rbManana);
        rbNoche = findViewById(R.id.rbNoche);

        txtHorasTrabajadas = findViewById(R.id.txtHoras);
        txtPagoHora = findViewById(R.id.txtPagoHora);

        btnCalcular = findViewById(R.id.btnCalcular);
        lblResultado = findViewById(R.id.lblResultado);

        //LLamar a los metodos
        cargarEstadoCivil();
        configurarFecha();



    }

    //metodo para cargar el estado civil
    public void cargarEstadoCivil(){

        //Definir un arreglo unidimensional de tipo cadena llamada elementos EC que contendrá soltero, casado, viudo, divorciado.
        String[] elementosEC = {"<Seleccionar>","Soltero", "Casado", "Viudo", "Divorciado", "Conviviente" };

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                elementosEC
        );

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spEstadoCivil.setAdapter(adaptador);
    }

    //Método para configurar el campo fecha
    public void configurarFecha() {
        //Programando el campo Seleccionar fecha de nacimiento txtFechaNacimiento
        txtFechaNacimiento.setOnClickListener(vista ->{
            //Instanciar el objeto calendario de la clase Calendar
            Calendar calendario = Calendar.getInstance();
            //Crear variables para almacenar los datos del dia, mes y año gracias al objeto calendario
            int dia = calendario.get(Calendar.DAY_OF_MONTH);
            int mes = calendario.get(Calendar.MONTH);
            int anho = calendario.get(Calendar.YEAR);

            //Procesamos los datos de la fecha
            DatePickerDialog dialogo = new DatePickerDialog(
                    MainActivity.this,
                    (datePicker, year, month, dayOfMonth) -> {
                        String fecha = dayOfMonth +"/" + (month + 1) + "/" + year;
                        txtFechaNacimiento.setText(fecha);
                    },
                    anho,
                    mes,
                    dia
            );

            dialogo.show();
        });
    }

}