package hw6.tokens;

import hw6.visitors.TokenVisitor;

public interface Token {
    void accept(TokenVisitor<?> visitor);
}
