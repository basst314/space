import { Component, OnInit } from '@angular/core';
import { SpaceWorld } from '../domain/SpaceWorld';
import { SpaceWebSocketService } from './service/space-web-socket.service';
import { WorldEventType, WebSocketEvent } from '../domain/WebSocketEvent';

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

  constructor(private spaceWebSocketService: SpaceWebSocketService) {
    this.spaceWebSocketService.messages.subscribe((event: WebSocketEvent) => {
      this.log("received event: " + event.worldEventType);
      if (event.worldEventType === 'UPDATE') {
        this.world = event.world;
      } else {
        this.log("ERROR: unknown event");
      }
    });
  }

  public ngOnInit(): void {
    // this.startGame();
  }

  public stepWorld(): void {
    this.spaceWebSocketService.messages.next(this.getEvent('STEP'));
  }

  public space(): void {
    this.spaceWebSocketService.messages.next(this.getEvent('SPACE'));
  }

  public doubleSpace(): void {
    this.spaceWebSocketService.messages.next(this.getEvent('DOUBLE_SPACE'));
  }

  public tripleSpace(): void {
    this.spaceWebSocketService.messages.next(this.getEvent('TRIPPLE_SPACE'));
  }

  public startGame(): void {
    this.log("starting game... playerId: " + this.playerId + ", worldId: " + this.worldId);
    this.spaceWebSocketService.messages.next(this.getEvent('START'));
  }

  public stopGame(): void {
    this.spaceWebSocketService.messages.next(this.getEvent('STOP'));
  }

  private log(msg: string) {
    this.msgs.unshift(msg + "\n");
  }

  get messages(): string[] {
    return this.msgs;
  }

  private getEvent(type: WorldEventType): WebSocketEvent {
    let event = {
      playerId: this.playerId,
      worldId: this.worldId,
      worldEventType: type
    };
    console.debug("sending event:", event);

    return event;
  }

}
