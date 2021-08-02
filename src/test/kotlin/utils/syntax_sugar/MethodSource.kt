package utils.syntax_sugar

import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

fun arguments(vararg arguments: Arguments): Stream<Arguments> = Stream.of(*arguments)
fun of(vararg argument: Any): Arguments = Arguments.of(*argument)