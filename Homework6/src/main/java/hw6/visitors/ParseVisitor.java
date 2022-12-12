package hw6.visitors;

import hw6.tokens.BracketToken;
import hw6.tokens.NumberToken;
import hw6.tokens.OperationToken;
import hw6.tokens.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParseVisitor extends TokenVisitor<List<Token>> {
    private final List<Token> polish;
    private final Stack<Token> stack;

    public ParseVisitor() {
        polish = new ArrayList<>();
        stack = new Stack<>();
    }

    @Override
    public void visit(NumberToken number) {
        polish.add(number);
    }

    @Override
    public void visit(BracketToken bracket) {
        if (bracket.bracket() == '(') {
            stack.push(bracket);
        } else {
            while (!stack.isEmpty()) {
                Token token = stack.pop();
                if (token instanceof BracketToken) {
                    if (((BracketToken) token).bracket() == '(') {
                        break;
                    } else {
                        throw new IllegalArgumentException("Wrong balance of brackets");
                    }
                }
                polish.add(token);
            }
        }
    }

    @Override
    public void visit(OperationToken operation) {
        while (!stack.isEmpty()) {
            Token token = stack.peek();
            if (token instanceof OperationToken) {
                if (((OperationToken) token).getPriority() >= operation.getPriority()) {
                    polish.add(stack.pop());
                    continue;
                }
            }
            break;
        }
        stack.push(operation);
    }

    @Override
    public List<Token> result() {
        while (!stack.isEmpty()) {
            Token token = stack.pop();
            if (token instanceof BracketToken) {
                throw new IllegalArgumentException("Unclosed bracket");
            }
            polish.add(token);
        }
        return polish;
    }
}
