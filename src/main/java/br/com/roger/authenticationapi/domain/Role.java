package br.com.roger.authenticationapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "ROLE" )
@Entity
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @Column( name = "CODE", length = 25, nullable = false )
    private String code;

    @Column( name = "NAME", length = 200, nullable = false )
    private String name;

    @ManyToMany( mappedBy = "roles" )
    private List< User > users;
}
