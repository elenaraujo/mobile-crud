package com.example.cadastroanimal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

// Activity retornada ao clicar em atualizar os dados de um animal ou ao clicar no ícone de "+"
// no menu superior, para cadastrar um novo animal.
public class CadastroAnimalActivity extends AppCompatActivity {

    private EditText especie, raca, nome, peso, idade;
    private AnimalDAO dao;
    private Animal animal = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_animal);

        especie = findViewById(R.id.editEspecie);
        raca = findViewById(R.id.editRaca);
        nome = findViewById(R.id.editNome);
        peso = findViewById(R.id.editPeso);
        idade = findViewById(R.id.editIdade);
        dao = new AnimalDAO(this);

        Intent it = getIntent();
        if(it.hasExtra("animal")) {
            animal = (Animal) it.getSerializableExtra("animal");
            especie.setText(animal.getEspecie());
            raca.setText(animal.getRaca());
            nome.setText(animal.getNome());
            peso.setText(animal.getPeso().toString());
            idade.setText(animal.getIdade().toString());
        }
    }

    // Método que é chamado ao clicar no botão "Salvar" na tela após o preenchimento dos dados
    // do novo animal ou atualização de dados de um animal existente. Este método verifica qual
    // desses casos é o atual e faz a ação de inserir ou atualizar no banco conforme a condição.
    public void salvar(View view) {

        if(animal == null) {
            animal = new Animal();
            animal.setEspecie(especie.getText().toString());
            animal.setRaca(raca.getText().toString());
            animal.setNome(nome.getText().toString());
            animal.setPeso(Float.parseFloat(peso.getText().toString()));
            animal.setIdade(Short.parseShort(idade.getText().toString()));
            dao.open();
            long id = dao.inserir(animal);
            dao.close();
            Toast.makeText(this, "Animal inserido com o id: " + id, Toast.LENGTH_SHORT).show();
        } else {
            animal.setEspecie(especie.getText().toString());
            animal.setRaca(raca.getText().toString());
            animal.setNome(nome.getText().toString());
            animal.setPeso(Float.parseFloat(peso.getText().toString()));
            animal.setIdade(Short.parseShort(idade.getText().toString()));
            dao.open();
            dao.atualizar(animal);
            dao.close();
            Toast.makeText(this, "Cadastro do animal atualizado", Toast.LENGTH_SHORT).show();
        }
    }
}
