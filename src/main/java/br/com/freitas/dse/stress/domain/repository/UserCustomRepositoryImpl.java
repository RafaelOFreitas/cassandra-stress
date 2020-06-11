package br.com.freitas.dse.stress.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

import br.com.freitas.dse.stress.domain.model.User;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {
    private final CassandraOperations cqlTemplate;

    public UserCustomRepositoryImpl(CassandraOperations cqlTemplate) {
        this.cqlTemplate = cqlTemplate;
    }

    public List<User> getQuery(Map<String, Object> filtro) {
        Select.Where select = QueryBuilder.select().from("tb_user")
                .where();

        filtro.forEach((chave, valor) -> {
            if (valor != null) {
				select.and(QueryBuilder.eq(chave, valor));
            }
        });

        List<User> users = this.cqlTemplate.select(select, User.class);
        return users;
    }
}