package hw6.tokens;

import hw6.visitors.TokenVisitor;
import lombok.Getter;

import java.util.function.BiFunction;

@Getter
public class OperationToken implements Token {
    private final Character symbol;
    private final Integer priority;
    private final BiFunction<Integer, Integer, Integer> operation;

    public OperationToken(Character symbol) {
        this.symbol = symbol;
        this.operation = switch (symbol) {
            case '+' -> (first, second) -> first + second;
            case '-' -> (first, second) -> first - second;
            case '*' -> (first, second) -> first * second;
            case '/' -> (first, second) -> first / second;
            default -> throw new IllegalArgumentException("Can't process operation %s".formatted(symbol));
        };
        this.priority = switch (symbol) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> throw new IllegalArgumentException("Can't process operation %s".formatted(symbol));
        };
    }

    @Override
    public void accept(TokenVisitor<?> visitor) {
        visitor.visit(this);
    }
}
