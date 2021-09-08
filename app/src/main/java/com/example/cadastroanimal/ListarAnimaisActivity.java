package com.example.cadastroanimal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Activity Principal, que é exibida ao abrir o App.
// Mostra uma listagem com os itens cadastrados
public class ListarAnimaisActivity extends AppCompatActivity {

    private ListView listView;
    private AnimalDAO dao;
    private List<Animal> animais;
    private List<Animal> animaisFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_animais);

        listView = findViewById((R.id.lista_animais));
        dao = new AnimalDAO(this);
        dao.open();
        animais = dao.obterTodos();
        animaisFiltrados.addAll(animais);

        AnimalAdapter adaptador = new AnimalAdapter(this, animaisFiltrados);
        listView.setAdapter((adaptador));

        registerForContextMenu(listView);
        dao.close();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procuraAnimal(s);
                return false;
            }
        });
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    // Método de busca pelo nome do animal, ao clicar no ícone de lupa
    public void procuraAnimal(String nome) {
        animaisFiltrados.clear();
        for(Animal a : animais) {
            if(a.getNome().toLowerCase().contains(nome.toLowerCase())) {
                animaisFiltrados.add(a);
            }
        }
        listView.invalidateViews();
    }

    // Método de exclusão do animal. O botão de exclusão aparece ao pressionar em cima de cada
    // objeto da lista.
    public void excluir(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Animal animalExcluir = animaisFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
            .setTitle("Atenção")
            .setMessage("Realmente deseja excluir o animal?")
            .setNegativeButton("Não", null)
            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    animaisFiltrados.remove((animalExcluir));
                    animais.remove(animalExcluir);
                    dao.open();
                    dao.excluir(animalExcluir);
                    dao.close();
                    listView.invalidateViews();
                }
            }).create();
        dialog.show();
    }

    // Método que executa a Activity CadastroAnimal, para que o usuário possa preencher os dados
    // de um novo animal a ser registrado
    public void cadastrar(MenuItem item) {
        Intent it = new Intent(this, CadastroAnimalActivity.class);
        startActivity(it);
    }

    // Método que é executado ao pressionar em cima de um item da lista e clicar na opção Atualizar.
    // Os dados desse item que foi pressionado são passados para a activity CadastrarAnimal,
    // sendo possível atualizar os dados desse animal baseado no ID.
    public void atualizar(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Animal animalAtualizar = animaisFiltrados.get(menuInfo.position);
        Intent it = new Intent(this, CadastroAnimalActivity.class);
        it.putExtra("animal", animalAtualizar);
        startActivity(it);
    }

    @Override
    public void onResume() {
        super.onResume();
        dao.open();
        animais = dao.obterTodos();
        dao.close();
        animaisFiltrados.clear();
        animaisFiltrados.addAll(animais);
        listView.invalidateViews();
    }
}