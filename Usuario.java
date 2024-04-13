import java.util.List;
import java.util.ArrayList;

public class Usuario {
    // Atributos
    protected String id;
    protected String nome;
    protected String username;
    protected String senha;
    protected boolean isAdmin;

    // Construtor
    public Usuario(String id, String nome, String username, String senha, boolean isAdmin) {
        this.id = id;
        this.nome = nome;
        this.username = username;
        this.senha = senha;
        this.isAdmin = isAdmin;
    }

    // Métodos

    // Adicionar usuário
    public static void adicionarUser(List<Usuario> listaUsuarios, Usuario user, Usuario admin) {
        if (admin.isAdmin) {
            listaUsuarios.add(user);
            System.out.println("Usuário adicionado com sucesso!");
        } else {
            System.out.println("Você não tem permissão para adicionar usuários.");
        }
    }

    // Editar usuário
    public static void editarUser(List<Usuario> listaUsuarios, Usuario user, Usuario admin) {
        if (admin.isAdmin) {
            // Aqui você implementaria a lógica para editar um usuário
            System.out.println("Usuário editado com sucesso!");
        } else {
            System.out.println("Você não tem permissão para editar usuários.");
        }
    }

    // Excluir usuário
    public static void excluirUser(List<Usuario> listaUsuarios, Usuario user, Usuario admin) {
        if (admin.isAdmin) {
            listaUsuarios.remove(user);
            System.out.println("Usuário excluído com sucesso!");
        } else {
            System.out.println("Você não tem permissão para excluir usuários.");
        }
    }

    // Atribuir permissão de administrador
    public static void atribuirPermissao(Usuario user, boolean isAdmin, Usuario admin) {
        if (admin.isAdmin) {
            user.isAdmin = isAdmin;
            System.out.println("Permissões atualizadas com sucesso!");
        } else {
            System.out.println("Você não tem permissão para atribuir permissões.");
        }
    }

    // Consultar usuário
    public static void consultaUser(List<Usuario> listaUsuarios, Usuario user) {
        boolean encontrado = false;
        for (Usuario u : listaUsuarios) {
            if (u.id.equals(user.id)) {
                System.out.println("Usuário encontrado:");
                System.out.println("ID: " + u.id);
                System.out.println("Nome: " + u.nome);
                System.out.println("Username: " + u.username);
                System.out.println("Admin: " + (u.isAdmin ? "Sim" : "Não"));
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Usuário não encontrado.");
        }
    }
}
