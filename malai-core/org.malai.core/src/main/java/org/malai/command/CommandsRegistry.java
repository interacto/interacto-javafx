/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.malai.undo.UndoCollector;
import org.malai.undo.Undoable;

/**
 * A register of commands.
 * This is a singleton. It automatically collects the executed commands when the command is executed by an instrument.
 * The register has a limited size that can be changed.
 * It can notify handler about changes in the registry.
 * @author Arnaud Blouin
 */
public final class CommandsRegistry {
	/** The singleton. */
	public static final CommandsRegistry INSTANCE = new CommandsRegistry();
	/** The saved commands. */
	private final List<Command> cmds;
	/** The commands handler. */
	private final List<CmdHandler> handlers;
	/** The max number of cleanable commands (cf. Command::getRegistrationPolicy) that can contain the register. */
	private int sizeMax;


	/**
	 * Creates and initialises a register.
	 */
	private CommandsRegistry() {
		super();
		cmds = new ArrayList<>();
		handlers = new ArrayList<>();
		sizeMax = 50;
	}

	public List<CmdHandler> getHandlers() {
		return Collections.unmodifiableList(handlers);
	}

	/**
	 * Notifies handler that a command has been executed.
	 * @param cmd The executed command.
	 */
	public void onCmdExecuted(final Command cmd) {
		if(cmd != null) {
			synchronized(handlers) {
				handlers.forEach(handler -> handler.onCmdExecuted(cmd));
			}
		}
	}


	/**
	 * Notifies handler that a command ends.
	 * @param cmd The ending command.
	 */
	public void onCmdDone(final Command cmd) {
		if(cmd != null) {
			synchronized(handlers) {
				handlers.forEach(handler -> handler.onCmdDone(cmd));
			}
		}
	}


	/**
	 * @return The stored commands. Cannot be null. Because of concurrency, you should not modify this list.
	 */
	public List<Command> getCommands() {
		return cmds;
	}


	/**
	 * Removes and flushes the commands from the register that use the given command type.
	 * @see Command ::unregisteredBy
	 * @param cmd The command that may cancels others.
	 */
	public void unregisterCommand(final Command cmd) {
		if(cmd == null) {
			return;
		}

		int i = 0;

		synchronized(cmds) {
			while(i < cmds.size()) {
				if(cmds.get(i).unregisteredBy(cmd)) {
					cmds.remove(i).flush();
				}else {
					i++;
				}
			}
		}
	}


	/**
	 * Adds a command to the register. Before being added, the given command is used to cancel commands
	 * already added. Handlers are notified of the add of the given command. If Undoable, the cmd is
	 * added to the undo collector as well.
	 * @param cmd The command to add. If null, nothing is done.
	 * @param cmdHandler The handler that produced or is associated to the command. If null, nothing is done.
	 */
	public void addCommand(final Command cmd, final CmdHandler cmdHandler) {
		synchronized(cmds) {
			if(cmd != null && !cmds.contains(cmd) &&
				(sizeMax > 0 || cmd.getRegistrationPolicy() == Command.RegistrationPolicy.UNLIMITED)) {
				unregisterCommand(cmd);

				// If there is too many commands in the register, the oldest removable command is removed and flushed.
				if(cmds.size() >= sizeMax) {
					cmds.stream().filter(command -> command.getRegistrationPolicy() != Command.RegistrationPolicy.UNLIMITED).findFirst().
						ifPresent(command -> {
							cmds.remove(command);
							command.flush();
						});
				}

				cmds.add(cmd);

				synchronized(handlers) {
					handlers.forEach(handler -> handler.onCmdAdded(cmd));
				}

				if(cmd instanceof Undoable) {
					UndoCollector.INSTANCE.add((Undoable) cmd, cmdHandler);
				}
			}
		}
	}


	/**
	 * Removes the command from the register. The cmd is then flushed.
	 * @param cmd The command to remove.
	 */
	public void removeCommand(final Command cmd) {
		if(cmd != null) {
			synchronized(cmds) {
				cmds.remove(cmd);
			}
			cmd.flush();
		}
	}


	/**
	 * Adds a command handler.
	 * @param handler The handler to add.
	 */
	public void addHandler(final CmdHandler handler) {
		if(handler != null) {
			synchronized(handlers) {
				handlers.add(handler);
			}
		}
	}


	/**
	 * Removes the given handler.
	 * @param handler The handler to remove.
	 */
	public void removeHandler(final CmdHandler handler) {
		if(handler != null) {
			synchronized(handlers) {
				handlers.remove(handler);
			}
		}
	}


	/**
	 * Removes all the command handler.
	 */
	public void removeAllHandlers() {
		synchronized(handlers) {
			handlers.clear();
		}
	}


	/**
	 * Flushes and removes all the stored commands.
	 */
	public void clear() {
		synchronized(cmds) {
			cmds.forEach(cmd -> cmd.flush());
			cmds.clear();
		}
	}


	/**
	 * Aborts the given command, i.e. the cmd is cancelled and removed from the register.
	 * Handlers are then notified. The command is finally flushed.
	 * @param cmd The command to cancel.
	 */
	public void cancelCmd(final Command cmd) {
		if(cmd != null) {
			cmd.cancel();
			synchronized(cmds) {
				cmds.remove(cmd);
			}
			synchronized(handlers) {
				handlers.forEach(handler -> handler.onCmdCancelled(cmd));
			}
			cmd.flush();
		}
	}


	/**
	 * @return The maximal number of commands that the register can contain.
	 */
	public int getSizeMax() {
		return sizeMax;
	}


	/**
	 * Changes the number of commands that the register can contain.
	 * In the case that commands have to be removed (because the new size is smaller than the old one),
	 * the necessary number of the oldest and cleanable commands (cf. Command::getRegistrationPolicy)
	 * are flushed and removed from the register.
	 * @param newSizeMax The max number of commands that can contain the register. Must be equal or greater than 0.
	 */
	public void setSizeMax(final int newSizeMax) {
		if(newSizeMax >= 0) {
			synchronized(cmds) {
				int i = 0;
				int nb = 0;
				final int toRemove = cmds.size() - newSizeMax;

				while(nb < toRemove && i < cmds.size()) {
					if(cmds.get(i).getRegistrationPolicy() != Command.RegistrationPolicy.UNLIMITED) {
						cmds.remove(i).flush();
						nb++;
					}else {
						i++;
					}
				}
			}
			sizeMax = newSizeMax;
		}
	}
}
