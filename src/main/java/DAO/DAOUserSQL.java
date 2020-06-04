package DAO;

import beans.User;
import db.ConnDetails;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * http://localhost:8080/hello
 * http://localhost:8080/hello?x=5
 */
public class DAOUserSQL implements DAO<User> {

    private final Connection conn;
    private final String SQL_getAll = "select * from users";
    private final String SQL_get    = "select * from users where id = ?";
    private final String SQL_put    = "insert into users (id, username, password,created_at, pic) values (?, ?, ?,?,?)";
    private final String SQL_delete = "delete from users where id = ?";

    public DAOUserSQL(Connection conn) {
        this.conn = conn;
    }

    @SneakyThrows
    @Override
    public List<User> getAll() {
        Class.forName(ConnDetails.dbDriver);
        Connection conn = DriverManager.getConnection(ConnDetails.url,
                ConnDetails.username,
                ConnDetails.password);
        PreparedStatement stmt = conn.prepareStatement(SQL_getAll);
        ResultSet rset = stmt.executeQuery();
        ArrayList<User> data = new ArrayList<>();
        while (rset.next()) {
            User s = new User(
                    rset.getString("username"),
                    rset.getString("password"),
                    rset.getString("pic"),
                    rset.getDate("created_at"),
                    rset.getString("id")


            );
            data.add(s);
        }
        return data;
    }

    @Override
    public List<User> getBy(Predicate<User> p) {
        return getAll().stream().filter(p).collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public Optional<User> get(String id) {
        Class.forName(ConnDetails.dbDriver);
        Connection conn = DriverManager.getConnection(ConnDetails.url,
                ConnDetails.username,
                ConnDetails.password);
        PreparedStatement stmt = conn.prepareStatement(SQL_get);
        stmt.setString(1, id);
        ResultSet rset = stmt.executeQuery();
        return !rset.next() ? Optional.empty() : Optional.of(
                new User(
                        rset.getString("username"),
                        rset.getString("password"),
                        rset.getString("pic"),
                        rset.getDate("created_at"),
                        rset.getString("id")

                )
        );
    }

    @SneakyThrows
    @Override
    public void put(User user) {
        Class.forName(ConnDetails.dbDriver);
        Connection conn = DriverManager.getConnection(ConnDetails.url,
                ConnDetails.username,
                ConnDetails.password);
        PreparedStatement stmt = conn.prepareStatement(SQL_put);
        stmt.setString(1, user.getId());
        stmt.setString(2, user.getUsername());
        stmt.setString(3, user.getPassword());
        stmt.setDate(4, user.getDate());
        stmt.setString(5, user.getProfile());
        stmt.execute();
    }

    @SneakyThrows
    @Override
    public void delete(String id) {
        PreparedStatement stmt = conn.prepareStatement(SQL_delete);
        stmt.setString(1, id);
        stmt.execute();
    }
}
