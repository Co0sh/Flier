/**
 * Copyright (c) 2017 Jakub Sapalski
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package pl.betoncraft.flier.event;

import pl.betoncraft.flier.api.content.Game;
import pl.betoncraft.flier.core.MatchingEvent;

/**
 * Called when the Game ends.
 *
 * @author Jakub Sapalski
 */
public class FlierGameEndEvent extends MatchingEvent {
	
	public enum GameEndCause {
		FINISHED(0),
		ABANDONED(1),
		ABORTED(2);
		private int type;
		private GameEndCause(int type) {
			this.type = type;
		}
		/**
		 * @return the magic number used in database
		 */
		public int get() {
			return type;
		}
	}
	
	private static final String CAUSE = "cause";
	private GameEndCause cause;

	public FlierGameEndEvent(Game game, GameEndCause cause) {
		super(game);
		this.cause = cause;
		setString(CAUSE, cause.toString().toLowerCase());
	}
	
	public GameEndCause getCause() {
		return cause;
	}

}