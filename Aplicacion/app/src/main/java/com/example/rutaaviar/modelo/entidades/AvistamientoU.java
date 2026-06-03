package com.example.rutaaviar.modelo.entidades;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class AvistamientoU implements Parcelable {
    private int id;

    private String pajaro;

    private String pRaza;
    private Date fecha;
    private double lat;
    private double lon;
    public AvistamientoU(int id, String pajaro, String pRaza, Date date, double lat, double lon){
        this.id=id;
        this.pajaro=pajaro;
        this.pRaza=pRaza;
        this.fecha=date;
        this.lat =lat;
        this.lon =lon;
    }

    public AvistamientoU( String pajaro, String pRaza, Date date, double lat, double lon){
        this.pajaro=pajaro;
        this.pRaza=pRaza;
        this.fecha=date;
        this.lat =lat;
        this.lon =lon;
    }


    protected AvistamientoU(Parcel in) {
        id = in.readInt();
        pajaro = in.readString();
        pRaza = in.readString();

        long tmpDate = in.readLong();
        fecha = tmpDate == -1 ? null : new Date(tmpDate);

        lat = in.readDouble();
        lon = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(pajaro);
        dest.writeString(pRaza);

        dest.writeLong(fecha != null ? fecha.getTime() : -1);

        dest.writeDouble(lat);
        dest.writeDouble(lon);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AvistamientoU> CREATOR = new Creator<AvistamientoU>() {
        @Override
        public AvistamientoU createFromParcel(Parcel in) {
            return new AvistamientoU(in);
        }

        @Override
        public AvistamientoU[] newArray(int size) {
            return new AvistamientoU[size];
        }
    };


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPajaro() {
        return pajaro;
    }

    public void setPajaro(String pajaro) {
        this.pajaro = pajaro;
    }

    public String getpRaza() {
        return pRaza;
    }

    public void setpRaza(String pRaza) {
        this.pRaza = pRaza;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
