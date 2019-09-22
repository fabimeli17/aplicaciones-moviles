package com.example.experttire.data;

import com.example.experttire.LocalesBean;
import com.example.experttire.data.model.LoggedInUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            /*
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
             */
            LoggedInUser fakeUser = autenticar(username,password);
            if (fakeUser == null){
                return new Result.Error(new IOException("Error logging in"));
            }else {
                return new Result.Success<>(fakeUser);
            }



        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    private LoggedInUser autenticar(String usuario, String clave){
        ArrayList<LoggedInUser>  usuarios = (ArrayList<LoggedInUser> ) buscar();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=0; i <usuarios.size();i++ ){
            if (usuario.equals(usuarios.get(i).getCorreo()) && clave.equals(usuarios.get(i).getClave())){
                return usuarios.get(i);
            }
        }
        return null;
    }

    private List<LoggedInUser> listaUsuarios(){
        List<LoggedInUser> usuarioExistentes = new ArrayList<LoggedInUser>();

        LoggedInUser usuario1 = new LoggedInUser("1","Diego Mori","123456","diego.mori.rodriguez@gmail.com");
        usuarioExistentes.add(usuario1);
        LoggedInUser usuario2 = new LoggedInUser("2","Fabiola Diaz","123456","fabimeli17@gmail.com");
        usuarioExistentes.add(usuario2);
        return  usuarioExistentes;
    }

    public List<LoggedInUser> buscar(){

        OkHttpClient client = new OkHttpClient();
        final List<LoggedInUser> lista = new ArrayList<LoggedInUser>();
        Request request = new Request.Builder()
                .url("http://experttire.atwebpages.com/usuariosService.php/usuarios")
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    String cadenaJson = response.body().string();

                    Gson gson = new Gson();
                    Type stringStringMap = new TypeToken<ArrayList<Map<String, Object>>>() { }.getType();

                    final ArrayList<Map<String, Object>> retorno = gson.fromJson(cadenaJson, stringStringMap);

                    for (Map<String, Object> x : retorno) {
                        LoggedInUser loggin = new LoggedInUser();
                        loggin.setUserId((String) x.get("codigo"));
                        loggin.setDisplayName((String) x.get("nombres"));
                        loggin.setClave((String) x.get("clave"));
                        loggin.setCorreo((String) x.get("correo"));

                        lista.add(loggin);
                    }

                }
            }
        });

        return lista;
    }




}
