package hw6.visitors;

import hw6.tokens.BracketToken;
import hw6.tokens.NumberToken;
import hw6.tokens.OperationToken;

import java.util.Stack;

public class CalculateVisitor extends TokenVisitor<Integer> {
    private final Stack<Integer> numbers;

    public CalculateVisitor() {
        this.numbers = new Stack<>();
    }

    @Override
    public void visit(NumberToken number) {
        numbers.push(number.getNumber());
    }

    @Override
    public void visit(BracketToken bracket) {
        throw new UnsupportedOperationException("Can't calculate bracket");
    }

    @Override
    public void visit(OperationToken operation) {
        if (numbers.size() < 2) {
            throw new IllegalArgumentException("Wrong number of numbers");
        }
        Integer second = numbers.pop();
        Integer first = numbers.pop();
        numbers.push(operation.getOperation().apply(first, second));
    }

    @Override
    public Integer result() {
        if (numbers.size() != 1) {
            throw new IllegalArgumentException("Wrong number of numbers");
        }
        return numbers.pop();
    }
}
