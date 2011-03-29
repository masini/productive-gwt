package org.googlecode.gwt.simplegrid.client.table.event;

import org.googlecode.gwt.simplegrid.client.table.handler.HasUnhighlightHandlers;
import org.googlecode.gwt.simplegrid.client.table.handler.UnhighlightHandler;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * Represents an unhighlight event.
 *
 * @param <V> the unhighlighted value type
 */
public class UnhighlightEvent<V> extends GwtEvent<UnhighlightHandler<V>> {

	/**
	 * Handler type.
	 */
	private static Type<UnhighlightHandler<?>> TYPE;

	/**
	 * Fires an unhighlight event on all registered handlers in the handler manager.
	 *
	 * @param <V>           the unhighlighted value type
	 * @param <S>           the event source
	 * @param source        the source of the handlers
	 * @param unhighlighted the value being unhighlighted
	 */
	public static <V, S extends HasUnhighlightHandlers<V> & HasHandlers> void fire(S source, V unhighlighted) {
		if (TYPE != null) {
			UnhighlightEvent<V> event = new UnhighlightEvent<V>(unhighlighted);
			source.fireEvent(event);
		}
	}

	/**
	 * Gets the type associated with this event.
	 *
	 * @return returns the handler type
	 */
	public static Type<UnhighlightHandler<?>> getType() {
		if (TYPE == null) {
			TYPE = new Type<UnhighlightHandler<?>>();
		}
		return TYPE;
	}

	private final V unhighlighted;

	/**
	 * Creates a new unhighlight event.
	 *
	 * @param unhighlighted value being unhighlighted
	 */
	protected UnhighlightEvent(V unhighlighted) {
		this.unhighlighted = unhighlighted;
	}

	// Because of type erasure, our static type is wildcarded,
	// yet the "real" type should use our V param.
	@SuppressWarnings("unchecked")
	@Override
	public final Type<UnhighlightHandler<V>> getAssociatedType() {
		//noinspection RedundantCast
		return (Type) TYPE;
	}

	/**
	 * @return the value being unhighlighted
	 */
	public V getUnhighlighted() {
		return unhighlighted;
	}

	@Override
	protected void dispatch(UnhighlightHandler<V> handler) {
		handler.onUnhighlight(this);
	}
}
