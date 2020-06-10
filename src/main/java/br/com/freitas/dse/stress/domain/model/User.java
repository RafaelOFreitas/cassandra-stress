package br.com.freitas.dse.stress.domain.model;

import com.datastax.driver.core.DataType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Data
@Table("tb_user")
public class User {
    @EqualsAndHashCode.Include
    @PrimaryKeyColumn(name = "id", ordinal = 2, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
    @CassandraType(type = DataType.Name.UUID)
    private UUID id;

    @Column("name")
    private String name;

    @Column(value = "gender")
    private String gender;

    @Column(value = "birthday")
    private Date birthday;

    @Column(value = "city")
    private String city;
}