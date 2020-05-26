package DAO;

import beans.Message;
import beans.Message;
import beans.User;
import lombok.SneakyThrows;

import java.sql.Connection;
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
public class DAOMessageSQL implements DAO<Message> {

    private final Connection conn;
    private final String SQL_getAll = "select * from messages";
    private final String SQL_get    = "select * from messages where id = ?";
    private final String SQL_put    = "insert into messages (who, whom, message) values (?, ?, ?)";
    private final String SQL_delete = "delete from messages where id = ?";

    public DAOMessageSQL(Connection conn) {
        this.conn = conn;
    }

    @SneakyThrows
    @Override
    public List<Message> getAll() {
        PreparedStatement stmt = conn.prepareStatement(SQL_getAll);
        ResultSet rset = stmt.executeQuery();
        ArrayList<Message> data = new ArrayList<>();
        while (rset.next()) {
            Message s = new Message(
                    rset.getString("who"),
                    rset.getString("whom"),
                    rset.getString("message"),
                    rset.getDate("date")
            );
            data.add(s);
        }
        return data;
    }

    @Override
    public List<Message> getBy(Predicate<Message> p) {
        return getAll().stream().filter(p).collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public Optional<Message> get(String id) {
        PreparedStatement stmt = conn.prepareStatement(SQL_get);
        stmt.setString(1, id);
        ResultSet rset = stmt.executeQuery();
        return !rset.next() ? Optional.empty() : Optional.of(new Message(
                        rset.getString("who"),
                        rset.getString("whom"),
                        rset.getString("message"),
                        rset.getDate("date")

                )
        );
    }

    @SneakyThrows
    @Override
    public void put(Message message) {
        PreparedStatement stmt = conn.prepareStatement(SQL_put);
        stmt.setString(1, message.getWho());
        stmt.setString(2, message.getWhom());
        stmt.setString(3, message.getMessage());
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
