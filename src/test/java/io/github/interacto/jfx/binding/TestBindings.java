/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
		final JfxWidgetBinding<ActivateInactivateInstruments, DnD, SrcTgtPointsData> binding =
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
