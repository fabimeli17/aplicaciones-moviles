package com.example.experttire.data;

import com.example.experttire.data.model.LoggedInUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        ArrayList<LoggedInUser>  usuarios = (ArrayList<LoggedInUser> ) listaUsuarios();
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




}
