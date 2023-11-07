package com.example.mvvmpractica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvvmpractica.databinding.FragmentIvaBinding;

public class IvaFragment extends Fragment {
    private FragmentIvaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentIvaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final MiIVAViewModel miIVAViewModel = new ViewModelProvider(this).get(MiIVAViewModel.class);

        binding.calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double precio = Double.parseDouble(binding.preciosiniva.getText().toString());
                int iva = Integer.parseInt(binding.iva.getText().toString());

                miIVAViewModel.calcular(precio, iva);
            }
        });

        miIVAViewModel.cuota.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double cuota) {
                binding.cuota.setText(String.format("%.2f",cuota));
            }
        });
        miIVAViewModel.errorIva.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer ivaMinimo) {
                if (ivaMinimo != null) {
                    binding.iva.setError("El iva no puede ser inferior al " + ivaMinimo + "%");
                } else {
                    binding.iva.setError(null);
                }
            }
        });
        miIVAViewModel.calculando.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean calculando) {
                if (calculando) {
                    binding.calculando.setVisibility(View.VISIBLE);
                    binding.cuota.setVisibility(View.GONE);
                } else {
                    binding.calculando.setVisibility(View.GONE);
                    binding.cuota.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}