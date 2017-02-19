import { Component, OnInit } from '@angular/core';
import { SpaceWorld } from '../domain/space-world';
import { SpaceWebSocketService } from './service/space-web-socket.service';
import { WorldEventType, WebSocketEvent } from '../domain/web-socket-event';
import { SpaceEventService } from '../event-capturing/service/space-event.service';

/**
 * Space WebSocket-Client
 * Created by basst314 on 02.01.2017.
 */
@Component({
  selector: 'web-socket-client',
  templateUrl: 'web-socket-client.component.html',
  styleUrls: [
    'web-socket-client.component.css'
  ]
})
export class WebSocketClientComponent implements OnInit {
  public playerId: number = 0;
  public worldId: number = 0;
  public world: SpaceWorld;
  public msgs: string[] = [];

  constructor(private _spaceWebSocketService: SpaceWebSocketService,
              private _spaceEventService: SpaceEventService) {
  }

  public ngOnInit(): void {
    // push space-events to webSocket-API
    this._spaceEventService.spaceEvents.subscribe((event: WorldEventType) => {
      this.log('sending event: ' + event);
      this._spaceWebSocketService.messages.next(this.buildWebSocketEvent(event));
    });

    // apply incoming gameWorld updates
    this._spaceWebSocketService.messages.subscribe((event: WebSocketEvent) => {
      this.log("received event: " + event.worldEventType);
      if (event.worldEventType === 'UPDATE') {
        this.world = event.world;
      } else {
        this.log("ERROR: unknown event");
      }
    });
  }

  public stepWorld(): void {
    this._spaceWebSocketService.messages.next(this.buildWebSocketEvent('STEP'));
  }

  public space(): void {
    this._spaceWebSocketService.messages.next(this.buildWebSocketEvent('SPACE'));
  }

  public doubleSpace(): void {
    this._spaceWebSocketService.messages.next(this.buildWebSocketEvent('DOUBLE_SPACE'));
  }

  public tripleSpace(): void {
    this._spaceWebSocketService.messages.next(this.buildWebSocketEvent('TRIPPLE_SPACE'));
  }

  public startGame(): void {
    this.log("starting game... playerId: " + this.playerId + ", worldId: " + this.worldId);
    this._spaceWebSocketService.messages.next(this.buildWebSocketEvent('START'));
  }

  public stopGame(): void {
    this._spaceWebSocketService.messages.next(this.buildWebSocketEvent('STOP'));
  }

  private log(msg: string) {
    this.msgs.unshift(msg + "\n");
  }

  get messages(): string[] {
    return this.msgs;
  }

  private buildWebSocketEvent(type: WorldEventType): WebSocketEvent {
    let event = {
      playerId: this.playerId,
      worldId: this.worldId,
      worldEventType: type
    };
    console.debug("sending event:", event);

    return event;
  }

}
