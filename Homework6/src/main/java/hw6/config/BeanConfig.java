package hw6.config;

import hw6.states.TokenStateMachinePersist;
import hw6.states.TokenStates;
import hw6.states.actions.BracketAction;
import hw6.states.actions.ErrorAction;
import hw6.states.actions.NumberAction;
import hw6.states.actions.OperationAction;
import hw6.states.actions.ProcessNumberAction;
import hw6.tokens.Token;
import hw6.utils.ParsedInformation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BeanConfig {

    @Bean
    public List<Token> createParsed() {
        return new ArrayList<>();
    }

    @Bean
    public StringBuilder createNumberBuilder() {
        return new StringBuilder();
    }

    @Bean
    public ParsedInformation createParseVisitor(List<Token> tokens) {
        return new ParsedInformation(tokens);
    }

    @Bean
    public BracketAction createBracketAction(List<Token> tokens) {
        return new BracketAction(tokens);
    }

    @Bean
    public NumberAction createNumberAction(StringBuilder number) {
        return new NumberAction(number);
    }

    @Bean
    public ProcessNumberAction createProcessNumberAction(
            StringBuilder number,
            List<Token> tokens
    ) {
        return new ProcessNumberAction(number, tokens);
    }

    @Bean
    public OperationAction createOperationAction(List<Token> tokens) {
        return new OperationAction(tokens);
    }

    @Bean
    public ErrorAction createErrorAction(
            StringBuilder number,
            List<Token> tokens
    ) {
        return new ErrorAction(number, tokens);
    }

    @Bean
    public StateMachinePersister<TokenStates, Character, String> persister() {
        return new DefaultStateMachinePersister<>(new TokenStateMachinePersist());
    }
}
