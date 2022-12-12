package hw6.states.actions;

import hw6.states.TokenStates;
import hw6.tokens.BracketToken;
import hw6.tokens.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.List;

@RequiredArgsConstructor
public class BracketAction implements Action<TokenStates, Character> {
    private final List<Token> tokens;

    @Override
    public void execute(StateContext<TokenStates, Character> context) {
        tokens.add(new BracketToken(context.getEvent()));
    }
}
