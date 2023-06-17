package upm.cabd.mssde_pas.sensor;

public class AccelerometerData {
    private double accelerationDelta_X_axis_inMetersPerSecondSquared;
    private double accelerationDelta_Y_axis_inMetersPerSecondSquared;
    private double accelerationDelta_Z_axis_inMetersPerSecondSquared;

    public AccelerometerData(double accelerationDelta_x_axis_inMetersPerSecondSquared, double accelerationDelta_y_axis_inMetersPerSecondSquared, double accelerationDelta_z_axis_inMetersPerSecondSquared) {
        accelerationDelta_X_axis_inMetersPerSecondSquared = accelerationDelta_x_axis_inMetersPerSecondSquared;
        accelerationDelta_Y_axis_inMetersPerSecondSquared = accelerationDelta_y_axis_inMetersPerSecondSquared;
        accelerationDelta_Z_axis_inMetersPerSecondSquared = accelerationDelta_z_axis_inMetersPerSecondSquared;
    }

    public void setAccelerationDelta_X_axis_inMetersPerSecondSquared(double accelerationDelta_X_axis_inMetersPerSecondSquared) {
        this.accelerationDelta_X_axis_inMetersPerSecondSquared = accelerationDelta_X_axis_inMetersPerSecondSquared;
    }

    public void setAccelerationDelta_Y_axis_inMetersPerSecondSquared(double accelerationDelta_Y_axis_inMetersPerSecondSquared) {
        this.accelerationDelta_Y_axis_inMetersPerSecondSquared = accelerationDelta_Y_axis_inMetersPerSecondSquared;
    }

    public void setAccelerationDelta_Z_axis_inMetersPerSecondSquared(double accelerationDelta_Z_axis_inMetersPerSecondSquared) {
        this.accelerationDelta_Z_axis_inMetersPerSecondSquared = accelerationDelta_Z_axis_inMetersPerSecondSquared;
    }

    public double getAccelerationDelta_X_axis_inMetersPerSecondSquared() {
        return accelerationDelta_X_axis_inMetersPerSecondSquared;
    }

    public double getAccelerationDelta_Y_axis_inMetersPerSecondSquared() {
        return accelerationDelta_Y_axis_inMetersPerSecondSquared;
    }

    public double getAccelerationDelta_Z_axis_inMetersPerSecondSquared() {
        return accelerationDelta_Z_axis_inMetersPerSecondSquared;
    }
}
