package io.github.interacto.jfx.binding;

import io.github.interacto.jfx.command.ActivateInactivateInstruments;
import io.github.interacto.jfx.interaction.library.DnD;
import io.github.interacto.jfx.interaction.library.SrcTgtPointsData;
import io.github.interacto.logging.LogLevel;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class TestBindings {
	@Test
	void testBinder() {
		final JfXWidgetBinding<ActivateInactivateInstruments, DnD, SrcTgtPointsData> binding =
			Bindings.nodeBinder()
				.continuousExecution()
				.on(new Button())
				.on(new CheckBox())
				.on(FXCollections.observableArrayList(new ToggleButton(), new CheckBox()))
				.log(LogLevel.BINDING)
				.strictStart()
				.throttle(11L)
				.when(() -> true)
				.usingInteraction(DnD::new)
				.cancel(i -> { })
				.endOrCancel(i -> { })
				.when(i -> true)
				.toProduce(ActivateInactivateInstruments::new)
				.end(c -> { })
				.then(c -> { })
				.bind();

		assertThat(binding).isNotNull();
		assertThat(binding.isContinuousCmdExec()).isTrue();
		assertThat(binding.isActivated()).isTrue();
		assertThat(binding.getInteraction()).isInstanceOf(DnD.class);
		assertThat(binding.produces()).isNotNull();
	}
}
