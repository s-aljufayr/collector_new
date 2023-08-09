package tech.wakeb.collector.valid;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {

    @NotEmpty
    private String name;
    @NotEmpty
    @Length(min = 5, max = 15)
    private String username;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Length(min = 10, max = 10)
    @Digits(integer = 10, fraction = 0)
    private String phone;
    @NotEmpty(message = "must be not null..")
    @Length(min = 8, max = 20)
    private String password;

}
