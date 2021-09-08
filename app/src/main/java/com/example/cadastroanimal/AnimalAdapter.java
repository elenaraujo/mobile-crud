package com.example.cadastroanimal;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

// Adaptador para exibir os dados da listagem de forma customizada para esta aplicação,
// com nome, espécie, idade, raça e peso de cada animal.
public class AnimalAdapter extends BaseAdapter {

    private List<Animal> animais;
    private Activity activity;

    public AnimalAdapter(Activity activity, List<Animal> animais) {
        this.activity = activity;
        this.animais = animais;
    }

    @Override
    public int getCount() {
        return animais.size();
    }

    @Override
    public Object getItem(int i) {
        return animais.get(i);
    }

    @Override
    public long getItemId(int i) {
        return animais.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = activity.getLayoutInflater().inflate(R.layout.item, viewGroup, false);
        TextView especie = v.findViewById(R.id.txtEspecie);
        TextView raca = v.findViewById(R.id.txtRaca);
        TextView nome = v.findViewById(R.id.txtNome);
        TextView peso = v.findViewById(R.id.txtPeso);
        TextView idade = v.findViewById(R.id.txtIdade);
        Animal animal = animais.get(i);

        especie.setText(animal.getEspecie());
        raca.setText(animal.getRaca());
        nome.setText(animal.getNome());
        peso.setText(animal.getPeso().toString());
        idade.setText(animal.getIdade().toString());

        return v;
    }
}
