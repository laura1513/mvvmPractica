package com.example.mvvmpractica;

public class SimuladorIVA {
    public static class Solicitud {
        public double precio;
        public int iva;

        public Solicitud(double precio, int iva) {
            this.precio = precio;
            this.iva = iva;
        }
    }
    interface Callback {
        void cuandoEsteCalculadoElTotal(double total);
        void cuandoHayaErrorDeIVAInferiorAlMinimo(int plazoMinimo);
        void cuandoEmpieceElCalculo();
        void cuandoFinaliceElCalculo();
    }
    public void calcular(Solicitud solicitud, Callback callback) {
        callback.cuandoEmpieceElCalculo();
        int ivaMinimo = 0;
        try {
            Thread.sleep(10000);
            //El IVA no puede ser menor del 3%
            ivaMinimo = 3;
        } catch (InterruptedException e) {}

        boolean error = false;
        if (solicitud.iva < ivaMinimo) {
            callback.cuandoHayaErrorDeIVAInferiorAlMinimo(ivaMinimo);
            error = true;
        }
        if(!error) {
            //Calculo total del precio con iva
            callback.cuandoEsteCalculadoElTotal(((solicitud.precio * solicitud.iva) / 100) + solicitud.precio);
        }
        callback.cuandoFinaliceElCalculo();
    }
}
