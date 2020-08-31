package br.com.roger.authenticationapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "USER" )
@Entity
public class User implements Serializable, UserDetails {
    private static final long serialVersionUID = 788124377329260517L;

    @Id
    @GeneratedValue
    private Long id;

    @Column( name = "NAME", nullable = false, length = 200 )
    private String name;

    @Column( name = "EMAIL", nullable = false, length = 100 )
    private String email;

    @Column( name = "PASSWORD", nullable = false, length = 256 )
    private String password;

    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = @JoinColumn( name = "USER_ID" ),
            inverseJoinColumns = @JoinColumn( name = "ROLE_ID" )
    )
    private List< Role > roles;

    @Override
    public Collection< ? extends GrantedAuthority > getAuthorities() {
        return this.getRoles()
                .stream()
                .map( role -> new SimpleGrantedAuthority( role.getCode() ) )
                .collect( Collectors.toList() );
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
