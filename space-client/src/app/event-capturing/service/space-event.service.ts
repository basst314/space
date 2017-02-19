import { Injectable } from '@angular/core';
import { WorldEventType } from '../../domain/web-socket-event';
import { Observable, Subject } from 'rxjs';

/* the keycode for the space-key in KeyboardEvent */
export const spaceKeyCode: number = 32;

/**
 * Aggregates simple keyboard events to logical SPACE/DOUBLE_SPACE/TRIPLE_SPACE events
 * and publishes them through an RxJS.Observable
 */
@Injectable()
export class SpaceEventService {

  /**
   * Maps the keyboard events of a single capturing time frame to a logical space event.
   * @param events
   * @return the logical space-event representing the raw keyboard events
   * @private
   */
  private static _mapToSpaceEvent(events: KeyboardEvent[]): WorldEventType {
    let spaceEvent: WorldEventType;
    switch (events.length) {
      case 1:
        spaceEvent = 'SPACE';
        break;
      case 2:
        spaceEvent = 'DOUBLE_SPACE';
        break;
      case 3:
        spaceEvent = 'TRIPPLE_SPACE';
        break;
      default:
        console.warn("received " + events.length +
          " keyboard events for timeframe, assuming triple_space");
        spaceEvent = 'TRIPPLE_SPACE';
        break;
    }

    console.debug("captured", spaceEvent);
    return spaceEvent;
  }

  /** size of the capturing time frame in ms */
  private _captureTime: number = 300;

  /** outbound queue for publishing logical space-events */
  private _spaceEvents: Subject<WorldEventType> = new Subject<WorldEventType>();
  /** inbound queue for processing raw space-key events */
  private _keyboardEvents: Subject<KeyboardEvent> = new Subject<KeyboardEvent>();

  /** emits when a new capture time frame should be started */
  private _startCapture: Subject<void> = new Subject<void>();
  /** emits when a capture time frame has elapsed */
  private _closeCapture: Subject<void> = new Subject<void>();
  /** whether event capturing is currently active */
  private _capturingActive: boolean = false;

  constructor() {
    /*
     * Configure Event Pipeline
     */
    // start capturing on keyboard events
    this._keyboardEvents.subscribe(() => {
      if (!this._capturingActive) {
        this._startCapture.next();
      }
    });

    // update capturing flag on events
    this._startCapture.subscribe(() => {
      this._capturingActive = true;
    });
    this._closeCapture.subscribe(() => {
      this._capturingActive = false;
    });

    // close event capturing after fixed capture time
    this._startCapture
      .delay(this._captureTime)
      .subscribe(() => {
        this._closeCapture.next();
      });

    // aggregate space keyboard events for each capturing time frame
    this._keyboardEvents
      .filter((event) => event && event.keyCode === spaceKeyCode)
      .bufferToggle(this._startCapture.asObservable(), () => this._closeCapture.asObservable())
      .map(SpaceEventService._mapToSpaceEvent)
      .subscribe((spaceEvent) => {
        this._spaceEvents.next(spaceEvent);
      });
  }

  /**
   * Notify this service about a game control keyboard-event
   * @param event of the pressed key
   */
  public notifyControlEvent(event: KeyboardEvent) {
    this._keyboardEvents.next(event);
  }

  get spaceEvents(): Observable<WorldEventType> {
    return this._spaceEvents.asObservable();
  }

}
