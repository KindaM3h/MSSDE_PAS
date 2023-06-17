package upm.cabd.mssde_pas.sensor;

public class MagnetometerData {
    private double magneticFlux_X_axis_inMicroTesla;
    private double magneticFlux_Y_axis_inMicroTesla;
    private double magneticFlux_Z_axis_inMicroTesla;


    public MagnetometerData(double magneticFlux_x_axis_inMicroTesla, double magneticFlux_y_axis_inMicroTesla, double magneticFlux_z_axis_inMicroTesla) {
        magneticFlux_X_axis_inMicroTesla = magneticFlux_x_axis_inMicroTesla;
        magneticFlux_Y_axis_inMicroTesla = magneticFlux_y_axis_inMicroTesla;
        magneticFlux_Z_axis_inMicroTesla = magneticFlux_z_axis_inMicroTesla;
    }

    public void setMagneticFlux_X_axis_inMicroTesla(double magneticFlux_X_axis_inMicroTesla) {
        this.magneticFlux_X_axis_inMicroTesla = magneticFlux_X_axis_inMicroTesla;
    }

    public void setMagneticFlux_Y_axis_inMicroTesla(double magneticFlux_Y_axis_inMicroTesla) {
        this.magneticFlux_Y_axis_inMicroTesla = magneticFlux_Y_axis_inMicroTesla;
    }

    public void setMagneticFlux_Z_axis_inMicroTesla(double magneticFlux_Z_axis_inMicroTesla) {
        this.magneticFlux_Z_axis_inMicroTesla = magneticFlux_Z_axis_inMicroTesla;
    }

    public double getMagneticFlux_X_axis_inMicroTesla() {
        return magneticFlux_X_axis_inMicroTesla;
    }

    public double getMagneticFlux_Y_axis_inMicroTesla() {
        return magneticFlux_Y_axis_inMicroTesla;
    }

    public double getMagneticFlux_Z_axis_inMicroTesla() {
        return magneticFlux_Z_axis_inMicroTesla;
    }
}
