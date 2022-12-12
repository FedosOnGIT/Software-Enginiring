package hw6.states;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

import java.util.HashMap;

public class TokenStateMachinePersist implements StateMachinePersist<TokenStates, Character, String> {
    private final HashMap<String, StateMachineContext<TokenStates, Character>> contexts = new HashMap<>();

    @Override
    public void write(StateMachineContext<TokenStates, Character> context, String contextObj) {
        contexts.put(contextObj, context);
    }

    @Override
    public StateMachineContext<TokenStates, Character> read(String contextObj) {
        return contexts.get(contextObj);
    }
}
