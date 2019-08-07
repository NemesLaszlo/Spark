package elte.rrlg.spark;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import elte.rrlg.spark.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RequestScope
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser {
    private User user;
}