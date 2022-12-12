package hw6;

import hw6.request.ExpressionRequest;
import hw6.response.ExpressionResponse;
import hw6.states.TokenStates;
import hw6.tokens.Token;
import hw6.utils.ParsedInformation;
import hw6.visitors.CalculateVisitor;
import hw6.visitors.ParseVisitor;
import hw6.visitors.PrintVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Processor {
    private final StateMachineFactory<TokenStates, Character> factory;
    private final ParsedInformation information;

    @PostMapping("/v0/process")
    public ResponseEntity<ExpressionResponse> process(@RequestBody ExpressionRequest request) {
        try {
            String expression = request.getExpression() + '\n';
            var machine = factory.getStateMachine();
            for (char symbol : expression.toCharArray()) {
                boolean accepted = machine.sendEvent(symbol);
                if (!accepted) {
                    machine.sendEvent('?');
                    return ResponseEntity.badRequest().body(null);
                }
            }
            List<Token> parsed = new ParseVisitor().process(information.tokens());
            String print = new PrintVisitor().process(parsed);
            Integer result = new CalculateVisitor().process(parsed);
            return ResponseEntity
                    .ok()
                    .body(ExpressionResponse
                            .builder()
                            .expression(print)
                            .result(result)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        } finally {
            information.tokens().clear();
        }
    }
}
