import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Context;
import android.hardware.Sensor;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import java.util.Locale;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    //variables definition
    TextView textView;
    private SensorManager sensorManager;
    // declare
    private Sensor accelerometer;
    private Sensor gyroscope;
    private Sensor magnetic;
    private Sensor pressure;
    private Sensor ambientTemperature;
    private Sensor proximity;
    private Sensor light;
    private Sensor temperature;

    private List<String> rawDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.txSensors);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //List<Sensor> list = sensorManager.getSensorList(Sensor.TYPE_ALL);  // collection of sensors
        //for (Sensor sensor:list){
        //textView.append(sensor.getName() + "\n");  //display all sensors the Android platform provides.

        //However, some hardware-based sensors need support from current smartphone
        //}
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        ambientTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);

        // registerListener
        //SENSOR_DELAY_NORMAL determines the update rate of sensor data.
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, ambientTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        int i = sensor.getType();
        String singleLine = null;

        if (motionSensors.contains(i)) {
            singleLine = String.format(Locale.ENGLISH, "%s,%.3f,%.3f,%.3f", getSensorName(i),sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
        }
        else if (environmentSensors.contains(i)) {
            singleLine = String.format(Locale.ENGLISH, "%s,%.3f", getSensorName(i), sensorEvent.values[0]);
        }

        if (singleLine!=null){
            //            Log.d(TAG,"The output: " + singleLine);
            rawDataList.add(singleLine);
        }

        if (rawDataList.size() > 10){
            String outPut = "";
            for(String str: rawDataList)
            {
                outPut = outPut + str + ";";
            }
            textView.setText(outPut);
        }
    }


    private Set<Integer> motionSensors = new HashSet<>(Arrays.asList(Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_GYROSCOPE, Sensor.TYPE_MAGNETIC_FIELD));

    private Set<Integer> environmentSensors = new HashSet<>(Arrays.asList(Sensor.TYPE_AMBIENT_TEMPERATURE, Sensor.TYPE_PRESSURE, Sensor.TYPE_PROXIMITY,
            Sensor.TYPE_LIGHT, Sensor.TYPE_TEMPERATURE));

    public String getSensorName(int senorType){
        String senStringName;
        switch (senorType) {
            case Sensor.TYPE_ACCELEROMETER:
                senStringName = "ACCE";
                break;
            case Sensor.TYPE_GYROSCOPE:
                senStringName = "GYRO";
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                senStringName = "MAGN";
                break;
            case Sensor.TYPE_LIGHT:
                senStringName = "LIGH";
                break;
            case Sensor.TYPE_PRESSURE:
                senStringName = "PRES";
                break;
            case Sensor.TYPE_PROXIMITY:
                senStringName = "PROX";
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                senStringName = "HUMI";
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                senStringName = "ATEM";
                break;
            case Sensor.TYPE_TEMPERATURE:
                senStringName = "TEMP";
                break;
            default:
                senStringName = "";
                break;
        }
        return senStringName;

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
