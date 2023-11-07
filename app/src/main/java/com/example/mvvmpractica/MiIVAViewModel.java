package com.example.mvvmpractica;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MiIVAViewModel extends AndroidViewModel {
    Executor executor;

    SimuladorIVA simulador;

    MutableLiveData<Double> cuota = new MutableLiveData<>();
    MutableLiveData<Integer> errorIva = new MutableLiveData<>();
    MutableLiveData<Boolean> calculando = new MutableLiveData<>();

    public MiIVAViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        simulador = new SimuladorIVA();
    }
    public void calcular(double precio, int iva) {

        final SimuladorIVA.Solicitud solicitud = new SimuladorIVA.Solicitud(precio, iva);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                simulador.calcular(solicitud, new SimuladorIVA.Callback() {
                    @Override
                    public void cuandoEsteCalculadoElTotal(double cuotaResultante) {
                        cuota.postValue(cuotaResultante);
                        errorIva.postValue(null);
                    }
                    @Override
                    public void cuandoHayaErrorDeIVAInferiorAlMinimo(int ivaMinimo) {
                        errorIva.postValue(ivaMinimo);
                    }

                    @Override
                    public void cuandoEmpieceElCalculo() {

                    }

                    @Override
                    public void cuandoFinaliceElCalculo() {

                    }
                });
            }
        });
    }
}
