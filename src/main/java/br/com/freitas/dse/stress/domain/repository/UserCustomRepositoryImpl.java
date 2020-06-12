package br.com.freitas.dse.stress.domain.repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.querybuilder.Ordering;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

import br.com.freitas.dse.stress.domain.model.User;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {
    private final CassandraOperations cqlTemplate;

    public UserCustomRepositoryImpl(CassandraOperations cqlTemplate) {
        this.cqlTemplate = cqlTemplate;
    }

    public List<User> getQuery(Map<String, Object> filtro, String order) {
        Select.Where select = QueryBuilder.select().from("tb_user")
                .where();

        filtro.forEach((chave, valor) -> {
            if (valor != null) {
                if (chave.equals("birthday_ini")) {
                    select.and(QueryBuilder.gte("birthday", this.convertToDate(valor)));
                    return;
                }

                if (chave.equals("birthday_end")) {
                    select.and(QueryBuilder.lte("birthday", this.convertToDate(valor)));
                    return;
                }

                select.and(QueryBuilder.eq(chave, valor));
               
            }
        });
        
        
        select.orderBy(QueryBuilder.asc(order));
        List<User> s = this.cqlTemplate.select(select, User.class);
		return s;
    }

    private Date convertToDate(Object Object) {
        LocalDate date = (LocalDate) Object;

        return Date.from(date.atStartOfDay()
                .atZone(ZoneId.of("GMT"))
                .toInstant());
    }
    
}