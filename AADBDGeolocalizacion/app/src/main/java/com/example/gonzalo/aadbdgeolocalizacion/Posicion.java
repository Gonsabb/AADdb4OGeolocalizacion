package com.example.gonzalo.aadbdgeolocalizacion;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Gonzalo on 16/02/2016.
 */
public class Posicion implements Parcelable{

    private float latitud, longitud;
    private int dia;

    public Posicion() {
    }

    public Posicion(float latitud, float longitud, int dia) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.dia = dia;
    }

    protected Posicion(Parcel in) {
        latitud = in.readFloat();
        longitud = in.readFloat();
        dia = in.readInt();
    }


    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Posicion posicion = (Posicion) o;

        if (Float.compare(posicion.latitud, latitud) != 0) return false;
        if (Float.compare(posicion.longitud, longitud) != 0) return false;
        return dia == posicion.dia;

    }

    @Override
    public int hashCode() {
        int result = (latitud != +0.0f ? Float.floatToIntBits(latitud) : 0);
        result = 31 * result + (longitud != +0.0f ? Float.floatToIntBits(longitud) : 0);
        result = 31 * result + dia;
        return result;
    }

    @Override
    public String toString() {
        return "Posicion{" +
                "latitud=" + latitud +
                ", longitud=" + longitud +
                ", dia=" + dia +
                '}';
    }

    public static final Parcelable.Creator<Posicion> CREATOR = new Parcelable.Creator<Posicion>() {
        @Override
        public Posicion createFromParcel(Parcel in) {
            return new Posicion(in);
        }

        @Override
        public Posicion[] newArray(int size) {
            return new Posicion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(latitud);
        dest.writeFloat(longitud);
        dest.writeInt(dia);
    }

}
