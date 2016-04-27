package org.haw.vsp.restopoly.services.games.entities;

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
