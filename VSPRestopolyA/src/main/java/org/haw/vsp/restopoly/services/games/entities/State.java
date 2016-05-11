package org.haw.vsp.restopoly.services.games.entities;

/**
 * Describes the possible states of a game. Can be either "Registration", "Running" or "Finished".
 * @author abt434
 *
 */
public enum State {
	REGISTRATION {
		@Override
		public String toString() {
			return "registration";
		}

		@Override
		public State nextStatus() {
			return RUNNING;
		}
	},
	RUNNING {
		@Override
		public String toString() {
			return "running";
		}

		@Override
		public State nextStatus() {
			return FINISHED;
		}
	},
	FINISHED {
		@Override
		public String toString() {
			return "finished";
		}

		@Override
		public State nextStatus() {
			return FINISHED;
		}
	};
	
	public abstract State nextStatus();
}
