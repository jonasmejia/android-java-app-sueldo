package com.isam.appsueldo;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


//Calcular el sueldo de un trabajardor en base a lo siguiente.
//pedir la siguiente información: Dni nombres y apellidos, fecha de nacimiento, estado civil (Soltero / casado / viudo / divorciado),
//conviviente (si / no)
//tiene hijos: (Si / No)
//Si tiene hijos mostrar campo para pedir cantidad de hijos.
//Abajo: horas trabajadas.
//Pago por horas.
//        turno: mañana / noche
//
//
//
//Ahora aplicar un descuento del seguro essalud si no tiene hijos 10% de descuento, si tiene hijos 20% de descuento de su sueldo bruto.
//si es casado un bono de 200 soles y si tiene hijos se aplica 5% de bono de su sueldo bruto por cada hijo.
//si tiene mas de 45 años un bono de 100 soles.
//si el turno de trabajo es nocturno se paga 80 soles la hora y si es diurno se paga 50 soles la hora.
//
//
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

    LinearLayout contenedorHijos;

    double pagoHora = 50;
    int edad = 0;

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

        contenedorHijos = findViewById(R.id.contenedorHijos);

        //LLamar a los metodos
        cargarEstadoCivil();
        configurarFecha();
        mostrarHijos();
        configurarTurno();
        configurarbtnCalcular();


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
                        calcularEdad(year, month, dayOfMonth);
                    },
                    anho,
                    mes,
                    dia
            );

            dialogo.show();
        });
    }

    //MEtodo que calcula la edad
    private void calcularEdad(int anho, int mes, int dia) {
        Calendar fechaActual = Calendar.getInstance();
        edad = fechaActual.get(Calendar.YEAR) - anho;

        Calendar fechaCumpleanho = Calendar.getInstance();
        fechaCumpleanho.set(anho, mes, dia);

        if (fechaActual.before(fechaCumpleanho)) {
            txtEdad.setText("0");
            return;
        }

        if (fechaCumpleanho.get(Calendar.DAY_OF_YEAR) < fechaCumpleanho.get(Calendar.DAY_OF_YEAR)) {
            edad--;
        }

        txtEdad.setText(String.valueOf(edad));
    }

    public void mostrarHijos(){
        //Mostrar / ocultar cantidad de hijos
        rgHijos.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if(checkedId == R.id.rbHijosSi){
                contenedorHijos.setVisibility(View.VISIBLE);
            }else{
                contenedorHijos.setVisibility(View.GONE);

            }
        });
    }

    //Metodo que asignara el monto a pagar por tuno
    public void configurarTurno(){
        rgTurno.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if(checkedId == R.id.rbManana){
                pagoHora = 50;
            }else if(checkedId == R.id.rbNoche){
                pagoHora = 80;
            }
            txtPagoHora.setText(String.format("%.2f", pagoHora));
        });
    }

    public void configurarbtnCalcular(){
        btnCalcular.setOnClickListener(vista -> {
            calcularSueldo();

        });
    };
    public void calcularSueldo(){
        //capturar datos
        //dni, nombres, fechaNacimiento, horas
        String dni = txtDni.getText().toString();
        String nombres = txtNombres.getText().toString();
        String fechaNacimiento = txtFechaNacimiento.getText().toString();
        String horas = txtHorasTrabajadas.getText().toString();

        if(dni.isEmpty() || nombres.isEmpty() || fechaNacimiento.isEmpty() || horas.isEmpty()){
            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if(dni.length() != 8){
            Toast.makeText(this, "El DNI debe tener 8 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        //CAlcular el sueldo bruto
        double sueldoBruto = Double.parseDouble(horas) * pagoHora;

        // verificar hijos
        boolean tieneHijos = rbHijosSi.isChecked();




    }
}