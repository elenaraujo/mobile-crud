package com.example.cadastroanimal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

// Arquivo com os métodos de criação (inserir()), leitura (obterTodos()), atualização (atualizar())
// e deleção (excluir()) dos dados no banco de dados.
public class AnimalDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public AnimalDAO(Context context) {
        conexao = new Conexao(context);
    }

    public void open() throws SQLException {
        banco = conexao.getWritableDatabase();
    }

    public void close() {
        conexao.close();
    }

    public long inserir(Animal animal) {
        ContentValues values = new ContentValues();
        values.put("especie", animal.getEspecie());
        values.put("raca", animal.getRaca());
        values.put("nome", animal.getNome());
        values.put("peso", animal.getPeso());
        values.put("idade", animal.getIdade());
        return banco.insert("animal", null, values);
    }

    public List<Animal> obterTodos() {
        List<Animal> animais = new ArrayList<>();
        Cursor cursor = banco.query("animal", new String[]{"id", "especie", "raca", "nome", "peso", "idade"},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            Animal animal = new Animal();
            animal.setId(cursor.getLong(0));
            animal.setEspecie(cursor.getString(1));
            animal.setRaca(cursor.getString(2));
            animal.setNome(cursor.getString(3));
            animal.setPeso(cursor.getFloat(4));
            animal.setIdade(cursor.getShort(5));
            animais.add(animal);
        }
        cursor.close();
        return animais;
    }

    public void excluir(Animal animal) {
        banco.delete("animal", "id = ?", new String[]{animal.getId().toString()});
    }

    public void atualizar(Animal animal) {
        ContentValues values = new ContentValues();
        values.put("especie", animal.getEspecie());
        values.put("raca", animal.getRaca());
        values.put("nome", animal.getNome());
        values.put("peso", animal.getPeso());
        values.put("idade", animal.getIdade());
        banco.update("animal", values,
                "id = ?", new String[]{animal.getId().toString()});
    }
}
