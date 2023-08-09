package tech.wakeb.collector.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Parent;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.NumberFormat;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "disks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disk {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 25)
    @NotEmpty(message = "Name can not be empty...")
    @jakarta.validation.constraints.Pattern(regexp = "[a-z]", message = "just small litter...")
    private String name;
    //Pattern pattern = Pattern.compile("[a-z]");
    @Column(nullable = false, length = 4)
    @NotEmpty(message = "Host can not be empty...")
    @Pattern(regexp = "[0 - 9]", message = "just integers...")
    private String host;
    @Column(nullable = false, length = 4)
    @NotEmpty(message = "Port can not be empty...")
    @Pattern(regexp = "[0 - 9]", message = "just integers...")
    private String port;
    @Column(nullable = false, length = 25)
    @NotEmpty(message = "Username can not be empty...")
    @Pattern(regexp = "[0 - 9]", message = "just integers...")
    private String username;
    @Column(nullable = false, length = 25)
    @NotEmpty(message = "Password can not be empty...")
    @Pattern(regexp = "[0 - 9]", message = "just integers...")
    private String password;
    private Boolean active;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToOne
    private User user;

    @OneToMany
    @JoinColumn(name = "disk_id")
    private Collection<Task> tasks;
}
